package org.cosma.ui.components.sidebar;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.cosma.configuration.BookmarkConfiguration;
import org.cosma.ui.components.bookmark.Action;
import org.cosma.ui.components.bookmark.Bookmark;
import org.cosma.ui.components.bookmark.BookmarkModel;
import org.cosma.ui.components.menu.ContextMenuBuilder;
import org.cosma.ui.components.menu.items.RenameBookmarkMenu;
import org.cosma.ui.components.notification.Notification;
import org.cosma.ui.utils.DragAndDrop;
import org.cosma.utils.ResourcesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sidebar {

    private static List<Bookmark> bookmarks;

    private static Node previewBookmark;

    private static BookmarkPanel box;

    private static ScrollPane sidebar;

    private static BorderPane borderPane;
    private static AnchorPane root;

    private static boolean isVisible = true;

    public static void insert(AnchorPane root, BorderPane borderPane) throws IOException {
        Sidebar.borderPane = borderPane;
        Sidebar.root = root;
        sidebar = new ScrollPane() {{
            setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            setVmax(1.5);
            getStylesheets().addAll(ResourcesUtils.loadStyleSheet("scrollbar").toExternalForm(),
                    ResourcesUtils.loadStyleSheet("sidebar").toExternalForm());
            setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        }};


        bookmarks = new ArrayList<>();

        root.getChildren().add(sidebar);

        AnchorPane.setBottomAnchor(sidebar, 0d);
        AnchorPane.setLeftAnchor(sidebar, 0d);
        AnchorPane.setRightAnchor(sidebar, 0d);
        AnchorPane.setTopAnchor(sidebar, 0d);

        box = new BookmarkPanel(bookmarks);
        sidebar.setContent(box);

        box.prefWidthProperty().bind(sidebar.widthProperty());
        box.prefHeightProperty().bind(sidebar.heightProperty().add(-10));

        BookmarkConfiguration.loadBookmarks().forEach(b -> loadBookmark(b, 0));

        previewBookmark = FXMLLoader.load(ResourcesUtils.loadComponent("sidebar/bookmark_item"));

        DragAndDrop.enableFileDragAndDrop(
                box,
                Sidebar::showPreviewBookmark,
                Sidebar::hidePreviewBookmark,
                files -> files.forEach(file -> loadBookmark(BookmarkModel.fromFile(file), box.getChildren().size() - 1))
        );
    }

    public static void toggleSidebar() {
        isVisible = !isVisible;
        borderPane.setLeft(isVisible ? root : null);
    }

    public static boolean isVisible() {
        return isVisible;
    }

    public static void selectBookmark(Bookmark bookmark) {
        bookmarks.forEach(b -> b.selectedProperty().setValue(false));
        bookmark.selectedProperty().setValue(true);
    }

    private static void loadBookmark(BookmarkModel model, int index) {
        try {
            Bookmark bookmark = new Bookmark(model);
            Node item = bookmark.getNode();

            if (!model.isSeparator()) {
                ((Label) item).prefWidthProperty().bind(sidebar.widthProperty());
                bookmark.applyStyle(box);
                box.bindItem(bookmark);
            }

            item.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY && !model.isSeparator()) {
                    if (model.isSelectable())
                        selectBookmark(bookmark);

                    Action.perform(model);
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    ContextMenuBuilder contextMenuBuilder = new ContextMenuBuilder();

                    if (!model.isSeparator()) {
                        contextMenuBuilder
                                .addItem("Open", MaterialDesignIcon.OPEN_IN_APP, bookmark::open)
                                .addSeparator()
                                .addItem("Rename", MaterialDesignIcon.PENCIL, () -> {
                                    contextMenuBuilder.hide();

                                    ContextMenuBuilder renameBuilder = new ContextMenuBuilder();
                                    renameBuilder.addCustom(new RenameBookmarkMenu(model).getNode(), null);
                                    renameBuilder.show(item, Side.RIGHT, 5, 0);
                                    renameBuilder.setOnHiding(windowEvent -> BookmarkConfiguration.save(bookmarks));
                                })
                                .addItem("Remove bookmark", MaterialDesignIcon.BOOKMARK_REMOVE, "red", () -> box.removeBookmark(bookmark))
                                .addSeparator()
                                .addItem("Add separator", MaterialDesignIcon.MINUS, () -> loadBookmark(new BookmarkModel(), box.getChildren().indexOf(item) + 1))
                                .addSeparator()
                                .addItem("Properties", MaterialDesignIcon.INFORMATION_VARIANT, () -> System.out.println("Open properties"));
                    } else {
                        contextMenuBuilder
                                .addItem("Remove", MaterialDesignIcon.MINUS, "red", () -> box.removeBookmark(bookmark));
                    }

                    item.setOnContextMenuRequested(contextMenuEvent -> contextMenuBuilder.hide());

                    contextMenuBuilder.setOnShown(event -> {
                        event.consume();
                        contextMenuBuilder.setAnchorX(contextMenuBuilder.getAnchorX() - contextMenuBuilder.getWidth() / 2.0);
                    });

                    contextMenuBuilder.show(item, Side.BOTTOM, item.getBoundsInParent().getWidth() / 2, 0);
                }
            });


            box.getChildren().add(index, item);
            bookmarks.add(index, bookmark);

            if (index > 0)
                BookmarkConfiguration.save(bookmarks);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Cannot load bookmark " + model.getLabel(), true);
        }

    }

    private static void showPreviewBookmark() {
        if (box.getChildren().indexOf(previewBookmark) < 0)
            box.getChildren().add(previewBookmark);

        sidebar.setVvalue(1);
    }

    private static void hidePreviewBookmark() {
        box.getChildren().remove(previewBookmark);
    }

}
