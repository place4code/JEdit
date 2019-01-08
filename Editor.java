package com.company;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.print.PrinterException;
import java.io.*;

public class Editor extends JFrame {

    private JEditorPane textArea;
    private RTFEditorKit rtf;
    private int minWidth = 800;
    private int minHeight = 600;
    private MyActions newFile, saveFile, closeApp, openFile, printFile;


    //###################################################################################
    //################################################################   Constructor:

    public Editor(String title) {
        super(title); // constructor JFrame with title

        textArea = new JEditorPane();
        rtf = new RTFEditorKit();
        textArea.setEditorKit(rtf);

        this.setLayout(new BorderLayout()); // new layout
        this.add(new JScrollPane(textArea), BorderLayout.CENTER); // add area to layout


        JMenuBar menuBar = new JMenuBar(); // create a menu bar

        JMenu file = new JMenu("File");

        newFile = new MyActions("New...", new ImageIcon("icons/New24.gif"), "New file", KeyStroke.getKeyStroke('N',InputEvent.CTRL_DOWN_MASK), "newFile");
        file.add(newFile);

        openFile = new MyActions("Open...", new ImageIcon("icons/Open24.gif"), "Open file", KeyStroke.getKeyStroke('O',InputEvent.CTRL_DOWN_MASK), "openFile");
        file.add(openFile);

        saveFile = new MyActions("Save...", new ImageIcon("icons/Save24.gif"), "Save file", KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK), "saveFile");
        file.add(saveFile);

        file.addSeparator();

        printFile = new MyActions("Print...", new ImageIcon("icons/Print24.gif"), "Print file", KeyStroke.getKeyStroke('P',InputEvent.CTRL_DOWN_MASK), "printFile");
        file.add(printFile);

        file.addSeparator();

        closeApp = new MyActions("Close App", new ImageIcon("icons/Exit24.gif"), "Close App", null, "closeApp");
        file.add(closeApp);

        menuBar.add(file);

        this.setJMenuBar(menuBar); // set a menu bar
        this.add(toolBar(), BorderLayout.NORTH);

        this.setMinimumSize(new Dimension(minWidth, minHeight)); // size of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // frame is centered on the screen

        ImageIcon iconImage;
        iconImage = new ImageIcon(this.getClass().getResource("contract.png"));

        this.setIconImage(iconImage.getImage());


    }

    //###################################################################################
    //#####################################################################   Methods:

    public void newFile() {
        System.out.println("new File method");
        if (JOptionPane.showConfirmDialog(this, "New file? Any unsaved data will be lost.", "Confirm...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
            textArea.setText("");
        }
    }

    public void openFile() {
        System.out.println("open File method");

        EditorDialog dialog = new EditorDialog();
        File file = dialog.selectFile("open");



        try {
            InputStream inputStream = new FileInputStream(file);
            rtf.read(inputStream, textArea.getDocument(), 0);
            textArea.read(new FileReader(file), null);
        } catch (IOException | BadLocationException e) {
            JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void saveFile() {
        System.out.println("save File method");

        EditorDialog dialog = new EditorDialog();
        File file = dialog.selectFile("save");

        if (file != null) {
            try {
                OutputStream output = new FileOutputStream(file);
                rtf.write(output, textArea.getDocument(), 0, textArea.getDocument().getLength());
            } catch (IOException | BadLocationException e) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void printFile() {
        try {
            textArea.print();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Beim Drucken hat es ein Problem gegeben", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public void closeApp() {
        System.exit(0);
    }


    private JToolBar toolBar() {

        JToolBar bar = new JToolBar();

        bar.add(newFile);
        bar.add(openFile);
        bar.add(saveFile);

        bar.addSeparator();

        Action boldAction = new StyledEditorKit.BoldAction();
        boldAction.putValue(Action.SHORT_DESCRIPTION, "Bold");
        boldAction.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/Bold24.gif"));
        bar.add(boldAction);

        Action italicAction = new StyledEditorKit.ItalicAction();
        italicAction.putValue(Action.SHORT_DESCRIPTION, "Italic");
        italicAction.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/Italic24.gif"));
        bar.add(italicAction);

        Action underlineAction = new StyledEditorKit.UnderlineAction();
        underlineAction.putValue(Action.SHORT_DESCRIPTION, "Underline");
        underlineAction.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/Underline24.gif"));
        bar.add(underlineAction);

        bar.addSeparator();

        //gewünschte Ausrichtung übergeben
        Action alignLeft = new StyledEditorKit.AlignmentAction("Linksbündig", StyleConstants.ALIGN_LEFT);
        alignLeft.putValue(Action.SHORT_DESCRIPTION, "Linksbündig ausrichten");
        alignLeft.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/AlignLeft24.gif"));
        bar.add(alignLeft);

        //das Symbol für die zentrierte Ausrichtung
        Action alignCenter = new StyledEditorKit.AlignmentAction("Zentriert", StyleConstants.ALIGN_CENTER);
        alignCenter.putValue(Action.SHORT_DESCRIPTION, "Zentriert ausrichten");
        alignCenter.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/AlignCenter24.gif"));
        bar.add(alignCenter);

        //das Symbol für die rechtsbündige Ausrichtung
        Action alignRight = new StyledEditorKit.AlignmentAction("Rechts", StyleConstants.ALIGN_RIGHT);
        alignRight.putValue(Action.SHORT_DESCRIPTION, "Rechtsbündig ausrichten");
        alignRight.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/AlignRight24.gif"));
        bar.add(alignRight);

        //das Symbol für den Blocksatz
        Action alignJustify = new StyledEditorKit.AlignmentAction("Blocksatz", StyleConstants.ALIGN_JUSTIFIED);
        alignJustify.putValue(Action.SHORT_DESCRIPTION, "Im Blocksatz ausrichten");
        alignJustify.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/AlignJustify24.gif"));
        bar.add(alignJustify);

        bar.addSeparator();
        bar.addSeparator();

        bar.add(printFile);

        return bar;
    }

//###################################################################################
//##################################################################   In-Classes:

    public class MyActions extends AbstractAction {

        public MyActions(String text, ImageIcon icon, String tipp, KeyStroke shortcut, String actionText) {
            super(text, icon);

            this.putValue(SHORT_DESCRIPTION, tipp);
            this.putValue(ACCELERATOR_KEY, shortcut);
            this.putValue(ACTION_COMMAND_KEY, actionText);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("newFile")) {
                newFile();
            }
            if (e.getActionCommand().equals("openFile")) {
                openFile();
            }
            if (e.getActionCommand().equals("saveFile")) {
                saveFile();
            }
            if (e.getActionCommand().equals("printFile")) {
                printFile();
            }
            if (e.getActionCommand().equals("closeApp")) {
                closeApp();
            }
        }
    }

//###################################################################################

}
