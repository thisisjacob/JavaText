import javax.swing.*;
import java.awt.*;

public class JavaText extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private JFrame baseWindow;
    private JTextArea mainText;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem save;
    private JMenuItem load;

    public static void main(String[] args) {
        new JavaText();
    }

    public class TextArea {

    }

    public JavaText() {
        super("JavaText");

        baseWindow = new JFrame();
        mainText = new JTextArea(40, 40);
        menuBar = new JMenuBar();
        file = new JMenu("File");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");


        baseWindow.setSize(WIDTH, HEIGHT);
        baseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseWindow.setLayout(new BorderLayout());

        mainText.setBounds(0, 0, 400, 400);
        mainText.append("testing testing one two three /n\n sfdadsafasdfsd\n\n\n dfasdds");


        
        menuBar.add(file);
        
        file.add(save);
        file.add(load);



        baseWindow.add(menuBar, BorderLayout.NORTH);
        baseWindow.add(mainText, BorderLayout.CENTER);
        baseWindow.setVisible(true);

        

    }
}