package pla;

import java.util.ArrayList;

public class Automate {
	private int tabEtatSuivant[][]; // [Decor_id][etat_courant_id]
	private int tabActionTransition[][]; // [Decor_id][etat_courant_id]
	private int tabAction[];// [etat_courant_id]
	private int posX; // position en abcisse sur la grille
	private int posY; // position en ordonn�es sur la grille
	private ArrayList<Etat> etats;
	private ArrayList<Transition> transitions;

	// automate par d�faut
	public Automate() {
		etats = new ArrayList<Etat>();
		transitions = new ArrayList<Transition>();
		posX = 5;
		posY = 6;
		initTabActionTransition(4);
		initTabEtatSuivant(getTaille());
	}

	public Automate(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		initTabActionTransition(4);
		initTabEtatSuivant(getTaille());
	}

	// remplissage du tableau des actions transitions en dur => a changer
	private void initTabActionTransition(int taille) {
		tabActionTransition = new int[taille][taille];
		tabActionTransition[0][0] = 0;
		tabActionTransition[0][1] = 1;
		tabActionTransition[0][2] = 3;
		tabActionTransition[0][3] = 1;
		tabActionTransition[1][0] = 1;
		tabActionTransition[1][1] = 0;
		tabActionTransition[1][2] = 0;
		tabActionTransition[1][3] = 0;
		tabActionTransition[2][0] = 0;
		tabActionTransition[2][1] = 0;
		tabActionTransition[2][2] = 0;
		tabActionTransition[2][3] = 1;
		tabActionTransition[3][0] = 4;
		tabActionTransition[3][1] = 4;
		tabActionTransition[3][2] = 4;
		tabActionTransition[3][3] = 0;
	}

	public int[][] getTabActionTransition() {
		return tabActionTransition;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getTaille() {
		return tabActionTransition[0].length;
	}

	public int getTailleColone(){
		return tabActionTransition.length;
	}

	// initialisation du tableau des etats suivants
	public void initTabEtatSuivant(int taille) {
		tabEtatSuivant = new int[taille][taille];
		tabEtatSuivant[0][0] = 0;
		tabEtatSuivant[0][1] = 3;
		tabEtatSuivant[0][2] = 0;
		tabEtatSuivant[0][3] = 3;
		tabEtatSuivant[1][0] = 0;
		tabEtatSuivant[1][1] = 1;
		tabEtatSuivant[1][2] = 2;
		tabEtatSuivant[1][3] = 3;
		tabEtatSuivant[2][0] = 3;
		tabEtatSuivant[2][1] = 1;
		tabEtatSuivant[2][2] = 2;
		tabEtatSuivant[2][3] = 2;
		tabEtatSuivant[3][0] = 1;
		tabEtatSuivant[3][1] = 2;
		tabEtatSuivant[3][2] = 0;
		tabEtatSuivant[3][3] = 3;
	}

	public void initTabAction() {
		/* A FAIRE */
	}

	public int[] getTabAction() {
		return tabAction;
	}
	
	 public void addEtat(Etat e) {
		 etats.add(e);
	 }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }
    
    public void afficher() {
        System.out.println("Etats :");
        for(int i=0; i<etats.size(); i++) {
            System.out.println("Etat " + i + " : " + etats.get(i).getActionEtat());
        }
        System.out.println();
        System.out.println();
        System.out.println("Transitions :");
        for(int i=0; i<transitions.size(); i++) {
            Transition t = transitions.get(i);
            System.out.println("Depart : " + t.getEtatDepart().getActionEtat());
            Condition c = t.getCondition();
            ConditionSimple c0 = c.getCondition(0);
            System.out.print("Condition : " + c0.getDecor() + "(" + c0.getCellule() + ")");
            for(int j=1; j<c.nombreConditions(); j++) {
                ConditionSimple cs = c.getCondition(j);
                System.out.print(" && " + cs.getDecor() + "(" + cs.getCellule() + ")");
            }
            System.out.println();
            System.out.println("Action transition : " + t.getActionTransition());
            System.out.println("Arrivee : " + t.getEtatArrivee().getActionEtat());
            System.out.println();
        }
    }
}
