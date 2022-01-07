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
     // O fantasma nao muda o conteudo do mapa, entao deve-se retornar ao mapa
     //o valor original daquele nodulo quando o fantasma sair dele.
    protected int previousGraphNodeBlockValue;
    
    /**
     * ArrayList utilizado para o direcionamento dos fantasmas a alguma posicao.
     */
    protected ArrayList<Integer> totalPath;
    
    private GraphNode randomGraphNode;
    
    
    
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
    public Ghosts(Graph G, Field m, Points p) {
        super(G,m,p);
        this.status = false;
        this.frameDelay = 3;
        this.framesPlayed = 0;
        this.totalPath = new ArrayList<>();
    }
    
    
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
    
    protected void updatePositionOnMap() {
        
        if(this.totalPath.isEmpty())
            return;

        int nodeValue = this.totalPath.get(1);
        
        GraphNode newNode = this.G.getGraphNode(nodeValue);
                
        // Salva os novos valores
        this.previousGraphNodeBlockValue = newNode.getOriginalBlockValue();
        this.gn = newNode;
        
        // Atualiza o Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
    }
    
    public int update() {
        if(this.framesPlayed < this.frameDelay) {
            this.framesPlayed++;
            return 0;
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
        if( this.gn.checkForNeighborWithBlockValue(10) ) {
            // Se for o vizinho quer dizer que o foi encostado no pacman.
            return -1;
        }else if(this.status) {
            if(this.gn != this.G.getGraphNode(406))
                pathFind( this.G.getGraphNode( 406 ) ); // Central node
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
        hasPacManEatenPallet();
        
        // Colocar blinky nessa nova posicao
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        this.framesPlayed = 0;
        
        return 0;
    }
}
