package Main;

import Engine.Field;
import Engine.Graph;
import Engine.Points;
import SystemElements.PacMan;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author franreno
 */
public class Main extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    private void redrawMap(Group root, Field field, int[][] fieldData ) {
        // y == rows
        // x == columns
        for(int y=0; y<field.getHeight(); y++) {
            for(int x=0; x<field.getWidth(); x++) {
                field.drawAtStage(root, fieldData[y][x], y, x);
            }
        }
    }
    
    
    @Override
    public void start(Stage stage) {

        
        Scanner scanner = new Scanner(System.in);
        
        // Mapa do jogo.
        Field field = new Field();
        
        // Criacao do grafo a partir do mapa.
        Graph _graph = new Graph(field);
        
        // Criacao do sistema de pontos.
        Points systemPoints = new Points();
        
        
        // Criacao do Pacman.
        PacMan _pacman = new PacMan(_graph, field, systemPoints);
        
        
        int[][] fieldData = field.getData();
        
        
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        stage.setWidth(field.SCREEN_WIDTH);
        stage.setHeight(field.SCREEN_HEIGHT);
        stage.setResizable(false);
        
        
        stage.setTitle("PacMan");
        stage.setScene(scene);
        stage.show();
        
        
        redrawMap(root, field, fieldData);
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            System.out.println(event.getText().charAt(0) );
            _pacman.updateVelocity( event.getText().charAt(0) );
            _pacman.updatePacMan();            
            redrawMap(root, field, fieldData);
        });
        
        new Timer().schedule(
            new TimerTask() {
                @Override
                public void run() {
                    System.out.println("ping");
                    _pacman.updatePacMan();
                    redrawMap(root, field, fieldData);
                }
            }, 0, 17);
        
//            try {
//                Thread.sleep(1/60);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
        
        
    }
}
