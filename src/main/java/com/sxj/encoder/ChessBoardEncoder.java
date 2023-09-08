package com.sxj.encoder;

import com.sxj.pojo.ChessBoard;
import net.sf.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChessBoardEncoder implements Encoder.Text<ChessBoard> {

    @Override
    public String encode(ChessBoard chessBoard) throws EncodeException {
        return JSONObject.fromObject(chessBoard).toString();

    }

    public void init(EndpointConfig ec) {
        System.out.println("MessageEncoder - 调用了init方法");
    }

    public void destroy() {
        System.out.println("MessageEncoder - 调用了destroy方法");
    }

}