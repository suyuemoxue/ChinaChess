package com.sxj.mapper;

import com.sxj.entity.PlayBean;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface PlayMapper {

    /* play表 */
  /*  @Select("select * from paly;")
    List<UserBean> search();*/
    @Select("<script>SELECT * FROM play " +
            "<where>" +
            "<if test='playBean.ids!=null and playBean.ids !=\"\"'> and ids=#{playBean.ids}</if>" +
            "<if test='playBean.gameId!=null and playBean.gameId !=\"\"'> and gameId=#{playBean.gameId}</if>" +
            "<if test='playBean.playUser!=null and playBean.playUser !=\"\"'> and playUser=#{playBean.playUser}</if>" +
            "<if test='playBean.timeConsuming!=null and playBean.timeConsuming !=\"\"'> and timeConsuming=#{playBean.timeConsuming}</if>" +
            "<if test='playBean.statusType!=null and playBean.statusType !=\"\"'> and statusType=#{playBean.statusType}</if>" +
            "<if test='playBean.Mess!=null and playBean.Mess !=\"\"'>and playUser=#{playBean.Mess}</if>;" +
            "</where></script>")
    Map<String,Object> search(@Param("playBean") PlayBean playBean);
    /*@Insert("insert into paly (ids, gameId,playUser,timeConsuming,statusType,Mess) values (#{ids},#{gameId},#{playUser},#{timeConsuming},#{statusType},#{Mess});")
    Integer insert();*/
//    @Insert("insert into paly (ids, gameId,playUser,timeConsuming,statusType,Mess) " +
//            "values (#{playBean.ids},#{playBean.gameId},#{playBean.playUser},#{playBean.timeConsuming},#{playBean.statusType},#{playBean.Mess});")

    @Insert("<script>insert into play(" +
            "<trim suffixOverrides=','>" +
            "<if test='playBean.ids!=null and playBean.ids !=\"\"'>ids,</if>" +
            "<if test='playBean.gameId!=null and playBean.gameId !=\"\"'>gameId,</if>" +
            "<if test='playBean.playUser!=null and playBean.playUser !=\"\"'>playUser,</if>" +
            "<if test='playBean.timeConsuming!=null and playBean.timeConsuming !=\"\"'>timeConsuming,</if>" +
            "<if test='playBean.statusType!=null and playBean.statusType !=\"\"'>statusType,</if>" +
            "<if test='playBean.Mess!=null and playBean.Mess !=\"\"'>Mess,</if>" +
            "</trim>" +
            ")  values (" +
            "<trim suffixOverrides=','>" +
            "<if test='playBean.ids!=null and playBean.ids !=\"\"'>#{playBean.ids},</if>" +
            "<if test='playBean.gameId!=null and playBean.gameId !=\"\"'>#{playBean.gameId},</if>" +
            "<if test='playBean.playUser!=null and playBean.playUser !=\"\"'>#{playBean.playUser},</if>" +
            "<if test='playBean.timeConsuming!=null and playBean.timeConsuming !=\"\"'>#{playBean.timeConsuming},</if>" +
            "<if test='playBean.statusType!=null and playBean.statusType !=\"\"'>#{playBean.statusType},</if>" +
            "<if test='playBean.Mess!=null and playBean.Mess !=\"\"'>#{playBean.Mess},</if></trim>" +
            ");" +
            "" +
            "</script>")
    Integer insert(@Param("playBean") PlayBean playBean);
    /*@Update("update play set ids=#{ids},gameId=#{gameId},playUser=#{playUser},timeConsuming=#{timeConsuming},statusType=#{statusType},Mess=#{Mess}")
    Integer update();*/
    @Update("<script>update play " +
            "<set>" +
            "<if test='playBean.ids!=null and playBean.ids !=\"\"'>ids=#{playBean.ids},</if>" +
            "<if test='playBean.gameId!=null and playBean.gameId !=\"\"'>gameId=#{playBean.gameId},</if>" +
//            "<if test='playBean.playUser!=null and playBean.playUser !=\"\"'>playUser=#{playBean.playUser},</if>" +
            "<if test='playBean.timeConsuming!=null and playBean.timeConsuming !=\"\"'>timeConsuming=#{playBean.timeConsuming},</if>" +
            "<if test='playBean.statusType!=null and playBean.statusType !=\"\"'>statusType=#{playBean.statusType},</if>" +
            "<if test='playBean.Mess!=null and playBean.Mess !=\"\"'>Mess=#{playBean.Mess},</if></set>WHERE playUser=#{playBean.playUser}</script>")
    Integer update(@Param("playBean") PlayBean playBean);

    @Update("<script>update play " +
            "<set>" +
            "<if test='playBean.ids!=null and playBean.ids !=\"\"'>ids=#{playBean.ids},</if>" +
            "<if test='playBean.gameId!=null and playBean.gameId !=\"\"'>gameId=#{playBean.gameId},</if>" +
//            "<if test='playBean.playUser!=null and playBean.playUser !=\"\"'>playUser=#{playBean.playUser},</if>" +
            "<if test='playBean.timeConsuming!=null and playBean.timeConsuming !=\"\"'>timeConsuming=#{playBean.timeConsuming},</if>" +
            "<if test='playBean.statusType!=null and playBean.statusType !=\"\"'>statusType=#{playBean.statusType},</if>" +
            "<if test='playBean.Mess!=null and playBean.Mess !=\"\"'>Mess=#{playBean.Mess},</if></set>WHERE gameId=#{playBean.gameId}</script>")
    Integer updateStartStatus(@Param("playBean") PlayBean playBean);


    @Delete("DELETE FROM play")
    Integer delete();


 /*   @Delete("delect from paly;")
    Integer delete();*/


}
