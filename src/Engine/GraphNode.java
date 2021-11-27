package Engine;
import java.util.ArrayList;

/**
 *
 * @author franreno
 */
public class GraphNode{
    private int id;
    private int blockValue;
    private int[] pos;
    private int neighborsAmount;
    private ArrayList<GraphNode> list;
    
    public GraphNode parent;
    public int f = 10000;
    public int g = 10000;
    
    public GraphNode(int _id, int _blockValue, int x, int y, Map e) {
        id = _id;
        blockValue = _blockValue;
        pos = new int[2];
        pos[0] = x;
        pos[1] = y;
        parent = null;
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
    
    public void setBlockValue(int value) {
        this.blockValue = value;
    }
    
    public boolean checkForNeighbor(int id) {
        return this.list.stream().anyMatch(gn -> (gn.getId() == id));
    }
    
    
    private int calculateNodeID(int width, int i, int j ) {
        return width*i + j;
    }
    private boolean verticalLook(int[][] data, int i, int j, int whereToLook, int constraint) {
        //Como eh olhar vertical deve ser usado o i
        if( whereToLook < constraint && whereToLook >= 0 ) {
            // Ver a posicao e checar se eh uma parede ou void
            if( data[whereToLook][j] != 4 && data[whereToLook][j] != 6) {
                return true;
            }
        }
        return false;
    }
    
    private boolean horizontalLook(int[][] data, int i, int j, int whereToLook, int constraint) {
        //Como eh olhar vertical deve ser usado o i
        if( whereToLook < constraint && whereToLook > 0) {
            // Ver a posicao e checar se eh uma parede ou void
            if( data[i][whereToLook] != 4 && data[i][whereToLook] != 6) {
                return true;
            }
        }
        return false;
    }
    
    public void calculateNeighbors(Map e, int i, int j) {
        this.neighborsAmount = 0;
        int [][] data = e.getData();
        
        // Olhar para baixo
        if( verticalLook(data, i, j, (i+1), e.getHeight()-1 ) )
            this.neighborsAmount++;
        
        // Olhar para cima
        if( verticalLook(data, i, j, (i-1), i) )
            this.neighborsAmount++;
        
        // Olhar para a direita
        if( horizontalLook(data, i, j, (j+1), e.getWidth()-1 ) )
            this.neighborsAmount++;
        
        // Olhar para a esquerda
        if( horizontalLook(data, i, j, (j-1), j ) )
            this.neighborsAmount++;
    }
    
    
    public void lookForSurroudings(Map e, int i, int j) {
        
        int[][] data = e.getData();
        int width = e.getWidth();
        int height = e.getHeight();
        
        // Olhar para baixo
        if( verticalLook(data, i, j, (i+1), height-1 ) )
            this.list.add( new GraphNode(calculateNodeID(width, (i+1), j), data[i+1][j], (i+1), j, e) );
        
        // Olhar para cima
        if( verticalLook(data, i, j, (i-1), i) )
            this.list.add( new GraphNode(calculateNodeID(width, (i-1), j), data[i-1][j], (i-1), j, e) );
        
        // Olhar para a direita
        if( horizontalLook(data, i, j, (j+1), width-1 ) )
            this.list.add( new GraphNode(calculateNodeID(width, i, (j+1)), data[i][j+1], i, (j+1), e) );
            
        
        // Olhar para a esquerda
        if( horizontalLook(data, i, j, (j-1), j ) )
            this.list.add( new GraphNode(calculateNodeID(width, i, (j-1)), data[i][j-1], i, (j-1), e) );
        
    }
    
    private String neighborsToString() {
        String ret = "";
        for(int i=0; i<list.size(); i++) {
            GraphNode gn = list.get(i);
            ret += "{" + gn.getId() + ", " + gn.getBlockValue() + ", (" + gn.getPos()[0] + "," + gn.getPos()[1] + ") " + gn.getNeighbors() + "};";
        }
        
        return ret;
    }
    
    private String parentToString() {
        return (this.parent != null) ? String.valueOf(this.parent.getId()) : "null";
    }
    
    @Override
    public String toString() {
        return "Id: " + id + " BlockValue: " + blockValue + " Pos: (" + pos[0] + "," + pos[1] + ") f,g,parent:" + this.f + " " + this.g + " " + this.parentToString() + " " + " Neighbors: " + neighborsAmount + " Neighbors: " + neighborsToString();
    }

}
