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
    private JScrollPane mainTextScrollPane; // scrollbar for when text goes off screen
    private JTextArea mainText; // text area user modifies
    private JMenuBar menuBar;
    private JMenu file; // file dropdown menu
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem exit;
    private JMenu formatMenu;
    private JMenu lineWrap;
    private ButtonGroup lineWrapGroup;
    private JRadioButtonMenuItem lineWrapTrue;
    private JRadioButtonMenuItem lineWrapFalse;
    private JMenu wrapStyle;
    private ButtonGroup wrapStyleGroup;
    private JRadioButtonMenuItem wrapStyleTrue;
    private JRadioButtonMenuItem wrapStyleFalse;
    private JMenuItem openTextAppearanceMenu;
    



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

        // adds line wrap options to formatting menu
        lineWrap = new JMenu("Line Wrapping");
        lineWrapTrue = new JRadioButtonMenuItem("Line Wrap On");
        lineWrapTrue.addActionListener(new ActionListener() { // for enabling linewrap
            public void actionPerformed(ActionEvent e) {
                mainText.setLineWrap(true);
            }
        });
        lineWrapFalse = new JRadioButtonMenuItem("Line Wrap Off");
        lineWrapFalse.addActionListener(new ActionListener() { // for disabling linewrap
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

        wrapStyle = new JMenu("Wrap Style");
        wrapStyleTrue = new JRadioButtonMenuItem("Word Wrap");
        wrapStyleTrue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainText.setWrapStyleWord(true);
            }
        });
        wrapStyleFalse = new JRadioButtonMenuItem("Character Wrap");
        wrapStyleFalse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainText.setWrapStyleWord(false);
            }
        });
        wrapStyleGroup = new ButtonGroup();
        wrapStyleGroup.add(wrapStyleTrue);
        wrapStyleGroup.add(wrapStyleFalse);
        wrapStyle.add(wrapStyleTrue);
        wrapStyle.add(wrapStyleFalse);
        formatMenu.add(wrapStyle);

        openTextAppearanceMenu = new JMenuItem("Font..."); // adds font window button to text appearance menu
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
        // closes if IOException caught
        try {
            JFileChooser savePath = new JFileChooser();
            FileWriter writeFile;
            int chosenPath = savePath.showSaveDialog(null);

            // if user chooses a file, the contents of mainText overwrite this file
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
        // closes if IOException caught
        try {
            JFileChooser loadPath = new JFileChooser();
            FileReader readFile;
            int chosenPath = loadPath.showSaveDialog(null);

            // if user chooses a file to open, the items in the file are appended one by one into mainText
            if (chosenPath == loadPath.APPROVE_OPTION) {
                readFile = new FileReader(loadPath.getSelectedFile().getAbsolutePath());
                mainText.setText("");
                int i;
                while((i = readFile.read()) != -1) {                                                   
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
        
        // loads available fonts on program run
        private String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); 
        private String[] availableStyles = {"Plain", "Bold", "Italic"};
        private Integer[] availableSizes = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 22, 24, 26, 27, 28, 29, 30, 32, 34, 36}; // avaible font sizes

        JFrame mainWindow;
        JTextField previewText; // previewing changes
        JScrollPane fontSelectScroll; // for selecting font of text
        JList<String> fontSelect;
        JScrollPane styleSelectScroll; // for selecting style of text
        JList<String> styleSelect;
        JScrollPane fontSizeSelectScroll; // for selecting size of text
        JList<Integer> fontSizeSelect;
        JPanel bottomButtonPanel; // confirm/cancel panel
        JButton confirmSelections; // confirms, makes chosen changes, closes window
        JButton cancelSelections; // cancels, no changes made, closes window

        private TextAppearanceWindow() {
            super("Text Appearance Menu");

            mainWindow = new JFrame("Text Appearance Menu"); // creates main window
            mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainWindow.setLayout(new BorderLayout());
            mainWindow.setSize(WIDTH, HEIGHT);

            // initializes preview text
            previewText = new JTextField("Font Preview");
            previewText.setPreferredSize(new Dimension(WIDTH, 50)); // allows all font sizes to fit previewText field
            previewText.setEditable(false);
            previewText.setHorizontalAlignment(JTextField.CENTER);

            
            fontSelect = new JList<String>(availableFonts); // sets up fontSelect and its JScrollPane
            fontSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontSelect.setLayoutOrientation(JList.VERTICAL);
            fontSelectScroll = new JScrollPane(fontSelect);
            fontSelectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            fontSelectScroll.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 50));

            styleSelect = new JList<String>(availableStyles); // sets up styleSelect and its JScrollPane
            styleSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            styleSelect.setLayoutOrientation(JList.VERTICAL);
            styleSelectScroll = new JScrollPane(styleSelect);
            styleSelectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            styleSelectScroll.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 50));

            fontSizeSelect = new JList<Integer>(availableSizes); // sets up fontSizeSelect and its JScrollPane
            fontSizeSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontSizeSelect.setLayoutOrientation(JList.VERTICAL);
            fontSizeSelectScroll = new JScrollPane(fontSizeSelect);
            fontSizeSelectScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            fontSizeSelectScroll.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 50));

            fontSelect.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                    updatePreview();
                }
            });
            styleSelect.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                    updatePreview();
                }
            });
            fontSizeSelect.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                    updatePreview();
                }
            });

            bottomButtonPanel = new JPanel(); // creates a button panel at bottom, adds Confirm and Cancel buttons
            confirmSelections = new JButton("Confirm");
            confirmSelections.addActionListener(new ActionListener() { // saves font changes, closes window
                public void actionPerformed(ActionEvent e) {
                    confirmButtonAction();
                }
            });
            cancelSelections = new JButton("Cancel");
            cancelSelections.addActionListener(new ActionListener() { // closes window, does not change font
                public void actionPerformed(ActionEvent e) {
                    mainWindow.setVisible(false);
                    mainWindow.dispose();
                }
            });
            bottomButtonPanel.add(confirmSelections); 
            bottomButtonPanel.add(cancelSelections);

            // adds sections to areas of window
            mainWindow.add(previewText, BorderLayout.NORTH);
            mainWindow.add(fontSelectScroll, BorderLayout.WEST);
            mainWindow.add(styleSelectScroll, BorderLayout.CENTER);
            mainWindow.add(fontSizeSelectScroll, BorderLayout.EAST);
            mainWindow.add(bottomButtonPanel, BorderLayout.SOUTH);
        
            mainWindow.setVisible(true);
        }

        // updates the preview text
        private void updatePreview() {
            if (!fontSelect.isSelectionEmpty()) {
                Font currentFont = previewText.getFont(); // copys current mainText Font into currentFont
                previewText.setFont(new Font(fontSelect.getSelectedValue(), currentFont.getStyle(), currentFont.getSize())); // changes font based on fontSelect, others are kept as they are
            } 
            if (!styleSelect.isSelectionEmpty()) {
                Font currentFont = previewText.getFont();
                previewText.setFont(new Font(currentFont.getFontName(), findStyleConstant(styleSelect.getSelectedValue()), currentFont.getSize()));
            }
            if (!fontSizeSelect.isSelectionEmpty()) {
                Font currentFont = previewText.getFont();
                previewText.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), fontSizeSelect.getSelectedValue()));
            }


        }

        private void confirmButtonAction() { // if confirmed, change font to what user chose, close window
            if (!fontSelect.isSelectionEmpty()) {
                Font currentFont = mainText.getFont(); // copys current mainText Font into currentFont
                mainText.setFont(new Font(fontSelect.getSelectedValue(), currentFont.getStyle(), currentFont.getSize())); // changes font based on fontSelect, others are kept as they are
            }                                                                                                             // same for others, does each in turn based on if each list selector is empty or not
            if (!styleSelect.isSelectionEmpty()) {
                Font currentFont = mainText.getFont();
                mainText.setFont(new Font(currentFont.getFontName(), findStyleConstant(styleSelect.getSelectedValue()), currentFont.getSize()));
            }
            if (!fontSizeSelect.isSelectionEmpty()) {
                Font currentFont = mainText.getFont();
                mainText.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), fontSizeSelect.getSelectedValue()));
            }

            mainWindow.setVisible(false);
            mainWindow.dispose();
        }

        private int findStyleConstant(String givenStyleString) {
            if (givenStyleString.equals("Plain")) {
                return Font.PLAIN;
            }
            else if (givenStyleString.equals("Bold")) {
                return Font.BOLD;
            }
            else if (givenStyleString.equals("Italic")) {
                return Font.ITALIC;
            }

            return Font.PLAIN; // if not one of available fonts, return to plain default

        }

    }

}