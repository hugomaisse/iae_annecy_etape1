package org.iae.annecy.st1.etape1.controller;

import java.util.Scanner;

import org.iae.annecy.st1.etape1.model.produit.Catalogue;
import org.iae.annecy.st1.etape1.model.produit.Produit;



public class CatalogueControler {
	Catalogue cat;
	
	public CatalogueControler (Catalogue c){
		this.cat = c;
	}
	
	public String get(){
	return cat.afficherList();
		
	}
	
}
