package com.company.logic;

import java.util.ArrayList;

public class AI {
    private static int[][] field;
    private static ArrayList<Move> stonePositions;
    private static Move best;

    public static Move getBest(int[][] field) {
        AI.field = field;
        AI.stonePositions = new ArrayList<>();
        minimax(2,true, field);
        return best;
    }

    private static int[] remainingStones(int[][] field) {
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

    private static int status(int[][] field) {
        int[] stonesCount = remainingStones(field);
        return stonesCount[1] - stonesCount[0];
    }

    private static void getAllStonesPosition(boolean black, int[][] field) {
        ArrayList<Move> stones = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if(black && field[j][i] == 2) stones.add(new Move(new Coordinate(i,j), false));
                else if(black && field[j][i] == 4) stones.add(new Move(new Coordinate(i,j), true));
                else if(!black && field[j][i] == 1) stones.add(new Move(new Coordinate(i,j), false));
                else if(!black && field[j][i] == 3) stones.add(new Move(new Coordinate(i,j), false));
            }
        }
        AI.stonePositions = stones;
    }

    private static int minimax(int depth, boolean black, int[][] field) {
        if(depth == 0 || remainingStones(field)[0] == 0 || remainingStones(field)[1] == 0){
            return status(field);
        }
        if(black) {
            int maxStatus = Integer.MIN_VALUE;
            for (Move move : getAllValidMoves(true, field)) {
                int[][] tmpField = new int[AI.field.length][AI.field.length];
                for (int i = 0; i < AI.field.length; i++) {
                    System.arraycopy(AI.field[i], 0, tmpField[i], 0, AI.field[i].length);
                }
                tryMove(move, tmpField);
                int status = minimax(depth-1,false, tmpField);
                maxStatus = Math.max(maxStatus, status);
                if(maxStatus == status) {
                    AI.best = move;
                }
            }
            return maxStatus;
        } else {
            int minStatus = Integer.MAX_VALUE;
            for (Move move : getAllValidMoves(false, field)) {
                int[][] tmpField = new int[AI.field.length][AI.field.length];
                for (int i = 0; i < AI.field.length; i++) {
                    System.arraycopy(AI.field[i], 0, tmpField[i], 0, AI.field[i].length);
                }
                tryMove(move, field);
                int status = minimax(depth-1,true, field);
                minStatus = Math.min(minStatus, status);
                if(minStatus == status) {
                    AI.best = move;
                }
            }
            return minStatus;
        }
    }

    private static void tryMove(Move move, int[][] field) {
        if(move.isKing()) field[move.getTo().getY()][move.getTo().getX()] = 4;
        else field[move.getTo().getY()][move.getTo().getX()] = 2;
        field[move.getFrom().getY()][move.getFrom().getX()] = 0;
        AI.removeStone(move.getTo().getX(), move.getTo().getY(), move.getFrom().getX(), move.getFrom().getY(), field);
    }

    private static ArrayList<Move> getAllValidMoves(boolean black, int[][] field) {
        ArrayList<Move> moves = new ArrayList<>();
        getAllStonesPosition(black, field);
        for (Move st : AI.stonePositions) {
            ArrayList<Coordinate> validMoves = Utils.updateValidMoves(black ? (st.isKing() ? 4 : 2 ) : (st.isKing() ? 3 : 1 ), st.getFrom(), field);
            for (Coordinate move : validMoves) {
                st.setTo(move);
                moves.add(st);
            }
        }
        return moves;
    }

    private static void removeStone(int x, int y, int x1, int y1, int[][] field) {
        int smerX = (x-x1) / Math.abs(x-x1);
        int smerY = (y-y1) / Math.abs(y-y1);
        for (int i = 1; i < Math.abs(x-x1); i++) {
            field[ y-i*smerY][x - i*smerX] = 0;
        }
    }
}