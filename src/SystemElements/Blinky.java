/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SystemElements;

import Engine.Graph;
import Engine.Map;
import Engine.Points;

/**
 *
 * @author franreno
 */
public class Blinky extends Ghosts{
    
    public Blinky(Graph G, Map m, Points p) {
        super(G,m,p);
        this.elementValue = 11;
        setFirstPosition();
        updateOnMap();
    }
    
    
}
