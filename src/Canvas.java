/*
 * This canvas is written as a JPanel rather than a JFrame. 
 * Which means that a JFrame needs to be initialised first to hold it.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.awt.geom.Line2D;

/**
 * <h1>Canvas</h1>
 * This class represents a canvas object that can be drawn to 
 * with various line segments.
 *
 * @author  Stuart Lacy
 * @version 1.0
 * @since   2015-01-19
 */

public class Canvas extends JPanel {

    private int xSize, ySize;
    private List<LS> lines;
    private List<MyCircle> circles;
    private List<MyArc> arcs;
    private final static int DEFAULT_X = 800;
    private final static int DEFAULT_Y = 600;

    /**
     * Default constructor which produces a canvas of the
     * default size of 800 x 600.
     */
    public Canvas() {
        this(DEFAULT_X, DEFAULT_Y);
    }

    /**
     * Constructor which produces a canvas of a specified size.
     *
     * @param x Width of the canvas.
     * @param y Height of the canvas.
     */
    public Canvas(int x, int y) {
        xSize = x;
        ySize = y;
        lines = Collections.synchronizedList(new ArrayList<LS>());
        circles = Collections.synchronizedList(new ArrayList<MyCircle>());
        arcs = Collections.synchronizedList(new ArrayList<MyArc>());
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setBackground(Color.BLACK);
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.xSize, this.ySize);
    }

    /**
     * <b>NB: You never need to call this method yourself.</b>
     * It handles the drawing but is called automatically each
     * time a line segment is drawn.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Smoother lines
        g2.setStroke(new BasicStroke(3));

        synchronized(lines)
        {
            for (LS line : lines)
            {
                g2.setColor(line.getColor());
                g2.draw(new Line2D.Double(line.getStartX(), line.getStartY(),
                        line.getEndX(), line.getEndY()));
            }
        }
        synchronized (circles) {
            for (MyCircle circle : circles) {
                g2.setColor(circle.getColour());
                g2.draw(new Arc2D.Double(
                                circle.getPosition().getX() - circle.getRadius(),
                                circle.getPosition().getY() - circle.getRadius(),
                                circle.getRadius() * 2,
                                circle.getRadius() * 2,
                                0, 360,
                                Arc2D.CHORD)
                );
            }
        }

        synchronized (arcs)
        {
            for(MyArc arc : arcs)
            {
                g2.setColor(arc.getColour());
                g2.draw(new Arc2D.Double(
                                arc.getPosition().getX() - arc.getRadius(),
                                arc.getPosition().getY() - arc.getRadius(),
                                arc.getRadius() * 2,
                                arc.getRadius() * 2,
                                arc.getStartAngle(),
                                arc.getExtent(),
                                Arc2D.PIE)
                );
            }
        }
    }

    /**
     * Draws a line between 2 CartesianDoubles to the canvas.
     *
     * @param startPoint Starting coordinate.
     * @param endPoint Ending coordinate.
     * @param color The colour in which to draw the line, as a String. Accepts the following values:
     *  white, black, blue, cyan, gray, darkgray, lightgray, green, magenta, red, orange, yellow
     */
    public void drawLineBetweenPoints(CartesianDouble startPoint, CartesianDouble endPoint, String color) {
        lines.add(new LS(startPoint.getX(), startPoint.getY(),
                endPoint.getX(), endPoint.getY(),
                this.parseColour(color)));
    }

    public void drawCircle(CartesianDouble pos, double size, String colour)
    {
        circles.add(new MyCircle(pos, size, this.parseColour(colour)));
    }

    public void drawArc(CartesianDouble position, double radius, double startAngle, double extent, String colour)
    {
        arcs.add(new MyArc(position, radius, startAngle, extent, this.parseColour(colour)));
    }

    public void repaintCanvas()
    {
        repaint();
    }

    /**
     * Converts a string representing a colour into a Color enumerate attribute.
     *
     * @param colour The desired colour as a string.
     * @return The Color representation of this value.
     */
    private Color parseColour(String colour)
    {
        Color drawColor;

        switch (colour.toLowerCase()) {

            case "white":
                drawColor = Color.WHITE;
                break;
            case "black":
                drawColor = Color.BLACK;
                break;
            case "blue":
                drawColor = Color.BLUE;
                break;
            case "cyan":
                drawColor = Color.CYAN;
                break;
            case "gray":
                drawColor = Color.GRAY;
                break;
            case "darkgray":
                drawColor = Color.DARK_GRAY;
                break;
            case "lightgray":
                drawColor = Color.LIGHT_GRAY;
                break;
            case "green":
                drawColor = Color.GREEN;
                break;
            case "magenta":
                drawColor = Color.MAGENTA;
                break;
            case "red":
                drawColor = Color.RED;
                break;
            case "orange":
                drawColor = Color.ORANGE;
                break;
            case "yellow":
                drawColor = Color.YELLOW;
                break;
            default:
                System.out.println("Unknown colour '" + colour + "', defaulting to black.");
                drawColor = Color.BLACK;
                break;
        }


        return drawColor;
    }



    /**
     * Clears the canvas of all drawing.
     *
     */
    public void clear() {
        lines.clear();
        circles.clear();
        arcs.clear();
    }

    /**
     * <h1>LS</h1>
     * Class to contain x and y starting and ending coordinates 
     * of a line segment.
     *
     * @author Stuart Lacy
     * @version 1.0
     * @since 2014-12-04
     */
    private class LS
    {
        private double startX, startY, endX, endY;
        private Color color;

        /**
         * Constructor to create a line segment with the specified start
         * and end points.
         *  @param startX Initial x-coordinate of the line segment.
         * @param startY Initial y-coordinate of the line segment.
         * @param endX Ending x-coordinate of the line segment.
         * @param endY Ending y-coordinate of the line segment.
         */
        public LS(double startX, double startY, double endX, double endY, Color c) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.color = c;
        }

        /**
         * Returns the initial x-coordinate of the line segment.
         *
         * @return int The initial x-coordinate of the line segment.
         */
        public double getStartX() {
            return this.startX;
        }

        public Color getColor() {
            return this.color;
        }

        /**
         * Returns the initial y-coordinate of the line segment.
         *
         * @return int The initial y-coordinate of the line segment.
         */
        public double getStartY() {
            return this.startY;
        }

        /**
         * Returns the end x-coordinate of the line segment.
         *
         * @return int The end x-coordinate of the line segment.
         */
        public double getEndX() {
            return this.endX;
        }

        /**
         * Returns the end y-coordinate of the line segment.
         *
         * @return int The end y-coordinate of the line segment.
         */
        public double getEndY() {
            return this.endY;
        }
    }

    private class MyCircle
    {
        private CartesianDouble position;
        double radius;
        Color colour;
        public MyCircle(CartesianDouble position, double radius, Color colour)
        {
            this.position = new CartesianDouble(position.getX(), position.getY());
            this.radius = radius;
            this.colour = colour;
        }

        public CartesianDouble getPosition()
        {
            return this.position;
        }

        public double getRadius()
        {
            return this.radius;
        }

        public Color getColour()
        {
            return this.colour;
        }
    }

    private class MyArc
    {
        double radius, startAngle, extent;
        CartesianDouble position;
        Color colour;

        public MyArc(CartesianDouble position, double radius, double startAngle, double extent, Color colour)
        {
            this.position = position;
            this.radius = radius;
            this.startAngle = startAngle;
            this.extent = extent;
            this.colour = colour;
        }

        public double getRadius()
        {
            return radius;
        }

        public double getStartAngle()
        {
            return startAngle;
        }

        public double getExtent()
        {
            return extent;
        }

        public CartesianDouble getPosition()
        {
            return position;
        }

        public Color getColour()
        {
            return colour;
        }
    }
}
