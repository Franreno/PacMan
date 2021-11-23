package SystemElements;

import Engine.Graph;
import Engine.GraphNode;
import Engine.Map;
import Engine.Points;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author franreno
 */
public class Blinky extends Ghosts{
    
    private ArrayList<Integer> totalPath;
    
    public Blinky(Graph G, Map m, Points p) {
        super(G,m,p);
        this.elementValue = 11;
        this.previousElementValue = this.elementValue;
        this.totalPath = new ArrayList<>();
        setFirstPosition();
        updateOnMap();
    }
    
    
    private void paintPath() {
        if(this.totalPath == null || this.totalPath.isEmpty())
            return;
        
        for(Integer e : this.totalPath) {
            GraphNode gn = this.G.getGraphNode(e);
            int blockValue = gn.getBlockValue();
            if(blockValue < 10) {
                this.map.setValueAtMap(blockValue + 100, gn);
            }
        }
    }
    
    private void clearPath() {
        if(this.totalPath == null || this.totalPath.isEmpty())
            return;
        
        for(Integer e : this.totalPath) {
            GraphNode gn = this.G.getGraphNode(e);
            if(this.map.getValueFromMap(gn.getPos()) >= 100) {
                this.map.setValueAtMap(gn.getBlockValue(), gn);
            }
        }
    }
    
        
    public void pathfindPacMan(GraphNode goal) {
        HashMap<Integer, Integer> path = this.G.A_Star(this.gn, goal);

        if( (!this.totalPath.isEmpty()) ) {
            clearPath();
            this.totalPath.clear();
        }
        
        System.out.println(path);
        while( (path.containsKey( goal.getId() )) ) {
            goal = this.G.getGraphNode( path.get( goal.getId() ) );
            this.totalPath.add(0, goal.getId());
        }
        
        //Remove blinky from the list
        this.totalPath.remove(0);
        paintPath();
        System.out.println(this.totalPath);
        
    }
    
    
}
