package com.company.logic;

import java.util.ArrayList;

public class AI {
    private Move best;

    public Move getBest(int[][] field) {
        minimax(2,true, field);
        return best;
    }

    private int[] remainingStones(int[][] field) {
        int white = 0;
        int black = 0;

        for (int[] ints : field) {
            for (int anInt : ints) {
                if(anInt % 2 == 0 && anInt != 0) black++;
                else if(anInt % 2 != 0) white++;
            }
        }

        return new int[]{white, black};
    }

    private int status(int[][] field) {
        int[] stonesCount = remainingStones(field);
        return stonesCount[1] - stonesCount[0]*2;
    }

    private ArrayList<Move> getAllStonesPosition(boolean black, int[][] field) { //OK
        ArrayList<Move> stones = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if(black && field[j][i] == 2) stones.add(new Move(new Coordinate(i,j), false));
                else if(black && field[j][i] == 4) stones.add(new Move(new Coordinate(i,j), true));
                else if(!black && field[j][i] == 1) stones.add(new Move(new Coordinate(i,j), false));
                else if(!black && field[j][i] == 3) stones.add(new Move(new Coordinate(i,j), false));
            }
        }
        return stones;
    }
//TODO: alfa-beta pruning
    private int minimax(int h, boolean black, int[][] field) {
        int[] remainingStones = remainingStones(field);
        if(h == 0 || remainingStones[0] == 0 || remainingStones[1] == 0){
            return status(field);
        }

        IntegerFunction f;

        if(black) f = (a,b) -> Math.max(a,b);
        else f = (a,b) -> Math.min(a,b);

        int bestStatus = black ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ArrayList<Move> validMoves = getAllValidMoves(black, field);
        for (Move move : validMoves) {
            int[][] tmpField = new int[field.length][field.length];
            for (int i = 0; i < field.length; i++) System.arraycopy(field[i], 0, tmpField[i], 0, field[i].length);
            tryMove(move, tmpField, black);
            int status = minimax(h - 1, !black, tmpField);
            bestStatus = f.run(bestStatus, status);
            if (bestStatus == status && black) this.best = move;
        }
        return bestStatus;
    }

    private void tryMove(Move move, int[][] field, boolean black) {
        field[move.getTo().getY()][move.getTo().getX()] = black ? (move.isKing() ? 4 : 2 ) : (move.isKing() ? 3 : 1 );
        field[move.getFrom().getY()][move.getFrom().getX()] = 0;
        this.removeStone(move.getTo().getX(), move.getTo().getY(), move.getFrom().getX(), move.getFrom().getY(), field);
    }

    public ArrayList<Move> getAllValidMoves(boolean black, int[][] field) { //OK
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Move> stonePositions = getAllStonesPosition(black, field);
        for (Move st : stonePositions) {
            ArrayList<Coordinate> validMoves = Utils.updateValidMoves(black ? (st.isKing() ? 4 : 2 ) : (st.isKing() ? 3 : 1 ), st.getFrom(), field);
            for (Coordinate move : validMoves) {
                Move myMove = new Move(new Coordinate(st.getFrom().getX(), st.getFrom().getY()), st.isKing());
                myMove.setTo(move);
                moves.add(myMove);
            }
        }
        return moves;
    }

    private void removeStone(int x, int y, int x1, int y1, int[][] field) {
        int smerX = (x-x1) / Math.abs(x-x1);
        int smerY = (y-y1) / Math.abs(y-y1);
        for (int i = 1; i < Math.abs(x-x1); i++) {
            field[ y-i*smerY][x - i*smerX] = 0;
        }
    }
}


//        if(black) {
//            int maxStatus = Integer.MIN_VALUE;
//            ArrayList<Move> validMoves = getAllValidMoves(true, field);
//            for (Move move : validMoves) {
//                int[][] tmpField = new int[field.length][field.length];
//                for (int i = 0; i < field.length; i++) System.arraycopy(field[i], 0, tmpField[i], 0, field[i].length);
//                tryMove(move, tmpField, true);
//                int status = minimax(h-1,false, tmpField);
//                maxStatus = Math.max(maxStatus, status);
//                if(maxStatus == status) this.best = move;
//            }
//            return maxStatus;
//        } else {
//            int minStatus = Integer.MAX_VALUE;
//            ArrayList<Move> validMoves = getAllValidMoves(false, field);
//            for (Move move : validMoves) {
//                int[][] tmpField = new int[field.length][field.length];
//                for (int i = 0; i < field.length; i++) System.arraycopy(field[i], 0, tmpField[i], 0, field[i].length);
//                tryMove(move, tmpField, false);
//                int status = minimax(h-1,true, tmpField);
//                minStatus = Math.min(minStatus, status);
//            }
//            return minStatus;
//        }