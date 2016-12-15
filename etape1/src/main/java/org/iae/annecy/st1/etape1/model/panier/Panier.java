package org.iae.annecy.st1.etape1.model.panier;

import java.util.ArrayList;
import java.util.Iterator;

import org.iae.annecy.st1.etape1.model.produit.Produit;

public class Panier {
	private ArrayList<Produit> produitspanier = new ArrayList<Produit>();

	public ArrayList<Produit> getProduits() {
		return produitspanier;
	}

	
	public void setProduits(ArrayList<Produit> produits) {
		this.produitspanier = produits;
	}
	public void ajouterProduitpanier(Produit p){
		this.produitspanier.add(p);
		}
	
	public String afficherListpanier(){
		String t = "";
		for (Produit produit : produitspanier) {
			t += produit.afficherProdpanier();
		}
		return t;
	}
	public Produit retrouveProduitpanier(String reference){
		Iterator<Produit> iter = this.getProduits().iterator();
		Produit prod = new Produit();
		while(iter.hasNext()){
			Produit current = iter.next();
			if(current.getRef().equals(reference)){
				prod = current;
				break;
			}
					
		}
			
		return prod;
	}
	
	
}
