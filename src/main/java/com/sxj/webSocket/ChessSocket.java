package com.sxj.webSocket;

import com.sxj.encoder.ChessBoardEncoder;
import com.sxj.encoder.ServerEncoder;
import com.sxj.enums.ChessEnum;
import com.sxj.enums.ColorEnum;
import com.sxj.pojo.Chess;
import com.sxj.pojo.ChessBoard;
import com.sxj.service.GameService;
import com.sxj.service.MessageService;
import com.sxj.service.PlayService;
import com.sxj.util.ChessUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/control", encoders = {ChessBoardEncoder.class, ServerEncoder.class}, configurator = GetHttpSessionConfigurator.class)
@Slf4j
public class ChessSocket {
    /**
     * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
     * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
     */
    // 两人对战棋局~~
    // TODO 做成只允许两人同时对战（多人桌）
//            1、修改webSocketSet为Map集合,key为桌号,value为对战List<>(2)
//            2、ChessBoard添加桌号属性
//            3、根据客户端所传桌号进行更新
    private final static ChessBoard chessBoard = ChessBoard.getInstance();

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount;

    // 存放对战双方的sessionId
    private static ConcurrentMap<ColorEnum, String> ipMap = new ConcurrentHashMap<>();

    // concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<ChessSocket> webSocketSet = new CopyOnWriteArraySet<ChessSocket>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    // 消息包装对象
    private JSONObject jsonObject;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */

//    websocket引用service层
    //错误引用
//    @Autowired
//    GameService gameService;
//    @Autowired
//    MessageService messageService;

    //错误引用
    // ApplicationContext act = ApplicationContextRegister.getApplicationContext();
    // SocketUserService socketUserService = act.getBean(SocketUserService.class);
    // 正确引用
    public static PlayService playService;
    public static MessageService messageService;
    public static GameService gameService;
    HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        System.out.println("--------------websocket.onOpen--------------");
        this.session = session;
        webSocketSet.add(this); // 加入set中
        // 对战人数未超2人，登记SessionId
        if (ipMap.size() < 3) {
            ipMap.put(chessBoard.getAuthority(), session.getId());
            changeColor();
        }
        addOnlineCount(); // 在线数加
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

        //       通过(configurator= GetHttpSessionConfigurator.class)配置获取HttpSession
        httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        //登录状态
        messageService.updateStatus(session, httpSession, "online", "在线的");
        System.out.println(session.getId());
//        通过httpSession读取用户名
        String uname = (String) httpSession.getAttribute("uname");
//        System.out.println(uname);
        //更新play表状态
        playService.updateData(uname, session);

        //进入游戏时查询战绩
        Map<String, Object> searchGameAchievementsByUserId = gameService.searchGameAchievementsByUserId(uname);
//        System.out.println(searchGameAchievementsByUserId);
        //查询的战绩发送信息到前端
        JSONObject jsonObject = JSONObject.fromObject(searchGameAchievementsByUserId);
        this.session.getBasicRemote().sendText(String.valueOf(jsonObject));
        System.out.println("--------------websocket.onOpen--------------");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("OnClose");
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        System.out.println(session.getId());
//        httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//        //登录状态 // httpSession顺序问题?
        messageService.updateStatus(session, httpSession, "offline", "离线的");
        //读取红棋和黑棋的sessionId
        String p1 = ipMap.get(ColorEnum.RED);
        String p2 = ipMap.get(ColorEnum.BLACK);
        /**
         * 掉线自动认输，不使用则停滞
         */
        admitDefeat(session, p1, p2, "掉线了");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println("--------------websocket.onMessage--------------");
        System.out.println("客户端发来的消息：" + message);
        //读取红棋和黑棋的sessionId
        String p1 = ipMap.get(ColorEnum.RED);
        String p2 = ipMap.get(ColorEnum.BLACK);
//        通过httpSession读取用户名
//        String uname = (String) httpSession.getAttribute("uname");
//        System.out.println(uname);
//        将String消息转换成JSONObject
        JSONObject jsonObject = JSONObject.fromObject(message);
        if (message.equals("{}") || jsonObject.has("way") && jsonObject.get("way").equals("1")) {
//            System.out.println("=========ipMap=========");
//            for (val entry : ipMap.entrySet()) {
//                System.out.println(entry.getKey() + "----" + entry.getValue());
//            }
//            System.out.println("=========ipMap=========");
//            System.out.println("ipMap=========" + ipMap);
//            System.out.println("ipMap=========" + ipMap.size());
            System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
            if (ipMap.size() >= 2) {
                System.out.println("游戏已经开始");
               /* for (val entry : ipMap.entrySet()) {
                    String sessionId = entry.getValue();
                    playService.updateStartStatus(sessionId);
                }*/
                for (Map.Entry<ColorEnum, String> entry : ipMap.entrySet()) {
                    String sessionId = entry.getValue();
                    playService.updateStartStatus(sessionId);
                }
                ////储存游戏开始状态信息，并且刷新前端数据..
                gameService.updateStartStatus(p1, p2);
                //更新战绩信息到当前的前端
                refreshStatus(session, p1, p2);

                //群发消息
                for (ChessSocket cs : webSocketSet) {
                    //更新战绩信息到前端
                    refreshStatus(cs.session, p1, p2);
                }
//                Map<String, Object> map = new HashMap<>();
//                map.put("11",22);
//                this.session.getBasicRemote().sendObject(map);
//                this.session.getBasicRemote().sendObject(ipMap);
//                this.session.getAsyncRemote().sendObject(ipMap);
                System.out.println("--------------websocket.onMessage--------------");
            }
        } else if (jsonObject.has("admitDefeat") && jsonObject.get("admitDefeat").equals(1)) {
            admitDefeat(session, p1, p2, "认输了");
        }
        //将字符串转换JSON对象
//        JSONObject jsonObject = JSONObject.fromObject(message);
//        String start = (String) jsonObject.get("start");
//        System.out.println(start);

        System.out.println("sessionId:" + session.getId());
        System.out.println("--------------websocket.webSocketSet--------------");
        // 群发消息
        for (ChessSocket cs : webSocketSet) {
            try {
//                System.out.println(cs.session);
//                System.out.println(cs.session.getId());
//                System.out.println(cs.session.getProtocolVersion());
//                System.out.println(cs.session.getUserPrincipal());
//                System.out.println(ipMap.get(chessBoard.getAuthority()));
                cs.handleResult(message, session);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        System.out.println("--------------websocket.webSocketSet--------------");
        System.out.println("--------------websocket.onMessage--------------");
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 根据不同行为处理
     *
     * @param message
     * @throws IOException
     */
    public void handleResult(String message, Session session) throws IOException {
        jsonObject = JSONObject.fromObject(message);
        // 鉴定申请移动的是否参战一方~
        String way = (String) jsonObject.get("way");
        if (way != null) {
            switch (way) {
                case "1":
                    if (ipMap.containsValue(session.getId())) {
                        //重置游戏
                        reset();
                    }
                    break;
                case "2":
                    if (session.getId().equals(ipMap.get(chessBoard.getAuthority()))) {
                        //移动棋子
                        move(jsonObject);
                    }
                    break;
                default:
            }
        }
        // this.session.getBasicRemote().sendText(message);
        this.session.getAsyncRemote().sendObject(chessBoard);
    }

    /**
     * 重置游戏
     */
    private void reset() {
        chessBoard.setEndFlag(Boolean.FALSE);
        chessBoard.emptyBoard().init();
    }

    /**
     * 移动棋子
     */
    private void move(JSONObject jsonObject) throws IOException {
        System.out.println("--------------websocket.move--------------");
        // 获取移动前后坐标
        int XA = (int) (jsonObject.get("XA"));
        int YA = (int) (jsonObject.get("YA"));
        int XB = (int) (jsonObject.get("XB"));
        int YB = (int) (jsonObject.get("YB"));
        Chess[][] board = chessBoard.getBoard();
        Chess chess = board[XA][YA]; // 所移动棋子
        // 判断移动是否符合规则
        if (chess != null && ChessUtil.moveOrNot(XB, YB, chess, board)) {
            // 符合规则且未杀死敌方将军
            if (board[XB][YB] == null || board[XB][YB].getIden() != ChessEnum.KING) {
                changeColor();
            } else {
                //一方将军死亡，结束游戏
                chessBoard.setEndFlag(Boolean.TRUE);
                System.out.println("一方将军死亡，结束游戏");
                System.out.println(Boolean.TRUE);
                System.out.println(ipMap.get(chessBoard.getAuthority()));
                //读取红棋和黑棋的sessionId
                String p1 = ipMap.get(ColorEnum.RED);
                String p2 = ipMap.get(ColorEnum.BLACK);
                //设置冠军
                playService.setWinner(ipMap.get(chessBoard.getAuthority()), p1, p2);
                // playService.searchPlayUserByGameId(ipMap.get(chessBoard.getAuthority()));

                //群发消息
                for (ChessSocket cs : webSocketSet) {
//                    更新战绩信息到前端
                    refreshStatus(cs.session, p1, p2);
                }
            }
            // 移动棋子
            board = ChessUtil.moveChessto(XB, YB, chess, board);
            chessBoard.setBoard(board);
        }
        System.out.println("--------------websocket.move--------------");
    }

    /**
     * 投降
     */
    private void admitDefeat(Session session, String p1, String p2, String messageString) throws IOException {
        //            认输玩家的信息
        System.out.println("你认输了！" + session.getId());
        String aceSessionID = null;

        //胜利的玩家的信息
        Map<String, Object> winningMapPlayerUser = null;
        //认输的玩家的信息
        Map<String, Object> admitDefeatMapPlayUser = null;
        //点击认输的玩家
        if (session.getId().equals(p1)) {
            //胜利玩家sessionid
            aceSessionID = p2;
            //通过sessionId查询胜利玩家的信息P2
            winningMapPlayerUser = playService.searchPlayUserByGameId(p2);

            //通过sessionId查询认输玩家的信息P1
            admitDefeatMapPlayUser = playService.searchPlayUserByGameId(p1);
        } else if (session.getId().equals(p2)) {
            //胜利玩家sessionid
            aceSessionID = p1;
            //通过sessionId查询胜利玩家的信息p1
            winningMapPlayerUser = playService.searchPlayUserByGameId(p1);
            //通过sessionId查询认输玩家的信息P2
            admitDefeatMapPlayUser = playService.searchPlayUserByGameId(p2);
        }
        //设置对手为冠军
        playService.setWinner(aceSessionID, p1, p2);
        //群发消息
        for (ChessSocket cs : webSocketSet) {
            String mess = "";
//                if (cs.session.getId().equals(p1)) {
//                    if (aceSessionID.equals(p1)) {
//                        mess = "{'message':'你赢了！'}";
//                    } else {
//                        mess = "{'message':'你输了！'}";
//                    }
//
//                } else if (cs.session.getId().equals(p2)) {
//                    if (aceSessionID.equals(p2)) {
//                        mess = "{'message':'你赢了！'}";
//                    } else {
//                        mess = "{'message':'你输了！'}";
//                    }
//                }
            String playUser;
//                判断客户端sessionID是否是第一名的sessionId
            if (aceSessionID.equals(cs.session.getId())) {
                //通过认输玩家的信息读取昵称
                playUser = (String) admitDefeatMapPlayUser.get("playUser");
                mess = "{'admitDefeatMessage':'" + playUser + " " + messageString + "，你赢了！'}";
            } else {
                playUser = (String) winningMapPlayerUser.get("playUser");
                mess = "{'admitDefeatMessage':'你" + messageString + "！" + playUser + "赢了！'}";
            }
            sendMessage(cs.session, mess);

            //更新战绩信息到前端
            refreshStatus(cs.session, p1, p2);
        }

        //重置游戏
        reset();
    }

    //储存游戏开始状态信息，并且刷新前端数据..
    public void refreshStatus(Session session, String p1, String p2) throws IOException {
        //通过红棋和黑棋的sessionId读取用户名
        Map<String, Object> stringObjectMapPlayUser = playService.searchPlayUserByGameId(p1);
        String playUser1 = (String) stringObjectMapPlayUser.get("playUser");
        //通过红棋和黑棋的sessionId读取用户名
        stringObjectMapPlayUser = playService.searchPlayUserByGameId(p2);
        String playUser2 = (String) stringObjectMapPlayUser.get("playUser");
        //查询红旗的战绩
        Map<String, Object> searchPlayUser1GameAchievementsByUserId = gameService.searchGameAchievementsByUserId(playUser1);
        //查询黑旗的战绩
        Map<String, Object> searchPlayUser2GameAchievementsByUserId = gameService.searchGameAchievementsByUserId(playUser2);
        System.out.println(session);
        System.out.println(session.getId());
        System.out.println(session.getProtocolVersion());
        System.out.println(session.getUserPrincipal());
        System.out.println(ipMap.get(chessBoard.getAuthority()));
        HashMap<String, Object> map = new HashMap<>();
        //双方都点击开始时，储存对手的信息发送给用户
        if (session.getId().equals(p1)) {
            //储存对手的ID
            map.put("maker", playUser2);
            //储存对手的棋子颜色
            map.put("makerColor", ColorEnum.BLACK);
            //储存查询对手的战绩
            map.put("makerWin", searchPlayUser2GameAchievementsByUserId.get("win"));
            map.put("makerFail", searchPlayUser2GameAchievementsByUserId.get("fail"));
            //储存查询个人战绩
            map.put("userWin", searchPlayUser1GameAchievementsByUserId.get("win"));
            map.put("userFail", searchPlayUser1GameAchievementsByUserId.get("fail"));
        } else if (session.getId().equals(p2)) {
            //储存对手的ID
            map.put("maker", playUser1);
            //储存对手的棋子颜色
            map.put("makerColor", ColorEnum.RED);
            //储存查询对手的战绩
            map.put("makerWin", searchPlayUser1GameAchievementsByUserId.get("win"));
            map.put("makerFail", searchPlayUser1GameAchievementsByUserId.get("fail"));
            //储存查询个人战绩
            map.put("userWin", searchPlayUser2GameAchievementsByUserId.get("win"));
            map.put("userFail", searchPlayUser2GameAchievementsByUserId.get("fail"));
        }
        JSONObject jsonObj = JSONObject.fromObject(map);
        // 给客户端发消息
//                    cs.session.getBasicRemote().sendText(jsonObj.toString());
        sendMessage(session, jsonObj.toString());
    }

    /**
     * 改变行权方
     */
    public void changeColor() {
        ColorEnum color = chessBoard.getAuthority();
        color = color == ColorEnum.BLACK ? ColorEnum.RED : ColorEnum.BLACK;
        chessBoard.setAuthority(color);
    }

    // 发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        } else {
            System.out.println("发消息失败session=" + session.getId());
        }
    }

    // 给指定用户发送信息
    public void sendInfo(String userName, String message) {

        System.out.println("给" + userName + "用户发送信息");
        Session session = sessionPools.get(userName);

        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //刷新状态
/*    public void refreshStatus1(String uname) throws IOException {
        //读取红棋和黑棋的sessionId
        String p1 = ipMap.get(ColorEnum.RED);
        String p2 = ipMap.get(ColorEnum.BLACK);
//                更新记录表信息开始比赛，并且返回用户信息
        Map<String, Object> stringObjectMap = gameService.updateStartStatus(p1, p2);
        System.out.println(stringObjectMap);

        //通过红棋和黑棋的sessionId读取用户名
        String playUser1 = (String) stringObjectMap.get(p1);
        String playUser2 = (String) stringObjectMap.get(p2);
        Map<String, Object> searchPlayUser1GameAchievementsByUserId = gameService.searchGameAchievementsByUserId(playUser1);
        Map<String, Object> searchPlayUser2GameAchievementsByUserId = gameService.searchGameAchievementsByUserId(playUser2);
        HashMap<String, Object> map = new HashMap<>();

        //双方都点击开始时，储存对手的信息发送给用户
        if (uname.equals(playUser1)) {
            //储存对手的ID
            map.put("maker", playUser2);
            //储存对手的棋子颜色
            map.put("makerColor", ColorEnum.BLACK);
            //储存查询对手的战绩
            map.put("makerWin", searchPlayUser2GameAchievementsByUserId.get("win"));
            map.put("makerFail", searchPlayUser2GameAchievementsByUserId.get("fail"));
            //储存查询个人战绩
            map.put("userWin", searchPlayUser1GameAchievementsByUserId.get("win"));
            map.put("userFail", searchPlayUser1GameAchievementsByUserId.get("fail"));
        } else if (uname.equals(playUser2)) {
            //储存对手的ID
            map.put("maker", playUser1);
            //储存对手的棋子颜色
            map.put("makerColor", ColorEnum.RED);
            //储存查询对手的战绩
            map.put("makerWin", searchPlayUser1GameAchievementsByUserId.get("win"));
            map.put("makerFail", searchPlayUser1GameAchievementsByUserId.get("fail"));
            //储存查询个人战绩
            map.put("userWin", searchPlayUser2GameAchievementsByUserId.get("win"));
            map.put("userFail", searchPlayUser2GameAchievementsByUserId.get("fail"));
        }
        JSONObject jsonObj = JSONObject.fromObject(map);
        // 给客户端发消息
//        String.valueOf(jsonObj)
//        jsonObj.toString();
        this.session.getBasicRemote().sendText(jsonObj.toString());
    }*/


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ChessSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ChessSocket.onlineCount--;
    }
}
