# graphics-project-1

This code is all three scenes for Programming Project 1: Picnic Time, done by Matthew Merritt, Michael Merritt, and Harsh Gandhi. 

The code is divided into three directories, with the following breakdown:

- `/java/` - Contains the animated 2D scene done using the Java Graphics2D package.
- `/javascript/` - Contains the animated 2D scene done using the JavaScript HTML Canvas.
- `/svg/` - Contains the 2D scene done using the SVG language.

In each directory, there is a README file explaining how to run the code to generate the scene.

In terms of special features, the Java and JavaScript versions include some unique bird flocking functionality. The program starts by drawing the one starting bird around the center of the canvas. If a bird flies too far off the right side of the canvas, it is destroyed and will no longer be drawn or handled in calculations. If all birds are destroyed, a new flock of one to three birds will be spawned slightly off the left side of the canvas in one of five set formations. These birds will maintain their formation, and have their flapping animation mostly synchronized with some slight variation. The formations include a solo bird, two birds in a vertical line, two birds in a diagonal line, three birds in a diagonal line, and three birds in a triangular formation.