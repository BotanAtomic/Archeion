package org.archeion.ui.components.sidebar;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import org.archeion.configuration.BookmarkConfiguration;
import org.archeion.ui.components.bookmark.Bookmark;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class OrganisablePanel extends VBox {

    private Map<Integer, Integer> organisation = new ConcurrentHashMap<>();
    private AtomicReference<Bookmark> dragged = new AtomicReference<>();
    private AtomicInteger index = new AtomicInteger(-1);
    private AtomicInteger lastIndex = new AtomicInteger(-1);

    private ObservableList<Node> nodes;

    private List<Bookmark> bookmarks;

    OrganisablePanel(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        this.nodes = getChildren();

        setOnMouseDragReleased(e -> {
            Bookmark bookmark = dragged.get();

            if (bookmark != null) {
                Node node = bookmark.getNode();

                int lastNodeIndex = nodes.indexOf(node);

                if (lastNodeIndex < 0) {
                    e.consume();
                    return;
                }

                node.getStyleClass().remove("drag-box");
                node.getStyleClass().add("selectable-bookmark");
                getScene().setCursor(Cursor.DEFAULT);
                node.setManaged(true);
                dragged.set(null);

                bookmarks.remove(bookmark);
                bookmarks.add(nodes.indexOf(node), bookmark);

                BookmarkConfiguration.save(bookmarks);

                organisation.clear();
                buildOrganisation();
            }

            e.consume();
        });

        setOnMouseDragExited(e -> getOnMouseDragReleased().handle(e));

        setOnMouseDragged(e -> {
            int nearestIndex = getNearest(e.getY());
            Bookmark bookmark = dragged.get();

            if (bookmark == null) {
                e.consume();
                return;
            }

            Node source = bookmark.getNode();

            source.relocate(0, e.getY());

            if (lastIndex.get() != nearestIndex) {
                lastIndex.set(nearestIndex);

                if (nearestIndex != nodes.indexOf(source) && nearestIndex != index.get()) {
                    index.set(nearestIndex);
                    nodes.remove(source);
                    nodes.add(index.get(), source);
                }
            }


            e.consume();
        });

        heightProperty().addListener(((observable, oldValue, newValue) -> buildOrganisation()));
    }

    void bindItem(Bookmark bookmark) {
        Node node = bookmark.getNode();
        node.setOnDragDetected(event -> {
            if (dragged.get() == null && event.getButton() == MouseButton.PRIMARY) {
                dragged.set(bookmark);
                getScene().setCursor(Cursor.MOVE);
                node.startFullDrag();
                node.getStyleClass().clear();
                node.getStyleClass().add("drag-box");
                event.consume();
            }
        });
    }

    public void removeBookmark(Bookmark bookmark) {
        nodes.remove(bookmark.getNode());
        bookmarks.remove(bookmark);
        BookmarkConfiguration.save(bookmarks);
    }


    private void buildOrganisation() {
        double totalHeight = 0;

        layout();

        int i = 0;
        for (Node node : nodes) {
            node.applyCss();

            double currentHeight = node.getBoundsInParent().getHeight();
            totalHeight += currentHeight;
            int middleHeight = (int) (totalHeight - (currentHeight / 2));
            organisation.put(middleHeight, i++);
        }

    }

    private int getNearest(double y) {
        final AtomicInteger nearestIndex = new AtomicInteger(),
                nearestDistance = new AtomicInteger(Integer.MAX_VALUE);


        organisation.forEach((index, value) -> {
            int distance = (int) Math.abs(y - index);

            if (distance < nearestDistance.get()) {
                nearestDistance.set(distance);
                nearestIndex.set(value);
            }

        });

        return nearestIndex.get();
    }

}
