package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class EditorDialog {

    public File selectFile(String operation) {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".rtf files", "rtf");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        if (operation.equals("open")) { // open dialog
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("Your file: " + chooser.getSelectedFile().getName());
                return chooser.getSelectedFile();
            }
        }
        if (operation.equals("save")) { // save dialog
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("Your file: " + chooser.getSelectedFile().getName());
                return chooser.getSelectedFile();
            }
        }
        return null;

    }

}
