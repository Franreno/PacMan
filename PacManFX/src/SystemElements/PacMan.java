package SystemElements;

import Engine.Points;
import Engine.Field;
import Engine.Graph;
import Engine.GraphNode;

/**
 * Classe que representa os atributos e metodos para o Pacman.
 * @author Francisco Reis Nogueira - 11954374
 */
public class PacMan extends Movement{
    
    /**
     * Quantidade de vidas do Pacman.
     */
    private int lifes;
    
    /**
     * Checa o atual status do Pacman.
     */
    private boolean alive;
        
    /**
     * Construtor da classe Pacman.
     * @param G Grafo referente ao jogo.
     * @param m Mapa referente ao jogo.
     * @param p Sistema de pontos do jogo.
     */
    public PacMan(Graph G, Field m, Points p) {
        super(G,m,p);
        this.lifes = 3;
        this.alive = true;
        this.elementValue = 10;
        setVelocity(0,0);
        setFirstPosition();
    }
    
    /**
     * @return a quantidade de vidas atuais do Pacman.
     */
    public int getLifes() {
        return this.lifes;
    }
        
    /**
     * Seleciona uma posicao aleatoria, pelo grafo, para colocar o PacMan no mapa.
     */
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
        
    /**
     * Pacman morreu, perde uma vida.
     */
    public void died() {
        this.alive = false;
        this.lifes--;
    }
    
    /**
     * Atualiza a velocidade do pacman (direcao) dependendo da direcao digitada.
     * @param ch Tipo de movimentacao que o Pacman seguira.
     */
    public void updateVelocity(char ch) {
        switch(ch) {
            case 'w': 
                setVelocity(-1, 0);
                break;
            
            case 's':
                setVelocity(1, 0);
                break;
            
            case 'd': 
                setVelocity(0, 1);
                break;
            
            case 'a': 
                setVelocity(0, -1);
                break;
        }
    }
    
    
    /**
     * Verfica o que o pacman comeu chama os metodos os pontos para realizar as 
     * atribuicoes aos pontos.
     * @param value Valor que foi comido.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    private int checkEatenValue(int value) {
        
        // Checagem necessaria por conta do caminho
        // que o fantasma vai seguir.
        if(value >= 100) 
            value -=100;
        
        switch(value) {
            // Comi normal
            case 1:
                this.points.hasEaten(1);
                break;
                
            // Comi super
            case 2: 
                this.points.hasEaten(2);
                break;
                
            // Comi fruta
            case 3: 
                this.points.hasEaten(3);
                break;
        }
        
        // Tocou ou comeu algum fantasma
        if(value > 10) 
            return 1;
        
        return 0; 
    }
    
    /**
     * Atualiza a posicao do Pacman no mapa de acordo com a sua velocidade.
     */
    private void updatePacManPosition() {
        this.pos[0] += this.dpos[0];
        this.pos[1] += this.dpos[1];
    }
    
    /**
     * Verifica e atualiza a nova posicao do Pacman no mapa.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    public int update() {
        
        // Verifica se o Pacman esta com poder ativo, se estiver 
        // a duracao do poder atual diminui.
        if(this.points.getPowerTimer() != 0)
            this.points.decreasePowerTimer();
        
        // Verificar se a nova posicao existe na lista de vizinhos
        int nextFrameId = this.map.getWidth() * (this.pos[0] + this.dpos[0]) + (this.pos[1] + this.dpos[1]);
        
        if( this.gn.checkForNeighbor( nextFrameId ) ) {
            
            int state = updadtePacManOnMap();
            if(state != 0) 
                return state; 
            
        }
            
        return 0;
    }
    
    /**
     * Atualiza diretamente os grafos e o mapa com as novas posicoes do Pacman.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    private int updadtePacManOnMap() {
        
        // Limpar o conteudo que existe nesse lugar que o pacman esta.
        this.gn.setBlockValue(0);
        this.gn.setOriginalBlockValue(0);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        this.map.setValueAtMap(0, this.gn.getPos());
        
        updatePacManPosition();
        
        // Pegar o valor do novo bloco
        this.gn = this.G.getGraphNode( this.map.getWidth() * this.pos[0] + this.pos[1] );
        
        // Verificar o que o PacMan comeu
        int ateStatus = checkEatenValue(this.gn.getBlockValue());
        
        if(ateStatus != 0)
            return ateStatus;
        
        // Atualizar o mapa e o Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        return 0;
    }
    
    @Override
    public String toString() {
        String ret = "";
        
        ret += "Pos: " + this.pos[0] + " , " + this.pos[1] + " Velocity: " + this.dpos[0] + " , " + this.dpos[1];
        
        return ret;
    }
}
