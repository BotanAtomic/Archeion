package org.cosma.ui.components.bookmark.actions.navigation;

import org.cosma.ui.components.bookmark.Action;
import org.cosma.ui.components.bookmark.BookmarkModel;

public class CustomPath implements Action {

    @Override
    public void execute(BookmarkModel model) {
        System.out.println("Custom path : " + model.getActionArgs());
    }
}
