package com.ytrsoft.ui;

import com.ytrsoft.entity.BootModel;

import javax.swing.*;

public abstract class BaseServer extends BaseFrame implements JServerPanel.BootListener {

    public BaseServer() {
        super(true);
    }

    @Override
    public JModePanel<BootModel> buildUI() {
        return new JServerPanel(this);
    }

    @Override
    public void onBoot(BootModel model) {
        SwingUtilities.invokeLater(() -> {
            onBootstrap(model);
        });
    }

    protected abstract void onBootstrap(BootModel model);

}
