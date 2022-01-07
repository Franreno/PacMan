/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SystemElements;

import Engine.Field;
import Engine.Graph;
import Engine.GraphNode;
import Engine.Points;

/**
 *
 * @author franreno
 */
public class Pinky extends Ghosts{
    
    public Pinky(Graph G, Field m, Points p) {
        super(G, m, p);

        // Valor do Clyde.
        this.elementValue = 17;

        // Define o nodulo de posicao do fantasma no mapa.
        setFirstPosition();
        
        this.basicSetup();
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
            if(this.gn != this.G.getGraphNode(407))
                pathFind( this.G.getGraphNode( 407 ) ); // Central node
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
