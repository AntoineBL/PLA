package pla;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
	
	public Fenetre(){
		//D�finit un titre pour votre fen�tre
				this.setTitle("Ma premi�re fen�tre java");

				//D�finit une taille pour celle-ci ; ici, 400 px de
				this.setSize(1000, 1000);

				//Nous allons maintenant dire � notre objet de se positionner au centre
				this.setLocationRelativeTo(null);

				//Terminer le processus lorsqu'on clique sur fermer 
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
			
				//On pr�vient notre JFrame que ce sera notre JPanel qui sera son contentPane
				this.setContentPane(new Map());
				
				// Faire afficher la fenetre
				this.setVisible(true);
	}
}
