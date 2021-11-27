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
    private int ammountEaten;
    
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
     * Construtor da classe dos pontos.
     */
    public Points() {
        this.ammountEaten = 0;
        this.points = 0;
        this.ghostsEaten = 0;
        this.level = 1;
        this.powerTimer = 0;
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
    public int getAmmountEaten() {
        return this.ammountEaten;
    }
    
    /**
     * @return Valor do tempo do poder do Pacman.
     */
    public int getPowerTimer() {
        return this.powerTimer;
    }
    
    /**
     * Metodo que diminui o tempo do poder do pacman.
     */
    public void decreasePowerTimer() {
        this.powerTimer--;
    }
    
    /**
     * Aumenta o nivel do jogo.
     */
    public void increaseLevel() {
        this.level++;
    }
    
    /**
     * Zera a quantidade sequencial de fantasmas comidos.
     */
    public void resetGhostsEaten() {
        this.ghostsEaten = 0;
    }
    
    /**
     * Comeu um Pac-dot.
     */
    private void ateNormalFood() {
        this.ammountEaten++;
        this.points += 10;
    }
    
    /**
     * Comeu uma Pilula de poder.
     */
    private void ateSuperFood() {
        this.ammountEaten++;
        this.points += 50;
        this.powerTimer = 20;
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
     * @param type
     */
    public void hasEaten(int type) {
        switch(type) {
            case 1 -> ateNormalFood();
            case 2 -> ateSuperFood();
            case 3 -> ateFruit();
        }
    }
}
