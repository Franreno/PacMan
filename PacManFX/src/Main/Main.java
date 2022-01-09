package Main;

import Engine.Field;
import Engine.Graph;
import Engine.GraphNode;
import Engine.Points;

import SystemElements.Ghosts;
import SystemElements.PacMan;
import SystemElements.PersuitGhost;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Timeline;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    
    private void switchCaseEatenGhost(int ghostType, Points points, PersuitGhost _b, PersuitGhost _p, Ghosts _i, Ghosts _c) {
        
        if(ghostType >= 211) {
            ghostType -= 200;
        } else if(ghostType >= 111 && ghostType < 200) {
            ghostType -= 100;
        }
        
        switch (ghostType) {
            case 11:
                if(_b.isGhostAlive() && _b.getGhostStatus()) {
                    System.out.println("Comi o blinky");
                    _b.ghostWasEaten();
                    points.ateGhost();
                }
                break;
                
            case 13:
                if(_i.isGhostAlive() && _i.getGhostStatus()) {
                    System.out.println("Comi o Inky");
                    _i.ghostWasEaten();
                    points.ateGhost();
                }
                break;
            case 17:
                if(_p.isGhostAlive() && _i.getGhostStatus()) {
                    System.out.println("Comi o Pinky");
                    _p.ghostWasEaten();
                    points.ateGhost();
                }
                break;
            case 19:
                if(_c.isGhostAlive() && _i.getGhostStatus()) {
                    System.out.println("Comi o Clyde");
                    _c.ghostWasEaten();
                    points.ateGhost();
                }
                break;
        }
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
    
    
    private void redrawLifes(Group root, Image img, int lifes) {
        for(int i=0; i<lifes; i++) {
            ImageView imageView = new ImageView( img );
            imageView.setY( Field.SCREEN_HEIGHT - 5*Field.IMG_SIZE );
            imageView.setX( (4+i)*Field.IMG_SIZE );
        
            root.getChildren().add(imageView);
        }
    }
    
    
    private void redrawFruit(Group root, Image img) {
        ImageView fruitImageView = new ImageView( img );
        fruitImageView.setY( Field.SCREEN_HEIGHT - 5*Field.IMG_SIZE );
        fruitImageView.setX( Field.SCREEN_WIDTH - 5*Field.IMG_SIZE );
        root.getChildren().add(fruitImageView);
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

        // Criacao do fantasma blinky.
        PersuitGhost _blinky = new PersuitGhost(_graph, field, systemPoints, 11, 350);
        
        PersuitGhost _pinky = new PersuitGhost(_graph, field, systemPoints, 17, 406);
        
        Ghosts _clyde = new Ghosts(_graph, field, systemPoints, 19, 405);
        
        Ghosts _inky = new Ghosts(_graph, field, systemPoints, 13, 407);
        
        
        // Procura o pacman.
        _blinky.pathFind(_pacman.getNode());
        _pinky.pathFind(_pacman.getNode());
        
        int[][] fieldData = field.getData();
        
        
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        stage.setWidth(Field.SCREEN_WIDTH);
        stage.setHeight(Field.SCREEN_HEIGHT);
        stage.setResizable(false);
        
        
        // Criacao do Text Score
        Text text = new Text("Score: " + systemPoints.getPoints());
        text.setFill(Color.WHITE);
        text.setX(Field.SCREEN_WIDTH / 2 - 30);
        text.setY(3*Field.IMG_SIZE);
        text.setFont(Font.font("Verdana", FontWeight.NORMAL, 16));
        root.getChildren().add(text);
        
        // Criacao das imagens de vida
        Image pacmanLifeImage = new Image("assets/Pacman.png", 16, 16, false, false);
        redrawLifes(root, pacmanLifeImage, _pacman.getLifes());
        
        // Criacao da imagem da fruta
        redrawFruit(root, field.getFruitType(systemPoints.getLevel()));
        
        
        stage.setTitle("PacMan");
        stage.setScene(scene);
        stage.show();
        
        
        redrawMap(root, field, fieldData);
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            _pacman.updateVelocity( event.getText().charAt(0) );

        });        
                
        
        Timer timer = new Timer();
        
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater( new Runnable() { 
                    @Override
                    public void run() {
                        
                        // Se o pacman pegou alguma coisa
                        if( !_pacman.update() ) {
                            if( systemPoints.getPowerState() ) {
                                // Comeu algum fantasma
                                switchCaseEatenGhost(_pacman.getGhostTypeEaten(), systemPoints, _blinky, _pinky, _inky, _clyde);
//                                System.out.println("Comi um fantasma");
                            } else {
                                _pacman.died();
                                System.out.println("Morri");
                            }
                        }
                        
                        
                        if( !_blinky.update(_pacman.getNode()) || !_clyde.update() || !_inky.update() || !_pinky.update(_pacman.getNode()) ) {
                            _pacman.died();
                            System.out.println("Morri");
                        }
                        
                        _blinky.checkPoints();
                            
                        if(systemPoints.checkAmountEaten()) {
                            int fruitNodeID = systemPoints.getFruitNodeID();
                            GraphNode fruitNode = _graph.getGraphNode(fruitNodeID);
                            if( field.getValueFromMap(fruitNode.getPos()) == 3 ) {
                                fruitNodeID -= 1;
                                fruitNode = _graph.getGraphNode(fruitNodeID);
                            }
                            
                            fruitNode.setBlockValue(3);
                            fruitNode.setOriginalBlockValue(3);
                            _graph.updateHashMap(fruitNodeID, fruitNode);
                            
                            field.setValueAtMap(3, fruitNode.getPos());
                        }
                            
                        
                        
                        redrawMap(root, field, fieldData);
                        text.setText("Score: " + systemPoints.getPoints());
                        redrawLifes(root, pacmanLifeImage, _pacman.getLifes());
                        redrawFruit(root, field.getFruitType(systemPoints.getLevel()));
                    }
                });
            }
            
        };
        
        long frameTime = (long) (1000.0 / 6.0);
        timer.schedule(timertask, 0, frameTime);
    }
}
