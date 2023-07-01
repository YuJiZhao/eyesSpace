package com.eyes.eyesspace.async.asynchronist.asyncRestrict;

import com.eyes.eyesspace.async.model.CommentNoticeModel;
import com.eyes.eyesspace.async.model.ReplyNoticeModel;

/**
 * @author eyesYeager
 * @date 2023/7/1 17:30
 */

public interface NoticeAsyncRestrict {

  /**
   * 用户评论时给站长发邮件通知
   * @param commentNoticeModel
   */
  void sendUserCommentNotice(CommentNoticeModel commentNoticeModel);

  /**
   * 用户回复评论时给被回复人发邮件通知
   * @param replyNoticeModel
   */
  void sendUserReplyCommentNotice(ReplyNoticeModel replyNoticeModel);
}
