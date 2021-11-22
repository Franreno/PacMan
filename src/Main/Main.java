package Main;

import Engine.Map;
import Engine.Graph;
import SystemElements.PacMan;
import SystemElements.Points;

import java.util.Scanner;

/**
 *
 * @author franreno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Map _map = new Map("level_1");
        Graph _graph = new Graph(_map);
        Points systemPoints = new Points();
        PacMan _pacman = new PacMan(_graph, _map, systemPoints);
        _map.printMap();
//        System.out.println(_graph.toString());
        
        
        boolean flag = false;
        while(!flag) {
            _pacman.updatePacMan();
            System.out.println("Pontuação: " + systemPoints.getPoints());
            _map.printMap();
            _pacman.updateVelocity(scanner.next().charAt(0));
            
        }
    }
    
}
