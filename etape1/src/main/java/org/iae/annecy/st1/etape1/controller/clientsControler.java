package org.iae.annecy.st1.etape1.controller;

import org.iae.annecy.st1.etape1.model.person.Clients;
import org.iae.annecy.st1.etape1.model.produit.Catalogue;

public class clientsControler {
Clients clt;
	


	public clientsControler (Clients clts){
		this.clt = clts;
	}
	
	public String get(){
	return clt.afficherListperson();
		
	}
	
	
	
}
