package com.company.presentation;

import javax.swing.*;

import com.company.logic.Coordinate;
import com.company.logic.Dama;

import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel{
    private static JButton[][] buttons;

    public Panel(Dama logic) {
        buttons = new JButton[logic.getRows()][logic.getColumns()];
        setLayout(new GridLayout(logic.getRows(), logic.getColumns(), 0, 0));
        boolean black = true;
        for (int i = 0; i < logic.getRows(); i++) {
            black = !black;
            for (int j = 0; j < logic.getColumns(); j++) {
                boolean addAction = false;
                Button button = new Button();
                if(black && j%2 == 0) addAction = true;
                else if(!black && j%2 != 0) addAction = true;
                buttons[i][j] = button;
                add(button);
                int pI = i;
                int pJ = j;
                if(addAction) {
                    button.addActionListener(e -> {
                        logic.click(pJ, pI);
                        resetColor(buttons);
                        redraw(logic, buttons);
                    });
                }
            }
        }
        resetColor(buttons);
        redraw(logic, buttons);
    }

    private void redraw(Dama logic, JButton[][] buttons) {
        if(logic.p1Win()) JOptionPane.showMessageDialog(null, "White player won!");
        else if(logic.p2Win()) JOptionPane.showMessageDialog(null, "Black player won!");

        ArrayList<Coordinate> list = logic.getValidMoves();

        for (Coordinate coordinate : list) {
            buttons[coordinate.getY()][coordinate.getX()].setBackground(new Color(153, 51, 0));
        }

        for (int i = 0; i < buttons.length; i++) {
            for (int i1 = 0; i1 < buttons[i].length; i1++) {
                int value = logic.getValue(i1, i);
                if(value == 0) {
                    buttons[i][i1].setText("");
                } else if(value == 1) {
                    buttons[i][i1].setText("◉");
                    buttons[i][i1].setForeground(Color.WHITE);
                } else if(value == 2) {
                    buttons[i][i1].setText("◉");
                    buttons[i][i1].setForeground(Color.BLACK);
                }else if(value == 3) {
                    buttons[i][i1].setText("♜");
                    buttons[i][i1].setForeground(Color.WHITE);
                }else if(value == 4) {
                    buttons[i][i1].setText("♜");
                    buttons[i][i1].setForeground(Color.BLACK);
                } else if(value == 5) {
                    buttons[i][i1].setText("◉");
                    buttons[i][i1].setForeground(new Color(92, 49, 1));
                } else if(value == 6) {
                    buttons[i][i1].setText("◎");
                    buttons[i][i1].setForeground(new Color(92, 49, 1));
                }else if(value == 7) {
                    buttons[i][i1].setText("♜");
                    buttons[i][i1].setForeground(new Color(92, 49, 1));
                }else if(value == 8) {
                    buttons[i][i1].setText("♖");
                    buttons[i][i1].setForeground(new Color(92, 49, 1));
                }
            }
        }
    }

    private void resetColor(JButton[][] buttons) {
        boolean black = true;
        for (int i = 0; i < buttons.length; i++) {
            black = !black;
            for (int j = 0; j < buttons[i].length; j++) {
                if (black) {
                    if (j % 2 == 0) {
                        buttons[i][j].setBackground(new Color(204, 102, 0));
                    } else buttons[i][j].setBackground(new Color(255, 238, 204));
                } else {
                    if (j % 2 != 0) {
                        buttons[i][j].setBackground(new Color(204, 102, 0));
                    } else buttons[i][j].setBackground(new Color(255, 238, 204));
                }
            }
        }
    }
}
