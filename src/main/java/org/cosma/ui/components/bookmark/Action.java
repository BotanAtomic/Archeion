package org.cosma.ui.components.bookmark;

import org.cosma.ui.components.bookmark.actions.google.AddGoogleAccount;
import org.cosma.ui.components.bookmark.actions.navigation.*;

import java.util.HashMap;
import java.util.Map;

public interface Action {

    Map<String, Action> actions = new HashMap<>() {{
        put("recent", new Recent());
        put("favorite", new Favorite());
        put("home-path", new HomePath());
        put("trash", new Trash());
        put("system-root", new SystemRoot());
        put("add-google-account", new AddGoogleAccount());
        put("custom-path", new CustomPath());
    }};

    static void perform(BookmarkModel model) {
        actions.getOrDefault(model.getAction(), (model1 ->
                System.out.println("Empty action ? WTF"))).execute(model);
    }

    void execute(BookmarkModel model);
}
