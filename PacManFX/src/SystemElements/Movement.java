package SystemElements;
import Engine.Field;
import Engine.Graph;
import Engine.GraphNode;
import Engine.Points;

/**
 * Classe geral que representa todo elemento que se move no mapa.
 * @author Francisco Reis Nogueira - 11954374
 */
public class Movement {
    /**
     * Grafo referente ao jogo. Utilizado para a movimentacao dos elementos.
     */
    protected Graph G;
    
    /**
     * Nodulo do grafo em que o elemento se encontra.
     */
    protected GraphNode gn;
    
    /**
     * Mapa referente ao jogo. Utilizado para a movimentacao dos elementos.
     */
    protected Field map;
    
    /**
     * Sistema de pontos para o elemento.
     */
    protected Points points;
    
    
    /**
     * Posicao do elemento na matriz do mapa.
     */
    protected int[] pos;
    
    /**
     * Velocidade do elemento.
     */
    protected int[] dpos;
    
    /**
     * Quantidade de frames que o elemento deve esperar ate realizar o proximo movimento.
     */
    protected int frameDelay;
    
    
    /**
     * Acumulador de quantos frames ja se passaram.
     */
    protected int framesPlayed;
    
    
    /**
     * Valor representativo do elemento no mapa.
     */
    protected int elementValue;
    
    /**
     * Posicao inicial do elemento.
     */
    protected GraphNode startingGraphNode;
    
    /**
     * Construtor da classe.posOffset
     * @param _G Grafo referente ao jogo.
     * @param m Mapa referente ao jogo.
     * @param p Sistema de pontos referente ao jogo.
     */
    public Movement(Graph _G, Field m, Points p) {
        this.G = _G;
        this.map = m;
        this.points = p;
        this.pos = new int[2];
        this.dpos = new int[2];
        this.gn = null;
        this.elementValue = -1;
        this.startingGraphNode = null;
    }
    
    /**
     * @return Retorna o Nodulo do Grafo referente a este elemento.
     */
    public GraphNode getNode() {
        return this.gn;
    }
    
    /**
     * Gera a atribuicao aleatoria de um nodulo para este elemento.
     */
    protected void setFirstPosition() {        
        this.gn = this.startingGraphNode;
        
        int[] _randomPos = this.gn.getPos();
        this.pos[0] = _randomPos[0];
        this.pos[1] = _randomPos[1];
    }
    
    /**
     * Define a velocidade do elemento.
     * @param dVertical Velocidade vertical.
     * @param dHorizontal Velocidade horizontal.
     */
    protected void setVelocity(int dVertical, int dHorizontal) {
        this.dpos[0] = dVertical;
        this.dpos[1] = dHorizontal;
    }
    
    /**
     * Coloca o valor deste elemento no mapa.
     */
    protected void updateOnMap() {
        this.map.setValueAtMap(elementValue, gn.getPos());
    }
    
    /**
     *  Retorna o elemento a sua posicao inicial.
     */
    public void resetEntity() {
        // Resetar pro bloco original
        // Colocar no mapa o startingPosition
        this.gn.setBlockValue( this.gn.getOriginalBlockValue() );
        this.G.updateHashMap(this.gn.getId(), this.gn);
        this.map.setValueAtMap(this.gn.getOriginalBlockValue(), this.gn.getPos());
        
        
        this.gn = this.startingGraphNode;
        int[] _pos = this.gn.getPos();
        
        this.pos[0] = _pos[0];
        this.pos[1] = _pos[1];
        
        // Atualiza o valor do nodulo no Grafo
        this.gn.setBlockValue( this.elementValue );
        this.G.updateHashMap(this.gn.getId(), this.gn);
        
        // Atualiza o valor do fantasma no mapa.
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
    }
    
    
}