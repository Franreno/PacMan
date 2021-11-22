package SystemElements;

import Engine.Map;
import Engine.Graph;
import Engine.GraphNode;

/**
 *
 * @author franreno
 */
public class PacMan extends Movement{

    private boolean powerStatus;
    private boolean alive;
    
    public PacMan(Graph G, Map m) {
        super(G,m);
        this.powerStatus = false;
        this.alive = true;
        setFirstPosition();
        setVelocity(0,1);
    }
    
    @Override
    protected void setFirstPosition() {
        this.pos[0] = 23;
        this.pos[1] = 14;
        this.gn = this.G.getGraphNode( this.map.getWidth() * this.pos[0] + this.pos[1] );
        updadtePacManOnMap();
    }
    
    @Override
    protected void setVelocity(int dVertical, int dHorizontal) {
        this.dpos[0] = dVertical;
        this.dpos[1] = dHorizontal;
    }
    
    private boolean verticalPositionValidation(int[][] data, int height, int x, int y) {
        if ( x + this.dpos[0] > height-2 ) {
            return false;
        }
        else if( data[x + this.dpos[0]][y] == 4 ) {
            return false;
        }
        else
            return true;
    }
    
    private boolean horizontalPositionValidation(int[][] data, int width, int x, int y) {
        if ( y + this.dpos[1] > width-2 || y + this.dpos[1] < 0)
            return false;
        else if( data[x][y + this.dpos[1]] == 4 )
            return false;
        else
            return true;
    }
    
    public void updatePacMan() {
        int[][] data = this.map.getData();
        int height = this.map.getHeight();
        int width = this.map.getWidth();
        
        
        if(this.dpos[0] != 0) {
            if(verticalPositionValidation(data, height, this.pos[0], this.pos[1]))
                updadtePacManOnMap();
        }
        
        if(this.dpos[1] != 0) {
            if(horizontalPositionValidation(data, width, this.pos[0], this.pos[1]))
                updadtePacManOnMap();
        }
        
    }
    
    public void updateVelocity(char ch) {
        switch(ch) {
            case 'w' -> {
                setVelocity(-1, 0);
            }
            case 's' -> {
                setVelocity(1, 0);
            }
            case 'd' -> {
                setVelocity(0, 1);
            }
            case 'a' -> {
                setVelocity(0, -1);
            }
        }
    }
     
    private void updadtePacManOnMap() {
        
        // Clear last position
        this.map.setValueAtMap(0, this.pos[0], this.pos[1]);
        this.pos[0] += this.dpos[0];
        this.pos[1] += this.dpos[1];
        
        // Set new position
        this.map.setValueAtMap(10, this.pos[0], this.pos[1]);
    }
}
