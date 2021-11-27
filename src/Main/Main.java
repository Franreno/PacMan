package Main;

import Engine.Map;
import Engine.Graph;
import SystemElements.PacMan;
import SystemElements.Blinky;
import Engine.Points;

import java.util.Scanner;

/**
 * Classe main do programa.
 * @author Francisco Reis Nogueira - 11954374
 */
public class Main {
    
    /**
     * Metodo main.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Leitura de teclado para teste de movimentacao sem a interface grafica.
        Scanner scanner = new Scanner(System.in);
        
        // Mapa do jogo.
        Map _map = new Map();
        
        // Criacao do grafo a partir do mapa.
        Graph _graph = new Graph(_map);
        
        // Criacao do sistema de pontos.
        Points systemPoints = new Points();
        
        // Criacao do Pacman.
        PacMan _pacman = new PacMan(_graph, _map, systemPoints);
        
        // Criacao do fantasma blinky.
        Blinky _blinky = new Blinky(_graph, _map, systemPoints);
        
        // Procura o pacman.
        _blinky.pathfindPacMan(_pacman.getNode());
        
        char ch = 0; //Guarda a variavel de leitura de teclado.
        while(ch != 'q' && _pacman.getLifes() != 0) {
            
            // Print do mapa.
            System.out.println("Utilize wasd + enter para se movimentar. q+enter para sair.");
            System.out.println("           Pontuação: " + systemPoints.getPoints() +  "    lifes: " + _pacman.getLifes());
            _map.printMap();

            //Leitura do novo tipo de movimento do Pacman
            ch = scanner.next().charAt(0);
            _pacman.updateVelocity(ch);
            

            // Variavel que guarda o status do jogo.
            // Caso o pacman tenha comido algum fantasma ou tenha morrido.
            // 0 -> Nada aconteceu
            // Diferente de 0 -> Pacman encostou em um fantasma
            int gameStatus = _pacman.updatePacMan();
           
            
            if(gameStatus == 0)
                gameStatus = _blinky.updateOnMap(_pacman.getNode());
            
            if(gameStatus != 0) {
                
                // Se o Pacman ainda tiver tempo no poder, 
                if(systemPoints.getPowerTimer() != 0) {
                    // Comeu o fantasma.
                    systemPoints.ateGhost();
                    System.out.println("Comi o fantasma");
                    break;
                }
                
                else {
                    // Morreu pro fantasma.
                    _pacman.died();
                    System.out.println("Fantasma me pegou");
                    break;
                }
            }
        }
    }
}
