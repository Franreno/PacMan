package SystemElements;

import Engine.Points;
import Engine.Field;
import Engine.Graph;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe que representa os atributos e metodos para o Pacman.
 * @author Francisco Reis Nogueira - 11954374
 */
public class PacMan extends Movement{
    
    // Imagens para a realizar a rotacao da imagem dependendo do sentido do pacman.
    private final Image PacManImage = new Image("assets/Pacman.gif", 16, 16, false, false);
    private ImageView PacImView = new ImageView(PacManImage);
    
    /**
     * Quantidade de vidas do Pacman.
     */
    private int lifes;
    
    /**
     * Checa o atual status do Pacman.
     */
    private boolean alive;
    
    /**
     * Qual fantasma que foi comido.
     */
    private int ghostTypeEaten;
        
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
        this.ghostTypeEaten = -1;
        this.startingGraphNode = this.G.getGraphNode(656);
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
     * @return O tipo do fantasma que foi comido.
     */
    public int getGhostTypeEaten() { return this.ghostTypeEaten; }
    
    /**
     *
     * @return O ImageView ja rotacionado para a direcao do pacman.
     */
    public ImageView getPacImView() { return this.PacImView; }
        
    /**
     * Seleciona uma posicao aleatoria, pelo grafo, para colocar o PacMan no mapa.
     */
    @Override
    protected void setFirstPosition() {
        this.gn = this.startingGraphNode;
        int[] position = this.gn.getPos();
        this.pos[0] = position[0];
        this.pos[1] = position[1];
        
        
        updadtePacManOnMap();
    }
        
    /**
     * Pacman morreu, perde uma vida.
     */
    public void died() {
        this.alive = false;
        this.lifes--;
        this.resetEntity();
    }
    
    /**
     * Atualiza a velocidade do pacman (direcao) dependendo da direcao digitada.
     * @param ch Tipo de movimentacao que o Pacman seguira.
     */
    public void updateVelocity(char ch) {
        this.PacImView.setRotate(0);
        switch(ch) {
            case 'w': 
                setVelocity(-1, 0);
                this.PacImView.setRotate(-90);
                break;
            
            case 's':
                setVelocity(1, 0);
                this.PacImView.setRotate(90);
                break;
            
            case 'd': 
                setVelocity(0, 1);
                break;
            
            case 'a': 
                setVelocity(0, -1);
                this.PacImView.setRotate(-90 * 2);
                break;
        }
    }
    
    
    /**
     * Verfica o que o pacman comeu chama os metodos os pontos para realizar as 
     * atribuicoes aos pontos.
     * @param value Valor que foi comido.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    private boolean checkEatenValue(int value) {
        if(value == 0)
            return true;
        
        
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
        if(value > 10) {
            this.ghostTypeEaten = value;
            return false;
        }
            
        
        return true; 
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
    public boolean update() {
        
        // Verifica se o Pacman esta com poder ativo, se estiver 
        // a duracao do poder atual diminui.
        if(this.points.getPowerTimer() != 0)
            this.points.decreasePowerTimer();
        
        // Verificar se a nova posicao existe na lista de vizinhos
        int nextFrameId = this.map.getWidth() * (this.pos[0] + this.dpos[0]) + (this.pos[1] + this.dpos[1]);
        
        if( this.gn.checkForNeighbor( nextFrameId ) ) {
            
            boolean state = updadtePacManOnMap();
            if(!state) 
                return false; 
            
        }
            
        return true;
    }
    
    /**
     * Atualiza diretamente os grafos e o mapa com as novas posicoes do Pacman.
     * @return 0 Para normalidade. Diferente de 0 para algum evento.
     */
    private boolean updadtePacManOnMap() {
        
        // Limpar o conteudo que existe nesse lugar que o pacman esta.
        this.gn.setBlockValue(0);
        this.gn.setOriginalBlockValue(0);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        this.map.setValueAtMap(0, this.gn.getPos());
        
        updatePacManPosition();
        
        // Pegar o valor do novo bloco
        this.gn = this.G.getGraphNode( this.map.getWidth() * this.pos[0] + this.pos[1] );
        
        // Verificar o que o PacMan comeu
        boolean ateStatus = checkEatenValue(this.gn.getBlockValue());
        
        if(!ateStatus)
            return false;
        
        // Atualizar o mapa e o Grafo
        this.gn.setBlockValue(this.elementValue);
        this.G.updateHashMap(this.gn.getId(), this.gn);
        this.map.setValueAtMap(this.elementValue, this.gn.getPos());
        
        return true;
    }
    
    @Override
    public String toString() {
        String ret = "";
        
        ret += "Pos: " + this.pos[0] + " , " + this.pos[1] + " Velocity: " + this.dpos[0] + " , " + this.dpos[1];
        
        return ret;
    }
}
