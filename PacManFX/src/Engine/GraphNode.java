package Engine;
import java.util.ArrayList;

/**
 * Classe que contem os valores de cada nodulo do grafo.
 * 
 * @author Francisco Reis Nogueira - 11954374
 */
public class GraphNode{
    
    /**
     * Id do nodulo. Largura * linha + coluna.
     */
    private int id;
    
    /**
     * Valor do local correpondente no mapa.
     */
    private int blockValue;
    
    /**
     * Valor original do node.
     */
    private int originalBlockValue;
    
    /**
     * Valor i,j correspondente a matriz do mapa.
     * pos[0] -> linha;
     * pos[1] = coluna;
     */
    private int[] pos;
    
    /**
     * Quantidade de vizinhos do nodulo.
     */
    private int neighborsAmount;
    
    /**
     * lista de nodulos vizinhos.
     */
    private ArrayList<GraphNode> list;
    
    /**
     * Nodulo antecessor desse nodulo. Utilizado no metodo A*.
     */
    public GraphNode parent;
    
    /**
     * Valor f desse nodulo. Utilizado no metodo A*.
     */
    public int f = 10000;
    
    /**
     * Valor g desse nodulo. Utilizado no metodo A*.
     */
    public int g = 10000;
    
    /**
     * Metodo construtor do nodulo do grafo.
     * @param _id Valor Id do nodulo.
     * @param _blockValue Valor do nodulo no matriz
     * @param i Linha do nodulo na matriz
     * @param j Coluna do nodulo na matriz
     * @param m Objeto Map para o calculo dos vizinhos
     */
    public GraphNode(int _id, int _blockValue, int i, int j, Field m) {
        this.id = _id;
        this.blockValue = _blockValue;
        this.originalBlockValue = blockValue;
        this.pos = new int[2];
        this.pos[0] = i;
        this.pos[1] = j;
        this.parent = null;
        this.list = new ArrayList();
    }
    
    /**
     * @return Id do Nodulo.
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * @return Valor do nodulo no mapa.
     */
    public int getBlockValue() {
        return this.blockValue;
    }
    
    /**
     *
     * @return Valor original do node.
     */
    public int getOriginalBlockValue() { return this.originalBlockValue; }
    
    /**
     * @return Posicao do nodulo no mapa.
     */
    public int[] getPos() {
        return this.pos;
    }
    
    /**
     * @return Quantidade de vizinhos desse nodulo.
     */
    public int getNeighbors() {
        return this.neighborsAmount;
    }
    
    /**
     * @return Lista de nodulos vizinhos deste nodulo
     */
    public ArrayList<GraphNode> getList() {
        return this.list;
    }
    
    /**
     * Metodo set para alteracao do valor de um nodulo.
     * @param value Valor a ser alterado no nodulo.
     */
    public void setBlockValue(int value) {
        this.blockValue = value;
    }
    
    /**
     *
     * @param value Valor para mudar o valor original desse node.
     */
    public void setOriginalBlockValue(int value) {
        this.originalBlockValue = value;
    }
    
    /**
     * Metodo que verifica se existe um nodulo vizinho entre a lista de vizinhos.
     * @param id Id do nodulo a ser buscado na lista.
     * @return True se encontrou na lista. False caso contrario.
     */
    public boolean checkForNeighbor(int id) {
        return this.list.stream().anyMatch(gn -> (gn.getId() == id));
    }
    
    /**
     * Metodo que verifica se existe um nodulo com um valor blockValue entre a lista de vizinhos.
     * @param blockValue Valor a ser procurado na lista de vizinhos.
     * @return True se encontrou na lista. False caso contrario.
     */
    public boolean checkForNeighborWithBlockValue(int blockValue) {
        return this.list.stream().anyMatch(gn -> (gn.getBlockValue() == blockValue));
    }
    
    /**
     * @param width Largura do mapa.
     * @param i Linha do nodulo.
     * @param j Coluna do nodulo.
     * @return Id representativo do nodulo na posicao do mapa.
     */
    private int calculateNodeID(int width, int i, int j ) {
        return width*i + j;
    }
    
    /**
     * Olhar verticalmente para ver se tem um lugar VOID ou WALL.
     * @param data Matriz do mapa.
     * @param j Coluna onde esta sendo verificado.
     * @param whereToLook Posicao na linha onde sera verificado.
     * @param constraint Tipo de restricao que sera utilizado para nao olhar em lugar invalido.
     * @return True caso seja um lugar valido. False caso contrario.
     */
    private boolean verticalLook(int[][] data, int j, int whereToLook, int constraint) {
        // Ver se a posicao que for olhar eh valida.
        if( whereToLook < constraint && whereToLook >= 0 ) {
            // Ver a posicao e checar se eh uma parede ou void.
            if( data[whereToLook][j] != 4 && data[whereToLook][j] != 6) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Olhar horizontal para ver se tem um lugar VOID ou WALL.
     * @param data Matriz do mapa.
     * @param i Linha onde esta sendo verificado.
     * @param whereToLook Posicao na coluna onde sera verificado.
     * @param constraint Tipo de restricao que sera utilizado para nao olhar em lugar invalido.
     * @return True caso seja um lugar valido. False caso contrario.
     */
    private boolean horizontalLook(int[][] data, int i, int whereToLook, int constraint) {
        // Ver se a posicao que for olhar eh valida.
        if( whereToLook < constraint && whereToLook > 0) {
            // Ver a posicao e checar se eh uma parede ou void.
            if( data[i][whereToLook] != 4 && data[i][whereToLook] != 6) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metodo para olhar os vizinhos desse nodulo, verifica se eh um lugar valido
     * e adiciona um novo objeto GraphNode na lista de vizinhos. Também calcula a quantidade
     * de vizinhos que este nodulo possui.
     * @param m Objeto mapa para olhar os vizinhos
     * @param i Linha do nodulo
     * @param j Coluna do nodulo
     */
    public void lookForSurroudings(Field m, int i, int j) {
        this.neighborsAmount = 0;
        
        int[][] data = m.getData();
        int width = m.getWidth();
        int height = m.getHeight();
        
        // Olhar para baixo
        if( verticalLook(data, j, (i+1), height-1 ) ) {
            this.list.add( new GraphNode(calculateNodeID(width, (i+1), j), data[i+1][j], (i+1), j, m) );
            this.neighborsAmount++;
        }
        
        // Olhar para cima
        if( verticalLook(data, j, (i-1), i) ) {
            this.list.add( new GraphNode(calculateNodeID(width, (i-1), j), data[i-1][j], (i-1), j, m) );
            this.neighborsAmount++;
        }
        
        // Olhar para a direita
        if( horizontalLook(data, i, (j+1), width-1 ) ) {
            this.list.add( new GraphNode(calculateNodeID(width, i, (j+1)), data[i][j+1], i, (j+1), m) );
            this.neighborsAmount++;
        }
        
        // Olhar para a esquerda
        if( horizontalLook(data, i, (j-1), j ) ) {
            this.list.add( new GraphNode(calculateNodeID(width, i, (j-1)), data[i][j-1], i, (j-1), m) );
            this.neighborsAmount++;
        }
        
    }
    
    /**
     * @return String que contem os dados dos nodulos vizinhos.
     */
    private String neighborsToString() {
        String ret = "";
        for(int i=0; i<list.size(); i++) {
            GraphNode gn = list.get(i);
            ret += "{" + gn.getId() + ", " + gn.getBlockValue() + ", (" + gn.getPos()[0] + "," + gn.getPos()[1] + ") " + gn.getNeighbors() + "};";
        }
        
        return ret;
    }
    
    /**
     * @return String que representa o estado atual do atributo parent deste nodulo.
     */
    private String parentToString() {
        return (this.parent != null) ? String.valueOf(this.parent.getId()) : "null";
    }
    
    /**
     * @return String representativa deste nodulo.
     */
    @Override
    public String toString() {
        return "Id: " + id + " BlockValue: " + blockValue + " Pos: (" + pos[0] + "," + pos[1] + ") f,g,parent:" + this.f + " " + this.g + " " + this.parentToString() + " " + " Neighbors: " + neighborsAmount + " Neighbors: " + neighborsToString();
    }

}
