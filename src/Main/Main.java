package Main;

import Engine.Map;
import Engine.Graph;
import SystemElements.PacMan;
import SystemElements.Blinky;
import Engine.Points;

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
        Blinky _blinky = new Blinky(_graph, _map, systemPoints);

//        System.out.println(_graph.toString());
        char ch = 0;
        while(ch != 'q') {
            
            _pacman.updatePacMan();
            
            _blinky.pathfindPacMan(_pacman.getPacManNode());
            

            System.out.println("Pontuação: " + systemPoints.getPoints() +  "    powerTimer: " + systemPoints.getPowerTimer() + "    PacManID: " + _pacman.getPacManNode().getId());
            
           
            _map.printMap();

            ch = scanner.next().charAt(0);
            _pacman.updateVelocity(ch);
            _blinky.hasPacManEatenPallet();
            
        }
    }
    
}
