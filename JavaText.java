import javax.swing.*;
import java.awt.*;

public class JavaText extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    public static void main(String[] args) {
        JavaText currentRun = new JavaText();
    }

    public JavaText() {
        JFrame baseWindow = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");


        baseWindow.setSize(WIDTH, HEIGHT);
        baseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseWindow.setLayout(new BorderLayout());



        
        menuBar.add(file);
        
        file.add(save);
        file.add(load);



        baseWindow.add(menuBar, BorderLayout.NORTH);

        baseWindow.setVisible(true);

    }
}