package com.sxj.util;

import com.sxj.enums.ColorEnum;
import com.sxj.pojo.Chess;

/**
 * 象棋工具类
 */
public class ChessUtil {

    /**
     * 是否可移动棋子
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    public static boolean moveOrNot(int x, int y, Chess chess, Chess[][] board) {
        boolean flag = false;
        // 目标坐标为空或所在非己方人物
        if (board[x][y] == null || board[x][y].getColor() != chess.getColor()) {
            switch (chess.getIden()) {
                case KING:
                    if (getDif(x, y, chess) == 1) {
                        flag = isInScope(x, y, chess);
                    } else {
                        flag = kingKillKing(x, y, chess, board);
                    }
                    break;
                case ROOK:
                    flag = rookPass(x, y, chess, board);
                    break;
                case KNIGHT:
                    flag = horsePass(x, y, chess, board);
                    break;
                case CANNON:
                    flag = cannonPass(x, y, chess, board);
                    break;
                case ELEPHANT:
                    flag = elephantPass(x, y, chess, board);
                    break;
                case MANDARIN:
                    flag = mandarinPass(x, y, chess);
                    break;
                case PAUN:
                    flag = paunPass(x, y, chess, board);
                    break;

            }
        }
        return flag;
    }

    /**
     * 移动棋子
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    public static Chess[][] moveChessto(int x, int y, Chess chess, Chess[][] board) {
        board[x][y] = chess;
        board[chess.getxN()][chess.getyN()] = null;
        chess.setCoordinate(x, y);
        return board;
    }

    /**
     * 移动格数绝对值
     *
     * @param x
     * @param y
     * @param chess
     * @return
     */
    private static int getDif(int x, int y, Chess chess) {
        int dif = x + y - (chess.getxN() + chess.getyN());
        if (targetQuadrant(x, y, chess) == 2) {
            dif = chess.getxN() - x + y - chess.getyN();
        } else if (targetQuadrant(x, y, chess) == 3) {
            dif = chess.getxN() + chess.getyN() - x - y;
        } else if (targetQuadrant(x, y, chess) == 4) {
            dif = x - chess.getxN() + chess.getyN() - y;
        }
        return Math.abs(dif);
    }

    /**
     * 将军飞杀
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    private static boolean kingKillKing(int x, int y, Chess chess, Chess[][] board) {
        // 是否飞杀路线(垂直)且目标非空
        if (y == chess.getyN() && board[x][y] != null) {
            // 是否敌方将军
            if (board[x][y].isKing() && board[x][y].getColor() != chess.getColor()) {
                // 飞杀路上是否无其他兵卒
                boolean pos = true;
                for (int i = Math.min(x, chess.getxN()) + 1; i < Math.max(x, chess.getxN()) - 1; i++) {
                    if (board[i][y] != null) {
                        pos = false;
                        break;
                    }
                }
                return pos;
            }
        }
        return false;
    }

    /**
     * 车路线鉴定
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    private static boolean rookPass(int x, int y, Chess chess, Chess[][] board) {
        if (targetQuadrant(x, y, chess) == 0) {
            boolean pos = true;
            // 直行路上是否有障碍
            if (x == chess.getxN()) {
                for (int i = Math.min(y, chess.getyN()) + 1; i < Math.max(y, chess.getyN()) - 1; i++) {
                    if (board[x][i] != null) {
                        pos = false;
                        break;
                    }
                }
            } else {
                for (int i = Math.min(x, chess.getxN()) + 1; i < Math.max(x, chess.getxN()) - 1; i++) {
                    if (board[i][y] != null) {
                        pos = false;
                        break;
                    }
                }
            }
            return pos;
        }
        return false;
    }

    /**
     * 马路线鉴定
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    private static boolean horsePass(int x, int y, Chess chess, Chess[][] board) {
        // 是否走日字
        if (getDif(x, y, chess) == 3 && (Math.abs(x - chess.getxN())) != 3) {
            // 关键位是否有障碍
            if (tripOrPass(x, y, chess, board)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 炮路线鉴定
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    private static boolean cannonPass(int x, int y, Chess chess, Chess[][] board) {
        // 是否直行
        if (targetQuadrant(x, y, chess) == 0) {
            int num = 0; // 障碍个数
            if (x == chess.getxN()) {
                for (int i = Math.min(y, chess.getyN()) + 1; i < Math.max(y, chess.getyN()); i++) {
                    if (board[x][i] != null) {
                        num++;
                    }
                }
            } else {
                for (int i = Math.min(x, chess.getxN()) + 1; i < Math.max(x, chess.getxN()); i++) {
                    if (board[i][y] != null) {
                        num++;
                    }
                }
            }
            // 未跳子或跳一子且目标坐标是敌方人物
            if ((num == 0 && board[x][y] == null) || (num == 1 && board[x][y].getColor() != chess.getColor())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 象路线鉴定
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    private static boolean elephantPass(int x, int y, Chess chess, Chess[][] board) {
        // 是否在本方半区
        ColorEnum color = chess.getColor();
        if ((color == ColorEnum.RED && x > 4) || (color == ColorEnum.BLACK && x < 5)) {
            // 是否走田字
            if (getDif(x, y, chess) == 4 && Math.abs(x - chess.getxN()) == 2) {
                // 关键位是否有障碍
                if (tripOrPass(x, y, chess, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 士路线鉴定
     *
     * @param x
     * @param y
     * @param chess
     * @return
     */
    private static boolean mandarinPass(int x, int y, Chess chess) {
        // 是否在大本营内行动且走一格斜线
        if (isInScope(x, y, chess) && (getDif(x, y, chess) == 2 && Math.abs(x - chess.getxN()) == 1)) {
            return true;
        }
        return false;
    }

    /**
     * 卒路线鉴定
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */
    private static boolean paunPass(int x, int y, Chess chess, Chess[][] board) {
        // 是否直走一格
        if (getDif(x, y, chess) == 1) {
            // 是否往前走
            if (x != chess.getxN()) {
                if (chess.getColor() == ColorEnum.RED && x < chess.getxN()) {
                    return true;
                } else if (chess.getColor() == ColorEnum.BLACK && x > chess.getxN()) {
                    return true;
                }
            } else {
                if (chess.getColor() == ColorEnum.RED && chess.getxN() < 5) {
                    return true;
                } else if (chess.getColor() == ColorEnum.BLACK && chess.getxN() > 4) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否绊脚(马/象)
     *
     * @param x
     * @param y
     * @param chess
     * @param board
     * @return
     */

    private static boolean tripOrPass(int x, int y, Chess chess, Chess[][] board) {
        if (targetQuadrant(x, y, chess) == 1 && board[x - 1][y - 1] == null) {
            return true;
        } else if (targetQuadrant(x, y, chess) == 2 && board[x + 1][y - 1] == null) {
            return true;
        } else if (targetQuadrant(x, y, chess) == 3 && board[x + 1][y + 1] == null) {
            return true;
        } else if (targetQuadrant(x, y, chess) == 4 && board[x - 1][y + 1] == null) {
            return true;
        }
        return false;
    }

    /**
     * 目标坐标所在象限
     *
     * @param x
     * @param y
     * @param chess
     * @return
     */
    private static int targetQuadrant(int x, int y, Chess chess) {
        // 默认直行
        int pos = 0;
        // 第一象限
        if (x > chess.getxN() && y > chess.getyN()) {
            pos = 1;
            // 第二象限
        } else if (x < chess.getxN() && y > chess.getyN()) {
            pos = 2;
            // 第三象限
        } else if (x < chess.getxN() && y < chess.getyN()) {
            pos = 3;
            // 第四象限
        } else if (x > chess.getxN() && y < chess.getyN()) {
            pos = 4;
        }
        return pos;
    }

    /**
     * 是否在大本营内走动(将/士)
     *
     * @param x
     * @param y
     * @param chess
     * @return
     */
    private static boolean isInScope(int x, int y, Chess chess) {
        int i1, i2;
        if (chess.getColor() == ColorEnum.BLACK) {
            i1 = 0;
            i2 = 2;
        } else {
            i1 = 7;
            i2 = 9;
        }
        if (x >= i1 && x <= i2 && y >= 3 && y <= 5) {
            return true;
        }
        return false;
    }
}
