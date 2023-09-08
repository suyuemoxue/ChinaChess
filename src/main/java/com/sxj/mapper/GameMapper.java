package com.sxj.mapper;

import com.sxj.entity.GameBean;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface GameMapper {


    /* Game表 */
 /*   @Select("select * from game;")
    List<UserBean> search();
*/
//    根据条件查询对局信息
//    @Select("<script>SELECT * FROM game " +
//            "<where>" +
//           "<if test='gameBean.ids!=null'> and ids=#{gameBean.ids}</if>" +
//            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'> and p1=#{gameBean.p1}</if>" +
//            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'> and p2=#{gameBean.p2}</if>" +
//            "<if test='gameBean.winner!=null and gameBean.winner !=\"\"'> and winner=#{gameBean.winner}</if>" +
//            "</where></script>")
//    Map<String, Object> search(@Param("gameBean") GameBean gameBean);
//    根据条件查询对局信息
    @Select("<script>SELECT * FROM game " +
            "<where>" +
            "<if test='gameBean.ids!=null'> and ids=#{gameBean.ids}</if>" +
            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'> and p1=#{gameBean.p1}</if>" +
            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'> and p2=#{gameBean.p2}</if>" +
            " and startTime is not null" +
            " and endTime is null" +
            " and winner is null" +
            "</where></script>")
    Map<String, Object> search(@Param("gameBean") GameBean gameBean);

    //    根据条件查询战绩SELECT count(1) as win,(select count(1) from game where winner!='abc') 'fail' FROM game where winner='abc';
    @Select("SELECT count(1) as win,(select count(1) from game where winner!=#{userId}) 'fail' FROM game where winner=#{userId};")
    Map<String, Object> searchGameAchievementsByUserId(@Param("userId") String userId);


    @Results(id = "studentMap", value = {
            @Result(column = "ids", property = "ids", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "p1", property = "p1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "p2", property = "p2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "startTime", property = "startTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "endTime", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "winner", property = "winner", jdbcType = JdbcType.VARCHAR),
//            @Result(column = " ", property = "", one = @One(select = "")),
//            @Result(property = "", column = "", many = @Many(select = ""))
    })
   /* @ConstructorArgs(value = {
            @Arg(column = "ids", javaType = Integer.class),
            @Arg(column = "p1", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "p2", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "startTime", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
            @Arg(column = "endTime", javaType = Timestamp.class, jdbcType = JdbcType.TIMESTAMP),
            @Arg(column = "winner", javaType = String.class, jdbcType = JdbcType.VARCHAR),

    })*/
    //引用Results
//    @ResultMap("studentMap")
    //    @MapKey("idmyfolder")
    /*查询游戏统计数据*/
    @Select("SELECT ids,p1,p2,startTime,endTime,winner FROM game where " +
            "p1=#{userName} && startTime is not null && endTime is not null && winner is not null ||" +
            " p2=#{userName} && startTime is not null && endTime is not null && winner is not null;")
    List<GameBean> searchGameDataByUserId(@Param("userName") String userName);
    /*!=null SET ANSI_NULLS OFF*/

    /*  @Insert("insert into game (ids, p1,p2,startTime,endTime,winner) values (#{ids},#{p1},#{p2},#{startTime},#{endTime},#{winner});")
      Integer insert();*/
    // 动态插入信息
//    @Insert("<script>insert into game(" +
//            "<trim suffixOverrides=','>" +
//            "<if test='gameBean.ids!=null and gameBean.ids !=\"\"'>ids,</if>" +
//            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'>p1,</if>" +
//            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'>p2,</if>" +
//            "startTime," +
//            "endTime," +
//            "<if test='gameBean.winner!=null and gameBean.winner !=\"\"'>winner,</if>" +
//            "</trim>" +
//            ")  values (" +
//            "<trim suffixOverrides=','>" +
//            "<if test='gameBean.ids!=null and gameBean.ids !=\"\"'>#{gameBean.ids},</if>" +
//            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'>#{gameBean.p1},</if>" +
//            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'>#{gameBean.p2},</if>" +
//            "<choose>" +
//            "<when test='null !=gameBean.startTime'>startTime=#{gameBean.startTime}," +
//            "</when>" +
//            "<when test='null == gameBean.startTime'>null," +
//            "</when>" +
//            "<otherwise></otherwise>" +
//            "</choose>" +
//            "<choose>" +
//            "<when test='null !=gameBean.endTime'>endTime=#{gameBean.endTime}," +
//            "</when>" +
//            "<when test='null == gameBean.endTime'>null," +
//            "</when>" +
//            "<otherwise></otherwise>" +
//            "</choose>" +
//            "<if test='gameBean.winner!=null and gameBean.winner !=\"\"'>#{gameBean.winner},</if></trim>" +
//            ");" +
//            "</script>")
    //    开始游戏
    @Insert("<script>insert into game(" +
            "<trim suffixOverrides=','>" +
            "<if test='gameBean.ids!=null'>ids,</if>" +
            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'>p1,</if>" +
            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'>p2,</if>" +
            "startTime," +
            "endTime," +
            "<if test='gameBean.winner!=null and gameBean.winner !=\"\"'>winner,</if>" +
            "</trim>" +
            ")  values (" +
            "<trim suffixOverrides=','>" +
            /*"<if test='gameBean.ids!=null'>#{gameBean.ids},</if>" +*/
            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'>#{gameBean.p1},</if>" +
            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'>#{gameBean.p2},</if>" +
            "current_timestamp()," +
            "null," +
            "<if test='gameBean.winner!=null and gameBean.winner !=\"\"'>#{gameBean.winner},</if></trim>" +
            ");" +
            "</script>")
    Integer insertStart(@Param("gameBean") GameBean gameBean);

    /*  @Update("update game(ids,p1,p2,startTime,endTime,winner) set ids=#{ids},p1=#{p1},p2=#{p2},startTime=#{startTime},endTime=#{endTime},winner=#{winner}")
  Integer update();*/
/* choose 标签按顺序判断其内部 when 标签中的判断条件是否成立，
如果有一个成立，则执行相应的 SQL 语句，choose 执行结束；
如果都不成立，则执行 otherwise 中的 SQL 语句。这类似于 Java 的 switch 语句，choose 为 switch，when 为 case，otherwise 则为 default。*/
    // 动态更新信息
  /*  @Update("<script>update game " +
            "<set> " +
            "<if test='gameBean.ids!=null'>ids=#{gameBean.ids},</if>" +
            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'>p1=#{gameBean.p1},</if>" +
            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'>p2=#{gameBean.p2},</if>" +
            "<choose>" +
            "<when test='gameBean.startTime != null'>startTime=#{gameBean.startTime}," +
            "</when>" +
            "<when test='gameBean.startTime == null'>null," +
            "</when>" +
            "<otherwise></otherwise>" +
            "</choose>" +
            "<choose>" +
            "<when test='gameBean.endTime != null'>endTime=#{gameBean.endTime}," +
            "</when>" +
            "<when test='gameBean.endTime == null'>null," +
            "</when>" +
            "<otherwise></otherwise>"+
                   *//*"<if test='gameBean.startTime!=null'>startTime=#{gameBean.startTime},</if>" +
         "<if test='gameBean.endTime!=null'>endTime=#{gameBean.endTime},</if>" *//*
            "</choose>" +
            "<if test='gameBean.winner!=null and gameBean.winner !=\"\"'>winner=#{gameBean.winner},</if></set>;</script>")
    Integer update(@Param("gameBean") GameBean gameBean);*/
//    结束游戏
    @Update("<script>update game " +
            "<set> " +
            "endTime=current_timestamp()," +
                   /*"<if test='gameBean.startTime!=null'>startTime=#{gameBean.startTime},</if>" +
         "<if test='gameBean.endTime!=null'>endTime=#{gameBean.endTime},</if>" */
            "<if test='gameBean.winner!=null and gameBean.winner !=\"\"'>startTime=startTime,winner=#{gameBean.winner},</if></set><where>" +
            "<if test='gameBean.ids!=null'> and ids=#{gameBean.ids}</if>" +
            "<if test='gameBean.p1!=null and gameBean.p1 !=\"\"'> and p1=#{gameBean.p1}</if>" +
            "<if test='gameBean.p2!=null and gameBean.p2 !=\"\"'> and p2=#{gameBean.p2}</if>" +
            " and startTime is not null" +
            " and endTime is null" +
            " and winner is null" +
            /*"<if test='gameBean.startTime!=null and gameBean.startTime !=\"\"'> and startTime=#{gameBean.startTime}</if>" +
            "<if test='gameBean.endTime!=null and gameBean.endTime !=\"\"'> and endTime=#{gameBean.endTime}</if>" +*/
            /*"<if test='gameBean.winner!=null and gameBean.winner !=\"\"'> and winner=#{gameBean.winner}</if>" +*/
            "</where>;</script>")
    Integer updateEnd(@Param("gameBean") GameBean gameBean);


    /*@Delete("delete * from game;")
    Integer delete();*/
    //清理没结束的比赛
    @Delete("delete from game where startTime is not null and endTime is null and winner is null;")
    Integer delete();


}
