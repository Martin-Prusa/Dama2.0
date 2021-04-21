package com.company.logic;

public class Move {
    private Coordinate from;
    private Coordinate to;
    private boolean isKing;

    public Move(Coordinate c, boolean k) {
        this.from = c;
        this.isKing = k;
    }
    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    public void setTo(Coordinate validMove) {
        this.to = validMove;
    }

    public boolean isKing() {
        return isKing;
    }
}
