package com.ytrsoft.ui;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.ytrsoft.entity.BootModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JClientPanel extends JModePanel<BootModel> implements ActionListener,
        JDirectoryChooser.SelectListener, JPanelConfig {

    public interface BootListener {
        void onBoot(BootModel model);
    }

    private boolean isLock;
    private final JButton startBtn;
    private final JButton selectBtn;
    private final BootListener bootListener;
    private final JComboBox<Integer> hostComboBox1;
    private final JComboBox<Integer> hostComboBox2;
    private final JComboBox<Integer> hostComboBox3;
    private final JComboBox<Integer> hostComboBox4;
    private final JComboBox<Integer> portComboBox;
    private final JDirectoryChooser directoryChooser;

    public JClientPanel(BootListener listener) {
        bootListener = listener;
        FormLayout formLayout = new FormLayout(buildColumnSpec(), buildRowSpec());
        setBorder(BorderFactory.createEmptyBorder(
                BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE
        ));
        setLayout(formLayout);
        //===================Host==================================
        JLabel hostLabel = new JLabel("Host");
        hostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(hostLabel, "2, 2, fill, default");
        hostComboBox1 = getHostSelect();
        add(hostComboBox1, "4, 2, fill, default");
        hostComboBox2 = getHostSelect();
        add(hostComboBox2, "6, 2, fill, default");
        hostComboBox3 = getHostSelect();
        add(hostComboBox3, "8, 2, fill, default");
        hostComboBox4 = getHostSelect();
        add(hostComboBox4, "10, 2, fill, default");
        //===================Port==================================
        JLabel portLabel = new JLabel("Port");
        portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(portLabel, "2, 4, right, default");
        portComboBox = getPortSelect();
        add(portComboBox, "4, 4, 7, 1, fill, default");
        //===================Path====================================
        JLabel pathLabel = new JLabel("Path");
        pathLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(pathLabel, "2, 6");
        selectBtn = new JButton("select");
        selectBtn.addActionListener(this);
        add(selectBtn, "4, 6, 7, 1");
        //===================start====================================
        startBtn = new JButton("start");
        startBtn.addActionListener(this);
        add(startBtn, "4, 8, 7, 1");
        directoryChooser = new JDirectoryChooser();
        directoryChooser.addSelectListener(this);
    }

    private JComboBox<Integer> getHostSelect() {
        JComboBox<Integer> jComboBox = new JComboBox<>();
        for (int i = 0; i <= 255; i++) {
            jComboBox.addItem(i);
        }
        return jComboBox;
    }

    private JComboBox<Integer> getPortSelect() {
        JComboBox<Integer> jComboBox = new JComboBox<>();
        for (int i = 5001; i <= 9999; i++) {
            jComboBox.addItem(i);
        }
        return jComboBox;
    }

    private RowSpec[] buildRowSpec() {
        return new RowSpec[] {
            ROW_RELATED_GAP,
            ROW_DEFAULT,
            ROW_RELATED_GAP,
            ROW_DEFAULT,
            ROW_RELATED_GAP,
            ROW_DEFAULT,
            ROW_RELATED_GAP,
            ROW_DEFAULT,
            ROW_RELATED_GAP,
            ROW_DEFAULT
        };
    }

    private ColumnSpec[] buildColumnSpec() {
        return new ColumnSpec[] {
            COL_RELATED_GAP,
            COL_DEFAULT,
            COL_RELATED_GAP,
            COL_DEFAULT_GROW,
            COL_RELATED_GAP,
            COL_DEFAULT_GROW,
            COL_RELATED_GAP,
            COL_DEFAULT_GROW,
            COL_RELATED_GAP,
            COL_DEFAULT_GROW
        };
    }

    @Override
    public void onSelect(String path) {
        getModel().setPath(path);
        String mode = getModel().getMode();
        String title = String.format("%s - %s",mode , path);
        ((BaseFrame)bootListener).setTitle(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(selectBtn)) {
            directoryChooser.open();
        }
        if (e.getSource().equals(startBtn)) {
            if (!isLock) {
                Integer host1 = (Integer) hostComboBox1.getSelectedItem();
                Integer host2 = (Integer) hostComboBox2.getSelectedItem();
                Integer host3 = (Integer) hostComboBox3.getSelectedItem();
                Integer host4 = (Integer) hostComboBox4.getSelectedItem();
                getModel().setHost(String.format("%d.%d.%d.%d", host1, host2, host3, host4));
                Integer port = (Integer) portComboBox.getSelectedItem();
                getModel().setPort(port);
                if (getModel().getPath() != null) {
                    isLock = true;
                    bootListener.onBoot(getModel());
                    hostComboBox1.setEnabled(false);
                    hostComboBox2.setEnabled(false);
                    hostComboBox3.setEnabled(false);
                    hostComboBox4.setEnabled(false);
                    portComboBox.setEnabled(false);
                    selectBtn.setEnabled(false);
                    startBtn.setText("stop");
                }
            }
        } else {
            isLock = false;
            System.exit(0);
        }
    }
}
