package org.archeion.ui.menu;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.paint.Color;

public class ContextMenuBuilder extends ContextMenu {

    public ContextMenuBuilder() {
    }

    public ContextMenuBuilder addSeparator() {
        getItems().add(new SeparatorMenuItem());
        return this;
    }

    public ContextMenuBuilder addItem(String text, String style, Runnable runnable) {
        return addItem(text, null, style, runnable);
    }

    public ContextMenuBuilder addItem(String text, Runnable runnable) {
        return addItem(text, null, "", runnable);
    }

    public ContextMenuBuilder addItem(String text, MaterialDesignIcon icon, Runnable runnable) {
        return addItem(text, icon, "", runnable);
    }

    public ContextMenuBuilder addItem(String text, MaterialDesignIcon icon, String style, Runnable runnable) {
        getItems().add(new MenuItem(text, icon == null ? null : new MaterialDesignIconView(icon) {{
            setFill(Color.valueOf("#575757"));
            setGlyphSize(20);
        }}) {{
            getStyleClass().add(style);
            setOnAction(event -> {
                if (runnable != null)
                    runnable.run();
            });
        }});
        return this;
    }

}
