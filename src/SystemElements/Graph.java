package SystemElements;

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
        for(int i=0; i<e.mapHeight; i++) {
            for(int j=0; j<e.mapWidth; j++) {
                                
                if(e.mapData[i][j] != 4 && e.mapData[i][j] != 6) {
                    
                    int thisNodeId = (e.mapWidth * i + j);
                    
                    GraphNode gn = new GraphNode( thisNodeId, e.mapData[i][j], i, j , e);
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
