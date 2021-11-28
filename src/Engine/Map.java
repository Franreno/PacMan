package Engine;

 
/**
 * Classe que contem os valores relacionados ao mapa.
 * Utilizado para o print na tela e mudanca de valores relevantes e a construcao
 * do Grafo.
 * 
 * @author Francisco Reis Nogueira - 11954374
 */
public class Map {
    
    /**
     * Largura do mapa.
     */
    private int mapWidth = 28;
    
    /**
     * Altura do mapa.
     */
    private int mapHeight = 31;
    
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
    
    /**
     * Valor padrao para o elemento no mapa.
     */
    public static final int FOOD = 1;
    /**
     * Valor padrao para o elemento no mapa.
     */
    public static final int SUPER_FOOD = 2;
    /**
     * Valor padrao para o elemento no mapa.
     */
    public static final int FRUIT = 3;
    /**
     * Valor padrao para o elemento no mapa.
     */
    public static final int WALL = 4;
    /**
     * Valor padrao para o elemento no mapa.
     */
    public static final int GHOST_CAGE = 5;
    /**
     * Valor padrao para o elemento no mapa.
     */
    public static final int VOID = 6;
    
    
    
    /**
     * String para print com cor.
     */
    private static final String COLOR_RESET = "\u001B[0m";
    /**
     * String para print com cor.
     */
    private static final String COLOR_YELLOW = "\u001B[33m"; //Pacman's color
    /**
     * String para print com cor.
     */
    private static final String COLOR_RED = "\u001B[31m"; //Blinky's color
    /**
     * String para print com cor.
     */
    private static final String COLOR_GREEN = "\u001B[32m"; // Inky's color
    /**
     * String para print com cor.
     */
    private static final String COLOR_CYAN = "\u001B[36m"; // Clyde's color
    /**
     * String para print com cor.
     */
    private static final String COLOR_PURPLE = "\u001B[35m"; // Pinky's color
    /**
     * String para print com cor.
     */
    private static final String COLOR_BLUE = "\u001B[34m"; // Dead Ghost
    /**
     * String para print com cor.
     */
    private static final String COLOR_WHITE = "\u001B[37m"; // Reseting Ghost

    
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
     * Switch case para imprimir os valores da matriz do mapa.
     * @param value Valor a ser impresso no mapa
     */
    private void printTargetMap(int value) {
        
        switch(value) {
            //Empty pixel
            case 0 -> 
                System.out.print("  ");
            //Food pixel
            case 1 -> 
                System.out.print("· ");
            //Poweup pixel
            case 2 -> 
                System.out.print("◆ ");
            //Fruit pixel
            case 3 -> 
                System.out.print("F ");
            //Wall pixel
            case 4 -> 
                System.out.print(COLOR_BLUE + "▩ " + COLOR_RESET);
            //Ghost zone pixels
            case 5 -> System.out.print("  ");
            //Void pixels
            case 6 -> System.out.print("  ");
            //PacMan
            case 10 -> System.out.print("\uD83D\uDE00");
            //⚫
            //Blinky
            case 11 -> System.out.print(COLOR_RED + "⚫ " + COLOR_RESET);
            //Inky
            case 13 -> System.out.print(COLOR_GREEN + "⚫ " + COLOR_RESET);
            //Clyde
            case 17 -> System.out.print(COLOR_CYAN + "⚫ " + COLOR_RESET);
            //Pinky
            case 19 -> System.out.print(COLOR_PURPLE + "⚫ " + COLOR_RESET);
            // Eatable ghosts
            case 23 -> System.out.print(COLOR_BLUE + "⚫ " + COLOR_RESET);
            // Reseting Ghosts
            case 29 -> System.out.print("⚫ ");
        }
        
        // Se o valor for maior que 100 vai ser representado por um ▲.
        // O ▲ representa o caminho que o fantasma blinky ira usar para
        // perseguir o pacman.
        if(value >= 100) {
            System.out.print(COLOR_GREEN + "▲ " + COLOR_RESET);
        }
    }
    
    /**
     * Metodo de impressao dos valores do mapa no terminal.
     */
    public void printMap() {
        for(int i=0; i<mapHeight; i++) {
            for(int j=0; j<mapWidth; j++){
                printTargetMap(mapData[i][j]);
            }
            System.out.print("\n");
        }
    }
}
