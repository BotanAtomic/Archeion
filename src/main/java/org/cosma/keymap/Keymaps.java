package org.cosma.keymap;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.util.Pair;
import org.cosma.ui.components.search.SearchBar;

import java.util.ArrayList;
import java.util.List;

public class Keymaps {

    private static final List<Pair<KeyCodeCombination, Runnable>> keymaps = new ArrayList<>();

    static {
        addKeymap(SearchBar::hide, KeyCode.ESCAPE);
    }

    private static void addKeymap(Runnable runnable, KeyCode key, KeyCombination.Modifier... modifiers) {
        keymaps.add(new Pair<>(new KeyCodeCombination(key, modifiers), runnable));
    }

    public static void bind(Scene scene) {
        scene.setOnKeyReleased(e -> keymaps.stream().filter(pair -> pair.getKey().match(e))
                .forEachOrdered(pair -> pair.getValue().run()));
    }

}
