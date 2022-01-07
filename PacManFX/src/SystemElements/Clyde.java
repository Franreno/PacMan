package SystemElements;

import Engine.Field;
import Engine.Graph;
import Engine.GraphNode;
import Engine.Points;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author franreno
 */
public class Clyde extends Ghosts{
    
    public Clyde(Graph G, Field m, Points p) {
        super(G, m, p);

        // Valor do Clyde.
        this.elementValue = 19;
        
        // Define o nodulo de posicao do fantasma no mapa.
        setFirstPosition();        

        this.basicSetup();
    }    
}
