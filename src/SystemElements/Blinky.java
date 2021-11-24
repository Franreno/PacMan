package SystemElements;

import Engine.Graph;
import Engine.GraphNode;
import Engine.Map;
import Engine.Points;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author franreno
 */
public class Blinky extends Ghosts{
    
    private int mainPathHeader;
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
        
        // nao pintar o pacman e o fantasma.
        this.totalPath.remove(0);
        this.totalPath.remove(this.totalPath.size() -1);
        
        System.out.println(this.totalPath);
        
        for(Integer e : this.totalPath) {
            GraphNode gn = this.G.getGraphNode(e);
            int blockValue = gn.getBlockValue();
            
            if(blockValue < 10) {
                gn.setBlockValue(blockValue + 100);
                this.G.updateHashMap(e, gn);
                this.map.setValueAtMap(blockValue + 100, gn.getPos());
            }
            
        }
    }
    
    private void clearPath() {
        if(this.totalPath == null || this.totalPath.isEmpty())
            return;
        
        for(Integer e : this.totalPath) {
            GraphNode gn = this.G.getGraphNode(e);
            int blockValue = gn.getBlockValue();
            
            if(blockValue >= 100)
                blockValue -= 100;
            gn.setBlockValue(blockValue);
            this.G.updateHashMap(e, gn);
            this.map.setValueAtMap(blockValue, gn.getPos());
        }
    }
    
        
    public void pathfindPacMan(GraphNode goal) {
        GraphNode path = this.G.A_Star(this.gn, goal);

        if( (!this.totalPath.isEmpty()) ) {
            clearPath();
            this.totalPath.clear();
        }


          while(path.parent != null) {
              this.totalPath.add(path.getId());
              path = path.parent;
          }

          this.totalPath.add(path.getId());
          Collections.reverse(totalPath);
          
//          System.out.println(this.totalPath);

          paintPath();        
    }
    
    
}
