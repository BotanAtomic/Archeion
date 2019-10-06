package org.cosma.ui.components.bookmark.actions.navigation;

import org.cosma.process.HomeDirectoryProcess;
import org.cosma.ui.components.bookmark.Action;
import org.cosma.ui.components.bookmark.BookmarkModel;
import org.cosma.ui.components.manager.FileLayoutManager;

public class HomePath implements Action {

    @Override
    public void execute(BookmarkModel model) {
        System.out.println("Home path: " + model.getActionArgs());

        FileLayoutManager.setCurrentPath(HomeDirectoryProcess.getHomeDirectory(model.getActionArgs()));
    }
}
