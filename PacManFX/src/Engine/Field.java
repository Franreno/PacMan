package Engine;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe que contem os valores relacionados ao mapa.
 * Utilizado para o print na tela e mudanca de valores relevantes e a construcao
 * do Grafo.
 * 
 * @author Francisco Reis Nogueira - 11954374
 */
public class Field {
    
    /**
     * Largura do mapa.
     */
    private int mapWidth = 28;
    
    /**
     * Altura do mapa.
     */
    private int mapHeight = 31;
    
    
    
    private final Image VoidImage = new Image("assets/Void.png");
    private final Image WallImage = new Image("assets/Wall.png");
    
    private final Image PowerDotImage = new Image("assets/Power.png");
    private final Image PacDotImage = new Image("assets/PacDot.png");
    
    private final Image CherryImage = new Image("assets/Cherry.png");
    private final Image StrawberryImage = new Image("assets/Strawberry.png");
    private final Image OrangeImage = new Image("assets/Orange.png");
    
    private final Image PacManImage = new Image("assets/Pacman.gif", 16, 16, false, false);
    private final Image BlinkyImage = new Image("assets/Blinky.gif", 16, 16, false, false);
    private final Image PinkyImage = new Image("assets/Pinky.gif", 16, 16, false, false);
    private final Image ClydeImage = new Image("assets/Clyde.gif", 16, 16, false, false);
    private final Image InkyImage = new Image("assets/Inky.gif", 16, 16, false, false);
    private final Image BlueGhostImage = new Image("assets/BlueGhost.gif", 16, 16, false, false);
    
    
    public static final int IMG_SIZE = 16;
    public static final int SCREEN_WIDTH_OFFSET = 2*IMG_SIZE;
    public static final int SCREEN_HEIGHT_OFFSET = 4*IMG_SIZE;
    
    
    public static final int SCREEN_WIDTH = 32*IMG_SIZE;
    public static final int SCREEN_HEIGHT = 40*IMG_SIZE;
    
    
    /**
     * Matriz que representa os valores do mapa.
     */
    private int mapData[][] = {
        {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
        {4,1,1,1,1,1,1,1,1,1,1,1,1,4,4,1,1,1,1,1,1,1,1,1,1,1,1,4},
        {4,1,4,4,4,4,1,4,4,4,4,4,1,4,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
        {4,2,4,6,6,4,1,4,6,6,6,4,1,4,4,1,4,6,6,6,4,1,4,6,6,4,2,4},
        {4,1,4,4,4,4,1,4,4,4,4,4,1,4,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
        {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
        {4,1,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,1,4},
        {4,1,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,1,4},
        {4,1,1,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
        {4,4,4,4,4,4,1,4,4,4,4,4,0,4,4,0,4,4,4,4,4,1,4,4,4,4,4,4},
        {6,6,6,6,6,4,1,4,4,4,4,4,0,4,4,0,4,4,4,4,4,1,4,6,6,6,6,6},
        {6,6,6,6,6,4,1,4,4,0,0,0,0,0,0,0,0,0,0,4,4,1,4,6,6,6,6,6},
        {6,6,6,6,6,4,1,4,4,0,4,4,4,5,5,4,4,4,0,4,4,1,4,6,6,6,6,6},
        {4,4,4,4,4,4,1,4,4,0,4,5,5,5,5,5,5,4,0,4,4,1,4,4,4,4,4,4},
        {4,0,0,0,0,0,1,0,0,0,4,5,5,5,5,5,5,4,0,0,0,1,0,0,0,0,0,4},
        {4,4,4,4,4,4,1,4,4,0,4,5,5,5,5,5,5,4,0,4,4,1,4,4,4,4,4,4},
        {6,6,6,6,6,4,1,4,4,0,4,4,4,4,4,4,4,4,0,4,4,1,4,6,6,6,6,6},
        {6,6,6,6,6,4,1,4,4,0,0,0,0,0,0,0,0,0,0,4,4,1,4,6,6,6,6,6},
        {6,6,6,6,6,4,1,4,4,0,4,4,4,4,4,4,4,4,0,4,4,1,4,6,6,6,6,6},
        {4,4,4,4,4,4,1,4,4,0,4,4,4,4,4,4,4,4,0,4,4,1,4,4,4,4,4,4},
        {4,1,1,1,1,1,1,1,1,1,1,1,1,4,4,1,1,1,1,1,1,1,1,1,1,1,1,4},
        {4,1,4,4,4,4,1,4,4,4,4,4,1,4,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
        {4,1,4,4,4,4,1,4,4,4,4,4,1,4,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
        {4,2,1,1,4,4,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,4,4,1,1,2,4},
        {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
        {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
        {4,1,1,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
        {4,1,4,4,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
        {4,1,4,4,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
        {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
        {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
    };

    
    private Image getImageFromFieldValue(int value) {
        switch(value) {
            case 0:
                return this.VoidImage;
            case 1:
                return this.PacDotImage;
            case 2:
                return this.PowerDotImage;
            case 3:
                return this.CherryImage;
            case 4:
                return this.WallImage;
            case 5:
                return this.VoidImage;
            case 6:
                return this.VoidImage;
            case 10:
                return this.PacManImage;
            case 11:
                return this.BlinkyImage;
            case 13:
                return this.InkyImage;
            case 17:
                return this.PinkyImage;
            case 19:
                return this.ClydeImage;
            case 23:
                return this.BlueGhostImage;
        }
        
        return null;
    }
    
    public void drawAtStage(Group root, int fieldValue, int row, int column) {
        
        ImageView imageView = new ImageView( getImageFromFieldValue(fieldValue) );
        imageView.setY( row*IMG_SIZE + SCREEN_HEIGHT_OFFSET);
        imageView.setX( column*IMG_SIZE + SCREEN_WIDTH_OFFSET);
        
        root.getChildren().add(imageView);
    }
    
    
    
    /**
     * @return Altura do mapa.
     */
    public int getHeight() {
        return this.mapHeight;
    }
    
    /**
     * @return Largura do mapa.
     */
    public int getWidth() {
        return this.mapWidth;
    }
    
    /**
     * @return Matriz do mapa.
     */
    public int[][] getData() {
        return this.mapData;
    }
    
    /**
     * Metodo para pegar o valor da matriz em uma posicao x
     * @param pos Posicao no mapa.
     * @return Valor da matriz reference a posicao.
     */
    public int getValueFromMap(int[] pos) {
        return this.mapData[pos[0]][pos[1]];
    } 
    
    /**
     * Muda o um valor em uma posicao da matriz do mapa.
     * @param updateValue Valor que sera atualizado na matriz do mapa.
     * @param pos Posicao (Linha, Coluna) que sera atualizado na matriz do mapa.
     * @throws IndexOutOfBoundsException Posicao invalida na matriz.
     */
    public void setValueAtMap(int updateValue, int[] pos) {
        int heightPosition = pos[0];
        int widthPosition = pos[1];
        
        
        // Verificar se a posicao eh valida
        if( heightPosition > this.mapHeight || heightPosition < 0) 
            throw new IndexOutOfBoundsException();
        if( widthPosition > this.mapWidth || widthPosition < 0)
            throw new IndexOutOfBoundsException();
        
        this.mapData[heightPosition][widthPosition] = updateValue;
        
    }
    
    
    public Image getFruitType(int level) {
        switch (level) {
            case 1:
                return this.CherryImage;
            case 2:
                return this.StrawberryImage;
            case 3:
                return this.OrangeImage;
        }
        
        return null;
    }
}