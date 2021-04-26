package com.company.logic;

import java.util.ArrayList;

public class Dama {
    private int rows;
    private int columns;
    private Coordinate coord;
    private ArrayList<Coordinate> validMoves;
    private boolean selected;
    private int[][] field;
    private boolean p1;
    private boolean p2;
    private boolean white;

    public Dama() {
        this.rows = 8;
        this.columns = 8;
        this.selected = false;
        this.validMoves = new ArrayList<>();
//        this.field = new int[][]{
//                {0,2,0,2,0,2,0,2},
//                {2,0,2,0,2,0,2,0},
//                {0,2,0,2,0,2,0,2},
//                {0,0,0,0,0,0,0,0},
//                {0,0,0,0,0,0,0,0},
//                {1,0,1,0,1,0,1,0},
//                {0,1,0,1,0,1,0,1},
//                {1,0,1,0,1,0,1,0}
//        };
        this.field = new int[][]{
                {0,2,0,2,0,2,0,2},
                {2,0,2,0,2,0,2,0},
                {0,0,0,2,0,2,0,2},
                {0,0,1,0,1,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,1,0,1,0},
                {0,0,0,1,0,1,0,0},
                {1,0,1,0,1,0,1,0}
        };
        this.p1 = false;
        this.p2 = false;
        this.white = true;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void ai() {
        AI AI = new AI();
        Move best = AI.getBest(this.field);
        if(best == null) {
            p1 = true;
            return;
        }
        this.field[best.getFrom().getY()][best.getFrom().getX()] = best.isKing() ? 2+4 : 4+4 ;
        this.field[best.getFrom().getY()][best.getFrom().getX()] = 0;
        if(best.isKing()) this.field[best.getTo().getY()][best.getTo().getX()] = 4;
        else this.field[best.getTo().getY()][best.getTo().getX()] = 2;
        this.removeStone(best.getTo().getX(), best.getTo().getY(), best.getFrom().getX(), best.getFrom().getY());
        this.validMoves.clear();
        this.normalToDama();
        this.checkWinner();
    }

    public void click(int x, int y) {
        boolean contains = false;
        for (Coordinate validMove : getValidMoves()) {
            if (validMove.getX() == x && validMove.getY() == y) {
                contains = true;
                break;
            }
        }
        if(white && !selected && getValue(x,y) % 2 != 0) {
            select(x,y);
        }
        else if(selected && contains) {
            this.field[y][x] = getValue(coord.getX(),coord.getY())-4;
            this.field[coord.getY()][coord.getX()] = 0;
            this.removeStone(x,y, coord.getX(), coord.getY());
            this.validMoves.clear();
            this.normalToDama();
            this.checkWinner();
            this.coord = null;
            ai();
        }else {
            return;
        }
        selected = !selected;
    }

    private void select(int x, int y) {
        this.field[y][x] += 4;
        coord = new Coordinate(x,y);
        validMoves = Utils.updateValidMoves(getValue(x,y)-4, this.coord, this.field);
        if(this.validMoves.isEmpty()) {
            selected = !selected;
            this.field[y][x] -= 4;
        }
    }

    private void removeStone(int x, int y, int x1, int y1) {
        int smerX = (x-x1) / Math.abs(x-x1);
        int smerY = (y-y1) / Math.abs(y-y1);
        for (int i = 1; i < Math.abs(x-x1); i++) {
//            System.out.println(this.field[ y-i*smerY][x - i*smerX]);
            this.field[ y-i*smerY][x - i*smerX] = 0;
        }
    }


    public boolean p1Win() {
        return p1;
    }

    public boolean p2Win() {
        return p2;
    }

    private void checkWinner() {
        int white = 0;
        int black = 0;

        for (int[] ints : this.field) {
            for (int anInt : ints) {
               if(anInt % 2 == 0 && anInt != 0) black++;
               else if(anInt % 2 != 0) white++;
            }
        }

        if(white == 0) this.p2 = true;
        if(black == 0) this.p1 = true;
    }

    /**
     * 0 -> nic
     * 1 -> bili normalni ️●◎◉
     * 2 -> cerny normalni
     * 3 -> bila dama
     * 4 -> cerna dama
     * 5 -> oznacena bila
     * 6 -> oznacena cerna
     * 7 -> oznacena bila dama
     * 8 -> oznacena cerna dama ♜♖
     */
    public int getValue(int x, int y) {
        return this.field[y][x];
    }

    private void normalToDama() {
        for (int i = 0; i < this.field[0].length; i++) {
            if(this.field[0][i] == 1) this.field[0][i] = 3;
        }

        for (int i = 0; i < this.field[this.field.length-1].length; i++) {
            if(this.field[this.field.length-1][i] == 2) this.field[this.field.length-1][i] = 4;
        }
    }

    public ArrayList<Coordinate> getValidMoves() {
       return validMoves;
    }

    public boolean getSelected() {
        return this.selected;
    }

}
