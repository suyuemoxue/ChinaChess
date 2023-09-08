package com.sxj.mapper;

import com.sxj.entity.MessageBean;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {


    /* message表 */
 /*   @Select("select * from message;")
    List<UserBean> search();*/
    @Select("select * from message;")
    List<MessageBean> search();


    //    通过ID查询
    @Select("<script>" +
            "select * from message where userName=#{userName};" +
            "</script>")
//    List<MessageBean>接收多个
//    Map<String ,Object>接收单个
    Map<String, Object> searchDataById(@Param("userName") String userName);

    /*    @Insert("insert into message (seraVersionUID, userName,playUser,status,mess) values (#{seraVersionUID},#{userName},#{playUser},#{status},#{mess};")
        Integer insert();*/
    /*新增*/
    @Insert("insert into message (seraVersionUID, userName,playUser,status,mess) values (#{MessageBean.seraVersionUID},#{MessageBean.userName},#{MessageBean.playUser},#{MessageBean.status},#{MessageBean.mess});")
    Integer insert(@Param("MessageBean") MessageBean messageBean);

    /*  @Update("update message(seraVersionUID, userName,playUser,status,mess) set seraVersionUID=#{seraVersionUID},userName=#{userName},playUser=#{playUser},status=#{status},mess=#{mess}")
  Integer update();*/
//    动态更新
   @Update("<script>update message  " +
            "<set>"
            + "<if test='messageBean.seraVersionUID!=null'>seraVersionUID=#{messageBean.seraVersionUID},</if>\n"
//            + "<if test='userName!=null and userName !=\"\"'>userName=#{userName}</if>\n"
//            + "<if test='messageBean.playUser!=null and messageBean.playUser !=\"\"'>playUser=#{messageBean.playUser},</if>\n"
            + "<if test='messageBean.status!=null and messageBean.status !=\"\"'>status=#{messageBean.status},</if>\n"
            + "<if test='messageBean.mess!=null and messageBean.mess !=\"\"'>mess=#{messageBean.mess},</if>\n"
            + "</set> where userName=#{messageBean.userName}" +
            "</script>")

    Integer update(@Param("messageBean") MessageBean messageBean);

    @Delete("delete from message;")
    Integer delete();
}
