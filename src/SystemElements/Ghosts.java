package SystemElements;

import Engine.Graph;
import Engine.Map;
import Engine.Points;

/**
 *
 * @author franreno
 */
public class Ghosts extends Movement{
    private boolean status;
    private Points points;

    public Ghosts(Graph G, Map m, Points p) {
        super(G,m);
        this.status = true;
        this.points = p;
    }
    
    
    public void hasPacManEatenPallet() {
        if(this.points.getPowerTimer() != 0) 
            this.elementValue = 19;
    }
}
