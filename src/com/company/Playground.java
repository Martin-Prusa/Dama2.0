package com.company;

import com.company.logic.AI;
import com.company.logic.Move;


public class Playground {
    public static void main(String[] args) {
        AI a = new AI();
        Move m = a.getBest(
                new int[][]{
                        {0,2,0,2,0,2,0,2},
                        {2,0,2,0,2,0,2,0},
                        {0,2,0,2,0,2,0,2},
                        {0,0,1,0,1,0,0,0},
                        {0,0,0,0,0,0,0,0},
                        {0,0,1,0,1,0,1,0},
                        {0,1,0,1,0,1,0,1},
                        {1,0,1,0,1,0,1,0}
                }
        );



    }
}

//System.out.println(validMove.getFrom().getX()+" "+m.getFrom().getY());
//        System.out.println(m.getTo().getX()+" "+m.getTo().getY());