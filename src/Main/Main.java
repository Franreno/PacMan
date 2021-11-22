package Main;

import Engine.Map;
import Engine.Graph;
import SystemElements.PacMan;
import java.util.Scanner;


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
        
        Scanner scanner = new Scanner(System.in);
        Map l1 = new Map("level_1");
        Graph G = new Graph(l1);
        PacMan pm = new PacMan(G, l1);
        l1.printMap();
        
        
        
        boolean flag = false;
        while(!flag) {
            pm.updatePacMan();
            l1.printMap();
            pm.updateVelocity(scanner.next().charAt(0));
        }
    }
    
}
