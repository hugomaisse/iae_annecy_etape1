/**
 * 
 */

package org.iae.annecy.st1.etape1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.iae.annecy.st1.common.mvc.DataView;
import org.iae.annecy.st1.common.mvc.StringView;
import org.iae.annecy.st1.etape1.controller.CatalogueControler;
import org.iae.annecy.st1.etape1.controller.MainController;
import org.iae.annecy.st1.etape1.model.UserModel;
import org.iae.annecy.st1.etape1.model.produit.Catalogue;
import org.iae.annecy.st1.etape1.model.produit.Produit;
import org.iae.annecy.st1.etape1.view.UserTextFrenchView;
import org.iae.annecy.st1.tools.ConsoleHelper;





public class Main {

	/**
	 * COntroller pemetant le traitement des actions d'exemple.
	 */
	private static MainController mainController;
	
	static {
		Main.mainController = new MainController();
	}

	/**
	 * Lance l'application.
	 * 
	 * @param args
	 *            command line parameters
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(final String[] args) throws IOException, ClassNotFoundException  {
		initUserModel();

		final DataView userData = mainController.get("user:display");
		final StringView userView = new UserTextFrenchView();

		ConsoleHelper.display(userView.build(userData));
		
		Catalogue c = new Catalogue(); 
		Produit p1 = new Produit("carotte","1", "orange", "provenant de France avec une agriculture bio", 5);
		Produit p2 = new Produit("pomme","2", "jaune","provenant d'Espagne avec une agriculture intensive", 3);
		c.ajouterProduit(p1);
		c.ajouterProduit(p2);

		Scanner sc = new Scanner(System.in);
		int choixMenu = 0;
		int choixQuit = 0;
		String chProd = null;
	
		
		try {
		  	 File fichier = new File ("catalogue");
	         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
	         c = (Catalogue) ois.readObject();
	     
		}catch(FileNotFoundException e){
			c = new Catalogue();
			c.ajouterProduit(p1);
			c.ajouterProduit(p2);
	    }
		
		
		do{
			affichageMenu();
			choixMenu = sc.nextInt();
				while(choixMenu != 1 && choixMenu != 2 && choixMenu != 3){
					System.out.println("erreur veuillez rentrer (1=modif/2=Afficher/3=ajouter)");
					choixMenu = sc.nextInt();
				}
			if(choixMenu == 1){
				
				System.out.println("quel produit voulez vous modifier ? (référence produit)");
				CatalogueControler cat = new CatalogueControler(c);
				System.out.println(cat.get());
				//c.afficherList();
				//chProd = sc.next();
				/*while(chProd > c.getProduits().size()){
					System.out.println("erreur veuillez rentrer un article valide dans la liste suivante :");
					c.afficherList();*/
					chProd = sc.next();
				
				
				
				
				System.out.println("quel attribut changer ?"+  "\n 1=description court" + "\n 2=description longue" +"\n 3=prix"+"\n 4=nom");
				int chAtt = sc.nextInt();
					while(chAtt != 1 && chAtt !=2 && chAtt != 3 && chAtt != 4){ 
						System.out.println("erreur veuillez rentrer \n 1=description" + "\n 2=description longue" +"\n 3=prix"+"\n 4=nom");
						chAtt = sc.nextInt();
					}
				sc.nextLine();
				switch(chAtt){
				
				case 1:
					
					System.out.println("quelle est la nouvelle description courte ?");
					c.retrouveProduit(chProd).setDesc(sc.nextLine());
					
					
				break;
				case 2:
					
					System.out.println("quelle est la nouvelle description longue ?");
					
					c.retrouveProduit(chProd).setDescLong(sc.nextLine());
				break;
				case 3:
					System.out.println("quel est le prix ?");
					c.retrouveProduit(chProd).setPrix(sc.nextInt());
				break;
				case 4:
					System.out.println("quel est le nouveau nom ?");
					c.retrouveProduit(chProd).setNom(sc.next());
				}	
			
				
			}else if(choixMenu == 2){
				CatalogueControler cat = new CatalogueControler(c);
				System.out.println(cat.get());
				
				
				
				
			}else{	
				Scanner sc1 = new Scanner(System.in);
				System.out.println("Quelle est le nom du produit ?");
				String nom = sc1.next();
				System.out.println("\t\t Quelle est la reférence ?");
				String ref = sc1.next();
					while(ref.equals(c.retrouveProduit(ref).getRef())){
						System.out.println("\t\t la reférence est déja utilisée, rentrez une nouvelle reférence");
						ref = sc1.next();
					}
				System.out.println("\t\t Quelle est la description courte ?");
				String desc = sc1.nextLine();
				sc1.nextLine();
				System.out.println("\t\t Quelle est la description longue ?");
				String descLong = sc1.nextLine();
				System.out.println("\t\t Quelle est le prix ?");
				int prix = sc1.nextInt();
					while(prix<= 0){
						System.out.println("Le prix est négatif !");
						prix = sc1.nextInt();
					}
				new Produit(nom,ref, desc, descLong, prix);
				c.ajouterProduit(new Produit(nom,ref, desc,descLong, prix));
			
					
				CatalogueControler cat = new CatalogueControler(c);
				System.out.println(cat.get());
			}	
			
			System.out.println("voulez-vous revenir au menu principal ? (1=oui/2=non)");
			choixQuit = sc.nextInt();
			try{
				File fichier = new File("catalogue");
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
				oos.writeObject(c);
			}catch (IOException ioe){
				ioe.printStackTrace();
			}
			
				while(choixQuit != 1 && choixQuit !=2){
					System.out.println("erreur veuillez rentrer (1=oui/2=non)");
					choixQuit = sc.nextInt();
				}
			
		}while(choixQuit == 1);
		}
		
	
	
	
		
	

	private static void initUserModel() {
		final UserModel userModel = new UserModel();
		userModel.register(mainController);
	}
	
	public static void affichageMenu(){
		System.out.println("    *******MENU*******\n"
				+ "1. Modifier un produit\n"
				+ "2. Afficher liste des produits\n"
				+ "3. Ajouter un produit");
		
	}
	
	}
	
	
	
	
	



