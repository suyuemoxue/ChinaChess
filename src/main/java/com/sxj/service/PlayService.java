package com.sxj.service;

import com.sxj.entity.GameBean;
import com.sxj.entity.PlayBean;
import com.sxj.mapper.GameMapper;
import com.sxj.mapper.PlayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.Map;

@Service
public class PlayService {
    @Autowired
    private PlayMapper playMapper;
    @Autowired
    private GameMapper gameMapper;

    public void updateData(String uname, Session session) {
        PlayBean playBean = new PlayBean();
        playBean.setPlayUser(uname);
//        查询是否存在
        Map<String, Object> searchData = playMapper.search(playBean);
        playBean.setIds(session.getId());
        playBean.setGameId(session.getId());
        System.out.println(searchData);
        if (searchData != null) {
            playMapper.update(playBean);
        } else {
            playBean.setTimeConsuming("time");
            playBean.setStatusType("waiting");
            playBean.setMess("测试");
            Integer insert = playMapper.insert(playBean);
            System.out.println(insert);
            if (insert > 0) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失败");
            }
        }
        System.out.println(searchData);
    }

    //把玩家设置成开始状态
    public void updateStartStatus(String value) {

        PlayBean playBean = new PlayBean();
        playBean.setGameId(value);
//        查询是否存在
        Map<String, Object> searchData = playMapper.search(playBean);
        playBean.setStatusType("start");
        if (searchData != null) {
            playMapper.updateStartStatus(playBean);

        }
    }

    public Map<String, Object> searchPlayUserByGameId(String sessionId) {
        PlayBean playBean = new PlayBean();
        playBean.setGameId(sessionId);
        Map<String, Object> searchData = playMapper.search(playBean);
        return searchData;
    }

    public void setWinner(String aceSessionId,String p1,String p2) {
        PlayBean playBean = new PlayBean();
//        playBean.setGameId(aceSessionId);

        playBean.setGameId(p1);
        //根据sessionId查询用户1ID
        Map<String, Object> playSearch = playMapper.search(playBean);
        String playUser1 = (String) playSearch.get("playUser");

        playBean.setGameId(p2);
        //根据sessionId查询用户2ID
        Map<String, Object> playSearch2 = playMapper.search(playBean);
        String playUser2 = (String) playSearch2.get("playUser");
        String aceString = null;
        if(aceSessionId.equals(playSearch.get("gameId"))){
            aceString=playUser1;
        }else if(aceSessionId.equals(playSearch2.get("gameId"))){
            aceString=playUser2;
        }
        GameBean gameBean = new GameBean();
        //结束对局设置冠军
        gameBean.setP1(playUser1);
        gameBean.setP2(playUser2);
        gameBean.setWinner(aceString);
        Integer integer1 = gameMapper.updateEnd(gameBean);
        if (integer1 > 0) {
            System.out.println("结束更新game表成功");
        } else {
            System.out.println("结束更新game表失败");
        }

    }
}
