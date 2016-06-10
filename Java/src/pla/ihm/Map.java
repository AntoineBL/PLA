package pla.ihm;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.omg.CORBA.TIMEOUT;

import pla.Association;
import pla.Automate;
import pla.Cellule;
import pla.Personnage;
import pla.action.transition.Action_transition;
import pla.decor.Decor;

public class Map {

	private SpriteSheet ssmap;
	private static final int TILE_SIZE = 64;

	/* largeur de la map */
	private int largeur = 10;

	/* longueur de la map */
	private int hauteur =8;

	// Matrice des Cases
	private Case cases[][];

	public Map() {
		cases = new Case[hauteur][largeur];
		// Cr�ation de la matrice des cases
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
				cases[i][j] = new Case(i, j);
			}
		}
	}

	public void init() throws SlickException {
		this.ssmap = new SpriteSheet("res/sprite.png", TILE_SIZE, TILE_SIZE);
	}

	public void afficher() {
		ssmap.startUse();
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
                                Decor d = cases[i][j].getDecor();
				ssmap.renderInUse(j * TILE_SIZE, i * TILE_SIZE, d.getX(), d.getY());
			}
		}
		ssmap.endUse();
	}

	/*
	 * 
	 * public void placerPersonnage(Personnage p, Graphics g) {
	 * 
	 * // La case du personnage contient un nouveau decor contenant une image
	 * modifierDecorCase(p.getPosX(), p.getPosY(), new
	 * DecorPersonnage(p.getImage()));
	 * 
	 * // Ajouter le personnage � la liste des personnages de la case try {
	 * cases[p.getPosX()][p.getPosY()].ajouterPersonnage(p); } catch
	 * (ArrayIndexOutOfBoundsException e) { System.out.println(
	 * "Erreur : Sortie tableau : X = " + p.getPosX() + " Y = " + p.getPosY());
	 * } // dessiner l'image du personnage
	 * dessinerImage(cases[p.getPosX()][p.getPosY()].getDecor().getImage(),
	 * p.getPosX() * TILE_SIZE, p.getPosY() * TILE_SIZE, g); }
	 */
	public void placerAutomate(Automate a, Color couleurPerso, Graphics g) {
		for (int i = 0; i < a.getNbLignes(); i++)
			for (int j = 0; j < a.getNbColonnes(); j++) {
				// pour chaque valeur dans le tableau action-transition, charger
				// l'image dans la case
				chargerDecor(a, g, i, j);
			}
	}

	/*
	 * public void dessinerContoursAutomate(Personnage p, Graphics g) { //
	 * dessiner un rectangle autour de l'automate et le colorier de la //
	 * couleur du personnage � qui appartient cet automate. Automate a =
	 * p.getAutomate(); g.setColor(p.getCouleur()); g.drawRect(a.getPosX() *
	 * TILE_SIZE, a.getPosY() * TILE_SIZE, a.getNbColonnes() * TILE_SIZE,
	 * a.getNbLignes() * TILE_SIZE);
	 * 
	 * }
	 */
	public void chargerDecor(Automate a, Graphics g, int i, int j) {
		Action_transition at = a.getTabActionTransition()[i][j];
		Decor decor = Association.getDecor(at);
		modifierDecorCase(i + a.getPosX(), j + a.getPosY(), decor);
	}

	public Case[][] getCases() {
		return cases;
	}

	/*
	 * public void setCases(Case[][] cases) { this.cases = cases; }
	 */
	public void modifierDecorCase(int i, int j, Decor decor) {
		if (i < largeur && j < hauteur) {
			cases[i][j].setDecor(decor);
		}

	}

	/*
	 * public void effacerDecorCase(int i, int j) { modifierDecorCase(i, j, new
	 * SolNormal()); }
	 * 
	 * public void dessinerImage(Image img, float x, float y, Graphics g) { if
	 * (x < largeur * TILE_SIZE && y < longueur * TILE_SIZE) { // Si la //
	 * position // voulue // est // bien dans // la // grille g.drawImage(img,
	 * x, y); } else { System.out.println("L'image" + img.getResourceReference()
	 * + "n'a pas pu �tre dessin�e"); } }
	 */
	public Case getCase(Case caseCourante, Cellule cellule) {
		try {
			switch (cellule) {
			case Nord:
				return cases[caseCourante.getIndexI()][caseCourante.getIndexJ() - 1];
			case Sud:
				return cases[caseCourante.getIndexI()][caseCourante.getIndexJ() + 1];
			case Est:
				return cases[caseCourante.getIndexI() + 1][caseCourante.getIndexJ()];
			case Ouest:
				return cases[caseCourante.getIndexI() - 1][caseCourante.getIndexJ()];
			case Case:
			default:
				return caseCourante;
			}
		} catch (ArrayIndexOutOfBoundsException e) { // Si on sort de la map
			return caseCourante;
		}
	}

	public Case getCase(int i, int j) {
		return cases[i][j];
	}
	/*
	 * // Compteur qui retourne un entier correspondant aux nombres de cases
	 * dans // la grille contenant le decor d public int nbDecor(Decor d,
	 * Graphics g) { int res = 0; if (d != null) { for (int i = 0; i < largeur;
	 * i++) for (int j = 0; j < longueur; j++) { if (cases[i][j].getDecor() !=
	 * null && cases[i][j].getDecor().getId() == d.getId()) { res++; } } }
	 * return res; }
	 * 
	 * public int getLargeur() { return largeur; }
	 * 
	 * public void setLargeur(int largeur) { this.largeur = largeur; }
	 * 
	 * public int getLongueur() { return longueur; }
	 * 
	 * public void setLongueur(int longueur) { this.longueur = longueur; }
	 */

	private static final int CERCLE = 360;
	private static final int DISTANCE = 2;

	public void placerAutoRandom(List<Personnage> lPersonnage) {
		Random rand = new Random();
		
		int nbAutomate = lPersonnage.size();
		int rayonCercle, centreCercleX, centreCercleY, anglePersonnage, firstAngle, hmax, lmax;

		hmax = hauteurMax(lPersonnage);
		lmax = largeurMax(lPersonnage);

		if (hmax <= lmax) {
			rayonCercle = (lmax * (nbAutomate) / 2 )*TILE_SIZE;
		} else {
			rayonCercle = (hmax * (nbAutomate) / 2 )*TILE_SIZE;
		}
		centreCercleX = rayonCercle;
		centreCercleY = rayonCercle;
		
		anglePersonnage = CERCLE / nbAutomate;
		firstAngle = rand.nextInt(CERCLE + 1);
		lPersonnage.get(0).getAutomate().setPosX((int)(rayonCercle * Math.cos(firstAngle*2*Math.PI/360)+ centreCercleX));
		lPersonnage.get(0).getAutomate().setPosY((int)(rayonCercle * Math.sin(firstAngle*2*Math.PI/360)+ centreCercleY));

		for (int i = 1; i < nbAutomate; i++) {
			firstAngle = (anglePersonnage +firstAngle)%CERCLE;
			lPersonnage.get(i).getAutomate().setPosX((int)(rayonCercle * Math.cos(firstAngle*2*Math.PI/360) + centreCercleX));
			lPersonnage.get(i).getAutomate().setPosY((int)(rayonCercle * Math.sin(firstAngle*2*Math.PI/360) + centreCercleY));
		}
	}

	private int hauteurMax(List<Personnage> lPersonnage) {
		int hmax = 0;
		for (int i = 0; i < lPersonnage.size(); i++) {
			if (lPersonnage.get(i).getAutomate().getNbLignes() > hmax) {
				hmax = lPersonnage.get(i).getAutomate().getNbColonnes();
			}
		}
		return hmax;
	}

	private int largeurMax(List<Personnage> lPersonnage) {
		int lmax = 0;
		for (int i = 0; i < lPersonnage.size(); i++) {
			if (lPersonnage.get(i).getAutomate().getNbColonnes() > lmax) {
				lmax = lPersonnage.get(i).getAutomate().getNbLignes();
			}
		}
		return lmax;
	}

	public void placerPersonnageRandom(List<Personnage> lPersonnage) {
		Random rand = new Random();

		int posX, posY;
		int w = getCases().length;
		int h = getCases()[0].length;

		do {
			posX = rand.nextInt(w)*TILE_SIZE;
			posY = rand.nextInt(w)*TILE_SIZE;
		} while (getCases()[posX/TILE_SIZE][posY/TILE_SIZE].getNbPersonnage() != 0);

		lPersonnage.get(0).setX(posX);
		lPersonnage.get(0).setY(posY);

		for (int i = 1; i < lPersonnage.size(); i++) {
			do {
				posX = rand.nextInt(w)*TILE_SIZE;
				posY = rand.nextInt(h)*TILE_SIZE;
			} while (getCases()[posX/TILE_SIZE][posY/TILE_SIZE].getNbPersonnage() != 0 || personnagePresent(lPersonnage, posX, posY, i));

			lPersonnage.get(i).setX(posX);
			lPersonnage.get(i).setY(posY);
		}

	}

	private boolean personnagePresent(List<Personnage> lPersonnage, int posX, int posY, int i) {
		boolean present = false;
		for (int j = 0; j < i; j++) {
			if ((lPersonnage.get(j).getX() - posX) + (lPersonnage.get(j).getY() - posY) <= DISTANCE
					|| (lPersonnage.get(j).getX() - posX) + (lPersonnage.get(j).getY() - posY) <= DISTANCE) {
				present = true;
			}
		}

		return present;
	}

}
