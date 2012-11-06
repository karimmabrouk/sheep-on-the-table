/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sheeponthetable.tools.map;

import java.awt.Color;
import org.jdesktop.swingx.mapviewer.DefaultWaypoint;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * A waypoint that also has a color and a label
 *
 * @author Martin Steiger
 */
public class MyWaypoint extends DefaultWaypoint {

    private final String label;
    private final Color color;
    private final boolean sheep;
    private final int index;

    /**
     * Constructor for Waypoints
     *
     */
    public MyWaypoint(String label, Color color, GeoPosition coord, int index, boolean sheep) {
        super(coord);
        this.index = index;
        this.label = label;
        this.color = color;
        this.sheep = sheep;
    }

    /**
     * @return the label text
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return True if this is a sheep waypoint, false if it is a update
     * waypoint
     */
    public boolean isSheepWaypoint() {
        return sheep;
    }

    /**
     * @return index of corresponding sheep or sheep update
     */
    public int getIndex() {
        return index;
    }
}