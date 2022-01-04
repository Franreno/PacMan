package Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Classe que contem todo os metodos e atributos do Grafo.
 * O grafo contem nodulos de todas as posicoes do mapa que sao andaveis
 * para o Pacman e para os fantasmas.
 * 
 * @author Francisco Reis Nogueira - 11954374
 */
public class Graph {
    /**
     * Numero de vertices do grafo.
     */
    private int numVertices;
    /**
     * Map que contem os valores dos nodulos.
     * O Map eh composto pelo Id do nodulo e pelo objeto (GraphNode).
     */
    private HashMap<Integer, GraphNode> hashList;
    /** 
     * Guarda os valores que precisam ser verificados.
     * ArrayList de GraphNodes que eh utilizado na busca A*.
     */
    private ArrayList<GraphNode> openList;
    /**
     * Guarda os valores que ja foram verificados.
     * ArrayList de GraphNodes que eh utilizado na busca A*.
     */
    private ArrayList<GraphNode> closedList;
    
    /**
     * Metodo construtor do grafo.
     * @param m Objeto do mapa que eh utilizado para construir o grafo
     */
    public Graph(Field m) {
        
        hashList = new HashMap<>();
        numVertices = 0;
        
        // Percorre a matriz do mapa para adicionar os nodulos.
        int[][] data = m.getData();
        int height = m.getHeight();
        int width = m.getWidth();
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                
                // So adiciona se for um lugar valido.
                if(data[i][j] != 4 && data[i][j] != 6) {
                    
                    int thisNodeId = (width * i + j);
                    
                    // Novo nodulo
                    GraphNode node = new GraphNode( thisNodeId, data[i][j], i, j , m);
                    node.lookForSurroudings(m, i, j);
                                        
                    //Coloca o novo nodulo no Map
                    hashList.put(thisNodeId, node);
                    numVertices++;
                }
                
            }
        }
    }
    
    /**
     * Metodo get para a quantidade de vertices do grafo.
     * @return Quantidade de vertices do grafo
     */
    public int getNumVertices() {
        return this.numVertices;
    }
    
    /**
     * Metodo que pega um nodulo aleatorio a partir da selecao de uma chave
     * por meio de uma lista dessas chaves do Map.
     * @return Um Nodulo aleatorio do grafo a partir do Map.
     */
    public GraphNode getRandomGraphNode() {
        ArrayList<Integer> keys = new ArrayList(this.hashList.keySet());
        Random rand = new Random();
        return this.hashList.get( keys.get(rand.nextInt(keys.size()) ));
    }

    /**
     * Metodo para pegar um nodulo do grafo.
     * @param index Chave correspondente a um nodulo no grafo.
     * @return Um objeto GraphNode. Null caso a chave nao corresponda a algum nodulo.
     */
    public GraphNode getGraphNode(int index) {
        GraphNode gn = this.hashList.get(index);
        if (gn == null)
            return null;

        return gn;
    }
    
    /**
     * Atualiza um nodulo do Grafo com novas informacoes.
     * @param key Chave correspondente ao nodulo que sera alterado.
     * @param gn Objeto GraphNode que substituira o conteudo antigo.
     */
    public void updateHashMap(int key, GraphNode gn) {
        this.hashList.replace(key, gn);
    }
    
    /**
     * Distancia heuristica para o metodo A* (A_Star).
     * Eh utilizada a distancia de Manhattan.
     * @param pos Posicao de origem do nodulo.
     * @param goal Objetivo do metodo.
     * @return Distancia de um nodulo x para o objetivo final do metodo A*.
     */
    private int h(int[] pos, int[] goal) {
        //Manhattan Distance
        int dx = Math.abs( pos[0] - goal[0] );
        int dy = Math.abs( pos[1] - goal[1] );
        return (dx + dy);
    }
    
    /**
     * Implementacao do metodo A* para o pathfinding para que o fantasma 
     * encontre o Pacman.
     * @param start Nodulo de comeco (Posicao do fantasma).
     * @param goal Nodulo de chegada (Posicao do Pacman).
     */
    public void A_Star(GraphNode start, GraphNode goal) {
        this.openList = new ArrayList<>();
        this.closedList = new ArrayList<>();

        GraphNode current = start;
        current.g = 0;
        current.f = current.g + h(current.getPos(), goal.getPos());
        
        // Enquanto o nodulo atual nao for igual ao nodulo de destino.
        while(current.getId() != goal.getId()) {
            
            // Distancia do nodulo atual + 1.
            // Cada nodulo tem distancia 1 de seu vizinho
            int gScore = current.g + 1;
            
            // Itera por cada vizinho no nodulo atual
            for(GraphNode neighbor : current.getList()) {
                // Pega os nodulos diretamente do Map e faz alteracoes neles
                GraphNode adj = this.hashList.get(neighbor.getId());
                
                // Se o nodulo nao estiver em nenhuma das listas
                if( ( !closedList.contains(adj) ) && (!openList.contains( adj )) ) {
                    // Adiciona o nodulo atual na lista dos possiveis a ser verificados
                    openList.add(this.hashList.get(adj.getId()));
                    
                    // Calcula o gScore e fScore desse nodulo
                    adj.g = gScore;
                    int fScore = adj.g + h(adj.getPos(), goal.getPos());
                    
                    // Verifica se o fScore desse nodulo eh menor que o previamente calculado
                    // (Valor default de g e f eh 10000).
                    if(fScore < adj.f) {
                        adj.f = fScore;
                        adj.parent = current; //Melhor opcao
                    }
                }
            }
            
            // Adicionar o nodulo atual na lista dos ja vistos
            closedList.add(this.hashList.get(current.getId()));
            
            // Pega o menor valor de f da openList
            int lowest = 10000;
            int indexStorage = 0;
            for(int i=0; i<openList.size(); i++) {
                GraphNode gn = openList.get(i);
                if(gn.f < lowest) {
                    lowest = gn.f;
                    indexStorage = i;
                }
            }
            
            // O nodulo que possui o menor f eh selecionado como atual 
            // E eh removido da lista aberta
            current = openList.get(indexStorage); 
            openList.remove(indexStorage);
        }
    }
    
    /**
     * Limpa o conteudo das listas abertas e fechadas.
     * Os nodulos devem ser limpados para que a proxima execucao do algoritmo
     * ocorra sem nenhum problema
     */
    public void clearContentOfLists() {
        for(GraphNode helper : this.openList) {
            helper.f = 10000;
            helper.g = 10000;
            helper.parent = null;
        }
        for(GraphNode helper : this.closedList) {
            helper.f = 10000;
            helper.g = 10000;
            helper.parent = null;
        }
    }
    
    /**
     * 
     * @return O conteudo do grafo ordenado pelas chaves
     */
    @Override
    public String toString() {
        String ret = "";
        ArrayList<Integer> sortedKeys = new ArrayList(hashList.keySet());
        Collections.sort(sortedKeys);
        
        for (Integer key : sortedKeys) {
            GraphNode node = hashList.get(key);
            ret += "{ " + key + ": " + node.toString() + " }\n";
        }
        return ret;
    }
}
