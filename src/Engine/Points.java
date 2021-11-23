package Engine;

/**
 *
 * @author franreno
 */
public class Points {
    private int ammountEaten;
    private int points;
    private int ghostsEaten;
    private int level;
    private int powerTimer;
    
    public Points() {
        this.ammountEaten = 0;
        this.points = 0;
        this.ghostsEaten = 0;
        this.level = 1;
        this.powerTimer = 0;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    public int getAmmountEaten() {
        return this.ammountEaten;
    }
    
    public int getPowerTimer() {
        return this.powerTimer;
    }
    
    public void decreasePowerTimer() {
        this.powerTimer--;
    }
    
    public void increaseLevel() {
        this.level++;
    }
    
    public void resetGhostsEaten() {
        this.ghostsEaten = 0;
    }
    
    private void ateNormalFood() {
        this.ammountEaten++;
        this.points += 10;
    }
    
    private void ateSuperFood() {
        this.points += 50;
        this.powerTimer = 20;
    }
    
    private void ateFruit() {
        this.points += 100 + (200 * (this.level -1));
    }
    
    private void ateGhost() {
        this.ghostsEaten++;
        this.points += 100 * Math.pow(2, this.ghostsEaten);
        
    }

    public void hasEaten(int type) {
        switch(type) {
            case 1 -> ateNormalFood();
            case 2 -> ateSuperFood();
            case 3 -> ateFruit();
            case 4 -> ateGhost();
        }
    }
}
