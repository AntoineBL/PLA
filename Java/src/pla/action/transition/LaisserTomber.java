/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pla.action.transition;

import pla.Jeu;
import pla.Personnage;
import pla.decor.*;
import pla.ihm.Case;
import pla.ihm.Map;

/**
 *
 * @author antoi
 */
public class LaisserTomber extends Action_transition {

    @Override
    public void executer(Personnage p, Case c, Jeu j, int delta) {
        Decor d = p.getObjet();
        p.removeObjet();
        if(d instanceof SolNormal) {
            c.setDecor(d);
        }
    }
    
}
