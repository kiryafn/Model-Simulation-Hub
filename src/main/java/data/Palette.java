package data;

import java.awt.*;

public enum Palette {
    EvenRow(new Color(255, 255, 255)),
    OddRow(new Color(211, 211, 211)),
    Selected(new Color(255, 185, 210)),
    Header(new Color(132, 118, 255));

    Color color;

    Palette(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
