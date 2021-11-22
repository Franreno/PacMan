package SystemElements;
import Engine.Map;
import Engine.Graph;
import Engine.GraphNode;
import java.util.Random;

/**
 *
 * @author franreno
 */
public class Movement {
    protected Graph G;
    protected GraphNode gn;
    public Map map;
    protected int[] pos;
    protected int[] dpos;
    protected int[] cumulative;
    
    public Movement(Graph _G, Map m) {
        this.G = _G;
        this.map = m;
        this.pos = new int[2];
        this.dpos = new int[2];
        this.gn = null;
    }
    
    protected void setFirstPosition() {
        Random random = new Random();
        int upperLimit = G.getNumVertices();
        while(this.gn == null) 
            this.gn = G.getGraphNode(random.nextInt(upperLimit));
        
        this.pos = this.gn.getPos();
    }
    
    protected void setVelocity(int dVertical, int dHorizontal) {
        this.dpos[0] = dVertical;
        this.dpos[1] = dHorizontal;
    }
}