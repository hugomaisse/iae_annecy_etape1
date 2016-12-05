package org.iae.annecy.st1.etape1.model.produit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Catalogue implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private ArrayList<Produit> produits = new ArrayList<Produit>();
	

	public ArrayList<Produit> getProduits() {
		return produits;
	}

	public void setProduits(ArrayList<Produit> produits) {
		this.produits = produits;
	}

	public void ajouterProduit(Produit p){
	this.produits.add(p);
	}
	public String afficherList(){
		String t = "";
		for (Produit produit : produits) {
			t += produit.afficherProd();
		}
		return t;
	
	}
	public Produit retrouveProduit(String reference){
		Iterator<Produit> it = this.getProduits().iterator();
		Produit prod = new Produit();
		while(it.hasNext()){
			Produit current = it.next();
			if(current.getRef().equals(reference)){
				prod = current;
				break;
			}
					
		}
			
		return prod;
	}
}

	


	

