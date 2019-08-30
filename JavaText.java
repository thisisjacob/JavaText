import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.io.FileWriter;
import java.io.FileReader;


public class JavaText extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private boolean isStreamOpen;

    private JFrame baseWindow;
    private JScrollPane mainTextScrollPane;
    private JTextArea mainText;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem save;
    private JMenuItem load;
    //private JFileChooser fileChooser;
    private FileWriter writeFile;
    private FileReader readFile;

    public static void main(String[] args) {
        new JavaText();
    }

    public class TextArea {

    }

    public JavaText() {
        super("JavaText");

        // creates main window of program
        baseWindow = new JFrame(); 
        baseWindow.setSize(WIDTH, HEIGHT);
        baseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseWindow.setLayout(new BorderLayout());
        
        // text area nestled within a scroll pane
        mainText = new JTextArea(20, 20);

        mainTextScrollPane = new JScrollPane(mainText);
        baseWindow.setLayout(new BorderLayout());
        mainTextScrollPane.setBounds(0, 0 , 400, 400);
        mainTextScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainTextScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainTextScrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // menu bar created, menu items added
        menuBar = new JMenuBar();
        file = new JMenu("File");
        menuBar.add(file);
        
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        //fileChooser = new JFileChooser();


        // filereader created for reading items in a file to jtextarea
        try {
            readFile = new FileReader("javatextwrite");
        }
        catch(IOException exc) {
            System.out.println("IO exception when creating new FileReader. Ending program.");
            System.exit(0);
        }

        // creates new FileWriter object, writes text currently in mainText to a file named javatextwrite
        // when save button is pressed
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                        writeFile = new FileWriter("javatextwrite", false);
                        writeFile.write(mainText.getText());
                        writeFile.flush();
                        writeFile.close();
                }
                catch (IOException exc) {
                    System.out.println(exc);
                    System.exit(0);
                }
            }
        });

        // reads to file when save menu item is pressed
        /** 
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                }
                catch (IOException exc) {
                    System.out.println("IO exception when writing to file. Ending program.");
                    System.exit(0);
                }
            }
        });*/
        
        // adds menu items to menu bar
        //load.addActionListener(this);
        file.add(save);
        file.add(load);


        // adds menubar and scrollpane to main window, makes visible
        baseWindow.add(menuBar, BorderLayout.NORTH);
        baseWindow.add(mainTextScrollPane, BorderLayout.CENTER);
        baseWindow.setVisible(true);
    }
}