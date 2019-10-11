package org.cosma.process;

import javafx.application.Platform;
import org.cosma.ui.components.notification.Notification;

import java.io.IOException;

public class TerminalProcess {

    public static void open(String path) {
        ProcessManager.execute(() -> {
            try {
                new ProcessBuilder().command("terminator", "--working-directory", path).start();
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> Notification.show("Cannot open terminal here", true));
            }
        }, null);
    }

}
