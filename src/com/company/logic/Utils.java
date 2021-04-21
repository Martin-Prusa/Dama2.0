package com.company.logic;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<Coordinate> updateValidMoves(int type, Coordinate coord, int[][] field) {
        ArrayList<Coordinate> validMoves = new ArrayList<>();
        if (type == 1 || type == 2) checkNormal(type, -1, coord, validMoves, field);
        else if (type == 3 || type == 4) {
            checkDama(type, 1, 1, coord, field, validMoves);
            checkDama(type, -1, -1, coord, field, validMoves);
            checkDama(type, 1, -1, coord, field, validMoves);
            checkDama(type, -1, 1, coord, field, validMoves);
        }
        return validMoves;
    }


    private static void checkNormal(int type, int jump, Coordinate coord2, ArrayList<Coordinate> validMoves, int[][] field) {
        int direction = 1;
        if (type == 1) direction = -1;
        if (jump == -1 && isInField(coord2.getX() + 1, coord2.getY() + direction, field) && getValue(coord2.getX() + 1, coord2.getY() + direction, field) == 0)
            validMoves.add(new Coordinate(coord2.getX() + 1, coord2.getY() + direction));
        if (jump == -1 && isInField(coord2.getX() - 1, coord2.getY() + direction, field) && getValue(coord2.getX() - 1, coord2.getY() + direction, field) == 0)
            validMoves.add(new Coordinate(coord2.getX() - 1, coord2.getY() + direction));
        if ((jump == -1 || jump == 1) && isInField(coord2.getX() + 1, coord2.getY() + direction, field) && getValue(coord2.getX() + 1, coord2.getY() + direction, field) % 2 == 1 && isInField(coord2.getX() + 2, coord2.getY() + direction * 2, field) && getValue(coord2.getX() + 2, coord2.getY() + direction * 2, field) == 0 && type == 2) {
            validMoves.add(new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2));
            checkNormal(type, 1, new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2), validMoves, field);
        } else if ((jump == -1 || jump == 2) && isInField(coord2.getX() + 1, coord2.getY() + direction, field) && getValue(coord2.getX() + 1, coord2.getY() + direction, field) % 2 == 0 && getValue(coord2.getX() + 1, coord2.getY() + direction, field) != 0 && isInField(coord2.getX() + 2, coord2.getY() + direction * 2, field) && getValue(coord2.getX() + 2, coord2.getY() + direction * 2, field) == 0 && type == 1) {
            validMoves.add(new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2));
            checkNormal(type, 2, new Coordinate(coord2.getX() + 2, coord2.getY() + direction * 2), validMoves, field);
        }
        if ((jump == -1 || jump == 3) && isInField(coord2.getX() - 1, coord2.getY() + direction, field) && getValue(coord2.getX() - 1, coord2.getY() + direction, field) % 2 == 1 && isInField(coord2.getX() - 2, coord2.getY() + direction * 2, field) && getValue(coord2.getX() - 2, coord2.getY() + direction * 2, field) == 0 && type == 2) {
            validMoves.add(new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2));
            checkNormal(type, 3, new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2), validMoves, field);
        } else if ((jump == -1 || jump == 4) && isInField(coord2.getX() - 1, coord2.getY() + direction, field) && getValue(coord2.getX() - 1, coord2.getY() + direction, field) % 2 == 0 && getValue(coord2.getX() - 1, coord2.getY() + direction, field) != 0 && isInField(coord2.getX() - 2, coord2.getY() + direction * 2, field) && getValue(coord2.getX() - 2, coord2.getY() + direction * 2, field) == 0 && type == 1) {
            validMoves.add(new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2));
            checkNormal(type, 4, new Coordinate(coord2.getX() - 2, coord2.getY() + direction * 2), validMoves, field);
        }
    }

    private static void checkDama(int type, int x, int y, Coordinate coord, int[][] field, ArrayList<Coordinate> validMoves) {
        int acX = coord.getX() + x;
        int acY = coord.getY() + y;
        while (isInField(acX, acY, field)) {
            if (type % 2 != 0 && field[acY][acX] % 2 != 0) {
                break;
            } else if (type % 2 == 0 && field[acY][acX] % 2 == 0 && type != 0 && field[acY][acX] % 2 == 0 && field[acY][acX] != 0) {
                break;
            } else if (field[acY][acX] == 0) {
                validMoves.add(new Coordinate(acX, acY));
            }
            acX += x;
            acY += y;
        }
    }

    private static boolean isInField(int x, int y, int[][] field) {
        return x >= 0 && y >= 0 && x < field.length && y < field.length;
    }

    private static int getValue(int x, int y, int[][] field) {
        return field[y][x];
    }
}
