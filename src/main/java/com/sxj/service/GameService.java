package com.sxj.service;

import com.sxj.entity.GameBean;
import com.sxj.entity.PlayBean;
import com.sxj.mapper.GameMapper;
import com.sxj.mapper.PlayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GameService {
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private PlayMapper playMapper;

    public void updateStartStatus(String p1, String p2) {
        System.out.println("--------------GameService.updateStartStatus--------------");
        PlayBean playBean = new PlayBean();
        playBean.setGameId(p1);
        //根据sessionId查询用户1ID
        Map<String, Object> playSearch = playMapper.search(playBean);
        String playUser1 = (String) playSearch.get("playUser");

        playBean.setGameId(p2);
        //根据sessionId查询用户2ID
        Map<String, Object> playSearch2 = playMapper.search(playBean);
        String playUser2 = (String) playSearch2.get("playUser");

//        Map<String, Object> stringObjectMap = new HashMap<>();
//        stringObjectMap.put(p1,playUser1);
//        stringObjectMap.put(p2,playUser2);

        GameBean gameBean = new GameBean();
        //生成game表ID
//        String ids=p1+p2;
//        gameBean.setIds(ids);
        gameBean.setP1(playUser1);
        gameBean.setP2(playUser2);
        //根据sessionID查询对局是否存在
        Map<String, Object> search = gameMapper.search(gameBean);
        if (search != null) {
            //根据sessionID查询对局 存在则更新玩家信息
            System.out.println("对局已经存在");
            //        更新开始时间
//            Integer update = gameMapper.update(gameBean);
//            if (update > 0) {
//                System.out.println("更新game表成功");
//            } else {
//                System.out.println("更新game表失败");
//            }
        } else {
            System.out.println(gameBean);
            //根据sessionID查询对局 不存在则插入玩家信息
            Integer integer = gameMapper.insertStart(gameBean);
            if (integer > 0) {
                System.out.println("开始插入game表成功");
            } else {
                System.out.println("开始插入game表失败");
            }
        }
        System.out.println("--------------GameService.updateStartStatus--------------");

    }
    //    查询战绩
    public  Map<String, Object> searchGameAchievementsByUserId(String uname) {
        Map<String, Object> stringObjectMap = gameMapper.searchGameAchievementsByUserId(uname);
        return stringObjectMap;
    }


    public List<GameBean> searchGameData(Map<String,Object> map) {
        String userName = (String) map.get("userName");
//        GameBean gameBean = new GameBean();
//        gameBean.setP1(userName);

        List<GameBean> gameBeans = gameMapper.searchGameDataByUserId(userName);
        return gameBeans;
    }
}
