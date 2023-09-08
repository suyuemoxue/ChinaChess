package com.sxj.webSocket;

import com.sxj.service.GameService;
import com.sxj.service.MessageService;
import com.sxj.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    //添加下面配置 在socket引入socketUserService
    @Autowired
    public void socketUserService(MessageService messageService){
        ChessSocket.messageService = messageService;
    }
    @Autowired
    public void socketUserService(PlayService playService){
        ChessSocket.playService = playService;
    }
    @Autowired
    public void socketUserService(GameService gameService){
        ChessSocket.gameService = gameService;
    }
}
