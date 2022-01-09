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
public class PersuitGhost extends Ghosts{
    
    private int pointsThreshHold;
    private int lastFrameDelay;
    
    /**
     * Contrutor da classe blinky.
     * @param G Grafo referente ao jogo.
     * @param m Mapa referente ao jogo.
     * @param p Sistema de pontos do jogo.
     */
    public PersuitGhost(Graph G, Field m, Points p, int _elementValue, int _startingNodeId) {
        super(G,m,p, _elementValue, _startingNodeId);
        
        this.pointsThreshHold = 75;
        this.lastFrameDelay = this.frameDelay;
    }
    
    public void checkPoints() {
        if(this.points.getAmountEaten() > this.pointsThreshHold && this.frameDelay != 0) {
            this.frameDelay--;
            this.lastFrameDelay = this.frameDelay;
            this.pointsThreshHold += 75;
        }
    }   
    
    
    /**
     * Metodo que atualiza a posicao de blinky no mapa.
     * @param goal Posicao do pacman que eh utilizada para verificar se o Pacman encostou em blinky.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    public boolean update(GraphNode goal) {
        if(this.framesPlayed < this.frameDelay) {
            this.framesPlayed++;
            return true;
        }
        
        // Limpar a posicao atual.
        // Colocar o valor antigo no mapa
        this.map.setValueAtMap(this.previousGraphNodeBlockValue, this.gn.getPos());
        
        // Voltar o valor anterior do node para ele
        // Atualizando o Grafo
        this.gn.setBlockValue(this.previousGraphNodeBlockValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);

        // Verificar localizacao do pacman.
        // Olhar nos vizinhos do node atual
        if( this.gn.checkForNeighbor(goal.getId()) && this.alive && !this.status) {
            // Se for o vizinho quer dizer que o foi encostado no pacman.
            return false;
        }else if(this.status) {
            if(this.gn != this.startingGraphNode)
                pathFind( this.startingGraphNode ); // Central node
        } else if(!this.alive) {
            this.frameDelay = 0;
            // Se o fantasma estiver 'morto'
            if(this.gn != this.startingGraphNode )
                pathFind( this.startingGraphNode );
            else {
                this.frameDelay = 3;
                this.alive = true;
                this.status = false;
                this.elementValue = this.previousElementValue;
                this.waitUntilPowerTimerWearsOff = true;
            }
        } 
        
        else {
            pathFind(goal);
        }
        
        // Ir para o proximo node.
        updatePositionOnMap();
        
        // Verifica o status do poder do pacman
        if(!this.waitUntilPowerTimerWearsOff)
            hasPacManEatenPallet();
        else if(!this.points.getPowerState()) {
            this.waitUntilPowerTimerWearsOff = false;
        }
        
        // Colocar blinky nessa nova posicao
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        this.framesPlayed = 0;
        
        return true;
    }
}
