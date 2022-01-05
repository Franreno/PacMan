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
     * Acumulacao da velocidade utilizado para elementos cuja velocidade nao eh
     * constante.
     */
    protected int[] posOffset;
    
    /**
     * Valor representativo do elemento no mapa.
     */
    protected int elementValue;
    
    /**
     * Construtor da classe.
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
        boolean flag = false;
        while(!flag) {
            // Pega valores no grafo que sao compativeis com o jogo.
            this.gn = G.getRandomGraphNode();
            if(this.gn.getBlockValue() != 2 && this.gn.getBlockValue() != 10 && !this.gn.checkForNeighborWithBlockValue(10))
                flag = true;
        }
        
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
}