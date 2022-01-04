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
    
    
    
    private final Image Void = new Image("assets/Void.png");
    private final Image Wall = new Image("assets/Wall.png");
    private final Image PowerDot = new Image("assets/Power.png");
    private final Image PacDot = new Image("assets/PacDot.png");
    private final Image Cherry = new Image("assets/Cherry.png");
    private final Image PacMan = new Image("assets/Pacman.png");
    
    private static final int IMG_SIZE = 16;
    private static final int SCREEN_WIDTH_OFFSET = 2*IMG_SIZE;
    private static final int SCREEN_HEIGHT_OFFSET = 4*IMG_SIZE;
    
    
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
                return this.Void;
            case 1:
                return this.PacDot;
            case 2:
                return this.PowerDot;
            case 3:
                return this.Cherry;
            case 4:
                return this.Wall;
            case 5:
                return this.Void;
            case 6:
                return this.Void;
            case 10:
                return this.PacMan;
        }
        
        return null;
    }
    
    public void drawAtStage(Group root, int fieldValue, int row, int column) {
        
        ImageView imageView = new ImageView( getImageFromFieldValue(fieldValue) );
        imageView.setY(row*IMG_SIZE + SCREEN_HEIGHT_OFFSET);
        imageView.setX(column*IMG_SIZE + SCREEN_WIDTH_OFFSET);
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
}