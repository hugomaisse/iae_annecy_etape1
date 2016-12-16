package org.iae.annecy.st1.etape1.controller;

import org.iae.annecy.st1.etape1.model.person.Clients;

public class ClientsControler {
Clients clt;
	


	public ClientsControler (Clients clts){
		this.clt = clts;
	}
	
	public String get(){
	return clt.afficherListperson();
		
	}
	
	
	
}
