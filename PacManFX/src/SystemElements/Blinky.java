package SystemElements;

import Engine.Graph;
import Engine.GraphNode;
import Engine.Field;
import Engine.Points;

/**
 * Classe que contem o funcionamento para o fantasma blinky 
 * (Perseguidor ativo do Pacman).
 * @author Francisco Reis Nogueira - 11954374
 */
public class Blinky extends Ghosts{
    
    private int pointsThreshHold;
    
    /**
     * Contrutor da classe blinky.
     * @param G Grafo referente ao jogo.
     * @param m Mapa referente ao jogo.
     * @param p Sistema de pontos do jogo.
     */
    public Blinky(Graph G, Field m, Points p) {
        super(G,m,p);
        
        // Valor do blinky.
        this.elementValue = 11;
        this.pointsThreshHold = 75;
        
        // Define o nodulo de posicao do fantasma no mapa.
        setFirstPosition();
        
        this.basicSetup();
    }
    
    private void checkPoints() {
        if(this.points.getAmountEaten() > this.pointsThreshHold && this.frameDelay != 0) {
            this.frameDelay--;
            this.pointsThreshHold += 75;
        }
    }   
    
    
    /**
     * Metodo que atualiza a posicao de blinky no mapa.
     * @param goal Posicao do pacman que eh utilizada para verificar se o Pacman encostou em blinky.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    public int update(GraphNode goal) {
        if(this.framesPlayed < this.frameDelay) {
            this.framesPlayed++;
            return 0;
        }
        
        this.checkPoints();
        
        // Limpar a posicao atual.
        // Colocar o valor antigo no mapa
        this.map.setValueAtMap(this.previousGraphNodeBlockValue, this.gn.getPos());
        
        // Voltar o valor anterior do node para ele
        // Atualizando o Grafo
        this.gn.setBlockValue(this.previousGraphNodeBlockValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);

        // Verificar localizacao do pacman.
        // Olhar nos vizinhos do node atual
        if( this.gn.checkForNeighbor(goal.getId()) ) {
            // Se for o vizinho quer dizer que o foi encostado no pacman.
            return -1;
        }else if(this.status) {
            if(this.gn != this.G.getGraphNode(406))
                pathFind( this.G.getGraphNode( 406 ) ); // Central node
        } else {
            pathFind(goal);
        }
        
        // Ir para o proximo node.
        updatePositionOnMap();
        
        // Verifica o status do poder do pacman
        hasPacManEatenPallet();
        
        // Colocar blinky nessa nova posicao
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        this.framesPlayed = 0;
        
        return 0;
    }
}
