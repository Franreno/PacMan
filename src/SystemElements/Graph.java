package SystemElements;

import java.util.*;

/**
 *
 * @author franreno
 */
public class Graph {
    private int numVertices;
    private LinkedList<HashMap<String, Integer>> adjList[];
    
    
    
    public Graph(Map e) {
        numVertices = e.countAmountOfLegalPlaces();
        
        adjList = new LinkedList[numVertices];
        for(int i=0; i<numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
        
        
        int auxNumVertices = 0;
        for(int i=0; i<e.mapHeight; i++) {
            for(int j=0; j<e.mapWidth; j++) {
                
                // Nao pode ser uma parede e um lugar void
                if(e.mapData[i][j] != 4 && e.mapData[i][j] != 6) {
                    adjList[auxNumVertices].addFirst(addValues(e.mapData[i][j], i, j));
//                    System.out.println("adjList[" + numVertices + "]: " + adjList[numVertices].size);
                    
                    
                    ArrayList<HashMap> vizinhos = lookForSurroudings(e, i, j);
                    
                    // Se estiver vazio eh pq esse nodulo nao tem nenhum vizinho?
                    if(vizinhos.isEmpty() == false) {
                        System.out.println("Valor em " + i + "," + j + ": " + e.mapData[i][j]);
                        System.out.println("Tam vizinhos: " + vizinhos.size() );
                        for (int k = 0; k<vizinhos.size(); k++) {
                            System.out.println(vizinhos.get(k));
//                            System.out.println(adjList[k]);
                            adjList[auxNumVertices].addLast(vizinhos.get(k));
                        }
                    }
                    
                    auxNumVertices++;
                }
            }
        }
    }
    
    private HashMap<String, Integer> addValues(int value, int i, int j) {
        HashMap<String, Integer> HM = new HashMap<>();
        HM.put("value", value);
        HM.put("posx", i);
        HM.put("posy", j);
        
        return HM;
    }
    
    private ArrayList lookForSurroudings(Map e, int i, int j) {
        ArrayList<HashMap<String,Integer>> dictList = new ArrayList<>();
        
        
        
        // Preciso olhar os valores que estao nas quatro direcoes
        
        // Olhar para baixo
        if( i != e.mapHeight-2 ) {
            int _i = i + 1; 
            //Verificar se eh um bloco legal
            if( e.mapData[_i][j] != 4 && e.mapData[_i][j] != 6 ) {
                dictList.add(addValues(e.mapData[_i][j], _i, j));
            }
        }
        
        // Olhar para cima
        if( i != 0 ) {
            int _i = i - 1;
            //Verificar se eh um bloco legal
            if( e.mapData[_i][j] != 4 && e.mapData[_i][j] != 6 ) {
                dictList.add(addValues(e.mapData[_i][j], _i, j));
            }
        }
        
        // Olhar para a direita
        if(j != e.mapWidth - 2) {
            int _j = j + 1;
            if( e.mapData[i][_j] != 4 && e.mapData[i][_j] != 6 ) {
                dictList.add(addValues(e.mapData[i][_j], i, _j));
            }
        }
        
        // Olhar para a esquerda
        if(j != 0) {
            int _j = j - 1;
            if( e.mapData[i][_j] != 4 && e.mapData[i][_j] != 6 ) {
                dictList.add(addValues(e.mapData[i][_j], i, _j));
            }
        }
        
        return dictList;
    }
    
}
