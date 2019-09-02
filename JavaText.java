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

    private JFrame baseWindow;
    private JScrollPane mainTextScrollPane;
    private JTextArea mainText;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem save;
    private JMenuItem load;
    private FileWriter writeFile;
    private FileReader readFile;



    public static void main(String[] args) {
        new JavaText();
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

        // adds ActionListeners to save and load buttons
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });


        file.add(save);
        file.add(load);


        // adds menubar and scrollpane to main window, makes visible
        baseWindow.add(menuBar, BorderLayout.NORTH);
        baseWindow.add(mainTextScrollPane, BorderLayout.CENTER);
        baseWindow.setVisible(true);
    }

    // actions for menu buttons

    // creates a new FileWriter object to clear writeFile, saves text to file named javatextwrite
    public void saveFile() {
        try {
            JFileChooser savePath = new JFileChooser();
            int chosenPath = savePath.showSaveDialog(null);

            if (chosenPath == JFileChooser.APPROVE_OPTION) {
                writeFile = new FileWriter(savePath.getSelectedFile().getAbsolutePath(), false);
                writeFile.write(mainText.getText());
                writeFile.flush();
                writeFile.close();
            }   
        }
        catch (IOException exc) {
            System.out.println(exc);
            System.exit(0);
        }
    }

    // creates a new FileReader object to clear readFile, reads each token in javatextwrite, converts it to readable format and appends to mainText
    public void loadFile() {
        try {
            JFileChooser loadPath = new JFileChooser();
            int chosenPath = loadPath.showSaveDialog(null);

            if (chosenPath == loadPath.APPROVE_OPTION) {
                readFile = new FileReader(loadPath.getSelectedFile().getAbsolutePath());
                mainText.setText("");
                int i;
                while((i = readFile.read()) != -1) {
                    System.out.println("testing");
                    System.out.println(i);                                                      
                    mainText.append(Character.toString((char) i));
                } 
            }  
        }
        catch (IOException exc) {
            System.out.println("IO exception when writing to file. Ending program.");
            System.exit(0);
        }
    }
}