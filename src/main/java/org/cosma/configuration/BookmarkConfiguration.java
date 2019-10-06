package org.cosma.configuration;

import org.cosma.ui.components.notification.Notification;
import org.cosma.ui.components.bookmark.Bookmark;
import org.cosma.ui.components.bookmark.BookmarkModel;
import org.cosma.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BookmarkConfiguration {

    public static List<BookmarkModel> loadBookmarks() {
        List<BookmarkModel> bookmarks = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(FileUtils.toString("configuration/bookmarks.json"));

            array.forEach(o -> bookmarks.add(0, new BookmarkModel((JSONObject) o)));
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Cannot load bookmarks", true);
        }
        return bookmarks;
    }

    public static void save(List<Bookmark> bookmarks) {
        try {
            JSONArray array = new JSONArray();

            bookmarks.forEach(bookmark -> {
                BookmarkModel model = bookmark.getModel();
                array.put(new JSONObject() {{
                    if (model.isSeparator()) {
                        put("separator", true);
                    } else {
                        put("label", model.getLabel());
                        put("selectable", model.isSelectable());
                        put("icon", model.getIconName());
                        put("action", new JSONObject() {{
                            put("name", model.getAction());
                            put("args", model.getActionArgs());
                        }});
                    }
                }});
            });

            Files.write(Paths.get("configuration/bookmarks.json"), array.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Cannot save bookmarks", true);
        }
    }
}
