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
    
    /**
     * Verificacao da quantidade comida para criar uma nova fruta no jogo.
     */
    private int amountEatenThreshold;
    
    
    /**
     * Quantas vezes a fruta apareceu nesse nivel.
     */
    private int fruitApperedXTimes;
    
    /**
     * Posicao que as frutas vao aparecer
     */
    private final int fruitNodeID = 658;
    
    /**
     * Poder do pacman esta ativo?
     */
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
    
    /**
     *
     * @return Se o poder do pacman esta ativo.
     */
    public boolean getPowerState() { return this.powerState; }
    
    /**
     *
     * @return O nivel do jogo.
     */
    public int getLevel() { return this.level; }

    /**
     *
     * @return A posicao original da fruta.
     */
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
        this.amountEaten = 0;
        this.amountEatenThreshold = 70;
        this.fruitApperedXTimes = 0;
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
    
    /**
     * Verifica a quantidade de PacDots comidos para a criacao de uma nova fruta.
     * @return Se uma nova fruta pode ser criada.
     */
    public boolean checkAmountEaten() {
        if( this.fruitApperedXTimes < 2 && this.amountEaten >= this.amountEatenThreshold ) {
            this.amountEatenThreshold += 30;
            this.fruitApperedXTimes++;
            return true;
        }
        
        return false;
    }
}
