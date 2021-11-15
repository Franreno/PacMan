package Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author franreno
 */
public class Graph {
    private int numVertices;
    private HashMap<Integer, GraphNode> hashList;
    
    
    
    public Graph(Map e) {
        hashList = new HashMap<>();
        numVertices = 0;
        int auxNumVertices = 0;
        int[][] data = e.getData();
        for(int i=0; i<e.getHeight(); i++) {
            for(int j=0; j<e.getWidth(); j++) {
                                
                if(data[i][j] != 4 && data[i][j] != 6) {
                    
                    int thisNodeId = (e.getWidth() * i + j);
                    
                    GraphNode gn = new GraphNode( thisNodeId, data[i][j], i, j , e);
                    gn.calculateNeighbors(e, i, j);
                    gn.lookForSurroudings(e, i, j);
                    hashList.put(thisNodeId, gn);
                    numVertices++;
                }
                
            }
        }
    }
       
    
    @Override
    public String toString() {
        String ret = "";
        ArrayList<Integer> sortedKeys = new ArrayList(hashList.keySet());
        Collections.sort(sortedKeys);
        
        for( Integer key : sortedKeys ) {
            GraphNode gn = hashList.get(key);
            ret += "{ " + key + ": " + gn.toString() + " }\n";
        }
        return ret;
    }
}
