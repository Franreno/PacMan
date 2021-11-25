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
    private ArrayList<GraphNode> openList;
    private ArrayList<GraphNode> closedList;
    
    
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
    
    
    public void updateHashMap(int key, GraphNode gn) {
        this.hashList.replace(key, gn);
    }
        
    private int h(int[] pos, int[] goal) {
        //Manhattan Distance
        int dx = Math.abs( pos[0] - goal[0] );
        int dy = Math.abs( pos[1] - goal[1] );
        return (dx + dy);
    }
        
    public GraphNode A_Star(GraphNode start, GraphNode goal) {
        this.openList = new ArrayList<>();
        this.closedList = new ArrayList<>();

        GraphNode current = start;
        current.g = 0;
        current.f = current.g + h(current.getPos(), goal.getPos());
        while(current.getId() != goal.getId()) {
            
            int gScore = current.g + 1;
            
            for(GraphNode neighbor : current.getList()) {
                GraphNode adj = this.hashList.get(neighbor.getId());
                
                if( ( !closedList.contains(adj) ) && (!openList.contains( adj )) ) {
                    openList.add(this.hashList.get(adj.getId()));
                    adj.g = gScore;
                    int fScore = adj.g + h(adj.getPos(), goal.getPos());
                    
                    if(fScore < adj.f) {
                        adj.f = fScore;
                        adj.parent = current;
                    }
                }
            }
            
            closedList.add(this.hashList.get(current.getId()));
            
            //Find lowest f value from openList
            int lowest = 10000;
            int indexStorage = 0;
            for(int i=0; i<openList.size(); i++) {
                GraphNode gn = openList.get(i);
                if(gn.f < lowest) {
                    lowest = gn.f;
                    indexStorage = i;
                }
            }
            
            //Lowest value in list is found at indexStorage
            current = openList.get(indexStorage); 
            openList.remove(indexStorage);
        }
        
        
        return current;
    }
    
    
    public void clearContentOfLists() {
        for(GraphNode helper : this.openList) {
            helper.f = 10000;
            helper.g = 10000;
            helper.parent = null;
        }
        for(GraphNode helper : this.closedList) {
//            System.out.println("Id: " + helper.getId() + " f: " + helper.f + " g: " + helper.g + " parent: " + helper.parent);
            helper.f = 10000;
            helper.g = 10000;
            helper.parent = null;
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
