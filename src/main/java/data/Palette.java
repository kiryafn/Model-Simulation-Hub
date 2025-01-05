package data;

import java.awt.*;

public enum Palette {
    EVEN_TABLE_ROW(new Color(255, 255, 255)),
    ODD_TABLE_ROW(new Color(211, 211, 211)),
    SELECTED(new Color(255, 185, 210)),
    HEADER(new Color(255, 176, 118));

    Color color;

    Palette(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
