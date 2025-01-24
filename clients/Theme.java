package clients;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Theme {

    public static final Theme LIGHT_MODE = new Theme(
            new Color(0, 0, 0),
            new Color(221, 221, 221),
            new Color(255, 255, 255),
            new Color(238, 238, 238)
    );
    public static final Theme DARK_MODE = new Theme(
            new Color(255, 255, 255),
            new Color(51, 51, 51),
            new Color(85, 85, 85),
            new Color(68, 68, 68)
    );

    private static final Map<String, Theme> currentTheme = new HashMap<>();

    public static Theme getCurrentTheme(Class<?> clazz) {
        return currentTheme.getOrDefault(clazz.getName(), LIGHT_MODE);
    }

    public static void toggleTheme(Class<?> clazz) {
        if (!currentTheme.containsKey(clazz.getName()))
            currentTheme.put(clazz.getName(), DARK_MODE);
        else {
            currentTheme.compute(clazz.getName(), (k, theme) -> theme == LIGHT_MODE ? DARK_MODE : LIGHT_MODE);
        }
    }

    public static void setTheme(Theme theme, Class<?> clazz) {
        currentTheme.put(clazz.getName(), theme);
    }

    private final Color text;
    private final Color background;
    private final Color highlight;
    private final Color outline;

    public Theme(Color text, Color background, Color highlight, Color outline) {
        this.text = text;
        this.background = background;
        this.highlight = highlight;
        this.outline = outline;
    }

    public Color getText() {
        return this.text;
    }

    public Color getBackground() {
        return this.background;
    }

    /**
     * Gets the highlight colour.
     *
     * @return the highlight colour.
     */
    public Color getHighlight() {
        return this.highlight;
    }

    public Color getOutline() {
        return outline;
    }
}
