package SystemElements;

import Engine.Points;
import Engine.Map;
import Engine.Graph;
import Engine.GraphNode;

/**
 *
 * @author franreno
 */
public class PacMan extends Movement{

    private boolean powerStatus;
    private boolean alive;
    private int lifes;
    private Points points;
    private GraphNode previousNode;
    
    public PacMan(Graph G, Map m, Points p) {
        super(G,m);
        this.powerStatus = false;
        this.alive = true;
        this.lifes = 3;
        this.points = p;
        this.elementValue = 10;
        setVelocity(0,0);
        setFirstPosition();
    }
    
    public GraphNode getPacManNode() {
        return this.gn;
    }
    
    @Override
    protected void setFirstPosition() {
        boolean flag = false;
        while(!flag) {
            this.gn = this.G.getRandomGraphNode();
            if(this.gn.getBlockValue() != 2)
                flag = true;
        }
        int[] _randomPos = this.gn.getPos();
        
        this.pos[0] = _randomPos[0];
        this.pos[1] = _randomPos[1];
        updadtePacManOnMap();
    }
    
    @Override
    protected void setVelocity(int dVertical, int dHorizontal) {
        this.dpos[0] = dVertical;
        this.dpos[1] = dHorizontal;
    }
    
    public void updateVelocity(char ch) {
        switch(ch) {
            case 'w' -> {
                setVelocity(-1, 0);
            }
            case 's' -> {
                setVelocity(1, 0);
            }
            case 'd' -> {
                setVelocity(0, 1);
            }
            case 'a' -> {
                setVelocity(0, -1);
            }
        }
    }
    
    private void checkEatenValue(int value) {
        if(value >= 100) 
            value -=100;
        
        switch(value) {
            // Ate normal Food
            case 1 -> this.points.hasEaten(1);
            // Ate super food
            case 2 -> this.points.hasEaten(2);
            // Ate fruit
            case 3 -> this.points.hasEaten(3);
        }
    }
    
    
    private void updatePacManPosition() {
        this.pos[0] += this.dpos[0];
        this.pos[1] += this.dpos[1];
    }
    
    public void updatePacMan() {
        if(this.points.getPowerTimer() != 0)
            this.points.decreasePowerTimer();
        
        // Verificar se a nova posicao existe na lista de vizinhos
        int nextFrameId = this.map.getWidth() * (this.pos[0] + this.dpos[0]) + (this.pos[1] + this.dpos[1]);
        if( this.gn.checkForNeighbor( nextFrameId ) )
            updadtePacManOnMap();
    }
    
    
    private void updadtePacManOnMap() {
        // Limpar o conteudo que existe nesse lugar que o pacman esta.
        // Onde o pacman esta eh vai ser 0 se o valor de onde ele esta eh 
        // menor que 10 (menor que alguma entidade)
        this.gn.setBlockValue(0);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        this.map.setValueAtMap(0, this.gn.getPos());
        
        updatePacManPosition();
        
        // Pegar o valor do novo bloco
        this.gn = this.G.getGraphNode( this.map.getWidth() * this.pos[0] + this.pos[1] );
        
        // Verificar o que o PacMan comeu
        checkEatenValue(this.gn.getBlockValue());
        
        // Atualizar o mapa e o Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
    }
}
