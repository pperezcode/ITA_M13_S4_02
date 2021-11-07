package com.httpServiceEmpleatsApp.model.entity;

import javax.persistence.Entity;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Empleat {
	
	private int id;
	private String nom;
	private TipusFeina feina;
	private double salari;
	private MultipartFile imatge;
	private String imagePath;
	
	public Empleat(String nom, TipusFeina feina) {
		this.id = -1;
		this.nom = nom;
		this.setFeina(feina);
		this.imagePath = ".//src//main//resources//static//files//" + id + "//";
	}
	
	// Getters i Setters

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public TipusFeina getFeina() {
		return feina;
	}
	
	public void setFeina(TipusFeina feina) {	// Al definir la feina s'assigna el salari
		this.feina = feina;
		
		if (feina == null) {
			this.salari = 0;
		} else if (feina.equals(TipusFeina.ADMINISTRATIU)) {
			this.salari = 18000;
			
		} else if (feina.equals(TipusFeina.MANAGER)) {
			this.salari = 24000;
			
		} else if (feina.equals(TipusFeina.DIRECTIU)) {
			this.salari = 40000;
			
		} else if (feina.equals(TipusFeina.CEO)) {
			this.salari = 60000;
			
		}
	}
	
	public double getSalari() {
		return salari;
	}
	
	public void setSalari(double salari) {
		this.salari = salari;
	}
	
	public MultipartFile getImatge() {
		return imatge;
	}
	
	public void setImatge(MultipartFile file) {
		this.imatge = file;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}	
	
	// Mètode sobreescrit
	
	@Override
	public String toString() {
		return "Empleat [id=" + id + ", nom=" + nom + ", feina=" + feina + ", salari=" + salari +  ", imatge=" + imatge.getContentType() + ", path=" + imagePath + "]";
	}
	
	// Mètode propi de la classe
	
	public boolean isValid() {
		return this.nom != null && !this.nom.equals("") && this.feina != null;
	}

	
}
