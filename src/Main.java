import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Main
{
    public static void main(String [] arg){

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setSize(1300, 900);
        frame.setLocation(0, 0);

        Game game = new Game(frame);
        frame.addKeyListener(game);
        frame.getContentPane().add(game);
        frame.setVisible(true);
    }
}
