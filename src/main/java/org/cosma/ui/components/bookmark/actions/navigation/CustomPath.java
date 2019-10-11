package org.cosma.ui.components.bookmark.actions.navigation;

import org.cosma.ui.components.bookmark.Action;
import org.cosma.ui.components.bookmark.BookmarkModel;
import org.cosma.ui.components.manager.FileLayoutManager;

public class CustomPath implements Action {

    @Override
    public void execute(BookmarkModel model) {
        FileLayoutManager.setCurrentPath(model.getActionArgs());
    }
}
