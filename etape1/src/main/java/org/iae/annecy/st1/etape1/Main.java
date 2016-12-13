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

import java.util.Scanner;

import org.iae.annecy.st1.common.mvc.BasicDataParam;
import org.iae.annecy.st1.common.mvc.ConsoleInputView;
import org.iae.annecy.st1.common.mvc.DataParam;
import org.iae.annecy.st1.common.mvc.DataView;
import org.iae.annecy.st1.common.mvc.StringView;
import org.iae.annecy.st1.etape1.controller.CatalogueControler;
import org.iae.annecy.st1.etape1.controller.MainController;
import org.iae.annecy.st1.etape1.controller.clientsControler;
import org.iae.annecy.st1.etape1.model.UserModel;
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
		Person pers1 = new Person(1, "MAISSE", "Hugo");
		clts.ajouterPerson(pers1);
		//mise en place d'une interface pour responsable produit responsable client et clients
		Scanner sc2 = new Scanner(System.in);

		int choixq;
		do{
			affichageMenuprincipal();
			int choixusers = sc2.nextInt();

			switch(choixusers){

			case  1:

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
				while(id == (clts.retrouvePerson(id).getId())){
					ConsoleHelper.display("L'ID est déja utilisé, rentrez une nouvel ID");
					id = sc3.nextInt();
				}
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
				System.out.println(clt.get());





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

				break;

				//Demande l'ajout d'une personne en une seul fois
				/*final ConsoleInputView customerCreateView = new PersonCreateFrenchView();
		customerCreateView.ask(scan);

		final DataView customerAddDataBulk = mainController.get("person:add", newCustomer);
		final StringView customerAddViewBulk = new PersonAddFrenchView();

		ConsoleHelper.display(customerAddViewBulk.build(customerAddDataBulk));*/

			case 2 :	

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

			case 3 :
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







	private static void initCustomerModel() {
		final PersonGetModel customerGetModel = new PersonGetModel();
		customerGetModel.register(mainController);

		final PersonAddModel customerAddModel = new PersonAddModel();
		customerAddModel.register(mainController);
	}
}



