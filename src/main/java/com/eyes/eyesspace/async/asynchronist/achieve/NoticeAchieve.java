package com.eyes.eyesspace.async.asynchronist.achieve;

import com.eyes.eyesAuth.thrift.TTClientPool;
import com.eyes.eyesAuth.thrift.config.TTSocket;
import com.eyes.eyesAuth.thrift.generate.common.TTCustomException;
import com.eyes.eyesAuth.thrift.generate.user.UserInfoReturnee;
import com.eyes.eyesTools.service.email.EmailSender;
import com.eyes.eyesspace.async.asynchronist.asyncRestrict.NoticeAsyncRestrict;
import com.eyes.eyesspace.async.model.CommentNoticeModel;
import com.eyes.eyesspace.async.model.ReplyNoticeModel;
import com.eyes.eyesspace.persistent.po.ReplyInfoPO;
import com.eyes.eyesspace.async.template.EmailCommentNoticeTemplate;
import com.eyes.eyesspace.async.template.EmailCommentReplyNoticeTemplate;
import com.eyes.eyesspace.persistent.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 用户通知相关任务监听
 * @author eyesYeager
 */

@Slf4j
@RefreshScope
@Component
public class NoticeAchieve implements NoticeAsyncRestrict {
    @Value("${app.name-cn}")
    private String appName;

    @Value("${app.author-cn}")
    private String authorCN;

    @Value("${app.author-email}")
    private String authorEmail;

    private final EmailSender emailSender;

    private final TTClientPool ttClientPool;

    private final CommentMapper commentMapper;

    public NoticeAchieve(EmailSender emailSender, TTClientPool ttClientPool, CommentMapper commentMapper) {
        this.emailSender = emailSender;
        this.ttClientPool = ttClientPool;
        this.commentMapper = commentMapper;
    }

    @Override
    public void sendUserCommentNotice(CommentNoticeModel commentNoticeModel) {
        emailSender.sendMail(
            authorEmail,
            appName + commentNoticeModel.getSubject(),
            new EmailCommentNoticeTemplate(
                appName + commentNoticeModel.getSubject(),
                authorCN,
                commentNoticeModel.getUrl(),
                commentNoticeModel.getContent()
            ).getTemplate()
        );
    }

    @Override
    public void sendUserReplyCommentNotice(ReplyNoticeModel replyNoticeModel) {
        // 获取回复信息
        ReplyInfoPO replyInfo = commentMapper.getReplyInfoByReplyId(replyNoticeModel.getReplyId());

        // 获取回复对象用户信息
        TTSocket ttSocket = null;
        UserInfoReturnee userInfo;
        try {
            ttSocket = ttClientPool.getConnect();
            userInfo = ttSocket.getUserClient().getUserInfo(replyInfo.getUid());
            ttClientPool.returnConnection(ttSocket);
        } catch (TTCustomException e) {
            log.error(e.getMsg());
            return;
        } catch (Exception e) {
            ttClientPool.invalidateObject(ttSocket);
            log.error("Thrift call error");
            e.printStackTrace();
            return;
        }

        // 发送邮件
        emailSender.sendMail(
            userInfo.getEmail(),
            appName + replyNoticeModel.getSubject(),
            new EmailCommentReplyNoticeTemplate(
                appName,
                authorCN,
                replyNoticeModel.getUrl(),
                replyInfo.getContent()
            ).getTemplate()
        );
    }
}
