package pla.action.etat;

import pla.Personnage;
import pla.action.etat.Action_etat;

/**
 *
 * @author antoi
 */
public class DeplacerDroite extends Action_etat {

	@Override
	public void executer(Personnage p, int delta,int modulo_tore_x,int modulo_tore_y) {
        
		// Si le personnage a un v�lo, il va plus vite
		float dep = p.hasVelo()?2.0f*deplacement:deplacement;
		
		// Modification de l'orientation du personnage => Sprite du personnage vers la droite
		p.setDirection(3);	
		// Calcul du d�placement du personnage : delta est une valeur g�r�e par Slick2D 
		//et qui permet de garder une fluidit� dans le mouvement
		float currdepl = dep*delta;
		// Calcul de la nouvelle position du personnage en prenant le tore en compte
		float newPos = (p.getX()+currdepl)%modulo_tore_x;
		// Modification de la coordonn�es en abcisse du personnage
		p.setX(newPos);
		// La variable d�placementCourant permet de d�finir quand est-ce que le personnage doit s'arreter : 
		// Le personnage s'arrete au moment ou d�placementCourant = 64 
		// 64 = taille d'une case => Le personnage se d�place donc de case en case
		p.setDeplacementCourant(p.getDeplacementCourant()+currdepl);
	}

	@Override
	protected void executer(Personnage p, int delta) {
		// TODO Auto-generated method stub
		
	}
}
