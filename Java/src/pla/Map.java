package pla;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Map {

	/* largeur de la map */
	private static final int WIDTH = 100;
	
	/* longueur de la map */
	private static final int HEIGHT = 100;
	
	/** taille de la case */
	private static final int TILE_SIZE = 20;
	
	private Case cases[][];

	
	public Map() {
		cases = new Case[WIDTH][HEIGHT];
		
		// Cr�ation de la matrice des cases
		for(int i = 0;i<WIDTH;i++){
			for(int j = 0;j<HEIGHT;j++){
				cases[i][j] = new Case();
			}
		}
	}

	public void paint(List<Personnage> persos, Graphics g) {
		for(int i = 0; i< WIDTH; i++){
			for(int j = 0; j<HEIGHT;j++){				
				// carr�s de fond noir
				// position en x, en y, largeur et longueur
				g.fillRect(i*TILE_SIZE,j*TILE_SIZE,TILE_SIZE,TILE_SIZE);							
				g.setColor(Color.lightGray);									
				if(cases[i][j].getDecor().getImage() != null){
					g.drawImage(cases[i][j].getDecor().getImage(),i*TILE_SIZE+2,j*TILE_SIZE+2); // Multiple de 20 (TILE_SIZE+2 pour centrer l'image)
				}									
			}
		}
		for(Personnage p : persos){
			placerPersonnage(p,g);		
			placerAutomate(p.getAutomate(),p.getCouleur(),g);
		}
		
		
	}
	// Attention : Inversion i et j => x et y dans la map
	//MAP
	/* 	i		
	 * ----> x
	 * |
	 *j|
	 * |
	 * -
	 * y
	 */
	//CASE
	/*	j
	 * --->
	 * |
	* i|
	 * |
	 * -
	 */	
	public void placerPersonnage(Personnage p, Graphics g){
		// La case du personnage contient un nouveau decor contenant une image
			cases[p.getPosX()][p.getPosY()].setDecor(new Decor(p.getImage()));
			// dessiner l'image du personnage
			g.drawImage(cases[p.getPosX()][p.getPosY()].getDecor().getImage(),p.getPosX()*TILE_SIZE+2,p.getPosY()*TILE_SIZE+2);		
	}
	
	public void placerAutomate(Automate a,Color couleurPerso, Graphics g){
		for(int i = 0; i< a.getTab_actionTransition().length;i++)
			for(int j = 0;j< a.getTab_actionTransition().length;j++){
				// pour chaque valeur dans le tableau action-transition 
				chargerImage(a,g,i,j);	
			}		
		g.drawRect(a.getPosX()*TILE_SIZE,a.getPosY()*TILE_SIZE, a.getTaille()*TILE_SIZE, a.getTaille()*TILE_SIZE);		
		g.setColor(Color.orange);
	}
	
	
	public void chargerImage(Automate a,Graphics g,int i, int j){
		int val = a.getTab_actionTransition()[i][j];
		Image img = null;
		switch(val){
			case 0 : try {
				img = new Image("res/beton.jpg");
			} catch (SlickException e) {				
				g.drawString("Erreur : L'image du sol n'a pas pu �tre charg�e", 50, 0);	
				g.setColor(Color.red);
			}break;
			
			case 1 : try {
				img = new Image("res/sol_vert.jpg");
			} catch (SlickException e) {				
				g.drawString("Erreur : L'image du sol vert n'a pas pu �tre charg�e", 50, 0);	
				g.setColor(Color.red);
			}break;
			
			case 2 : case 4 : try {
				img = new Image("res/sol_bleu.jpg");
			} catch (SlickException e) {				
				g.drawString("Erreur : L'image du sol bleu n'a pas pu �tre charg�e", 50, 0);	
				g.setColor(Color.red);
			}break;
			
			case 3 : try {
				img = new Image("res/wall.png");
			} catch (SlickException e) {				
				g.drawString("Erreur : L'image du mur n'a pas pu �tre charg�e", 50, 0);	
				g.setColor(Color.red);
			}break;	
			
			default : img = null; 
		}
		// Attention inversion des indices entre les cases et la map
		cases[j+a.getPosX()][i+a.getPosY()].setDecor(new Decor(img));		
	}
	
	public Case[][] getCases() {
		return cases;
	}

	public void setCases(Case[][] cases) {
		this.cases = cases;
	}
	
}