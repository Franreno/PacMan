package Engine;

/**
 * Classe que contem os metodos e atributos referentes ao esquema de pontos do jogo.
 * 
 * @author Francisco Reis Nogueira - 11954374
 */
public class Points {
    
    /**
     * Quantidade de Pac-dots que o Pacman ja comeu.
     */
    private int amountEaten;
    
    /**
     * Valor total dos pontos do jogo.
     */
    private int points;
    
    /**
     * Quantidade de fantasmas comidos em sequencia.
     */
    private int ghostsEaten;
    
    /**
     * Nivel no qual o jogo se encontra.
     */
    private int level;
    
    /**
     * Temporizador referente ao poder do pacman.
     */
    private int powerTimer;
    
    private int amountEatenThreshold;
    
    private int fruitApperedXTimes;
    
    private final int fruitNodeID = 658;
    
    private boolean powerState;
    
    
    /**
     * Construtor da classe dos pontos.
     */
    public Points() {
        this.amountEaten = 0;
        this.amountEatenThreshold = 70;
        this.fruitApperedXTimes = 0;
        this.points = 0;
        this.ghostsEaten = 0;
        this.level = 1;
        this.powerTimer = 0;
        this.powerState = false;
        
    }
    
    /**
     * @return Valor dos pontos do jogo.
     */
    public int getPoints() {
        return this.points;
    }
    
    /**
     * @return Quantidade de Pac-dots comidos.
     */
    public int getAmountEaten() {
        return this.amountEaten;
    }
    
    /**
     * @return Valor do tempo do poder do Pacman.
     */
    public int getPowerTimer() {
        return this.powerTimer;
    }
    
    public boolean getPowerState() { return this.powerState; }
    
    
    public int getLevel() { return this.level; }
    
    public int getFruitNodeID() { return this.fruitNodeID; }
    
    /**
     * Metodo que diminui o tempo do poder do pacman.
     */
    public void decreasePowerTimer() {
        this.powerTimer--;
        if(this.powerTimer <= 0) {
            this.powerState = false;
            this.ghostsEaten = 0;
        }
            
    }
    
    /**
     * Aumenta o nivel do jogo.
     */
    public void increaseLevel() {
        this.level++;
    }
    
    /**
     * Comeu um Pac-dot.
     */
    private void ateNormalFood() {
        this.amountEaten++;
        this.points += 10;
    }
    
    /**
     * Comeu uma Pilula de poder.
     */
    private void ateSuperFood() {
        this.amountEaten++;
        this.points += 50;
        this.powerTimer = 90;
        this.powerState = true;
    }
    
    /**
     * Comeu uma fruta.
     */
    private void ateFruit() {
        this.points += 100 + (200 * (this.level -1));
    }
    
    /**
     * Comeu um fantasma.
     */
    public void ateGhost() {
        this.ghostsEaten++;
        this.points += 100 * Math.pow(2, this.ghostsEaten);    
    }

    /**
     * Metodo que seleciona o tipo de elemento que foi comido pelo PacMan.
     * @param type tipo de comida que o Pacman comeu
     */
    public void hasEaten(int type) {
        switch(type) {
            case 1:
                ateNormalFood();
                break;
            case 2: 
                ateSuperFood();
                break;
                
            case 3: 
                ateFruit();
                break;
        }
    }
    
    
    public boolean checkAmountEaten() {
        if( this.fruitApperedXTimes < 2 && this.amountEaten >= this.amountEatenThreshold ) {
            this.amountEatenThreshold += 30;
            this.fruitApperedXTimes++;
            return true;
        }
        
        return false;
    }
}
