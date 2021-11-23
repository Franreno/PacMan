package Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.PriorityQueue;

/**
 *
 * @author franreno
 */
public class Graph {
    private int numVertices;
    public HashMap<Integer, GraphNode> hashList;
    
    
    
    public Graph(Map e) {
        hashList = new HashMap<>();
        numVertices = 0;
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
    
    public int getNumVertices() {
        return this.numVertices;
    }
    
    public GraphNode getRandomGraphNode() {
        ArrayList<Integer> keys = new ArrayList(this.hashList.keySet());
        Random rand = new Random();
        return this.hashList.get( keys.get(rand.nextInt(keys.size()) ));
    }

    public GraphNode getGraphNode(int index) {
        GraphNode gn = this.hashList.get(index);
        if (gn == null)
            return null;
        else if(gn.getBlockValue() == 4 || gn.getBlockValue() == 6) 
            return null;
        else
            return gn;
    }
    
        
    private int h(int[] pos, int[] goal) {
        //Manhattan Distance
        int dx = Math.abs( pos[0] - goal[0] );
        int dy = Math.abs( pos[1] - goal[1] );
        return (dx + dy);
    }
    
    public HashMap<Integer, Integer> A_Star(GraphNode start, GraphNode goal) {
        // Conjunto de nodes descobertos
        PriorityQueue<Integer> openSet = new PriorityQueue<>();
        openSet.add(start.getId());
        
        // Para um node 'n' cameFrom[n] eh o node que o precede com o menor caminho desde o comeco ate n.
        HashMap<Integer, Integer> cameFrom = new HashMap<>();
        
        // Para um node 'n', gScore[n] eh o custo do menor caminho do comeco ate n.
        HashMap<Integer, Integer> gScore = new HashMap<>();
        gScore.put(start.getId(), 0);
        
        // Para um node 'n', fScore[n] = gScore[n] + h(n).
        // fScore[n] representa a atual melhor tentativa do menor caminho do comeco ate n.
        HashMap<Integer, Integer> fScore = new HashMap<>();
        fScore.put(start.getId(), h(start.getPos(), goal.getPos()));
        
        
        while (!openSet.isEmpty()) {
            GraphNode current = hashList.get( openSet.poll() );
            
            if (current.getId() == goal.getId()) {
                System.out.println("Chegou no fim");
                return cameFrom;
            }
            
            ArrayList<GraphNode> neighbors = current.getList();
            for (GraphNode neighbor : neighbors) {
                
                // tentative_gScore eh a distancia do comeco ate o neighbor pela distancia gScore
                int tentative_gScore = gScore.getOrDefault(current.getId(), 10000) + 1;
                
                if (tentative_gScore < gScore.getOrDefault(neighbor.getId(), 10000)) {
                    // Esse caminho ate o neighbor eh o melhor que todos os outros. Guardar ele
                    cameFrom.put(neighbor.getId(), current.getId());
                    gScore.put(neighbor.getId(), tentative_gScore);
                    fScore.put(neighbor.getId(), ( tentative_gScore + h( neighbor.getPos(), goal.getPos() ) ));
                    if (!openSet.contains(neighbor.getId())) {
                        openSet.add(neighbor.getId());
                    }
                }
            }
        }
        
        System.out.println("Nao conseguiu achar o goal");
        return null;
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
