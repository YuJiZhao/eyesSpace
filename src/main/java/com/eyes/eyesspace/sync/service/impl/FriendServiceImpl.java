package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesAuth.thrift.TTClientPool;
import com.eyes.eyesAuth.thrift.config.TTSocket;
import com.eyes.eyesAuth.thrift.generate.common.TTCustomException;
import com.eyes.eyesAuth.thrift.generate.user.UserInfoReturnee;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.service.email.EmailSender;
import com.eyes.eyesspace.constant.FriendChainConstant;
import com.eyes.eyesspace.persistent.dto.FriendChainApplyDTO;
import com.eyes.eyesspace.persistent.mapper.ContextMapper;
import com.eyes.eyesspace.persistent.mapper.FriendMapper;
import com.eyes.eyesspace.persistent.po.ContextPO;
import com.eyes.eyesspace.persistent.po.FriendOperatePO;
import com.eyes.eyesspace.sync.common.result.ResultCode;
import com.eyes.eyesspace.sync.convert.FriendConvert;
import com.eyes.eyesspace.sync.model.dto.FriendListDTO;
import com.eyes.eyesspace.sync.model.request.FriendChainApplyRequest;
import com.eyes.eyesspace.sync.model.request.FriendChainApprovalRequest;
import com.eyes.eyesspace.sync.model.request.FriendChainRefuseRequest;
import com.eyes.eyesspace.sync.model.vo.FriendListInfoVO;
import com.eyes.eyesspace.sync.model.vo.FriendListVO;
import com.eyes.eyesspace.sync.model.vo.FriendPreambleVO;
import com.eyes.eyesspace.sync.service.FriendService;
import com.eyes.eyesspace.utils.email.EmailFriendApplyNotice;
import com.eyes.eyesspace.utils.email.EmailFriendApproval;
import com.eyes.eyesspace.utils.email.EmailFriendInvalid;
import com.eyes.eyesspace.utils.email.EmailFriendRefuse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eyesYeager
 * @date 2023/6/1 9:20
 */
@RefreshScope
@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
  private static final String APPLY_SUBJECT = " | 友链申请";

  private static final String APPROVAL_SUBJECT = " | 友链通过";

  private static final String REFUSE_SUBJECT = " | 审核失败";

  private static final String INVALID_SUBJECT = " | 友链失效";

  private static final int WAIT_TIME = 48;  // 单位为h

  private static final List<Integer> PREAMBLE_ID = Collections.singletonList(10);

  @Value("${app.name-cn}")
  private String appName;

  @Value("${app.author-cn}")
  private String authorCN;

  @Value("${app.author-email}")
  private String authorEmail;

  @Value("${path.url.site}")
  private String siteUrl;

  @Value("${path.url.friend}")
  private String friendUrl;

  @Value("${app.introduce}")
  private String introduce;

  @Value("${app.author-avatar}")
  private String avatar;

  private final FriendMapper friendMapper;

  private final ContextMapper contextMapper;

  private final EmailSender emailSender;

  private final TTClientPool ttClientPool;

  @Resource
  private ExecutorService singleThreadPool;

  @Resource
  private ScheduledExecutorService scheduledThreadPool;

  public FriendServiceImpl(FriendMapper friendMapper, ContextMapper contextMapper, EmailSender emailSender, TTClientPool ttClientPool) {
    this.friendMapper = friendMapper;
    this.contextMapper = contextMapper;
    this.emailSender = emailSender;
    this.ttClientPool = ttClientPool;
  }

  @Override
  public void applyFriendChain(FriendChainApplyRequest friendChainApplyRequest) throws CustomException {
    // 校验是否已经提交过申请
    Long uid = UserInfoHolder.getUid();
    Integer status = friendMapper.getFriendStatusByUid(uid);
    if (Objects.nonNull(status)) {
      switch (status) {
        case 0:
          throw new CustomException("每位用户只能提交一条友链");
        case 1:
          throw new CustomException("您的友链正在审核中");
        case 2:
          throw new CustomException("您的友链已失效，请联系站长恢复");
      }
    }

    // 获取用户信息
    UserInfoReturnee userInfo;
    TTSocket ttSocket = null;
    try {
      ttSocket = ttClientPool.getConnect();
      userInfo = ttSocket.getUserClient().getUserInfo(uid);
      ttClientPool.returnConnection(ttSocket);
    } catch (TTCustomException e) {
      throw new CustomException(e.getMsg());
    } catch (Exception e) {
      ttClientPool.invalidateObject(ttSocket);
      e.printStackTrace();
      throw new CustomException(ResultCode.FAILURE);
    }

    // 插入友链信息
    FriendChainApplyDTO friendChainApplyDTO = FriendConvert.INSTANCE.friendChainApplyRequest2DTO(friendChainApplyRequest);
    friendChainApplyDTO.setUid(uid);
    friendChainApplyDTO.setEmail(userInfo.getEmail());
    if (!friendMapper.addFriendChain(friendChainApplyDTO)) {
      throw new CustomException("申请失败，请稍后重试");
    }

    // 通知站长
    singleThreadPool.execute(() -> emailSender.sendMail(
        authorEmail,
        appName + APPLY_SUBJECT,
        new EmailFriendApplyNotice(
            appName + APPLY_SUBJECT,
            authorCN,
            friendChainApplyDTO
        ).getTemplate()
    ));
  }

  @Override
  public FriendListInfoVO getFriendListData() {
    List<Integer> statusList = friendMapper.getFriendStatusList();
    Map<Integer, Long> statusMap = statusList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return new FriendListInfoVO(
        statusMap.getOrDefault(FriendChainConstant.NORMAL, 0L),
        statusMap.getOrDefault(FriendChainConstant.VERIFYING, 0L),
        statusMap.getOrDefault(FriendChainConstant.INVALID, 0L) + statusMap.getOrDefault(FriendChainConstant.STAGING, 0L)
    );
  }

  @Override
  public FriendListVO getFriendList(Integer page, int pageSize) {
    List<Integer> statusList = Arrays.asList(FriendChainConstant.NORMAL, FriendChainConstant.INVALID, FriendChainConstant.STAGING);
    List<FriendListDTO> friendListDTOs = friendMapper.getFriendList((page - 1) * pageSize, pageSize, statusList);
    Integer total = friendMapper.getShowTotalFriend(statusList);
    return new FriendListVO(total, friendListDTOs);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void approvalFriendChain(FriendChainApprovalRequest friendChainApprovalRequest) throws CustomException {
    // 校验可行性
    FriendOperatePO friendOperatePO = friendMapper.getFriendApprovalInfo(friendChainApprovalRequest.getId());
    if (Objects.isNull(friendOperatePO)) {
      throw new CustomException("id为" + friendChainApprovalRequest.getId() + "的友链不存在");
    }
    if (FriendChainConstant.VERIFYING != friendOperatePO.getStatus()) {
      throw new CustomException("id为" + friendChainApprovalRequest.getId() + "的友链不处于审核状态！status:" + friendOperatePO.getStatus());
    }

    // 更改状态
    if (!friendMapper.approvalFriendChain(friendChainApprovalRequest.getId(), FriendChainConstant.NORMAL)) {
      throw new CustomException("审批失败");
    }

    // 通知友链站长
    singleThreadPool.execute(() -> emailSender.sendMail(
        friendOperatePO.getEmail(),
        appName + APPROVAL_SUBJECT,
        new EmailFriendApproval(
            appName,
            APPROVAL_SUBJECT,
            authorCN,
            siteUrl,
            avatar,
            friendUrl,
            introduce,
            friendOperatePO,
            friendChainApprovalRequest.getNotes()
        ).getTemplate()
    ));
  }

  @Override
  public void refuseFriendChain(FriendChainRefuseRequest friendChainRefuseRequest) throws CustomException {
    // 校验可行性
    FriendOperatePO friendOperatePO = friendMapper.getFriendApprovalInfo(friendChainRefuseRequest.getId());
    if (Objects.isNull(friendOperatePO)) {
      throw new CustomException("id为" + friendChainRefuseRequest.getId() + "的友链不存在");
    }

    // 更改状态
    if (friendChainRefuseRequest.getNeedDelete()) {
      if (!friendMapper.changeFriendChainStatus(friendChainRefuseRequest.getId(), FriendChainConstant.DELETED)) {
        throw new CustomException("状态更改失败");
      }
      return;
    }

    // 邮件通知
    singleThreadPool.execute(() -> emailSender.sendMail(
        friendOperatePO.getEmail(),
        appName + REFUSE_SUBJECT,
        new EmailFriendRefuse(
            appName + REFUSE_SUBJECT,
            authorCN,
            friendChainRefuseRequest.getNotes()
        ).getTemplate()
    ));
  }

  @Override
  public void noticeInvalidFriendChain(Integer id) throws CustomException {
    // 校验可行性
    FriendOperatePO friendOperatePO = friendMapper.getFriendApprovalInfo(id);
    if (Objects.isNull(friendOperatePO)) {
      throw new CustomException("id为" + id + "的友链不存在");
    }

    // 更改状态
    if (!friendMapper.setInvalidFriendChain(id, FriendChainConstant.STAGING)) {
      throw new CustomException("状态更新失败");
    }

    // 设置延时任务
    scheduledThreadPool.schedule(() -> {
      // 校验操作可行性
      FriendOperatePO nowFriendOperatePO = friendMapper.getFriendApprovalInfo(id);
      if (Objects.isNull(nowFriendOperatePO)) {
        log.error("id为{}的友链不存在", id);
      }
      if (FriendChainConstant.STAGING != nowFriendOperatePO.getStatus()) {
        // 如果不是暂存状态，说明友链站长已经回复了，这时候的状态由站长手动修改
        return;
      }
      // 删除友链
      if (!friendMapper.changeFriendChainStatus(id, FriendChainConstant.DELETED)) {
        log.error("id为{}的友链状态更改失败", id);
      }
    }, WAIT_TIME, TimeUnit.HOURS);

    // 邮件通知
    singleThreadPool.execute(() -> emailSender.sendMail(
        friendOperatePO.getEmail(),
        appName + INVALID_SUBJECT,
        new EmailFriendInvalid(
            appName + INVALID_SUBJECT,
            authorCN,
            WAIT_TIME,
            friendOperatePO.getName(),
            friendOperatePO.getAddress()
        ).getTemplate()
    ));
  }

  @Override
  public FriendPreambleVO getFriendPreamble() {
    List<ContextPO> context = contextMapper.getContext(PREAMBLE_ID);
    return new FriendPreambleVO(context.get(0).getValue());
  }
}
