package SystemElements;

import Engine.Graph;
import Engine.GraphNode;
import Engine.Map;
import Engine.Points;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author franreno
 */
public class Blinky extends Ghosts{
    
    private int mainPathHeader;
    private ArrayList<Integer> totalPath;
    
    public Blinky(Graph G, Map m, Points p) {
        super(G,m,p);
        this.elementValue = 11;
        this.previousElementValue = this.elementValue;
        this.totalPath = new ArrayList<>();
        
        setFirstPosition();
        
        this.previousGraphNodeBlockValue = this.gn.getBlockValue();
        
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
    }
    
    
    private void paintPath() {
        if(this.totalPath == null || this.totalPath.isEmpty())
            return;
        
        // nao pintar o pacman e o fantasma.
        ArrayList<Integer> separatedInstance = new ArrayList<>(this.totalPath);
        
        separatedInstance.remove(0);
        if(separatedInstance.size() > 0)
            separatedInstance.remove(separatedInstance.size() -1);
        
        
        for(Integer e : separatedInstance) {
            GraphNode gn = this.G.getGraphNode(e);
            int blockValue = gn.getBlockValue();
            
            if(blockValue < 10) {
                gn.setBlockValue(blockValue + 100);
                this.G.updateHashMap(e, gn);
                this.map.setValueAtMap(blockValue + 100, gn.getPos());
            }
            
        }
    }
    
    private void clearPath() {
        if(this.totalPath == null || this.totalPath.isEmpty())
            return;

        this.G.clearContentOfLists();
        
        for(Integer e : this.totalPath) {
            GraphNode gn = this.G.getGraphNode(e);
            int blockValue = gn.getBlockValue();
            
            if(blockValue >= 100 && blockValue != 11)
                blockValue -= 100;
            gn.setBlockValue(blockValue);
            this.G.updateHashMap(e, gn);
            this.map.setValueAtMap(blockValue, gn.getPos());
        }
    }
    
        
    public void pathfindPacMan(GraphNode goal) {
        if( (!this.totalPath.isEmpty()) ) {
            clearPath();
            if(this.totalPath.size() == 2) {
                System.out.println("Entrei aqui");
                this.totalPath.clear();
                return;
            }
            this.totalPath.clear();
        }
        
        if(this.gn.getBlockValue() == goal.getBlockValue()) {
            System.out.println("Funcionou aqui");
            return;
        }
        
        
        GraphNode path = this.G.A_Star(this.gn, goal);
        
        while(path.parent != null) {
            this.totalPath.add(path.getId());
            path = path.parent;
        }

        this.totalPath.add(path.getId());
        Collections.reverse(totalPath);
        paintPath();        
    }
    
    private void updatePositionOnMap() {
        //Ir para a primeira posicao do totalPath
        
//        if(this.totalPath.isEmpty()) 
//            return;
        
        int nodeValue = this.totalPath.get(1);
        
        GraphNode newNode = this.G.getGraphNode(nodeValue);
        
        int newValue = newNode.getBlockValue();
        if(newValue >= 100)
            newValue -=100;
        
        this.previousGraphNodeBlockValue = newValue;
        this.gn = newNode;
        
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);    
    }
    
    public int updateOnMap(GraphNode goal) {
        //Limpar a posicao atual.
        
        
        this.map.setValueAtMap(this.previousGraphNodeBlockValue, this.gn.getPos());
        
        //Voltar o valor anterior do node para ele
        this.gn.setBlockValue(this.previousGraphNodeBlockValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);

        // Verificar localizacao do pacman.
        // Olhar nos vizinhos do node atual
        if( this.gn.checkForNeighbor(goal.getId()) ) {
            return -1;
        }else {
            pathfindPacMan(goal);
        }
        
        // Ir para o proximo node.
        updatePositionOnMap();
        hasPacManEatenPallet();
        
        // Colocar blinky nessa nova posicao
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        return 0;
    }
    
}
