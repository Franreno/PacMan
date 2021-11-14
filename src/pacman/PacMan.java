package pacman;

import SystemElements.Map;
import SystemElements.level1;
import SystemElements.Graph;

/**
 *
 * @author franreno
 */
public class PacMan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        level1 l1 = new level1();
        l1.printMap();
        Graph G = new Graph(l1);
        System.out.println(G.toString());
    }
    
}
