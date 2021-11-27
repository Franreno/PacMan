package SystemElements;

import Engine.Graph;
import Engine.Map;
import Engine.Points;

/**
 * Classe que representa os atributos basicos dos fantasmas.
 * @author Francisco Reis Nogueira - 11954374
 */
public class Ghosts extends Movement{
    /**
     * Fantasma pode ser comido?
     */
    protected boolean status;
    
    /**
     * Sistema de pontos para verificacao do status do poder do Pacman.
     */
    protected Points points;
    
    /**
     * Guarda o ultimo valor utilizado na cor do fantasma.
     */
    protected int previousElementValue;
    
    /**
     * Guarda o valor original do nodulo onde o fantasma esta.
     */
     // O fantasma nao muda o conteudo do mapa, entao deve-se retornar ao mapa
     //o valor original daquele nodulo quando o fantasma sair dele.
    protected int previousGraphNodeBlockValue;
    
    //Valores para as cores do fantasma
    
    /**
     * Poder do Pacman esta ativo.
     */
    private static final int EATABLE_GHOST = 23; 
    
    /**
     * O poder do Pacman esta resetando.
     */
    private static final int RESETING_GHOST = 29;

    /**
     * Construtor para a classe dos fantasmas.
     * @param G Grafo referente ao jogo.
     * @param m Mapa referente ao jogo.
     * @param p Sistema de pontos do jogo.
     */
    public Ghosts(Graph G, Map m, Points p) {
        super(G,m);
        this.status = false;
        this.points = p;
    }
    
    /**
     * Verifica se o poder do Pacman esta ativo e muda as cores do fantasma de 
     * acordo com o poder.
     */
    public void hasPacManEatenPallet() {
        
        int powerTimer = this.points.getPowerTimer();
        
        if(powerTimer != 0 && powerTimer > 10) {
            // Poder esta ativo.
            this.elementValue = EATABLE_GHOST;
            this.status = true;
        }
        else if(powerTimer != 0 && powerTimer <= 10) {
            // Poder esta resetando.
            this.elementValue = (this.elementValue == EATABLE_GHOST) ? RESETING_GHOST : EATABLE_GHOST;
            this.status = true;
        }
        else  {
            // Pacman nao esta com poder.
            this.elementValue = this.previousElementValue;
            this.status = false;
        }
        
        // Atualiza a cor fantasma no mapa
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
    }
}
