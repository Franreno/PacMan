package Main;

import Engine.Map;
import Engine.Graph;
import SystemElements.PacMan;

import java.util.concurrent.TimeUnit;
/**
 *
 * @author franreno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Map l1 = new Map("level_1");
        Graph G = new Graph(l1);
        PacMan pac = new PacMan(l1);
        l1.printMap();
        
        while(true) {
//            TimeUnit.SECONDS.sleep(2);
            TimeUnit.MILLISECONDS.sleep(500);
            pac.updatePacMan(l1);
            l1.printMap();
        }
    }
    
}
