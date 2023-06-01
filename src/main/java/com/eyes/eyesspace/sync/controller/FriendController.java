package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesspace.sync.model.request.FriendChainApplyRequest;
import com.eyes.eyesspace.sync.model.request.FriendChainApprovalRequest;
import com.eyes.eyesspace.sync.model.request.FriendChainRefuseRequest;
import com.eyes.eyesspace.sync.model.vo.FriendListInfoVO;
import com.eyes.eyesspace.sync.model.vo.FriendListVO;
import com.eyes.eyesspace.sync.model.vo.FriendPreambleVO;
import com.eyes.eyesspace.sync.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eyesYeager
 * @date 2023/5/31 11:48
 */
@Api(tags = "友链模块")
@Validated
@RestController
@RequestMapping("/friend")
public class FriendController {
  // 友链列表单页大小
  private static final int FRIEND_PAGE_SIZE = 8;

  private final FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  @ApiOperation("友链申请")
  @Permission(PermissionEnum.USER)
  @Limiter
  @PostMapping("/applyFriendChain")
  public Result<Void> applyFriendChain(@Validated @RequestBody FriendChainApplyRequest friendChainApplyRequest) throws CustomException {
    friendService.applyFriendChain(friendChainApplyRequest);
    return Result.success();
  }

  @ApiOperation("获取友链整体信息")
  @Permission
  @Limiter
  @GetMapping("/getFriendListData")
  public Result<FriendListInfoVO> getFriendListData() {
    return Result.success(friendService.getFriendListData());
  }

  @ApiOperation("获取友链列表")
  @Permission
  @Limiter
  @GetMapping("/getFriendList")
  public Result<FriendListVO> getFriendList(@RequestParam(required = false) Integer page) {
    page = (Objects.isNull(page) || page < 1) ? 1 : page;
    return Result.success(friendService.getFriendList(page, FRIEND_PAGE_SIZE));
  }

  @ApiOperation("审批友链——通过")
  @Permission(PermissionEnum.ADMIN)
  @Limiter
  @PostMapping("/approvalFriendChain")
  public Result<Void> approvalFriendChain(@Validated @RequestBody FriendChainApprovalRequest friendChainApprovalRequest) throws CustomException {
    friendService.approvalFriendChain(friendChainApprovalRequest);
    return Result.success();
  }

  @ApiOperation("审批友链——拒绝")
  @Permission(PermissionEnum.ADMIN)
  @Limiter
  @PostMapping("/refuseFriendChain")
  public Result<Void> refuseFriendChain(@Validated @RequestBody FriendChainRefuseRequest friendChainRefuseRequest) throws CustomException {
    friendService.refuseFriendChain(friendChainRefuseRequest);
    return Result.success();
  }

  @ApiOperation("通知友链失效")
  @Permission(PermissionEnum.ADMIN)
  @Limiter
  @GetMapping("/noticeInvalidFriendChain")
  public Result<Void> noticeInvalidFriendChain(Integer id) throws CustomException {
    friendService.noticeInvalidFriendChain(id);
    return Result.success();
  }

  @ApiOperation("获取友链前言")
  @Permission
  @Limiter
  @GetMapping("/getFriendPreamble")
  public Result<FriendPreambleVO> getFriendPreamble() {
    return Result.success(friendService.getFriendPreamble());
  }
}