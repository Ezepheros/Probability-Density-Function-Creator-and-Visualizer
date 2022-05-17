package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class BarChart extends JPanel {
    private Map<Color, Double> bars = new LinkedHashMap<Color, Double>();

    // MODIFIES: this
    // EFFECTS: adds bar to bars
    public void addBar(Color color, double value) {
        bars.put(color, value);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // determine longest bar

        double max = Double.MIN_VALUE;
        for (Double value : bars.values()) {
            max = Math.max(max, value);
        }

        // paint bars
        if (bars.size() > 0) {
            int width = (getWidth() / bars.size()) - 2;
            int x = 1;
            for (Color color : bars.keySet()) {
                Double value = bars.get(color);
                int height = (int)
                        ((getHeight() - 5) * (value / max));
                g.setColor(color);
                g.fillRect(x, getHeight() - height, width, height);
                g.setColor(Color.black);
                g.drawRect(x, getHeight() - height, width, height);
                x += (width + 2);
            }
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bars.size() * 10 + 2, 50);
    }

    // MODIFIES: this
    // EFFECTS: empties the barchart
    public void clear() {
        bars.clear();
    }
}
