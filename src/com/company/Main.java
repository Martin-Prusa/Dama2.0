package com.company;

import com.company.logic.Dama;
import com.company.presentation.Frame;
import com.company.presentation.Panel;

public class Main {

    public static void main(String[] args) {
        Dama dama = new Dama();
        Frame frame = new Frame();
        Panel panel = new Panel(dama);
        frame.add(panel);
        frame.setVisible(true);
    }
}
