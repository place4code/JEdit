package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class EditorDialog {

    class MyFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            if (f.getName().toLowerCase().endsWith(".txt")) {
                return true;
            }
            return false;
        }

        @Override
        public String getDescription() {
            return ".txt Files";
        }
    }

    public File showMeOpenDialog() {
        JFileChooser dialog = new JFileChooser();
        dialog.setFileFilter(new MyFilter());
        dialog.setAcceptAllFileFilterUsed(false);
        int status = dialog.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }
        return null;
    }

    public File showMeSaveDialog() {
        JFileChooser dialog = new JFileChooser();
        dialog.setFileFilter(new MyFilter());
        dialog.setAcceptAllFileFilterUsed(false);
        int status = dialog.showSaveDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }
        return null;
    }

}
