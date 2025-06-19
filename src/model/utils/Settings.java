package model.utils;

import java.util.prefs.Preferences;

public class Settings {
    private static final Preferences prefs = Preferences.userRoot().node(Settings.class.getName());
    private static final String USE_GREEDY_KEY = "useGreedy";
    private static final boolean DEFAULT_USE_GREEDY = false;

    public static boolean useGreedy() {
        return prefs.getBoolean(USE_GREEDY_KEY, DEFAULT_USE_GREEDY);
    }

    public static void setUseGreedy(boolean useGreedy) {
        prefs.putBoolean(USE_GREEDY_KEY, useGreedy);
    }

    /*
    public static void main(String[] args) {
        // Example usage
        System.out.println("Use Greedy: " + useGreedy());
        setUseGreedy(false);
        System.out.println("Use Greedy after setting to false: " + useGreedy());
    }
    */
}
