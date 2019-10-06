package org.cosma.ui.components.bookmark;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.JSONObject;

import java.io.File;

public class BookmarkModel {

    private StringProperty icon;
    private StringProperty label;
    private boolean isSeparator;
    private boolean selectable;

    private String action;
    private String actionArgs;

    public BookmarkModel(JSONObject object) {
        if (object.has("separator")) {
            this.isSeparator = true;
            return;
        }

        this.isSeparator = false;
        this.icon = new SimpleStringProperty(object.getString("icon"));
        this.label = new SimpleStringProperty(object.getString("label"));
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
            super.label = new SimpleStringProperty(file.getName());
            super.icon = new SimpleStringProperty(file.isDirectory() ? "folder" : "document");
            super.action = "custom-path";
            super.actionArgs = file.getAbsolutePath();
            super.selectable = file.isDirectory();
            super.isSeparator = false;
        }};
    }

    public String getIconName() {
        return icon.get();
    }

    public StringProperty getIconProperty() {
        return icon;
    }

    public String getLabel() {
        return isSeparator ? "separator" : label.get();
    }

    public StringProperty getLabelProperty() {
        return label;
    }

    public boolean isSeparator() {
        return isSeparator;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public String getAction() {
        return action;
    }

    public String getActionArgs() {
        return actionArgs == null ? "" : actionArgs;
    }

}
