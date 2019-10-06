package org.cosma.process;

public class HomeDirectoryProcess {

    public static String getHomeDirectory(String name) {
        try {
            Process process = new ProcessBuilder()
                    .command("xdg-user-dir", name.toUpperCase())
                    .start();

            return new String(process.getInputStream().readAllBytes()).replace("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.getProperty("user.home");
    }

}
