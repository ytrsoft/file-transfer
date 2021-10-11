package com.ytrsoft.ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JDirectoryChooser extends JFileChooser implements ActionListener {

    interface SelectListener {
        void onSelect(String path);
    }

    static class DirectoryFilter extends FileFilter {

        @Override
        public boolean accept(File file) {
            return file.isDirectory();
        }

        @Override
        public String getDescription() {
            return null;
        }
    }

    private SelectListener mSelectListener;

    public JDirectoryChooser() {
        addActionListener(this);
        setFileFilter(new DirectoryFilter());
        setCurrentDirectory(getHomeDirectory());
        setMultiSelectionEnabled(false);
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this) && mSelectListener != null) {
            File selectedFile = getSelectedFile();
            if (selectedFile != null) {
                this.mSelectListener.onSelect(selectedFile.getAbsolutePath());
            }
        }
    }

    public void addSelectListener(SelectListener listener) {
        this.mSelectListener = listener;
    }

    public void open() {
        showOpenDialog(this);
    }

    private File getHomeDirectory() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        return fsv.getHomeDirectory();
    }

}
