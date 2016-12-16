package org.iae.annecy.st1.etape1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.iae.annecy.st1.common.mvc.DataView;
import org.iae.annecy.st1.common.mvc.StringView;
import org.iae.annecy.st1.etape1.controller.CatalogueControler;
import org.iae.annecy.st1.etape1.controller.MainController;
import org.iae.annecy.st1.etape1.controller.PanierController;
import org.iae.annecy.st1.etape1.controller.ClientsControler;
import org.iae.annecy.st1.etape1.model.UserModel;
import org.iae.annecy.st1.etape1.model.panier.Panier;
import org.iae.annecy.st1.etape1.model.person.Clients;
import org.iae.annecy.st1.etape1.model.person.Person;
import org.iae.annecy.st1.etape1.model.person.PersonAddModel;
import org.iae.annecy.st1.etape1.model.person.PersonGetModel;
import org.iae.annecy.st1.etape1.model.produit.Catalogue;
import org.iae.annecy.st1.etape1.model.produit.Produit;
import org.iae.annecy.st1.etape1.view.UserTextFrenchView;
import org.iae.annecy.st1.tools.ConsoleHelper;

public class Main {

	static Scanner sc = new Scanner(System.in);
	private static MainController mainController;
	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;
	private static ObjectOutputStream oos2;
	private static ObjectInputStream ois2;
	private static ObjectOutputStream oos3;



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

	public static void main(final String[] args) throws IOException, ClassNotFoundException {
		initUserModel();
		initCustomerModel();

		final DataView userData = mainController.get("user:display");
		final StringView userView = new UserTextFrenchView();

		ConsoleHelper.display(userView.build(userData));

		Catalogue c = new Catalogue();
		Produit p1 = new Produit("carotte", "1", "orange", "provenant de France avec une agriculture bio", 5);
		Produit p2 = new Produit("pomme", "2", "jaune", "provenant d'Espagne avec une agriculture intensive", 3);
		c.ajouterProduit(p1);
		c.ajouterProduit(p2);
		Clients clts = new Clients();
		Panier pan = new Panier();
		Person pers1 = new Person(1, "MAISSE", "Hugo");
		clts.ajouterPerson(pers1);

		// mise en place d'une interface pour responsable produit responsable
		// client et clients


		int choixq;
		do {
			affichageMenuprincipal();
			int choixusers = sc.nextInt();

			switch (choixusers) {

			case 1: // Menu responsable client
				try {
					File file = new File("clients");
					ois = new ObjectInputStream(new FileInputStream(file));
					clts = (Clients) ois.readObject();

				} catch (FileNotFoundException e) {
					clts = new Clients();
					clts.ajouterPerson(pers1);
				}
				affichageresponsableclient();
				int choixClt = sc.nextInt();
				switch (choixClt) {

				case 1: // création client
					try {
						File file = new File("clients");
						ois = new ObjectInputStream(new FileInputStream(file));
						clts = (Clients) ois.readObject();

					} catch (FileNotFoundException e) {
						clts = new Clients();
						clts.ajouterPerson(pers1);
					}


					ConsoleHelper.display("Quelle est l'ID du client ?");
					int id = sc.nextInt();
					/*
					 * while(id == (clts.retrouvePerson(id).getId())){
					 * ConsoleHelper.
					 * display("L'ID est déja utilisé, rentrez une nouvel ID");
					 * id = sc3.nextInt(); }
					 */

					if (clts.retrouvePerson(id) != null) {
						ConsoleHelper.display("L'ID est déja utilisé");
					} else {
						ConsoleHelper.display("Quelle est le nom du client ?");
						String nom1 = sc.next();
						ConsoleHelper.display("Quelle est le prénom du client ?");
						String prenom = sc.next();
						new Person(id, nom1, prenom);
						clts.ajouterPerson(new Person(id, nom1, prenom));

						try {
							File file = new File("clients");
							oos = new ObjectOutputStream(new FileOutputStream(file));
							oos.writeObject(clts);
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
						ClientsControler clt = new ClientsControler(clts);
						ConsoleHelper.display(clt.get());
					}
					break;

				case 2: // modification client

					ConsoleHelper.display("quel client voulez vous modifier ? (ID)");//id = 1,2,3 ...
					ClientsControler cl = new ClientsControler(clts);
					ConsoleHelper.display(cl.get());
					int chClient = sc.nextInt();
					while (chClient > clts.getPersons().size()) {
						ConsoleHelper.display("erreur veuillez rentrer un client valide dans la liste suivante :");
						c.afficherList();
						chClient = sc.nextInt();
					}

					ConsoleHelper.display("quel attribut changer ?" + "\n 1=Nom" + "\n 2=Prénom");
					int chAtts = sc.nextInt();
					while (chAtts != 1 && chAtts != 2) {
						ConsoleHelper.display("erreur veuillez rentrer \n 1=Nom" + "\n 2=Prénom");
						chAtts = sc.nextInt();
					}

					switch (chAtts) {//choix des attributs a modifier
					case 1:

						ConsoleHelper.display("quel est le nouveau Nom ?");
						clts.retrouvePerson(chClient).setNom(sc.next());
						break;
					case 2:

						ConsoleHelper.display("quel est le nouveau prénom ?");

						clts.retrouvePerson(chClient).setPrenom(sc.next());
						break;
					}
					break;

				case 3:// affichage list client

					ClientsControler client = new ClientsControler(clts);
					ConsoleHelper.display(client.get());
					break;
				}
				try {
					File file = new File("clients");
					oos2 = new ObjectOutputStream(new FileOutputStream(file));
					oos2.writeObject(clts);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				break;


			case 2: // Menu Responsable produit


				int choixMenu = 0;
				int choixQuit = 0;
				String chProd = null;

				try {
					File fichier = new File("catalogue");
					ois2 = new ObjectInputStream(new FileInputStream(fichier));
					c = (Catalogue) ois2.readObject();

				} catch (FileNotFoundException e) {
					c = new Catalogue();
					c.ajouterProduit(p1);
					c.ajouterProduit(p2);
				}

				do {
					affichageMenu();
					choixMenu = sc.nextInt();
					while (choixMenu != 1 && choixMenu != 2 && choixMenu != 3) {
						ConsoleHelper.display("erreur veuillez rentrer (1=modif/2=Afficher/3=ajouter)");
						choixMenu = sc.nextInt();
					}
					if (choixMenu == 1) {//modification d'un produit

						ConsoleHelper.display("quel produit voulez vous modifier ? (référence produit)");
						CatalogueControler cat = new CatalogueControler(c);
						ConsoleHelper.display(cat.get());

						chProd = sc.next();

						while (!chProd.equals(c.retrouveProduit(chProd).getRef())) {
							ConsoleHelper.display("la reférence est inconnue, rentrez une nouvelle reférence");
							chProd = sc.next();
						}

						ConsoleHelper.display("quel attribut changer ?" + "\n 1=description courte"
								+ "\n 2=description longue" + "\n 3=prix" + "\n 4=nom");
						int chAtt = sc.nextInt();
						while (chAtt != 1 && chAtt != 2 && chAtt != 3 && chAtt != 4) {
							ConsoleHelper.display("erreur veuillez rentrer \n 1=description" + "\n 2=description longue"
									+ "\n 3=prix" + "\n 4=nom");
							chAtt = sc.nextInt();
						}
						sc.nextLine();
						switch (chAtt) {
						case 1:

							ConsoleHelper.display("quelle est la nouvelle description courte ?");
							c.retrouveProduit(chProd).setDesc(sc.nextLine());
							break;
						case 2:

							ConsoleHelper.display("quelle est la nouvelle description longue ?");

							c.retrouveProduit(chProd).setDescLong(sc.nextLine());
							break;
						case 3:
							ConsoleHelper.display("quel est le prix ?");
							c.retrouveProduit(chProd).setPrix(sc.nextInt());
							break;
						case 4:
							ConsoleHelper.display("quel est le nouveau nom ?");
							c.retrouveProduit(chProd).setNom(sc.next());
						}

					} else if (choixMenu == 2) {//choix afficher liste
						CatalogueControler cat = new CatalogueControler(c);
						ConsoleHelper.display(cat.get());

					} else {//choix ajouter un produit

						ConsoleHelper.display("Quelle est le nom du produit ?");
						String nom = sc.next();
						ConsoleHelper.display("Quelle est la reférence ?");
						String ref = sc.next();
						while (ref.equals(c.retrouveProduit(ref).getRef())) {
							ConsoleHelper
							.display("la reférence est déja utilisée, rentrez une nouvelle reférence");
							ref = sc.next();
						}
						ConsoleHelper.display("Quelle est la description courte ?");
						String desc = sc.nextLine();
						sc.nextLine();
						ConsoleHelper.display("Quelle est la description longue ?");
						String descLong = sc.nextLine();
						ConsoleHelper.display("Quelle est le prix ?");
						int prix = sc.nextInt();
						while (prix <= 0) {
							ConsoleHelper.display("Le prix est négatif !");
							prix = sc.nextInt();
						}
						new Produit(nom, ref, desc, descLong, prix);
						c.ajouterProduit(new Produit(nom, ref, desc, descLong, prix));

						CatalogueControler cat = new CatalogueControler(c);
						ConsoleHelper.display(cat.get());
					}

					ConsoleHelper.display("voulez-vous revenir au menu principal ? (1=oui/2=non)");
					choixQuit = sc.nextInt();

					try {
						File fichier = new File("catalogue");
						oos3 = new ObjectOutputStream(new FileOutputStream(fichier));
						oos3.writeObject(c);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}

					while (choixQuit != 1 && choixQuit != 2) {
						ConsoleHelper.display("erreur veuillez rentrer (1=oui/2=non)");
						choixQuit = sc.nextInt();
					}

				} while (choixQuit == 1);//quite l'appli
				break;

			case 3: // menu clients
				int chaJout = 0;
				int chCo = 0;
				int qt = 0;
				int prix = 0;
				int prixTot = 0;
				int code1 = 0;
				int pourcent = 0;
				double prixPromo = 0;
				double prixCal = 0;
				double px = 0;
				double prixCaltot = 0;

				do {
					CatalogueControler cat = new CatalogueControler(c);
					ConsoleHelper.display(cat.get());
					ConsoleHelper.display("quel produit voulez vous ajouter à votre panier ?");
					String choixProduitpanier = sc.next();
					pan.ajouterProduitpanier(c.retrouveProduit(choixProduitpanier));
					ConsoleHelper.display("quelle quantitée ?");
					qt = sc.nextInt();
					pan.retrouveProduitpanier(choixProduitpanier).setQuant(qt);
					ConsoleHelper.display("    *******PANIER*******");
					PanierController pan1 = new PanierController(pan);
					ConsoleHelper.display(pan1.get());
					

					prix = (pan.retrouveProduitpanier(choixProduitpanier).getPrix()) * qt;
					prixTot = prixTot + prix;
					ConsoleHelper.display("prix total du panier : " + prixTot);
					ConsoleHelper.display("ajouter d'autre produit ? (1=OUI/2=NON)");
					chaJout = sc.nextInt();

				} while (chaJout == 1);
				ConsoleHelper.display("avez-vous un code promo ? 1=OUI/2=NON");
				code1 = sc.nextInt();
				while (code1 != 1 && code1 != 2) {
					ConsoleHelper.display("erreur veuillez rentrer (1=oui/2=non)");
					code1 = sc.nextInt();
				}
				switch (code1) {
				case 1:
					ConsoleHelper.display("quelle est votre pourcentage de promotion ?");
					pourcent = sc.nextInt();
					while(pourcent < 0 || pourcent > 100){
						ConsoleHelper.display("le pourcentage n'est pas valide, nouveaux pourcentage ?");
						pourcent = sc.nextInt();
					}
					px = pourcent;
					prixCal = (px / 100);
					prixPromo = prixTot*prixCal;
					prixCaltot = prixTot-prixPromo;
					ConsoleHelper.display("prix total du panier  avec promo : " + prixCaltot);
					break;
				case 2:
					ConsoleHelper.display("prix total du panier : " +prixTot);
					break;
				}

				ConsoleHelper.display("Voulez vous mettre votre panier en commande ? (1=OUI/2=NON)");
				chCo = sc.nextInt();
				while (chCo != 1 && chCo != 2) {
					ConsoleHelper.display("erreur veuillez rentrer (1=oui/2=non)");
					chCo = sc.nextInt();
				}
				if (chCo == 1) {
					ConsoleHelper.display("***Création de la Commande***");
				} else {
					ConsoleHelper.display("***Abandon de la commande***");
				}
				break;
			}

			ConsoleHelper.display("voulez-vous revenir au ***Menu USER*** ? (1=oui/2=non)");
			choixq = sc.nextInt();
			while (choixq != 1 && choixq != 2) {
				ConsoleHelper.display("erreur veuillez rentrer (1=oui/2=non)");
				choixq = sc.nextInt();
			}
		} while (choixq == 1);
	}

	private static void initUserModel() {
		final UserModel userModel = new UserModel();
		userModel.register(mainController);
	}

	public static void affichageMenu() {
		ConsoleHelper.display("    *******MENU*******\n" + "1. Modifier un produit\n"
				+ "2. Afficher liste des produits\n" + "3. Ajouter un produit");

	}

	public static void affichageMenuprincipal() {
		ConsoleHelper.display("    *******Choix User*******\n" + "1. Menu responsable clientèle\n"
				+ "2. Menu responsable produit	\n" + "3. Menu clientèle");

	}

	public static void affichageresponsableclient() {
		ConsoleHelper.display("    *******Choix Responsable clts*******\n" + "1. Création client\n"
				+ "2. Modification client	\n" + "3. Affichage clients");

	}

	private static void initCustomerModel() {
		final PersonGetModel customerGetModel = new PersonGetModel();
		customerGetModel.register(mainController);

		final PersonAddModel customerAddModel = new PersonAddModel();
		customerAddModel.register(mainController);
	}
}
