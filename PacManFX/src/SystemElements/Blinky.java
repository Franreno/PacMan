package SystemElements;

import Engine.Graph;
import Engine.GraphNode;
import Engine.Field;
import Engine.Points;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe que contem o funcionamento para o fantasma blinky 
 * (Perseguidor ativo do Pacman).
 * @author Francisco Reis Nogueira - 11954374
 */
public class Blinky extends Ghosts{
    
    /**
     * ArrayList que contem os valores do caminho que blinky utilizara para 
     * perseguir o Pacman.
     */
    private ArrayList<Integer> totalPath;
    
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
        
        // Valor utilizado para o valor do ultimo elemento.
        this.previousElementValue = this.elementValue;
        
        
        this.totalPath = new ArrayList<>();
        
        // Define o nodulo de posicao do fantasma no mapa.
        setFirstPosition();
        
        // Guarda o valor anterior do nodulo que o fantasma sera colocado.
        this.previousGraphNodeBlockValue = this.gn.getBlockValue();
        
        // Atualiza o valor do nodulo no Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        
        // Atualiza o valor do fantasma no mapa.
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
    }
    
    
    /**
     *  Realiza a procura pelo Pacman utilizando o algoritmo A* 
     * @param goal Nodulo em que o Pacman se encontra.
     */
    public void pathfindPacMan(GraphNode goal) {
        
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

        this.totalPath.add(goal.getId());
        Collections.reverse(totalPath); // goal -> start. vai para start -> goal
    }
    
    /**
     * Metodo que pega a proxima posicao da lista de caminho e realiza todas as
     * manipulacoes necessarias para manter a integridade do mapa.
     */
    private void updatePositionOnMap() {
        //Ir para a primeira posicao do totalPath

        int nodeValue = this.totalPath.get(1);
        
        GraphNode newNode = this.G.getGraphNode(nodeValue);
        
        int newValue = newNode.getBlockValue();
        if(newValue >= 100)
            newValue -=100;
        
        // Salva os novos valores
        this.previousGraphNodeBlockValue = newValue;
        this.gn = newNode;
        
        // Atualiza o Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);    
    }
    
    /**
     * Metodo que atualiza a posicao de blinky no mapa.
     * @param goal Posicao do pacman que eh utilizada para verificar se o Pacman encostou em blinky.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    public int updateOnMap(GraphNode goal) {
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
        }else {
            pathfindPacMan(goal);
        }
        
        // Ir para o proximo node.
        updatePositionOnMap();
        
        // Verifica o status do poder do pacman
        hasPacManEatenPallet();
        
        // Colocar blinky nessa nova posicao
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        return 0;
    }
}
