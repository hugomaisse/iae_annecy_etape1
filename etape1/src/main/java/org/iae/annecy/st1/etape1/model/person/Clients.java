package org.iae.annecy.st1.etape1.model.person;

import java.util.ArrayList;
import java.util.Iterator;

import org.iae.annecy.st1.etape1.model.produit.Produit;



public class Clients implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Person> persons = new ArrayList<Person>();

	public ArrayList<Person> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}
	
	public void ajouterPerson(Person pers){
		this.persons.add(pers);
		}
	
	
	public String afficherListperson(){
		String t = "";
		for (Person person : persons) {
			t += person.afficherPerson();
		}
		return t;
	
	}
	
	
	public Person retrouvePerson(int ids){
		Iterator<Person> ite = this.getPersons().iterator();
		Person pers = new Person();
		while(ite.hasNext()){
			Person current = ite.next();
			if(current.getId().equals(ids)){
				pers = current;
				break;
			}
					
		}
		return pers;
	}
}
