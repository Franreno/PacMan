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
    protected int elementValue;
    
    public Movement(Graph _G, Map m) {
        this.G = _G;
        this.map = m;
        this.pos = new int[2];
        this.dpos = new int[2];
        this.gn = null;
        this.elementValue = -1;
    }
    
    public GraphNode getNode() {
        return this.gn;
    }
    
    protected void setFirstPosition() {
        boolean flag = false;
        while(!flag) {
            this.gn = G.getRandomGraphNode();
            if(this.gn.getBlockValue() != 2 && this.gn.getBlockValue() != 10 )
                flag = true;
        } 
        
        int[] _randomPos = this.gn.getPos();
        
        this.pos[0] = _randomPos[0];
        this.pos[1] = _randomPos[1];
    }
    
    protected void setVelocity(int dVertical, int dHorizontal) {
        this.dpos[0] = dVertical;
        this.dpos[1] = dHorizontal;
    }
    
    protected void updateOnMap() {
        this.map.setValueAtMap(elementValue, gn.getPos());
    }
}