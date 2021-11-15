package SystemElements;

import Engine.Map;

/**
 *
 * @author franreno
 */
public class PacMan {
    private int[] pos; // Pacman Position
    private int[] dpos; // Pacman Velocity
    
    public PacMan(Map m) {
        this.pos = new int[2];
        this.dpos = new int[2];
        
        this.pos[0] = 23;
        this.pos[1] = 14;
        
        this.dpos[0] = 0;
        this.dpos[1] = 1;
        
        this.updadtePacManOnMap(m);
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
    
    public void updatePacMan(Map e) {
        int[][] data = e.getData();
        int height = e.getHeight();
        int width = e.getWidth();
        
        
        if(this.dpos[0] != 0) {
            if(verticalPositionValidation(data, height, this.pos[0], this.pos[1]))
                updadtePacManOnMap(e);
        }
        
        if(this.dpos[1] != 0) {
            if(horizontalPositionValidation(data, width, this.pos[0], this.pos[1]))
                updadtePacManOnMap(e);
        }
        
    }
    
    private void updadtePacManOnMap(Map m) {
        System.out.println("pos: (" + this.pos[0] + "," + this.pos[1] + ")");
        
        // Clear last position
        m.setValueAtMap(0, this.pos[0], this.pos[1]);
        this.pos[0] += this.dpos[0];
        this.pos[1] += this.dpos[1];
        
        // Set new position
        m.setValueAtMap(10, this.pos[0], this.pos[1]);
    }
}
