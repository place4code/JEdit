package com.company;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.*;

public class Editor extends JFrame {

    private JEditorPane textArea;
    private RTFEditorKit rtf;
    private int minWidth = 600;
    private int minHeight = 800;
    private MyActions newFile, saveFile, closeApp, openFile, printFile, saveFileAs, infoItem;
    private File file;
    private JPopupMenu popupMenu;
    final private String INFO = "Author: Lukasz Romankiewicz \n"
                                + "version 2.0";


    //###################################################################################
    //################################################################   Constructor:

    public Editor(String title) {
        super(title); // constructor JFrame with title

        this.setLayout(new BorderLayout()); // new layout

        textArea = new JEditorPane();
        rtf = new RTFEditorKit();
        textArea.setEditorKit(rtf);


        this.add(new JScrollPane(textArea), BorderLayout.CENTER); // add area to layout


        JMenuBar menuBar = new JMenuBar(); // create a menu bar

        JMenu file = new JMenu("File");



        newFile = new MyActions("New", new ImageIcon("icons/New24.png"), "New file", KeyStroke.getKeyStroke('N',InputEvent.CTRL_DOWN_MASK), "newFile");
        file.add(newFile);

        openFile = new MyActions("Open", new ImageIcon("icons/Open24.png"), "Open file", KeyStroke.getKeyStroke('O',InputEvent.CTRL_DOWN_MASK), "openFile");
        file.add(openFile);

        saveFile = new MyActions("Save", new ImageIcon("icons/Save24.png"), "Save file", KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK), "saveFile");
        file.add(saveFile);

        saveFileAs = new MyActions("Save as...", null, "", null, "saveFileAs");
        file.add(saveFileAs);

        file.addSeparator();

        printFile = new MyActions("Print", new ImageIcon("icons/Print24.png"), "Print file", KeyStroke.getKeyStroke('P',InputEvent.CTRL_DOWN_MASK), "printFile");
        file.add(printFile);

        file.addSeparator();

        JMenu info = new JMenu("Info");
        infoItem = new MyActions("About", new ImageIcon("icons/Information24.png"), "About", null, "about");
        info.add(infoItem);
        file.add(info);


        closeApp = new MyActions("Close App", new ImageIcon("icons/Exit24.png"), "Close App", null, "closeApp");
        file.add(closeApp);

        menuBar.add(file);

        this.setJMenuBar(menuBar); // set a menu bar

        rightClick();

        textArea.addMouseListener(new Popup());

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

    private void rightClick() {
        popupMenu = new JPopupMenu();
        popupMenu.add(newFile);
        popupMenu.add(openFile);
        popupMenu.add(saveFile);


    }
    public void newFile() {
        System.out.println("new File method");
        if (JOptionPane.showConfirmDialog(this, "New file? Any unsaved data will be lost.", "Confirm...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
            textArea.setText("");
            file = null;
        }

    }

    public void openFile() {
        System.out.println("open File method");

        EditorDialog dialog = new EditorDialog();
        File fileLocal = dialog.selectFile("open");

        if (fileLocal != null) {
            try {
                InputStream inputStream = new FileInputStream(fileLocal);
                rtf.read(inputStream, textArea.getDocument(), 0);
                textArea.read(new FileReader(fileLocal), null);
                file = fileLocal;
            } catch (IOException | BadLocationException e) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }


    }

    public void saveFile() {
        System.out.println("save File method");

        if (file == null) {
            try {

                EditorDialog dialog = new EditorDialog();
                file = dialog.selectFile("save");

                OutputStream output = new FileOutputStream(file);
                rtf.write(output, textArea.getDocument(), 0, textArea.getDocument().getLength());
            } catch (IOException | BadLocationException e) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
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

    private void saveFileAs() {

        EditorDialog dialog = new EditorDialog();
        File fileLocal = dialog.selectFile("save");

        if (fileLocal != null) {
            file = fileLocal;
            saveFile();
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


    private void about() {
        JOptionPane.showMessageDialog(this, INFO, "About", 1);
    }


    private JToolBar toolBar() {

        JToolBar bar = new JToolBar();

        bar.add(newFile);
        bar.add(openFile);
        bar.add(saveFile);

        bar.addSeparator();

        Action boldAction = new StyledEditorKit.BoldAction();
        boldAction.putValue(Action.SHORT_DESCRIPTION, "Bold");
        boldAction.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/Bold24.png"));
        bar.add(boldAction);

        Action italicAction = new StyledEditorKit.ItalicAction();
        italicAction.putValue(Action.SHORT_DESCRIPTION, "Italic");
        italicAction.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/Italic24.png"));
        bar.add(italicAction);

        Action underlineAction = new StyledEditorKit.UnderlineAction();
        underlineAction.putValue(Action.SHORT_DESCRIPTION, "Underline");
        underlineAction.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/Underline24.png"));
        bar.add(underlineAction);

        bar.addSeparator();

        //gewünschte Ausrichtung übergeben
        Action alignLeft = new StyledEditorKit.AlignmentAction("Linksbündig", StyleConstants.ALIGN_LEFT);
        alignLeft.putValue(Action.SHORT_DESCRIPTION, "Linksbündig ausrichten");
        alignLeft.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/align_left.png"));
        bar.add(alignLeft);

        //das Symbol für die zentrierte Ausrichtung
        Action alignCenter = new StyledEditorKit.AlignmentAction("Zentriert", StyleConstants.ALIGN_CENTER);
        alignCenter.putValue(Action.SHORT_DESCRIPTION, "Zentriert ausrichten");
        alignCenter.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/align_center.png"));
        bar.add(alignCenter);

        //das Symbol für die rechtsbündige Ausrichtung
        Action alignRight = new StyledEditorKit.AlignmentAction("Rechts", StyleConstants.ALIGN_RIGHT);
        alignRight.putValue(Action.SHORT_DESCRIPTION, "Rechtsbündig ausrichten");
        alignRight.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/align_right.png"));
        bar.add(alignRight);

        //das Symbol für den Blocksatz
        Action alignJustify = new StyledEditorKit.AlignmentAction("Blocksatz", StyleConstants.ALIGN_JUSTIFIED);
        alignJustify.putValue(Action.SHORT_DESCRIPTION, "Im Blocksatz ausrichten");
        alignJustify.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/align_justify.png"));
        bar.add(alignJustify);

        bar.addSeparator();
        bar.addSeparator();

        bar.add(printFile);
        bar.add(infoItem);

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
            if (e.getActionCommand().equals("saveFileAs")) {
                saveFileAs();
            }
            if (e.getActionCommand().equals("printFile")) {
                printFile();
            }
            if (e.getActionCommand().equals("closeApp")) {
                closeApp();
            }
            if (e.getActionCommand().equals("about")) {
                about();
            }
        }
    }

//###################################################################################

    class Popup extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);

            if (e.isPopupTrigger())
                popupMenu.show(e.getComponent(), e.getX(), e.getY());

        }
    }



}
