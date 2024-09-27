
/********************
 * PicnicScene
 * Author: Matthew Merritt, Michael Merritt, Harsh Gandhi
 * Fall 24: CSC345/CSC645
 *
 * Based off of the HierarchyScene.java file from class, which was 
 * based off the GraphicsStarter2D.java file from Eck's text.
 * 
 * Java implementation of the picnic scene, with animations.
 ********************/

import java.awt.*; // import statements to make necessary classes available
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class PicnicScene extends JPanel {
    /**
     * This main() routine makes it possible to run the class PicnicScene
     * as an application. It simply creates a window that contains a panel
     * of type PicnicScene. The program ends when the user closed the
     * window by clicking its close box.
     */
    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Java: Picnic Time!"); // The parameter shows in the window title bar.
        final PicnicScene panel = new PicnicScene(); // The drawing area.
        window.setContentPane(panel); // Show an instance of this class in main window pane
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        window.pack(); // Set window size based on the preferred sizes of its contents.
        window.setResizable(true); // Let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        // Center window on screen.
        window.setLocation((screen.width - window.getWidth()) / 2, (screen.height - window.getHeight()) / 2);

        // Timer configuration for animation events and repaints.
        final int timer_delay_60fps = 1000 / 16; // Used by the animation timer for framerate.
        final long startTime = System.currentTimeMillis();
        Timer animationTimer = new Timer(timer_delay_60fps, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.birdX -= 0.1;
                if(panel.birdX <= -1) {
                    panel.birdX = 10;
                }
                panel.wingScaler += panel.wingDelta;
                if(panel.wingScaler <= -1) {
                    panel.wingScaler = -1;
                    panel.wingDelta = -panel.wingDelta;
                } else if(panel.wingScaler >= 1) {
                    panel.wingScaler = 1;
                    panel.wingDelta = -panel.wingDelta;
                }
                panel.birdY -= 0.3 * panel.wingDelta;
                panel.frameNumber++;
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });

        window.setVisible(true); // Open the window, making it visible on the screen.
        animationTimer.start(); // Start the animation running.
    }

    private float pixelSize; // This is the measure of a pixel in the coordinate system
                             // set up by calling the applyLimits method. It can be used
                             // for setting line widths, for example.

    private int frameNumber; // The current number of frames elasped during the program.
                             // Used to determine how far along animations are.

    private long elapsedTimeMillis; // The time, in milliseconds, since the animation started.
    private double birdX = 6;
    private double birdY = 8;
    private double wingScaler = 1;
    private double wingDelta = -0.1;

    /**
     * This constructor sets up a PicnicScene when it is created. Here, it
     * sets the size of the drawing area. (The size is set as a "preferred size,"
     * which will be used by the pack() command in the main() routine.)
     */
    public PicnicScene() {
        setPreferredSize(new Dimension(1600, 1000)); // Set size of drawing area, in pixels.
    }

    /**
     * The paintComponent method draws the content of the JPanel. The parameter
     * is a graphics context that can be used for drawing on the panel. Note that
     * it is declared to be of type Graphics but is actually of type Graphics2D,
     * which is a subclass of Graphics.
     */
    protected void paintComponent(Graphics g) {
        /*
         * First, create a Graphics2D drawing context for drawing on the panel.
         * (g.create() makes a copy of g, which will draw to the same place as g,
         * but changes to the returned copy will not affect the original.)
         */
        Graphics2D g2 = (Graphics2D) g.create();

        /*
         * Turn on antialiasing in this graphics context, for better drawing.
         */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*
         * Fill in the entire drawing area with a black background.
         */
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight()); // From the old graphics API!

        /*
         * Here, I set up a new coordinate system on the drawing area, by calling
         * the applyLimits() method that is defined below. Without this call, I
         * would be using regular pixel coordinates. This function sets the global
         * variable pixelSize, which I need for stroke widths in the transformed
         * coordinate system.
         */

        applyWindowToViewportTransformation(g2, 0, 10, 0, 10, true);

        /*
         * Finish by drawing a few shapes as an example. You can erase the rest of
         * this subroutine and substitute your own drawing.
         */

        drawScene(g2);
    }

    /**
     * Draw the scene
     */
    private void drawScene(Graphics2D g2) {
       drawCoordinateFrame(g2, 10); // 10 is the number of "ticks" to show

        // Scene version 1
        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform
        g2.scale(1, 1); // No scaling yet, but setting it up to make a tad bigger
        // TODO: calls to additional functions to draw shapes and components
        drawBackground(g2);
        drawLake(g2); 
        drawSun(g2, 6.5, 10);
        drawBlanket(g2, 6, 1.5); // TODO: move up with screen resize
        drawTree(g2, 8.5, 3);
        drawTree(g2, 1.5, 3);
        drawSeesaw(g2, 4, 2);
        drawBird(g2);

        g2.setTransform(cs); // Restore previous coordinate system
    }

    private void drawBird(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.translate(birdX, birdY);
        g2.scale(0.25, 0.25 * wingScaler);
        g2.setStroke(new BasicStroke(10 * pixelSize));
        g2.setPaint(Color.BLACK); 
        g2.drawArc(0, -1, 4, 2, 180, 90);
        g2.scale(-1, 1);
        g2.drawArc(0, -1, 4, 2, 180, 90);
        g2.setTransform(cs); 
    }

    private void drawLake(Graphics2D g2) {
        AffineTransform cs = g2.getTransform(); 
        g2.scale(1, 0.9);
        g2.translate(1, 3.05);
        g2.setPaint(new Color(0, 0, 139)); 
        g2.fillArc(0, 0, 8, 5, 0, 180); 
        
    
        g2.setTransform(cs); 
    }
    

    /**
     * Example function template, currently unimplemented
     * 
     * @param g2 The drawing context whose transform will be set.
     */
    private void drawBackground(Graphics2D g2) {
        AffineTransform cs = g2.getTransform(); 
    
        g2.setPaint(new Color(135, 206, 250));
        g2.fillRect(0, 5, 10, 5); 

        g2.setPaint(new Color(117, 225, 0));
        g2.fillRect(0, 0, 10, 5); 

        g2.setTransform(cs); 
    }    

    /**
     * Draws the seesaw, with the two animated people on it.
     * Composed of a green triangular base with a pink rectangle, and two people
     * facing each other.
     * 
     * @param g2 The drawing context whose transform will be set.
     * @param x  The x-location for the midpoint of the bottom side of the
     *           triangular base.
     * @param y  The y-location for the midpoint of the bottom side of the
     *           triangular base.
     */
    private void drawSeesaw(Graphics2D g2, double x, double y) {
        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform

        // move the transform to the location of the triangle
        g2.translate(x, y);

        // begin by creating the triangular base
        Path2D poly = new Path2D.Double();
        poly.moveTo(-1, 0);
        poly.lineTo(0, 1.5);
        poly.lineTo(1, 0);
        poly.closePath();
        g2.setPaint(new Color(58, 95, 11));
        g2.fill(poly);

        // move to the center of the seesaw bar (the point it rotates on)
        g2.translate(0, 1.5);
        // rotate the transform based on the current number of frames
        // the rotation angles fall on the interval: [-45 degrees, 45 degrees]
        g2.rotate(Math.sin(frameNumber * 0.25) * Math.PI / 6);

        // add the animated bar to the seesaw
        g2.setPaint(new Color(200, 0, 200));
        g2.fill(new Rectangle2D.Double(-3, -0.1, 6, 0.2));

        // TODO: implement the animated people

        g2.setTransform(cs); // Restore previous coordinate system
    }

    /**
     * Draws the seesaw, with the two animated people on it.
     * Composed of lines and circles.
     * The animation covers the legs bending as they reach the ground.
     * 
     * @param g2 The drawing context whose transform will be set.
     * @param x  The x-location for the midpoint of the bottom side of the
     *           triangular base.
     * @param y  The y-location for the midpoint of the bottom side of the
     *           triangular base.
     */
    private void drawPerson(Graphics2D g2, double x, double y, boolean isAnimated) {
        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform

        // TODO: implement drawing code and any other transformations
        // TODO: implement animation

        g2.setTransform(cs); // Restore previous coordinate system
    }

    /**
     * Draws the sun, with five layers that fluctuate slightly.
     * Composed of five yellow circles of decreasing alpha values.
     * 
     * @param g2 The drawing context whose transform will be set.
     * @param x  The x-location for the midpoint of the sun.
     * @param y  The y-location for the midpoint of the sun.
     */
    private void drawSun(Graphics2D g2, double x, double y) {
        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform

        // move the transform to the location of the object
        g2.translate(x, y);

        g2.setPaint(new Color(255, 255, 0, 50));
        g2.fill(new Ellipse2D.Double(-1.5, -1.5, 3, 3));
        g2.setPaint(new Color(255, 255, 0, 100));
        g2.fill(new Ellipse2D.Double(-1.25, -1.25, 2.5, 2.5));
        g2.setPaint(new Color(255, 255, 0, 150));
        g2.fill(new Ellipse2D.Double(-1, -1, 2, 2));
        g2.setPaint(new Color(255, 255, 0, 200));
        g2.fill(new Ellipse2D.Double(-0.75, -0.75, 1.5, 1.5));
        g2.setPaint(new Color(255, 255, 0, 250));
        g2.fill(new Ellipse2D.Double(-0.5, -0.5, 1, 1));

        g2.setTransform(cs); // Restore previous coordinate system
    }

    /**
     * Draws the sun, with five layers that fluctuate slightly.
     * Composed of a green circle and a polygonal path shape.
     * 
     * @param g2 The drawing context whose transform will be set.
     * @param x  The x-location for the midpoint of the sun.
     * @param y  The y-location for the midpoint of the sun.
     */
    private void drawTree(Graphics2D g2, double x, double y) {
        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform

        // move the transform to the location of the object
        g2.translate(x, y);

        // draw trunk
        Path2D poly = new Path2D.Double();
        poly.moveTo(-0.5, 2); // top left
        poly.lineTo(-0.5, 1.5);
        poly.curveTo(-0.5, -2, -1, -2, -1, -2);
        poly.lineTo(-0.25, -1.75);
        poly.lineTo(0.15, -2.25);
        poly.lineTo(0.4, -1.75);
        poly.lineTo(1, -2);
        poly.curveTo(1, -2, 0.5, -2, 0.5, 1.5);
        poly.lineTo(0.5, 2); // top right
        poly.closePath();

        g2.setPaint(new Color(83, 53, 10));
        g2.fill(poly);

        // draw leaves
        g2.setPaint(new Color(58, 95, 11));
        g2.fill(new Ellipse2D.Double(-1.5, 0.7, 3, 3));

        g2.setTransform(cs); // Restore previous coordinate system
    }

    /**
     * Draws the blanket, with a person and an apple on it.
     * Composed of a tan polygon, red circle, and person.
     * 
     * @param g2 The drawing context whose transform will be set.
     * @param x  The x-location for the midpoint of the sun.
     * @param y  The y-location for the midpoint of the sun.
     */
    private void drawBlanket(Graphics2D g2, double x, double y) {
        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform

        // move the transform to the location of the object
        g2.translate(x, y);

        // draw blanket
        Path2D poly = new Path2D.Double();
        poly.moveTo(-2, -1);
        poly.lineTo(0, -1);
        poly.lineTo(2, 1);
        poly.lineTo(0, 1);
        poly.closePath();

        g2.setPaint(new Color(207, 185, 151));
        g2.fill(poly);

        // draw basket
        g2.setPaint(new Color(92, 77, 57));
        g2.draw(new Arc2D.Double(1, 1, 0.5, 0.5, 180, 180, Arc2D.OPEN));
        g2.fill(new Rectangle2D.Double(0.9, 0.75, 0.7, 0.5));

        // draw apple
        g2.setPaint(Color.RED);
        g2.fill(new Ellipse2D.Double(-0.4, 0.4, 0.1, 0.1));

        // draw person
        // TODO: add person here

        g2.setTransform(cs); // Restore previous coordinate system
    }

    /**
     * Draw a coordinate frame, left for debugging purposes
     */
    private void drawCoordinateFrame(Graphics2D g2, int length) {
        g2.setPaint(Color.WHITE);
        g2.setStroke(new BasicStroke(2 * pixelSize));
        // Draw y-axis and its tick marks
        g2.draw(new Line2D.Double(0, 0, 0, length)); // Y-axis
        for (int i = 1; i <= length; i++)
            g2.draw(new Line2D.Double(-0.2, i, 0.2, i));

        // Draw x-axis and its tick marks
        g2.draw(new Line2D.Double(0, 0, length, 0));
        for (int i = 1; i <= length; i++)
            g2.draw(new Line2D.Double(i, -0.2, i, 0.2));
    }

    /**
     * Applies a coordinate transform to a Graphics2D graphics context. The upper
     * left corner of the viewport where the graphics context draws is assumed to
     * be (0,0). The coordinate transform will make a requested view window visible
     * in the drawing area. The requested limits might be adjusted to preserve the
     * aspect ratio.
     * This method sets the value of the global variable pixelSize, which is defined
     * as the
     * maximum of the width of a pixel and the height of a pixel as measured in the
     * coordinate system. (If the aspect ratio is preserved, then the width and
     * height will agree.
     * 
     * @param g2             The drawing context whose transform will be set.
     * @param left           requested x-value at left of drawing area.
     * @param right          requested x-value at right of drawing area.
     * @param bottom         requested y-value at bottom of drawing area; can be
     *                       less than
     *                       top, which will reverse the orientation of the y-axis
     *                       to make the positive
     *                       direction point upwards.
     * @param top            requested y-value at top of drawing area.
     * @param preserveAspect if preserveAspect is false, then the requested view
     *                       window
     *                       rectangle will exactly fill the viewport; if it is
     *                       true, then the limits will be
     *                       expanded in one direction, horizontally or vertically,
     *                       if necessary, to make the
     *                       aspect ratio of the view window match the aspect ratio
     *                       of the viewport.
     *                       Note that when preserveAspect is false, the units of
     *                       measure in the horizontal
     *                       and vertical directions will be different.
     */
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right,
            double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth(); // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the world window vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the world window horizontally.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }
}
