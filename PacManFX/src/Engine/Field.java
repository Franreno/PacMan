package Engine;

import java.util.Arrays;
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
    
    // A imagem do pacman eh processada diretamente na classe do pacman
    // So eh retornado o imageview para a construcao da tela.
    private ImageView PacImView;
    
    
    private final Image BlinkyImage = new Image("assets/Blinky.gif", 16, 16, false, false);
    private final Image PinkyImage = new Image("assets/Pinky.gif", 16, 16, false, false);
    private final Image ClydeImage = new Image("assets/Clyde.gif", 16, 16, false, false);
    private final Image InkyImage = new Image("assets/Inky.gif", 16, 16, false, false);
    private final Image BlueGhostImage = new Image("assets/BlueGhost.gif", 16, 16, false, false);
    private final Image ResetingGhostImage = new Image("assets/ResetingGhost.gif", 16, 16, false, false);
    private final Image DeadGhostImage = new Image("assets/Eyes.png", 16, 16, false, false);
    
    
    // Equivalente a 16 pixels
    public static final int IMG_SIZE = 16;
    
    // Offsets para centralizar o mapa no meio da tela.
    public static final int SCREEN_WIDTH_OFFSET = 2*IMG_SIZE;
    public static final int SCREEN_HEIGHT_OFFSET = 4*IMG_SIZE;
    
    
    public static final int SCREEN_WIDTH = 32*IMG_SIZE;
    public static final int SCREEN_HEIGHT = 40*IMG_SIZE;
    
    
    private Image fruitImageBeingUsedInThisLevel;
    
    
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
        {4,2,1,1,4,4,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,4,4,1,1,2,4},
        {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
        {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
        {4,1,1,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
        {4,1,4,4,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
        {4,1,4,4,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
        {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
        {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
    };
    
    private int originalMapData[][] = {
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
        {4,2,1,1,4,4,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,4,4,1,1,2,4},
        {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
        {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
        {4,1,1,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
        {4,1,4,4,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
        {4,1,4,4,4,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
        {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
        {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
    };

    /**
     * @return ImageView que sera utilizado na construcao da tela.
     */
    private ImageView getImageFromFieldValue(int value) {
        switch(value) {
            case 0:
                return new ImageView(this.VoidImage);
            case 1:
                return new ImageView(this.PacDotImage);
            case 2:
                return new ImageView(this.PowerDotImage);
            case 3:
                return new ImageView(this.fruitImageBeingUsedInThisLevel);
            case 4:
                return new ImageView(this.WallImage);
            case 5:
                return new ImageView(this.VoidImage);
            case 6:
                return new ImageView(this.VoidImage);
            case 10:
                return this.PacImView;
            case 11:
                return new ImageView(this.BlinkyImage);
            case 13:
                return new ImageView(this.InkyImage);
            case 17:
                return new ImageView(this.PinkyImage);
            case 19:
                return new ImageView(this.ClydeImage);
            case 31:
                return new ImageView(this.DeadGhostImage);
        }
        
        if(value >= 111 && value < 211) {
            return new ImageView(this.BlueGhostImage);
        } else if(value >= 211) {
            return new ImageView(this.ResetingGhostImage);
        }
        
        return null;
    }
    
    /**
     * Desenha na tela o conteudo presente no mapa.
     * @param root Group onde sera desenhado
     * @param fieldValue Valor do mapa
     * @param row Posicao linha
     * @param column Posicao coluna.
     */
    public void drawAtStage(Group root, int fieldValue, int row, int column) {
        
        ImageView imageView = getImageFromFieldValue(fieldValue);
        
        imageView.setY( row*IMG_SIZE + SCREEN_HEIGHT_OFFSET);
        imageView.setX( column*IMG_SIZE + SCREEN_WIDTH_OFFSET);
        
        
        root.getChildren().add(imageView);
    }
    
    /**
     * Atualiza o imageview do pacman com uma nova direcao.
     */
    public void updatePacImView(ImageView newPacImView) {
        this.PacImView = newPacImView;
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
    
    /**
     * Reseta o conteudo do mapa para o conteudo original.
     */
    public void resetFieldForLevelUpdate() {
        for(int i=0; i<this.mapHeight; i++) {
            for(int j=0; j<this.mapWidth; j++) {
                this.mapData[i][j] = this.originalMapData[i][j];
            }
        }
    }
    
    /**
     * Seta o tipo de fruta que sera utlizado dependendo do nivel.
     */
    public void setFruitType(int level) {
                
        switch (level) {
            case 1:
                this.fruitImageBeingUsedInThisLevel = this.CherryImage;
                break;
            case 2:
                this.fruitImageBeingUsedInThisLevel = this.StrawberryImage;
                break;
            case 3:
                this.fruitImageBeingUsedInThisLevel = this.OrangeImage;
                break;
        }
        
    }
    
    /**
     *
     * @return O tipo de fruta que esta sendo utilizado nesse nivel.
     */
    public Image getFruitTypeBeingUsed() { return this.fruitImageBeingUsedInThisLevel; }
}