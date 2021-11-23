package SystemElements;

import Engine.Graph;
import Engine.Map;
import Engine.Points;

/**
 *
 * @author franreno
 */
public class Ghosts extends Movement{
    protected boolean status;
    protected Points points;
    protected int previousElementValue;
    
    private static final int EATABLE_GHOST = 23;
    private static final int RESETING_GHOST = 29;

    public Ghosts(Graph G, Map m, Points p) {
        super(G,m);
        this.status = true;
        this.points = p;
    }
    
    
    public void hasPacManEatenPallet() {
        int powerTimer = this.points.getPowerTimer();
        if(powerTimer != 0 && powerTimer > 10)
            //Power is active
            this.elementValue = EATABLE_GHOST;
        else if(powerTimer != 0 && powerTimer <= 10)
            // Power is reseting
            this.elementValue = (this.elementValue == EATABLE_GHOST) ? RESETING_GHOST : EATABLE_GHOST;
        else 
            //Power is off
            this.elementValue = this.previousElementValue;
        updateOnMap();
    }
}
