package SystemElements;

import Engine.Graph;
import Engine.GraphNode;
import Engine.Field;
import Engine.Points;

/**
 * Classe que contem o funcionamento para os fantasmas 
 * Perseguidores do Pacman
 * @author Francisco Reis Nogueira - 11954374
 */
public class PersuitGhost extends Ghosts{
    
    /**
     * Controla o aumento da velocidade do fantasma blinky.
     */
    private int pointsThreshHold;
    
    /**
     * Salva o ultimo valor da velocidade do fantasma.
     */
    private int lastFrameDelay;
    
    /**
     * Contrutor da classe blinky.
     * @param G Grafo referente ao jogo.
     * @param m Mapa referente ao jogo.
     * @param p Sistema de pontos do jogo.
     * @param _elementValue Valor int do tipo do fantasma
     * @param _startingNodeId Posicao inicial do fantasma
     */
    public PersuitGhost(Graph G, Field m, Points p, int _elementValue, int _startingNodeId) {
        super(G,m,p, _elementValue, _startingNodeId);
        
        this.pointsThreshHold = 75;
        this.lastFrameDelay = this.frameDelay;
    }
    
    /**
     * Verifica a quantidade de pontos que o pacman comeu e atualiza a velocidade do fantasma.
     */
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
     * @return True Para normalidade. False para ter pego o pacman.
     */
    public boolean update(GraphNode goal) {
        
        // Verifica quantos frames ja se passaram (Sensacao de velocidade dos fantasmas).
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
        }else if(this.status && this.alive) {
            // Poder esta ativo, volta para a posicao de origem
            if(this.gn != this.startingGraphNode)
                pathFind( this.startingGraphNode );
        } else if(!this.alive) {
            // Se o fantasma estiver 'morto'
            this.frameDelay = 0;
            if(this.gn != this.startingGraphNode )
                pathFind( this.startingGraphNode );
            else {
                this.frameDelay = lastFrameDelay;
                this.alive = true;
                this.status = false;
                this.elementValue = this.previousElementValue;
                this.waitUntilPowerTimerWearsOff = true;
            }
        } 
        
        else {
            // Procura o pacman e segue ele.
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
        
        // Reseta os frames que ja passaram
        this.framesPlayed = 0;
        
        return true;
    }
}
