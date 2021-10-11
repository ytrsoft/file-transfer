package com.ytrsoft.ui;

import com.ytrsoft.model.BootModel;

import javax.swing.*;

public abstract class BaseClient extends BaseFrame implements JClientPanel.BootListener {

    public BaseClient() {
        super(false);
    }

    @Override
    public JModePanel<BootModel> buildUI() {
        return new JClientPanel(this);
    }

    @Override
    public void onBoot(BootModel model) {
        SwingUtilities.invokeLater(() -> {
            onBootstrap(model);
        });
    }

    protected abstract void onBootstrap(BootModel model);

}
