package com.sxj.service;

import com.sxj.entity.MessageBean;
import com.sxj.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public void updateStatus(Session session, HttpSession httpSession,String status,String mess) {
//        System.out.println("--------------MessageService--------------");
//        通过HttpSession获取用户名
//        System.out.println(httpSession.getAttribute(" uname"));
        //用户名
        String userName = (String) httpSession.getAttribute("uname");
        //用户ID
        String playUser = "p1";
        //通过Session获取sessionId
        String sessionId = session.getId();
        Integer sssId = Integer.valueOf(sessionId);
//        查询是否存在重复的sessionId

//        装入Bean
        MessageBean messageBean = new MessageBean();
        messageBean.setSeraVersionUID(sssId);
        messageBean.setUserName(userName);
        //默认状态online/offline
        messageBean.setStatus(status);
        messageBean.setMess(mess);
//        System.out.println(messageBean);
        Integer update;
//        把Bean存入登录状态表
        Map<String, Object> stringObjectMap = messageMapper.searchDataById(userName);
//        System.out.println(stringObjectMap);
        if (stringObjectMap != null) {
            //存在则更新
            update = messageMapper.update(messageBean);
            if (update > 0) {
//                System.out.println("messageService:更新成功!");
            } else {
//                System.out.println("messageService:更新失败");
            }
//            for (val entry : stringObjectMap.entrySet()) {
//                System.out.println( entry.getKey()+"----"+entry.getValue());
//            }
        } else {
            // 不存在则新增
//            装入Bean
            messageBean.setPlayUser(playUser);
            update = messageMapper.insert(messageBean);
            if (update > 0) {
//                System.out.println("messageService:存入成功!");
            } else {
//                System.out.println("messageService:存入失败");
            }
        }

//        System.out.println("--------------MessageService--------------");
    }

}
