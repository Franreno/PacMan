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
        
//        this.previousGraphNodeBlockValue = this.gn.getBlockValue();
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
        
        System.out.println(separatedInstance);
        System.out.println(this.totalPath);
        
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
            this.totalPath.clear();
        }
        
        System.out.println("Estou mandando o caminho: " + this.gn);
        GraphNode path = this.G.A_Star(this.gn, goal);

//        System.out.println(this.G.toString());
//
//        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------\n----------------------------------------------------------------------------------------------------------------------------------------\n----------------------------------------------------------------------------------------------------------------------------------------");
//       
//        System.out.println(this.G.toString());

        System.out.print("Estou criando a lista -> " + this.totalPath);
        while(path.parent != null) {
            this.totalPath.add(path.getId());
            path = path.parent;
        }

        this.totalPath.add(path.getId());
        Collections.reverse(totalPath);

        System.out.println("  Criei a lista: " + this.totalPath);
        
        paintPath();        
    }
    
    private void updatePositionOnMap() {
        //Ir para a primeira posicao do totalPath
        if(this.totalPath.isEmpty()) 
            return;
        
        System.out.println("Anterior: " + this.gn + "ValorElementoAnterior: " + this.previousElementValue);
        int nodeValue = this.totalPath.get(1);
        
        GraphNode newNode = this.G.getGraphNode(nodeValue);
        int newValue = newNode.getBlockValue();
        if(newValue >= 100)
            newValue -=100;
        this.previousElementValue = newValue;
        this.gn = newNode;
        
        System.out.println("Atual: " + this.gn + "ValorElementoAnterior: " + this.previousElementValue);
        
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);    
    }
    
    @Override
    public void updateOnMap() {
        //Limpar a posicao atual.
        System.out.println("Estou setando a posicao: (" + this.gn.getPos()[0] + "," + this.gn.getPos()[1] + ") Com o valor Anterior: " + this.previousElementValue );
        this.map.setValueAtMap(this.previousElementValue, this.gn.getPos());
        
        //Voltar o valor anterior do node para ele
        this.gn.setBlockValue(this.previousElementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        
        // Ir para o proximo node.
        updatePositionOnMap();
        
        // O VALOR DO THIS.ELEMENTVALUE TA MUDANDO
        // ELE TA SE TORNANDO O MESMO VALOR QUE O PREVIOUSELEMENTVALUE!!!
        
        System.out.println("Estou setando a posicao: (" + this.gn.getPos()[0] + "," + this.gn.getPos()[1] + ") Com o valor do elemento: " + this.elementValue );
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
    }
    
}
