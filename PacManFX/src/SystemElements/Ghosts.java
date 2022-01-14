package SystemElements;

import Engine.Graph;
import Engine.Field;
import Engine.GraphNode;
import Engine.Points;
import java.util.ArrayList;
import java.util.Collections;

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
     * Guarda o ultimo valor utilizado na cor do fantasma.
     */
    protected int previousElementValue;
    
    /**
     * Guarda o valor original do nodulo onde o fantasma esta.
     */
    protected int previousGraphNodeBlockValue;
    
    /**
     * Verifica se o fantasma pode ser comido ou nao.
     */
    protected boolean alive;
    
    /**
     * Verifica o tempo do poder do pacman pra deixar os fantasmas que ja foram comidos 
     * voltarem ao normal de novo.
     */
    protected boolean waitUntilPowerTimerWearsOff;
    
    /**
     * ArrayList utilizado para o direcionamento dos fantasmas a alguma posicao.
     */
    protected ArrayList<Integer> totalPath;
    
    /**
     * Utilizado na movimentacao aleatoria dos fantasmas Inky e Clyde.
     */
    private GraphNode randomGraphNode;
    
    
    
    //Valores para as cores do fantasma
    
    /**
     * Poder do Pacman esta ativo.
     */
    private static final int EATABLE_GHOST = 100; 
    private boolean blueStatus;
    
    /**
     * O poder do Pacman esta resetando.
     */
    private static final int RESETING_GHOST = 200;
    private boolean resetingStatus;
    
    
    
    /**
     * Construtor para a classe dos fantasmas.
     * @param G Grafo referente ao jogo.
     * @param m Mapa referente ao jogo.
     * @param p Sistema de pontos do jogo.
     * @param _elementValue Valor int do tipo do fantasma
     * @param _startingNodeId Posicao inicial do fantasma
     */
    public Ghosts(Graph G, Field m, Points p, int _elementValue, int _startingNodeId) {
        super(G,m,p);
        
        this.elementValue = _elementValue;
        this.startingGraphNode = this.G.getGraphNode(_startingNodeId);
        
        this.status = false;
        this.frameDelay = 3;
        this.framesPlayed = 0;
        this.alive = true;
        this.blueStatus = false;
        this.resetingStatus = false;
        this.waitUntilPowerTimerWearsOff = false;
        this.totalPath = new ArrayList<>();
        
        this.setFirstPosition();
        this.basicSetup();
    }
    
    /**
     * Seta as variaveis basicas dos fantasmas, como mudanca no grafo e no mapa.
     */
    protected void basicSetup() {

        this.randomGraphNode = this.G.getRandomGraphNode();
        
        // Valor utilizado para o valor do ultimo elemento.
        this.previousElementValue = this.elementValue;
        
        // Guarda o valor anterior do nodulo que o fantasma sera colocado.
        this.previousGraphNodeBlockValue = this.gn.getBlockValue();
        
        // Atualiza o valor do nodulo no Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        
        // Atualiza o valor do fantasma no mapa.
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
    }
    
    /**
     * Pacman comeu o fantasma.
     */
    public void ghostWasEaten() {
        this.alive = false;
        this.elementValue = 31;
        this.waitUntilPowerTimerWearsOff = true;
    }
    
    /**
     * @return Se o fantasma foi comido.
     */
    public boolean isGhostAlive() { return this.alive; }
    
    /**
     *
     * @return Se o fantasma pode ser comido ou nao.
     */
    public boolean getGhostStatus() { return this.status; }
    
    /**
     * Verifica se o poder do Pacman esta ativo e muda as cores do fantasma de 
     * acordo com o poder.
     */
    public void hasPacManEatenPallet() {
        
        int powerTimer = this.points.getPowerTimer();
        
        if(powerTimer != 0 && powerTimer > 25 && this.alive && this.elementValue != 31) {
            // Poder esta ativo.
            if(!this.blueStatus) {
                this.elementValue = this.previousElementValue + EATABLE_GHOST;
                this.blueStatus = true;
            }
            this.status = true;
                       
        }
        else if(powerTimer != 0 && powerTimer <= 25 && this.alive && this.elementValue != 31) {
            // Poder esta resetando.
            if( !this.blueStatus ) {
                this.elementValue = this.previousElementValue + EATABLE_GHOST;
                this.blueStatus = true;
                this.resetingStatus = false;
            } else if (!this.resetingStatus) {
                this.elementValue = this.previousElementValue + RESETING_GHOST;
                this.resetingStatus = true;
                this.blueStatus = false;
            }
            
            this.status = true;
        }
        else  {
            // Pacman nao esta com poder.
            this.elementValue = this.previousElementValue;
            this.status = false;
            this.blueStatus = false;
            this.resetingStatus = false;
        }
        
        // Atualiza a cor fantasma no mapa
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
    }
    
    /**
     *  Realiza a procura por um GraphNode utilizando o algoritmo A* 
     * @param goal Nodulo de destino.
     */
    public void pathFind(GraphNode goal) {
        
        // Se a lista nao estiver vazia na nova interacao eh pq precisa
        // limpar a lista, os nodulos e o caminho utilizados na ultima iteracao
        if( (!this.totalPath.isEmpty()) ) {
            this.G.clearContentOfLists();
            
            if(this.totalPath.size() == 2) {
                this.totalPath.clear();
                return;
            }
            
            this.totalPath.clear();
        }
                
        // Algoritmo A*
        this.G.A_Star(this.gn, goal);
        
        // Quando o algoritmo chega no fim o nodulo goal possui um nodulo parente
        // e esse nodulo parente possui outro ate chegar na posicao do fantasma.
        // Essa lista representa o melhor caminho calculado pelo algoritmo A*
        
        // A partir dessa lista eh construido uma lista para q o caminho seja 
        // printado no mapa e faca blinky andar.
        while(goal.parent != null) {
            this.totalPath.add(goal.getId());
            goal = goal.parent;
        }
        this.G.killAllNodesParentData();

        this.totalPath.add(goal.getId());
        Collections.reverse(totalPath); // goal -> start. vai para start -> goal
    }
    
    /**
     * Atualiza a posicao do fantasma no mapa.
     */
    protected void updatePositionOnMap() {
        
        // Verifica se a lista total de caminhos ta vazia
        if(this.totalPath.isEmpty())
            return;

        // Pega o primeiro valor dessa litsta
        int nodeValue = this.totalPath.get(1);
        
        GraphNode newNode = this.G.getGraphNode(nodeValue);
                
        // Salva os novos valores
        this.previousGraphNodeBlockValue = newNode.getOriginalBlockValue();
        this.gn = newNode;
        
        // Atualiza o Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
    }
    
    /**
     * Atualiza a posicao de um fantasma no jogo.
     * @return True se tudo ocorreu certo, False caso o fantasma encostou no pacman.
     */
    public boolean update() {
        
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
        if( this.gn.checkForNeighborWithBlockValue(10) && this.alive && !this.status) {
            // Se for o vizinho quer dizer que o foi encostado no pacman.
            return false;
        }else if(this.status && this.alive) {
            // Poder esta ativo, volta para a posicao de origem
            if(this.gn != this.startingGraphNode)
                pathFind( this.startingGraphNode ); 
        }  else if( !this.alive ) {
            // Se o fantasma estiver 'morto'
            
            this.frameDelay = 0; // Velocidade maxima
            if(this.gn != this.startingGraphNode )
                pathFind( this.startingGraphNode );
            else {
                this.frameDelay = 3;
                this.alive = true;
                this.status = false;
                this.elementValue = this.previousElementValue;
                this.waitUntilPowerTimerWearsOff = true;
            }   
        } else {
            // Random movment!!!
            if(this.gn == this.randomGraphNode) {
                this.randomGraphNode = this.G.getRandomGraphNode();
            }
            pathFind(this.randomGraphNode);
        }
        
        // Ir para o proximo node.
        updatePositionOnMap();
       
        // Verifica o status do poder do pacman
        if(!this.waitUntilPowerTimerWearsOff)
            hasPacManEatenPallet();
        else if(!this.points.getPowerState()) {
            this.waitUntilPowerTimerWearsOff = false;
        }
        
        // Colocar fantasma na nova posicao
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        // Reseta os frames que ja passaram
        this.framesPlayed = 0;
        
        return true;
    }
}
