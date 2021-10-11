package com.ytrsoft.ui;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public interface JPanelConfig {
    int BORDER_SIZE = 10;
    RowSpec ROW_DEFAULT = FormSpecs.DEFAULT_ROWSPEC;
    RowSpec ROW_RELATED_GAP = FormSpecs.RELATED_GAP_ROWSPEC;
    ColumnSpec COL_DEFAULT = FormSpecs.DEFAULT_COLSPEC;
    ColumnSpec COL_RELATED_GAP = FormSpecs.RELATED_GAP_COLSPEC;
    ColumnSpec COL_DEFAULT_GROW = ColumnSpec.decode("default:grow");
}
