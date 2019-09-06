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
    private JMenuItem exit;
    private FileWriter writeFile;
    private FileReader readFile;
    private JMenu formatMenu;
    private JMenu lineWrap;
    private ButtonGroup lineWrapGroup;
    private JRadioButtonMenuItem lineWrapTrue;
    private JRadioButtonMenuItem lineWrapFalse;
    private JButton editText;



    public static void main(String[] args) {
        new JavaText();
    }


    public JavaText() {
        super("JavaText");

        // creates main window of program
        baseWindow = new JFrame("JavaText"); 
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
        exit = new JMenuItem("Exit");

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
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(save);
        file.add(load);
        file.add(exit);

        // sets up formatting menu
        formatMenu = new JMenu("Formatting");
        lineWrap = new JMenu("Line Wrapping");
        lineWrapTrue = new JRadioButtonMenuItem("Line Wrap On");
        lineWrapTrue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainText.setLineWrap(true);
            }
        });
        lineWrapFalse = new JRadioButtonMenuItem("Line Wrap Off");
        lineWrapFalse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainText.setLineWrap(false);
            }
        });
        editText = new JButton("Text Appearance");
        editText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new JavaTextStyleMenu();
            }
        });

        lineWrapGroup = new ButtonGroup();
        lineWrapGroup.add(lineWrapTrue);
        lineWrapGroup.add(lineWrapFalse);
        lineWrap.add(lineWrapTrue);
        lineWrap.add(lineWrapFalse);
        formatMenu.add(lineWrap);
        formatMenu.add(editText);
        menuBar.add(formatMenu);





        // adds menubar and scrollpane to main window, makes visible
        baseWindow.add(menuBar, BorderLayout.NORTH);
        baseWindow.add(mainTextScrollPane, BorderLayout.CENTER);
        baseWindow.setVisible(true);
    }

    // actions for menu buttons

    // creates a new FileWriter object to clear writeFile, saves text to user chosen file
    // IMPORTANT: OVERWRITES ORIGINAL FILE
    public void saveFile() {
        try {
            JFileChooser savePath = new JFileChooser();
            int chosenPath = savePath.showSaveDialog(null);

            // writes the entire mainText to chosen file, overwriting original
            if (chosenPath == JFileChooser.APPROVE_OPTION) {
                writeFile = new FileWriter(savePath.getSelectedFile().getAbsolutePath(), false);
                writeFile.write(mainText.getText());
                writeFile.flush();
                writeFile.close();
            }   
        }
        catch (IOException exc) {
            System.out.println("IO exception when writing to file. Ending program.");
            System.exit(0);
        }
    }

    // creates a new FileReader object to clear readFile, reads each token in javatextwrite, converts it to readable format and appends to mainText
    public void loadFile() {
        try {
            JFileChooser loadPath = new JFileChooser();
            int chosenPath = loadPath.showSaveDialog(null);

            // reads each token in user chosen file, appends to mainText
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
        } // ends program if there is an error while reading
        catch (IOException exc) {
            System.out.println("IO exception when reading to file. Ending program.");
            System.exit(0);
        }
    }

    private class JavaTextStyleMenu extends JFrame {
        private final int WIDTH = 200;
        private final int HEIGHT = 200;

        private JFrame mainStyleWindow;
        private JScrollPane firstSelectorScrollPane;
        private JList firstSelector;
        private JScrollPane secondSelectorScrollPane;
        private JList secondSelector;
        private JScrollPane thirdSelectorScrollPane;
        private JList thirdSelector;
        private JTextArea displayArea;
        private JButton accept;
        private JButton cancel;

        // set up for a menu for changing style of text
        public JavaTextStyleMenu() { 
            // creates an array of available fonts on local computer
            String availableFonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            System.out.println(availableFonts);

            // sets up main window
            mainStyleWindow = new JFrame("Change Text Appearance");
            mainStyleWindow.setSize(WIDTH, HEIGHT);
            mainStyleWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainStyleWindow.setLayout(new BorderLayout());
            mainStyleWindow.setVisible(true);

            firstSelectorScrollPane = new JScrollPane();
            firstSelector = new JList(availableFonts);
            firstSelectorScrollPane.add(firstSelector);
            mainStyleWindow.add(firstSelectorScrollPane, BorderLayout.WEST);
        }

    }

}