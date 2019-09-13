import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.awt.GraphicsEnvironment;


public class JavaText extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

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
    private JMenuItem openTextAppearanceMenu;

    // loads available fonts on program run
    private String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); 
    
    private String fontName;
    private int fontStyle;
    private int fontSize;



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
        lineWrapGroup = new ButtonGroup();
        lineWrapGroup.add(lineWrapTrue);
        lineWrapGroup.add(lineWrapFalse);
        lineWrap.add(lineWrapTrue);
        lineWrap.add(lineWrapFalse);
        formatMenu.add(lineWrap);

        openTextAppearanceMenu = new JMenuItem("Font...");
        openTextAppearanceMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TextAppearanceWindow();
            }
        });
        formatMenu.add(openTextAppearanceMenu);

        menuBar.add(formatMenu);



        // adds menubar and scrollpane to main window, makes visible
        baseWindow.add(menuBar, BorderLayout.NORTH);
        baseWindow.add(mainTextScrollPane, BorderLayout.CENTER);
        baseWindow.setVisible(true);
    }

    // actions for menu buttons

    // creates a new FileWriter object to clear writeFile, saves text to user chosen file
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

    private class TextAppearanceWindow extends JFrame {
        final int WIDTH = 400;
        final int HEIGHT = 400;
        

        JFrame mainWindow;
        JScrollPane fontSelectScroll;
        JList<String> fontSelect;
        JPanel bottomButtonPanel;
        JButton confirmSelections;
        JButton cancelSelections;

        private TextAppearanceWindow() {
            super("Text Appearance Menu");

            mainWindow = new JFrame("Text Appearance Menu");
            mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainWindow.setLayout(new BorderLayout());
            mainWindow.setSize(WIDTH, HEIGHT);

            
            fontSelect = new JList<String>(availableFonts);
            fontSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontSelect.setLayoutOrientation(JList.VERTICAL);
            fontSelectScroll = new JScrollPane(fontSelect);
            fontSelectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            fontSelectScroll.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 50));

            bottomButtonPanel = new JPanel();
            confirmSelections = new JButton("Confirm");
            confirmSelections.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    confirmButtonAction();
                }
            });
            cancelSelections = new JButton("Cancel");
            cancelSelections.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mainWindow.setVisible(false);
                    mainWindow.dispose();
                }
            });
            bottomButtonPanel.add(confirmSelections);
            bottomButtonPanel.add(cancelSelections);

            mainWindow.add(fontSelectScroll, BorderLayout.WEST);
            mainWindow.add(bottomButtonPanel, BorderLayout.SOUTH);
        
            mainWindow.setVisible(true);
        }

        private void confirmButtonAction() {
            if (!fontSelect.isSelectionEmpty()) {
                mainText.setFont(new Font(fontSelect.getSelectedValue(), Font.PLAIN, 11));
            }
            mainWindow.setVisible(false);
            mainWindow.dispose();
        }
    }

}