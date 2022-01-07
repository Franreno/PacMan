/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SystemElements;

import Engine.Field;
import Engine.Graph;
import Engine.Points;

/**
 *
 * @author franreno
 */
public class Inky extends Ghosts{
    
    public Inky(Graph G, Field m, Points p) {
        super(G, m, p);

        // Valor do Clyde.
        this.elementValue = 13;

        // Define o nodulo de posicao do fantasma no mapa.
        setFirstPosition();
        
        this.basicSetup();
        
    }   
    
}
