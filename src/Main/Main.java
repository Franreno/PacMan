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
        _blinky.pathfindPacMan(_pacman.getNode());
        
        
//        System.out.println(_graph.toString());
        char ch = 0;
        while(ch != 'q' && _pacman.getLifes() != 0) {
            
            
            
            System.out.println("Pontuação: " + systemPoints.getPoints() +  "    lifes: " + _pacman.getLifes());
            
            _map.printMap();

            ch = scanner.next().charAt(0);
            _pacman.updateVelocity(ch);
            
            int gameStatus = _pacman.updatePacMan();
           
            
            if(gameStatus == 0)
                gameStatus = _blinky.updateOnMap(_pacman.getNode());
            
            if(gameStatus != 0) {
                if(systemPoints.getPowerTimer() != 0) {
                    //Ate ghost
                    systemPoints.ateGhost();
                    System.out.println("Comi o fantasma");
                    break;
                }
                else {
                    // Fantasma pegou
                    _pacman.died();
                    System.out.println("Fantasma me pegou");
                    break;
                }
            }
        }
    }
    
    
    
}
