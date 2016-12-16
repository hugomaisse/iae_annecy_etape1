package org.iae.annecy.st1.etape1.controller;

import org.iae.annecy.st1.etape1.model.produit.Catalogue;



public class CatalogueControler {
	Catalogue cat;
	
	public CatalogueControler (Catalogue c){
		this.cat = c;
	}
	
	public String get(){
	return cat.afficherList();
		
	}
	
}
