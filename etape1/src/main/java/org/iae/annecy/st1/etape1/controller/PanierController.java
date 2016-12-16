package org.iae.annecy.st1.etape1.controller;

import org.iae.annecy.st1.etape1.model.panier.Panier;

public class PanierController {
	Panier pan;
	
	public PanierController (Panier p){
		this.pan = p;
	}
	
	public String get(){
	return pan.afficherListpanier();
		
	}
	public void panierClient(){
		
	}
}
