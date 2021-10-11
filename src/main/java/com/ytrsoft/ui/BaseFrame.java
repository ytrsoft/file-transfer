package com.ytrsoft.ui;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.Theme;
import com.ytrsoft.entity.BootModel;

import javax.swing.*;

public abstract class BaseFrame extends JFrame implements JFrameConfig {

    public BaseFrame() {
        this(true);
    }

    public BaseFrame(boolean isServer) {
        if (isServer) {
            setTitle(SERVER_TITLE);
            setSize(SERVER_WIDTH, SERVER_HEIGHT);
        } else {
            setTitle(CLIENT_TITLE);
            setSize(CLIENT_WIDTH, CLIENT_HEIGHT);
        }
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BootModel bootModel = new BootModel();
        bootModel.setMode(getTitle());
        JModePanel<BootModel> contentPane = buildUI();
        contentPane.setModel(bootModel);
        setContentPane(contentPane);
    }

    public abstract JModePanel<BootModel> buildUI();

    public static void launch(Class<? extends BaseFrame> clz) {
        launch(clz, new DarculaTheme());
    }

    public static void launch(Class<? extends BaseFrame> clz, Theme theme) {
        SwingUtilities.invokeLater(() -> {
            LafManager.install(theme);
            try {
                BaseFrame frame = clz.newInstance();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}