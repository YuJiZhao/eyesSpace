package com.eyes.eyesspace.queue.monitor;

import com.eyes.eyesAuth.thrift.TTClientPool;
import com.eyes.eyesAuth.thrift.config.TTSocket;
import com.eyes.eyesAuth.thrift.generate.common.TTCustomException;
import com.eyes.eyesAuth.thrift.generate.user.UserInfoReturnee;
import com.eyes.eyesTools.service.email.EmailSender;
import com.eyes.eyesspace.queue.constant.QueueConstant;
import com.eyes.eyesspace.queue.model.CommentNoticeModel;
import com.eyes.eyesspace.queue.model.ReplyNoticeModel;
import com.eyes.eyesspace.persistent.po.ReplyInfoPO;
import com.eyes.eyesspace.queue.template.EmailCommentNotice;
import com.eyes.eyesspace.queue.template.EmailCommentReplyNotice;
import com.eyes.eyesspace.persistent.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
public class NoticeMonitor {
    @Value("${app.name-cn}")
    private String appName;

    @Value("${app.logo}")
    private String appLogo;

    @Value("${app.author-cn}")
    private String authorCN;

    @Value("${app.author-email}")
    private String authorEmail;

    private final EmailSender emailSender;

    private final TTClientPool ttClientPool;

    private final CommentMapper commentMapper;

    public NoticeMonitor(EmailSender emailSender, TTClientPool ttClientPool, CommentMapper commentMapper) {
        this.emailSender = emailSender;
        this.ttClientPool = ttClientPool;
        this.commentMapper = commentMapper;
    }

    /**
     * 用户评论时给站长发邮件通知
     */
    @RabbitListener(queues = QueueConstant.EMAIL_COMMENT_NOTICE)
    public void sendUserCommentNotice(CommentNoticeModel commentNoticeModel) {
        emailSender.sendMail(
            authorEmail,
            appName + commentNoticeModel.getSubject(),
            new EmailCommentNotice(
                appName + commentNoticeModel.getSubject(),
                appLogo,
                authorCN,
                commentNoticeModel.getUrl(),
                commentNoticeModel.getContent()
            ).getTemplate()
        );
    }

    /**
     * 用户回复评论时给被回复人发邮件通知
     */
    @RabbitListener(queues = QueueConstant.EMAIL_COMMENT_REPLY_NOTICE)
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
            new EmailCommentReplyNotice(
                appName,
                appLogo,
                authorCN,
                replyNoticeModel.getUrl(),
                replyInfo.getContent()
            ).getTemplate()
        );
    }
}
