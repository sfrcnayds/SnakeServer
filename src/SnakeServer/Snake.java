package SnakeServer;

import javax.swing.JFrame;

public class Snake extends JFrame {
    public static Board board;
    public Snake() {
        
        initUI();
    }
    
    private void initUI() {
        Board b = new Board();
        board = b;
        add(b);
        setResizable(false);
        pack();
        setTitle("SnakeServer");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
