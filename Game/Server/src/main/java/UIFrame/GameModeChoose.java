package UIFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameModeChoose {
    private int choice;

    private JFrame app;

    public GameModeChoose() {
        app = new JFrame();
        choice = -1;
        JButton newGame = new JButton("开始游戏");
        JButton loadGame = new JButton("继续游戏");
        JButton onlineGame = new JButton("四人联机");
        JButton exitGame = new JButton("退出游戏");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choice = 1;
            }
        });
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choice = 2;
            }
        });
        onlineGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choice = 3;
            }
        });
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        app.add(newGame);
        app.add(loadGame);
        app.add(onlineGame);
        app.add(exitGame);
//        app.setLayout(new GridLayout(3, 1));
        app.setLayout(new FlowLayout());
        app.setSize(180, 180);
        app.setVisible(true);
        app.setLocation(200, 300);
        app.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public int getChoice() {
        return this.choice;
    }

    public void hidden() {
        app.setVisible(false);
    }

}
