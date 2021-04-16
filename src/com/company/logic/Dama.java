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
        this.field = new int[][]{
                {0,2,0,2,0,2,0,2},
                {2,0,2,0,2,0,2,0},
                {0,2,0,2,0,2,0,2},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,1,0,1,0,1,0},
                {0,1,0,1,0,1,0,1},
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
        } else if(!white && !selected && getValue(x,y) % 2 == 0 && getValue(x,y) != 0) {
            select(x,y);
        } else if(selected && contains) {
            this.field[y][x] = getValue(coord.getX(),coord.getY())-4;
            this.field[coord.getY()][coord.getX()] = 0;
            this.removeStone(x,y, coord.getX(), coord.getY());
            this.validMoves.clear();
            this.normalToDama();
            this.checkWinner();
            coord = null;
            white = !white;
        } else {
            return;
        }
        selected = !selected;
    }

    private void select(int x, int y) {
        this.field[y][x] += 4;
        coord = new Coordinate(x,y);
        updateValidMoves(getValue(x,y)-4);
        if(this.validMoves.isEmpty()) {
            selected = !selected;
            this.field[y][x] -= 4;
        }
    }

    private void removeStone(int x, int y, int x1, int y1) {
        int smerX = (x-x1) / Math.abs(x-x1);
        int smerY = (y-y1) / Math.abs(y-y1);
        for (int i = 1; i < Math.abs(x-x1); i++) {
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

    public void updateValidMoves(int type) {
        if(type == 1 || type == 2) checkNormal(type, -1, this.coord);
        else if(type == 3 || type == 4) {
            checkDama(type, 1, 1);
            checkDama(type, -1, -1);
            checkDama(type, 1, -1);
            checkDama(type, -1, 1);
        }
    }

    private void checkNormal(int type, int jump, Coordinate coord2) {
        int direction = 1;
        if(type == 1) direction = -1;
        if(jump == -1 && isInField(coord2.getX() + 1,coord2.getY() + direction) && getValue(coord2.getX() + 1, coord2.getY()+direction) == 0) this.validMoves.add(new Coordinate(coord2.getX() + 1, coord2.getY()+direction));
        if(jump == -1 && isInField(coord2.getX() - 1,coord2.getY() + direction) && getValue(coord2.getX() - 1, coord2.getY()+direction) == 0) this.validMoves.add(new Coordinate(coord2.getX() - 1, coord2.getY()+direction));
        if((jump == -1 || jump == 1) && isInField(coord2.getX() + 1,coord2.getY() + direction) && getValue(coord2.getX() + 1, coord2.getY()+direction) % 2 == 1 && isInField(coord2.getX() + 2,coord2.getY() + direction*2) && getValue(coord2.getX() + 2, coord2.getY()+direction*2) == 0 && type == 2) {
            this.validMoves.add(new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2));
            checkNormal(type, 1, new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2));
        }
        else if((jump == -1 || jump == 2) && isInField(coord2.getX() + 1,coord2.getY() + direction) && getValue(coord2.getX() + 1, coord2.getY()+direction) % 2 == 0 && getValue(coord2.getX() + 1, coord2.getY()+direction) != 0 && isInField(coord2.getX() + 2,coord2.getY() + direction*2) && getValue(coord2.getX() + 2, coord2.getY()+direction*2) == 0 && type == 1) {
            this.validMoves.add(new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2));
            checkNormal(type, 2, new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2));
        }
        if((jump == -1 || jump == 3) && isInField(coord2.getX() - 1,coord2.getY() + direction) && getValue(coord2.getX() - 1, coord2.getY()+direction) % 2 == 1 && isInField(coord2.getX() - 2,coord2.getY() + direction*2) && getValue(coord2.getX() - 2, coord2.getY()+direction*2) == 0 && type == 2) {
            this.validMoves.add(new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2));
            checkNormal(type, 3, new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2));
        }
        else if((jump == -1 || jump == 4) && isInField(coord2.getX() - 1,coord2.getY() + direction) && getValue(coord2.getX() - 1, coord2.getY()+direction) % 2 == 0 && getValue(coord2.getX() - 1, coord2.getY()+direction) != 0 && isInField(coord2.getX() - 2,coord2.getY() + direction*2) && getValue(coord2.getX() - 2, coord2.getY()+direction*2) == 0 && type == 1) {
            this.validMoves.add(new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2));
            checkNormal(type, 4, new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2));
        }
    }

    private void checkDama(int type, int x, int y) {
        int acX = coord.getX()+x;
        int acY = coord.getY()+y;
        while(isInField(acX, acY)) {
            if(type % 2 != 0 && this.field[acY][acX] % 2 != 0) {
                break;
            } else if(type % 2 == 0 && this.field[acY][acX] % 2 == 0 && type != 0 && this.field[acY][acX] % 2 == 0 && this.field[acY][acX]  != 0 ) {
                break;
            } else if(this.field[acY][acX]  == 0) {
                this.validMoves.add(new Coordinate(acX, acY));
            }
            acX+=x;
            acY+=y;
        }
    }

    private void normalToDama() {
        for (int i = 0; i < this.field[0].length; i++) {
            if(this.field[0][i] == 1) this.field[0][i] = 3;
        }

        for (int i = 0; i < this.field[this.field.length-1].length; i++) {
            if(this.field[this.field.length-1][i] == 2) this.field[this.field.length-1][i] = 4;
        }
    }

    private boolean isInField(int x, int y) {
        return x>= 0 && y >= 0 && x < getRows() && y < getColumns();
    }

    public ArrayList<Coordinate> getValidMoves() {
       return validMoves;
    }

}
