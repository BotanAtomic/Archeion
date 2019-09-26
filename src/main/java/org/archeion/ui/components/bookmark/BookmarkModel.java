package org.archeion.ui.components.bookmark;


import org.json.JSONObject;

import java.io.File;

public class BookmarkModel {

    private String svgIcon;
    private String label;
    private boolean isSeparator;
    private boolean expendable;
    private boolean selectable;
    private boolean fromFile;

    private String action;
    private String actionArgs;

    public BookmarkModel(JSONObject object) {
        if (object.has("separator")) {
            this.isSeparator = true;
            return;
        }

        this.isSeparator = false;
        this.svgIcon = object.getString("icon");
        this.label = object.getString("label");
        this.expendable = object.getBoolean("expandable");
        this.selectable = object.getBoolean("selectable");

        JSONObject action = object.getJSONObject("action");
        this.action = action.getString("name");
        this.actionArgs = action.isNull("args") ? null : action.getString("args");
    }

    public BookmarkModel() {
        this.isSeparator = true;
    }

    public static BookmarkModel fromFile(File file) {
        return new BookmarkModel() {{
            super.label = file.getName();
            super.svgIcon = file.isDirectory() ? "folder" : "document";
            super.action = "custom-path";
            super.actionArgs = file.getAbsolutePath();
            super.selectable = file.isDirectory();
            super.expendable = super.selectable;
            super.fromFile = true;
            super.isSeparator = false;
        }};
    }

    public String getIconName() {
        return svgIcon;
    }

    public String getLabel() {
        return isSeparator ? "separator" : label;
    }

    public boolean isSeparator() {
        return isSeparator;
    }

    public boolean isExpendable() {
        return expendable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public boolean isFromFile() {
        return fromFile;
    }

    public String getAction() {
        return action;
    }

    public String getActionArgs() {
        return actionArgs;
    }

}
