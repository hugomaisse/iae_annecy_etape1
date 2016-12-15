package org.iae.annecy.st1.etape1.model.produit;

import java.io.Serializable;

public class Produit implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ref;
	private String desc;
	private int prix;
	private String nom;
	private String descLong;
	private int quant ;
	private int px;

	

	
	
	public String getDescLong() {
		return descLong;
	}
	public void setDescLong(String descLong) {
		this.descLong = descLong;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public Produit (String nm, String rf, String des, String descL, int px){
		this.nom= nm;
		this.ref= rf;
		this.desc= des;
		this.descLong= descL;
		this.prix= px;
		
	}
	public Produit(){
		
	}
	
	public String afficherProd(){
		String t = "";
		t += ("\n ***" + this.getNom() +"***" + " \n ref: "  + this.getRef() + "\n description courte: " + this.getDesc() + "\n description longue: " + this.getDescLong() + "\n de prix : "+ this.getPrix());
		return t;
	}
	public String afficherProdpanier(){
		String t = "";
		t += ("\n ***" + this.getNom() +"***" + " prix unitaire : "  + this.getPrix() + " prix produits: " + this.getPrix()*this.getQuant()); 
		return t;
	}
	public int getQuant() {
		return quant;
	}
	public void setQuant(int quant) {
		this.quant = quant;
	}
	public void prixTot(){
		for( int i=0; i< quant ;i++){
			px = (this.getPrix()*this.getQuant()); 
		}
	}
}
