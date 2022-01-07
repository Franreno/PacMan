package Engine;

import javafx.scene.image.Image;

/**
 *
 * @author franreno
 */
public class Images {
    
    public static final int IMG_SIZE = 16;
    public static final int SCREEN_WIDTH_OFFSET = 2*IMG_SIZE;
    public static final int SCREEN_HEIGHT_OFFSET = 4*IMG_SIZE;
    
    
    public static final int SCREEN_WIDTH = 32*IMG_SIZE;
    public static final int SCREEN_HEIGHT = 40*IMG_SIZE;
    
    
    public final Image VoidImage = new Image("assets/Void.png");
    public final Image WallImage = new Image("assets/Wall.png");
    
    public final Image PowerDotImage = new Image("assets/Power.png");
    public final Image PacDotImage = new Image("assets/PacDot.png");
    
    public final Image CherryImage = new Image("assets/Cherry.png");
    public final Image StrawberryImage = new Image("assets/Strawberry.png");
    public final Image OrangeImage = new Image("assets/Orange.png");
    
    public final Image PacManImage = new Image("assets/Pacman.gif", IMG_SIZE, IMG_SIZE, false, false);
    public final Image BlinkyImage = new Image("assets/Blinky.gif", IMG_SIZE, IMG_SIZE, false, false);
    public final Image PinkyImage = new Image("assets/Pinky.gif", IMG_SIZE, IMG_SIZE, false, false);
    public final Image ClydeImage = new Image("assets/Clyde.gif", IMG_SIZE, IMG_SIZE, false, false);
    public final Image InkyImage = new Image("assets/Inky.gif", IMG_SIZE, IMG_SIZE, false, false);
    public final Image BlueGhostImage = new Image("assets/BlueGhost.gif", IMG_SIZE, IMG_SIZE, false, false);
    
    
    public Image getImageFromFieldValue(int value) {
        switch(value) {
            case 0:
                return this.VoidImage;
            case 1:
                return this.PacDotImage;
            case 2:
                return this.PowerDotImage;
            case 3:
                return this.CherryImage;
            case 4:
                return this.WallImage;
            case 5:
                return this.VoidImage;
            case 6:
                return this.VoidImage;
            case 10:
                return this.PacManImage;
            case 11:
                return this.BlinkyImage;
            case 13:
                return this.InkyImage;
            case 17:
                return this.PinkyImage;
            case 19:
                return this.ClydeImage;
            case 23:
                return this.BlueGhostImage;
        }
        
        return null;
    }
    
    public Image getFruitType(int level) {
        switch (level) {
            case 1:
                return this.CherryImage;
            case 2:
                return this.StrawberryImage;
            case 3:
                return this.OrangeImage;
        }
        
        return null;
    }
}
