package com.sxj.pojo;

import com.sxj.enums.ChessEnum;
import com.sxj.enums.ColorEnum;

/**
 * 棋盘
 */
public class ChessBoard {
    private Chess[][] board = new Chess[10][9];// 虚拟棋格
    private ColorEnum authority; // 执行方
    private boolean endFlag = Boolean.FALSE; // 结束标志

    private static ChessBoard chessBoard; // 单例

    // 私有构造器
    private ChessBoard() {
    }

    {
        init();
    }

    /**
     * 重置棋盘
     */
    public ChessBoard init() {
        // 默认红子先走
        authority = ColorEnum.RED;
        // 重置棋子状态
        board[0][0] = new Chess.Builder(ChessEnum.ROOK, ColorEnum.BLACK).setCoordinate(0, 0).build();
        board[0][1] = new Chess.Builder(ChessEnum.KNIGHT, ColorEnum.BLACK).setCoordinate(0, 1).build();
        board[0][2] = new Chess.Builder(ChessEnum.ELEPHANT, ColorEnum.BLACK).setCoordinate(0, 2).build();
        board[0][3] = new Chess.Builder(ChessEnum.MANDARIN, ColorEnum.BLACK).setCoordinate(0, 3).build();
        board[0][4] = new Chess.Builder(ChessEnum.KING, ColorEnum.BLACK).setCoordinate(0, 4).build();
        board[0][5] = new Chess.Builder(ChessEnum.MANDARIN, ColorEnum.BLACK).setCoordinate(0, 5).build();
        board[0][6] = new Chess.Builder(ChessEnum.ELEPHANT, ColorEnum.BLACK).setCoordinate(0, 6).build();
        board[0][7] = new Chess.Builder(ChessEnum.KNIGHT, ColorEnum.BLACK).setCoordinate(0, 7).build();
        board[0][8] = new Chess.Builder(ChessEnum.ROOK, ColorEnum.BLACK).setCoordinate(0, 8).build();
        board[2][1] = new Chess.Builder(ChessEnum.CANNON, ColorEnum.BLACK).setCoordinate(2, 1).build();
        board[2][7] = new Chess.Builder(ChessEnum.CANNON, ColorEnum.BLACK).setCoordinate(2, 7).build();
        board[3][0] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.BLACK).setCoordinate(3, 0).build();
        board[3][2] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.BLACK).setCoordinate(3, 2).build();
        board[3][4] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.BLACK).setCoordinate(3, 4).build();
        board[3][6] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.BLACK).setCoordinate(3, 6).build();
        board[3][8] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.BLACK).setCoordinate(3, 8).build();

        board[9][0] = new Chess.Builder(ChessEnum.ROOK, ColorEnum.RED).setCoordinate(9, 0).build();
        board[9][1] = new Chess.Builder(ChessEnum.KNIGHT, ColorEnum.RED).setCoordinate(9, 1).build();
        board[9][2] = new Chess.Builder(ChessEnum.ELEPHANT, ColorEnum.RED).setCoordinate(9, 2).build();
        board[9][3] = new Chess.Builder(ChessEnum.MANDARIN, ColorEnum.RED).setCoordinate(9, 3).build();
        board[9][4] = new Chess.Builder(ChessEnum.KING, ColorEnum.RED).setCoordinate(9, 4).build();
        board[9][5] = new Chess.Builder(ChessEnum.MANDARIN, ColorEnum.RED).setCoordinate(9, 5).build();
        board[9][6] = new Chess.Builder(ChessEnum.ELEPHANT, ColorEnum.RED).setCoordinate(9, 6).build();
        board[9][7] = new Chess.Builder(ChessEnum.KNIGHT, ColorEnum.RED).setCoordinate(9, 7).build();
        board[9][8] = new Chess.Builder(ChessEnum.ROOK, ColorEnum.RED).setCoordinate(9, 8).build();
        board[7][1] = new Chess.Builder(ChessEnum.CANNON, ColorEnum.RED).setCoordinate(7, 1).build();
        board[7][7] = new Chess.Builder(ChessEnum.CANNON, ColorEnum.RED).setCoordinate(7, 7).build();
        board[6][0] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.RED).setCoordinate(6, 0).build();
        board[6][2] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.RED).setCoordinate(6, 2).build();
        board[6][4] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.RED).setCoordinate(6, 4).build();
        board[6][6] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.RED).setCoordinate(6, 6).build();
        board[6][8] = new Chess.Builder(ChessEnum.PAUN, ColorEnum.RED).setCoordinate(6, 8).build();

        return this;
    }

    /**
     * 获取程序单例
     *
     * @return
     */
    public static ChessBoard getInstance() {
        if (chessBoard == null) {
            synchronized (ChessBoard.class) {
                if (chessBoard == null) {
                    chessBoard = new ChessBoard();
                }
            }
        }
        return chessBoard;
    }

    /**
     * 获取程序新对象
     *
     * @return
     */
    public static ChessBoard newInstance() {
        return new ChessBoard();
    }


    /**
     * 清空棋盘
     */
    public ChessBoard emptyBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }
        return this;
    }

    public Chess[][] getBoard() {
        return board;
    }

    public void setBoard(Chess[][] board) {
        this.board = board;
    }

    public ColorEnum getAuthority() {
        return authority;
    }

    public void setAuthority(ColorEnum authority) {
        this.authority = authority;
    }

    public boolean isEndFlag() {
        return endFlag;
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }
}