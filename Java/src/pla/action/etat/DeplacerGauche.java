package pla.action.etat;

import pla.Personnage;
import pla.action.etat.Action_etat;

/**
 *
 * @author antoi
 */
public class DeplacerGauche extends Action_etat {

	@Override
	public void executer(Personnage p, int delta) {
		p.setDirection(1);		
		float depl = (p.getX()-0.1f*delta)%MODULO_TORE_Y;
		p.setX(depl);
		if(p.getX() < 0){
			p.setX(MODULO_TORE_X - 1);
		}
	}
}
