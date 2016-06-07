package pla;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pla.ihm.Map;

public class Jeu extends BasicGame {
	private Map map; // carte du jeu
	private List<Personnage> personnages; // Liste des personnages
	private GameContainer gc; // conteneur

	public Jeu(String titre) {
		super(titre); // Nom du jeu
		personnages = new ArrayList<Personnage>();
	}

	public void ajouterPersonnage(Personnage p) {
		if (p != null) {
			this.personnages.add(p);
		} else {
			System.out.println("Le personnage que vous voulez ajouter dans la liste des personnages est vide");
		}
	}

	public void supprimerPersonnage(Personnage p) {
		if (p != null && this.personnages.contains(p)) {
			this.personnages.remove(p);
		} else {
			System.out.println("Le personnage que vous voulez supprimer n'est pas dans la liste ou est nul");
		}
	}

	// Initialise le contenu du jeu, charge les graphismes, la musique, etc..
	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		this.gc = gc;
		// Cr�ation de la carte
		this.map = new Map();
		// Cr�ation des personnages
		ajouterPersonnage(new Personnage(Color.blue, 10, 10, "res/perso_bleu.gif"));
		ajouterPersonnage(new Personnage(Color.green, 20, 20, "res/perso_vert.png", new Automate(10, 10)));
		ajouterPersonnage(new Personnage(Color.black, 15, 15, "res/cop.png", new Automate(1, 1)));
	}

	// Affiche le contenu du jeu
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		dessinerCarte(g);
		dessinerElements(g);// dessine les automates et les personnages sur la carte
	}

	// Met � jour les �l�ments de la sc�ne en fonction du delta temps survenu.
	// C'est ici que la logique du jeu est enferm�.
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		// D�placement test du personnage bleu vers la droite
		deplacerPersonnage(0, 100);
	}

	// Arreter correctement le jeu en appuyant sur ECHAP
	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			gc.exit();
		}
		if (Input.KEY_P == key) {
			gc.setPaused(true);
		}
	}

	public void dessinerCarte(Graphics g) {
		this.map.paint(personnages, g);
	}

	public void dessinerElements(Graphics g) {
		// Pour chaque persoonage de la liste de personnages, le dessiner et
		// dessiner son automate
		for (Personnage p : personnages) {
			map.placerPersonnage(p, g);
			map.placerAutomate(p.getAutomate(), p.getCouleur(), g);
		}
	}

	public void deplacerPersonnage(int indexPerso, int pause) {

		// Chercher le personnage correspondant � l'indexPerso
		Personnage p = personnages.get(indexPerso);
		// Prendre sa couleur et ses coordonn�es
		Color c = p.getCouleur();
		int coordI = p.getPosX();
		int coordJ = p.getPosY();
		// La case sur lequel le personnage �tait doit revenir � son etat d'origine
		map.modifierDecorCase(coordI, coordJ, getImageParCouleur(c));
		// On enleve le personnage p a la liste des personnages de la case que le personnage s'apprete � quitter 
		map.getCases()[coordI][coordJ].supprimerPersonnage(p);
		// On met � jour les coordonn�es du personnage
		personnages.get(indexPerso).deplacerX(map.getLargeur());
		// Pause
		try {
			Thread.sleep(pause); // latence
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Image getImageParCouleur(Color c) {
		try {
			if (c == Color.blue) {
				return new Image("res/sol_bleu.jpg");
			} else if (c == Color.green) {
				return new Image("res/sol_vert.jpg");
			} else {
				return null;
			}
		} catch (SlickException e) {
			System.out.println("Une image n'a pas pu �tre charg�e");
		}
		return null;
	}
}