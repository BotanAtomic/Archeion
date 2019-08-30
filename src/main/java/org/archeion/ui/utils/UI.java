package org.archeion.ui.utils;


import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class UI {

    public static final int SEARCH_TRANSITION_TIME = 300;
    public static final double MIN_WIDTH = 733, MIN_HEIGHT = 500;

    public static List<Node> firstNodes(Pane... nodes) {
        List<Node> firstNodes = new ArrayList<>();

        for (Pane node : nodes)
            firstNodes.add(node.getChildrenUnmodifiable().get(0));

        return firstNodes;
    }

    public static boolean isVisible(Node node) {
        return node.getOpacity() > 0;
    }


}
