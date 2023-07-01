package com.eyes.eyesspace.async.asynchronist.actuator;

import com.eyes.eyesspace.async.asynchronist.asyncRestrict.NoticeAsyncRestrict;
import com.eyes.eyesspace.async.asynchronist.pool.SingleThreadPool;
import com.eyes.eyesspace.async.model.CommentNoticeModel;
import com.eyes.eyesspace.async.model.ReplyNoticeModel;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author eyesYeager
 * @date 2023/7/1 17:31
 */
@Component
public class NoticeActuator implements NoticeAsyncRestrict {
  private final ExecutorService singleThreadPool = SingleThreadPool.getExecutor();

  @Resource
  private NoticeAsyncRestrict noticeAchieve;

  @Override
  public void sendUserCommentNotice(CommentNoticeModel commentNoticeModel) {
    singleThreadPool.execute(() -> noticeAchieve.sendUserCommentNotice(commentNoticeModel));
  }

  @Override
  public void sendUserReplyCommentNotice(ReplyNoticeModel replyNoticeModel) {
    singleThreadPool.execute(() -> noticeAchieve.sendUserReplyCommentNotice(replyNoticeModel));
  }
}
