package com.sxj.pojo;

import com.sxj.enums.ChessEnum;
import com.sxj.enums.ColorEnum;

/**
 * 象棋
 */
public class Chess {
    private int xN; // x坐标
    private int yN; // y坐标
    private ColorEnum color; // 颜色方
    private ChessEnum iden; // 角色

    public static class Builder {
        private int xN;
        private int yN;
        private ColorEnum color;
        private ChessEnum iden;

        public Builder(ChessEnum iden, ColorEnum color) {
            this.iden = iden;
            this.color = color;
        }

        public Builder setCoordinate(int xN, int yN) {
            this.xN = xN;
            this.yN = yN;
            return this;
        }

        public Chess build() {
            return new Chess(this);
        }
    }

    private Chess(Builder builder) {
        xN = builder.xN;
        yN = builder.yN;
        color = builder.color;
        iden = builder.iden;
    }

    /**
     * 设置坐标
     *
     * @param x_axis
     * @param y_axis
     */
    public Chess setCoordinate(int x_axis, int y_axis) {
        this.xN = x_axis;
        this.yN = y_axis;
        return this;
    }

    /**
     * 是否将/帅
     *
     * @return
     */
    public Boolean isKing() {
        return this.iden == ChessEnum.KING;
    }

    public int getxN() {
        return xN;
    }

    public int getyN() {
        return yN;
    }

    public ColorEnum getColor() {
        return color;
    }

    public ChessEnum getIden() {
        return iden;
    }
}