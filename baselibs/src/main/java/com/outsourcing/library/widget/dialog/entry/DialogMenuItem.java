package com.outsourcing.library.widget.dialog.entry;

/**
 * @author bb
 * @time 2019/10/31 17:14
 * @des ${TODO}
 **/
public class DialogMenuItem {

    public String operName;
    public int resId;
    public boolean highlight=false;

    /**
     *  弹出框项
     * @param operName 名称
     *
     */
    public DialogMenuItem(String operName) {
        this.operName = operName;
    }

    /**
     *  弹出框项
     * @param operName 名称
     * @param resId 图标资源id
     */
    public DialogMenuItem(String operName, int resId)
    {
        this.operName = operName;
        this.resId = resId;
    }

    /**
     * 弹出框项
     * @param operName  名称
     * @param resId 图标资源id
     * @param highlight 是否高亮 默认false
     */
    public DialogMenuItem(String operName, int resId, boolean highlight) {
        this.operName = operName;
        this.resId = resId;
        this.highlight = highlight;
    }
}
