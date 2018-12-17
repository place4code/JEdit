package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.*;

public class Editor extends JFrame {
    private JTextArea textArea;

    private int w = 800;
    private int h = 600;

    public Editor(String title) {
        super(title);
        setLayout(new BorderLayout());
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        MyListener listener = new MyListener();
        //####################################### menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem itemNew = new JMenuItem("New");
        itemNew.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
        itemNew.setIcon(new ImageIcon("icons/New24.gif"));
        itemNew.setActionCommand("new");
        itemNew.addActionListener(listener);

        JMenuItem itemOpen = new JMenuItem("Open");
        itemOpen.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
        itemOpen.setIcon(new ImageIcon("icons/Open24.gif"));
        itemOpen.setActionCommand("open");
        itemOpen.addActionListener(listener);

        JMenuItem itemSave = new JMenuItem("Save");
        itemSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        itemSave.setIcon(new ImageIcon("icons/Save24.gif"));
        itemSave.setActionCommand("save");
        itemSave.addActionListener(listener);

        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.setActionCommand("exit");
        itemExit.addActionListener(listener);

        menu.add(itemNew);
        menu.add(itemOpen);
        menu.add(itemSave);
        menu.addSeparator();
        menu.add(itemExit);
        menuBar.add(menu);

        this.setJMenuBar(menuBar);
        //#######################################

        //####################################### ToolBar
        add(makeList(), BorderLayout.NORTH);
        //#######################################

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(w, h));
        setLocation(((int) screenSize.getWidth()/2)-(w/2), ((int)screenSize.getHeight()/2)-(h/2));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }





    class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("new")) {
                newFile();
            }
            if (e.getActionCommand().equals("open")) {
                openFile();
            }
            if (e.getActionCommand().equals("save")) {
                saveFile();
            }
            if (e.getActionCommand().equals("exit")) {
                exitApp();
            }
        }
    }




    private void newFile() {
        if (JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich ein neues Datei anlegen?",
                "New File", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            textArea.setText("");
        }
    }

    private void openFile() {
        EditorDialog dialog = new EditorDialog();
        File f = dialog.showMeOpenDialog(); //return of file
        if (f != null) {
            try {
                textArea.read(new FileReader(f), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        EditorDialog dialog = new EditorDialog();
        File f = dialog.showMeSaveDialog();
        if (f != null) {
            try {
                textArea.write(new FileWriter(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void exitApp() {
        if (JOptionPane.showConfirmDialog(this, "Exit",
                "New File", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private JToolBar makeList() {

        JToolBar list = new JToolBar();
        MyListener listener = new MyListener();

        JButton btn1 = new JButton();
        btn1.setActionCommand("new");
        btn1.setIcon(new ImageIcon("icons/New24.gif"));
        btn1.setToolTipText("New File");
        btn1.addActionListener(listener);
        list.add(btn1);

        JButton btn2 = new JButton();
        btn2.setActionCommand("open");
        btn2.setIcon(new ImageIcon("icons/Open24.gif"));
        btn2.setToolTipText("Open File");
        btn2.addActionListener(listener);
        list.add(btn2);

        JButton btn3 = new JButton();
        btn3.setActionCommand("save");
        btn3.setIcon(new ImageIcon("icons/Save24.gif"));
        btn3.setToolTipText("Save File");
        btn3.addActionListener(listener);
        list.add(btn3);

        return list;
    }

}
