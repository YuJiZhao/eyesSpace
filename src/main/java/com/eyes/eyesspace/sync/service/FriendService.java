package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.request.FriendChainApplyRequest;
import com.eyes.eyesspace.sync.model.request.FriendChainApprovalRequest;
import com.eyes.eyesspace.sync.model.request.FriendChainRefuseRequest;
import com.eyes.eyesspace.sync.model.vo.FriendListInfoVO;
import com.eyes.eyesspace.sync.model.vo.FriendListVO;
import com.eyes.eyesspace.sync.model.vo.FriendPreambleVO;

/**
 * @author eyesYeager
 * @date 2023/6/1 9:20
 */

public interface FriendService {

  void applyFriendChain(FriendChainApplyRequest friendChainApplyRequest) throws CustomException;

  FriendListInfoVO getFriendListData();

  FriendListVO getFriendList(Integer page, int pageSize);

  void approvalFriendChain(FriendChainApprovalRequest friendChainApprovalRequest) throws CustomException;

  void refuseFriendChain(FriendChainRefuseRequest friendChainRefuseRequest) throws CustomException;

  void noticeInvalidFriendChain(Integer id) throws CustomException;

  FriendPreambleVO getFriendPreamble();
}
