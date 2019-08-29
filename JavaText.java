import javax.swing.*;
import java.awt.*;

public class JavaText extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private JFrame baseWindow;
    private JScrollPane mainTextScrollPane;
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
        baseWindow.setSize(WIDTH, HEIGHT);
        baseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseWindow.setLayout(new BorderLayout());
        

        mainText = new JTextArea(20, 20);

        mainTextScrollPane = new JScrollPane(mainText);
        baseWindow.setLayout(new BorderLayout());
        mainTextScrollPane.setBounds(0, 0 , 400, 400);
        mainTextScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainTextScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainTextScrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));


        menuBar = new JMenuBar();
        file = new JMenu("File");
        menuBar.add(file);
        
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        file.add(save);
        file.add(load);



        baseWindow.add(menuBar, BorderLayout.NORTH);
        baseWindow.add(mainTextScrollPane, BorderLayout.CENTER);
        baseWindow.setVisible(true);
    }
}