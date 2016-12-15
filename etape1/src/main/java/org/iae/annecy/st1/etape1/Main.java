/**
 * 
 */

package org.iae.annecy.st1.etape1;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Scanner;

import org.iae.annecy.st1.common.mvc.BasicDataParam;
import org.iae.annecy.st1.common.mvc.ConsoleInputView;
import org.iae.annecy.st1.common.mvc.DataParam;
import org.iae.annecy.st1.common.mvc.DataView;
import org.iae.annecy.st1.common.mvc.StringView;
import org.iae.annecy.st1.etape1.controller.CatalogueControler;
import org.iae.annecy.st1.etape1.controller.MainController;
import org.iae.annecy.st1.etape1.controller.PanierController;
import org.iae.annecy.st1.etape1.controller.clientsControler;
import org.iae.annecy.st1.etape1.model.UserModel;
import org.iae.annecy.st1.etape1.model.panier.Panier;
import org.iae.annecy.st1.etape1.model.produit.Catalogue;
import org.iae.annecy.st1.etape1.model.produit.Produit;
import org.iae.annecy.st1.etape1.model.person.Clients;
import org.iae.annecy.st1.etape1.model.person.Person;
import org.iae.annecy.st1.etape1.model.person.PersonAddModel;
import org.iae.annecy.st1.etape1.model.person.PersonGetModel;
import org.iae.annecy.st1.etape1.view.UserTextFrenchView;
import org.iae.annecy.st1.etape1.view.person.PersonAddFrenchView;
import org.iae.annecy.st1.etape1.view.person.PersonCreateFrenchView;
import org.iae.annecy.st1.etape1.view.person.PersonGetFrenchView;
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
		initCustomerModel();

		final Scanner scan = new Scanner(System.in, "UTF-8");

		final DataView userData = mainController.get("user:display");
		final StringView userView = new UserTextFrenchView();

		ConsoleHelper.display(userView.build(userData));

		Catalogue c = new Catalogue(); 
		Produit p1 = new Produit("carotte","1", "orange", "provenant de France avec une agriculture bio", 5);
		Produit p2 = new Produit("pomme","2", "jaune","provenant d'Espagne avec une agriculture intensive", 3);
		c.ajouterProduit(p1);
		c.ajouterProduit(p2);
		Clients clts = new Clients();
		Panier pan = new Panier();
		Person pers1 = new Person(1, "MAISSE", "Hugo");
		clts.ajouterPerson(pers1);
		//mise en place d'une interface pour responsable produit responsable client et clients
		Scanner sc2 = new Scanner(System.in);
		Scanner sc5 = new Scanner(System.in);

		int choixq;
		do{
			affichageMenuprincipal();
			int choixusers = sc2.nextInt();

			switch(choixusers){

			case  1: //Menu responsable client
				try {
					File file = new File ("clients");
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
					clts = (Clients) ois.readObject();

				}catch(FileNotFoundException e){
					clts = new Clients();
					clts.ajouterPerson(pers1);
				}
				affichageresponsableclient();
				int choixclt = sc5.nextInt();
				switch(choixclt){
				case 1 :  //création client
					try {
						File file = new File ("clients");
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
						clts = (Clients) ois.readObject();

					}catch(FileNotFoundException e){
						clts = new Clients();
						clts.ajouterPerson(pers1);
					}


					Scanner sc3 = new Scanner(System.in);
					ConsoleHelper.display("Quelle est l'ID du client ?");
					int id = sc3.nextInt();
					/*while(id == (clts.retrouvePerson(id).getId())){
						ConsoleHelper.display("L'ID est déja utilisé, rentrez une nouvel ID");
						id = sc3.nextInt();
					}*/

					if(clts.retrouvePerson(id) != null){
						ConsoleHelper.display("L'ID est déja utilisé");
					}else {
						ConsoleHelper.display("Quelle est le nom du client ?");
						String nom1 = sc3.next();
						ConsoleHelper.display("Quelle est le prénom du client ?");
						String prénom = sc3.next();
						new Person(id, nom1, prénom);
						clts.ajouterPerson(new Person(id, nom1, prénom));


						try{
							File file = new File("clients");
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
							oos.writeObject(clts);
						}catch (IOException ioe){
							ioe.printStackTrace();
						}
						clientsControler clt = new clientsControler(clts);
						ConsoleHelper.display(clt.get());
					}
					break;

				case 2 : //modification client


					ConsoleHelper.display("quel client voulez vous modifier ? (ID)");
					clientsControler cl = new clientsControler(clts);
					ConsoleHelper.display(cl.get());
					Scanner sc6 = new Scanner(System.in);
					int chClient = sc6.nextInt();
					while(chClient > clts.getPersons().size()){
						ConsoleHelper.display("erreur veuillez rentrer un client valide dans la liste suivante :");
						c.afficherList();

						chClient = sc6.nextInt();
					}

					ConsoleHelper.display("quel attribut changer ?"+  "\n 1=Nom" + "\n 2=Prénom");
					int chAtts = sc6.nextInt();
					while(chAtts != 1 && chAtts !=2){ 
						ConsoleHelper.display("erreur veuillez rentrer \n 1=Nom" + "\n 2=Prénom");
						chAtts = sc6.nextInt();
					}

					switch(chAtts){
					case 1:

						System.out.println("quel est le nouveau Nom ?");
						clts.retrouvePerson(chClient).setNom(sc6.next());
						break;
					case 2:

						System.out.println("quel est le nouveau prénom ?");

						clts.retrouvePerson(chClient).setPrenom(sc6.next());
						break;
					}
					break;


				case 3 ://affichage list client

					clientsControler client = new clientsControler(clts);
					ConsoleHelper.display(client.get());
					break ;
				}
				try{
					File file = new File("clients");
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
					oos.writeObject(clts);
				}catch (IOException ioe){
					ioe.printStackTrace();
				}
				break;









				// get a Person
				/*DataParam searchPersonParam = new BasicDataParam();
				searchPersonParam.add("id", "12"); //0-5 inconu, 5-10 TEST, >10 DERUETTE
				final DataView customerData = mainController.get("person:get", searchPersonParam);
				final StringView customerGetView = new PersonGetFrenchView();

				ConsoleHelper.display(customerGetView.build(customerData));*/

				//demande l'ajout d'une personne attribut/attribut
				/*DataParam newCustomer = new BasicDataParam();
				String personId = ConsoleHelper.read(scan, "Quel est l'ID du client ?");
				newCustomer.add("id", personId); // <100 = OK, sinon KO
				String personNom = ConsoleHelper.read(scan, "Quel est le nom du client ?");
				newCustomer.add("nom", personNom);
				String personPrenom = ConsoleHelper.read(scan, "Quel est le prénom du client ?");
				newCustomer.add("prenom", personPrenom);

				final DataView customerAddData = mainController.get("person:add", newCustomer);
				final StringView customerAddView = new PersonAddFrenchView();

				ConsoleHelper.display(customerAddView.build(customerAddData));*/



				//Demande l'ajout d'une personne en une seul fois
				/*final ConsoleInputView customerCreateView = new PersonCreateFrenchView();
		customerCreateView.ask(scan);

		final DataView customerAddDataBulk = mainController.get("person:add", newCustomer);
		final StringView customerAddViewBulk = new PersonAddFrenchView();

		ConsoleHelper.display(customerAddViewBulk.build(customerAddDataBulk));*/

			case 2 :	//Menu Responsable produit

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


						while(!chProd.equals(c.retrouveProduit(chProd).getRef())) {
							System.out.println("\t\t la reférence est inconnue, rentrez une nouvelle reférence");
							chProd = sc.next();
						}


						ConsoleHelper.display("quel attribut changer ?"+  "\n 1=description courte" + "\n 2=description longue" +"\n 3=prix"+"\n 4=nom");
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
				break;

			case 3 : //menu clients
				int chajout = 0;
				Scanner scpanier = new Scanner(System.in);
				int chco = 0;
				int qt = 0;
				int prix=0;
				int prixtot=0;
				do{
					CatalogueControler cat = new CatalogueControler(c);
					ConsoleHelper.display(cat.get());
					ConsoleHelper.display("quel produit voulez vous ajouter à votre panier ?");
					String choixProduitpanier = scpanier.next();
					pan.ajouterProduitpanier(c.retrouveProduit(choixProduitpanier));
					ConsoleHelper.display("quelle quantitée ?");
					qt = scpanier.nextInt();
					pan.retrouveProduitpanier(choixProduitpanier).setQuant(qt);
					ConsoleHelper.display("    *******PANIER*******\n");
					PanierController pan1 = new PanierController(pan);
					ConsoleHelper.display(pan1.get());
					
					prix = (pan.retrouveProduitpanier(choixProduitpanier).getPrix())*qt;
					prixtot = prixtot +prix;
					ConsoleHelper.display("ajouter d'autre produit ? (1=OUI/2=NON)");
					chajout = scpanier.nextInt();
					
				}while(chajout == 1);
				
				ConsoleHelper.display("prix total du panier : "+ prixtot);
				ConsoleHelper.display("Voulez vous mettre votre panier en commande ? (1=OUI/2=NON)");
				chco = scpanier.nextInt();
				if(chco == 1){
					ConsoleHelper.display("***Création de la Commande***");
				}else{
					ConsoleHelper.display("***Abandon de la commande***");
				}
				break;
			}
			Scanner sc4 = new Scanner(System.in);
			System.out.println("voulez-vous revenir au menu choix responsable ? (1=oui/2=non)");
			choixq = sc4.nextInt();
			while(choixq != 1 && choixq !=2){
				ConsoleHelper.display("erreur veuillez rentrer (1=oui/2=non)");
				choixq = sc4.nextInt();
			}
		}while(choixq == 1);	
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

	public static void affichageMenuprincipal(){
		System.out.println("    *******Choix User*******\n"
				+ "1. Menu responsable clientèle\n"
				+ "2. Menu responsable produit	\n"
				+ "3. Menu clientèle");

	}

	public static void affichageresponsableclient(){
		ConsoleHelper.display("    *******Choix Responsable clts*******\n"
				+ "1. Création client\n"
				+ "2. Modification client	\n"
				+ "3. Affichage clients");

	}





	private static void initCustomerModel() {
		final PersonGetModel customerGetModel = new PersonGetModel();
		customerGetModel.register(mainController);

		final PersonAddModel customerAddModel = new PersonAddModel();
		customerAddModel.register(mainController);
	}
}



