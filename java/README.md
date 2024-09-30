# Running the Scene

To run this program, open a terminal in the `/java/` directory.

Then run the following commands: 

```bash
javac PicnicScene.java
java PicnicScene
```

A window should appear with the animated scene. 

## Note about resolution:

The window will be 1600 pixels by 1000 pixels. A 1080p monitor should be able to display the application.

The window is not meant to be resized. While the window is technically resizable, non-uniform scaling of the window will reveal elements that are not intended to be in the viewport. Namely, the black background and debug axes will revealed, as well as the offscreen portions of the sun.