/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SystemElements;
import java.util.*;

/**
 *
 * @author franreno
 */
public class GraphNode {
    private int id;
    private int blockValue;
    private int[] pos;
    private int neighborsAmount;
    private ArrayList<GraphNode> list;
    
    public GraphNode(int _id, int _blockValue, int x, int y, Map e) {
        id = _id;
        blockValue = _blockValue;
        pos = new int[2];
        pos[0] = x;
        pos[1] = y;        
        list = new ArrayList();
        this.calculateNeighbors(e, x, y);
    }
    
    
    public int getId() {
        return this.id;
    }
    
    public int getBlockValue() {
        return this.blockValue;
    }
    
    public int[] getPos() {
        return this.pos;
    }
    
    public int getNeighbors() {
        return this.neighborsAmount;
    }
    
    public ArrayList<GraphNode> getList() {
        return this.list;
    }
    
    
    private int calculateNodeID(int width, int i, int j ) {
        return width*i + j;
    }
    
    private boolean verticalLook(Map e, int i, int j, int whereToLook, int constraint) {
        //Como eh olhar vertical deve ser usado o i
        if( i <= constraint && i >= 0 ) {
            // Ver a posicao e checar se eh uma parede ou void
            if( e.mapData[whereToLook][j] != 4 && e.mapData[whereToLook][j] != 6) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean horizontalLook(Map e, int i, int j, int whereToLook, int constraint) {
        //Como eh olhar vertical deve ser usado o i
        if( j <= constraint && j > 0) {
            // Ver a posicao e checar se eh uma parede ou void
            if( e.mapData[i][whereToLook] != 4 && e.mapData[i][whereToLook] != 6) {
                return true;
            }
        }
        return false;
    }
    
    public void calculateNeighbors(Map e, int i, int j) {
        this.neighborsAmount = 0;
        
        // Olhar para baixo
        if( verticalLook(e, i, j, (i+1), e.mapHeight-2 ) )
            this.neighborsAmount++;
        
        // Olhar para cima
        if( verticalLook(e, i, j, (i-1), i) )
            this.neighborsAmount++;
        
        // Olhar para a direita
        if( horizontalLook(e, i, j, (j+1), e.mapWidth-2 ) )
            this.neighborsAmount++;
        
        // Olhar para a esquerda
        if( horizontalLook(e, i, j, (j-1), j ) )
            this.neighborsAmount++;
    }
    
    
    public void lookForSurroudings(Map e, int i, int j) {
        
        // Olhar para baixo
        if( verticalLook(e, i, j, (i+1), e.mapHeight-2 ) )
            this.list.add( new GraphNode(calculateNodeID(e.mapWidth, (i+1), j), e.mapData[i+1][j], (i+1), j, e) );
        
        // Olhar para cima
        if( verticalLook(e, i, j, (i-1), i) )
            this.list.add( new GraphNode(calculateNodeID(e.mapWidth, (i-1), j), e.mapData[i-1][j], (i-1), j, e) );
        
        // Olhar para a direita
        if( horizontalLook(e, i, j, (j+1), e.mapWidth-2 ) )
            this.list.add( new GraphNode(calculateNodeID(e.mapWidth, i, (j+1)), e.mapData[i][j+1], i, (j+1), e) );
            
        
        // Olhar para a esquerda
        if( horizontalLook(e, i, j, (j-1), j ) )
            this.list.add( new GraphNode(calculateNodeID(e.mapWidth, i, (j-1)), e.mapData[i][j-1], i, (j-1), e) );
        
    }
    
    private String neighborsToString() {
        String ret = "";
        for(int i=0; i<list.size(); i++) {
            GraphNode gn = list.get(i);
            ret += "{" + gn.getId() + ", " + gn.getBlockValue() + ", (" + gn.getPos()[0] + "," + gn.getPos()[1] + ") " + gn.getNeighbors() + "};";
        }
        
        return ret;
    }
    
    
    @Override
    public String toString() {
        return "Id: " + id + " BlockValue: " + blockValue + " Pos: (" + pos[0] + "," + pos[1] + ") Neighbors: " + neighborsAmount + " Neighbors: " + neighborsToString();
    }
    
}
