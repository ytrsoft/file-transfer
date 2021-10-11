package com.ytrsoft.ui;

import javax.swing.*;

public class JModePanel<T> extends JPanel {

    private T model;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

}
