package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.dto.FriendChainApplyDTO;
import com.eyes.eyesspace.persistent.po.FriendOperatePO;
import com.eyes.eyesspace.sync.model.dto.FriendListDTO;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author eyesYeager
 * @date 2023/6/1 9:25
 */
@Mapper
public interface FriendMapper {
  @Insert("insert into friend "
      + "(uid, email, name, introduce, avatar, address, create_time) "
      + "values "
      + "(#{uid}, #{email}, #{name}, #{introduce}, #{avatar}, #{address}, now())")
  Boolean addFriendChain(FriendChainApplyDTO friendChainApplyDTO);

  @Select("select status from friend where uid=#{uid}")
  Integer getFriendStatusByUid(Long uid);

  @Select("select status from friend")
  List<Integer> getFriendStatusList();

  @Select("<script>"
      + "select id, name, introduce, avatar, address, status from friend where status in "
      + "<foreach collection='statusList' item='item' index='index' open='(' separator=',' close=')'>"
      + "#{item}"
      + "</foreach> "
      + "limit #{start}, #{pageSize}"
      + "</script>")
  List<FriendListDTO> getFriendList(int start, int pageSize, List<Integer> statusList);

  @Select("<script>"
      + "select count(*) from friend where status in "
      + "<foreach collection='statusList' item='item' index='index' open='(' separator=',' close=')'>"
      + "#{item}"
      + "</foreach> "
      + "</script>")
  Integer getShowTotalFriend(List<Integer> statusList);

  @Update("update friend set status=#{normal}, success_time=now() where id=#{id}")
  Boolean approvalFriendChain(Integer id, int normal);

  @Select("select email, name, introduce, avatar, address, status from friend where id=#{id}")
  FriendOperatePO getFriendApprovalInfo(Integer id);

  @Update("update friend set status=#{deleted} where id=#{id}")
  Boolean changeFriendChainStatus(Integer id, int deleted);

  @Update("update friend set status=#{invalid}, invalid_time=now() where id=#{id}")
  Boolean setInvalidFriendChain(Integer id, int invalid);
}
