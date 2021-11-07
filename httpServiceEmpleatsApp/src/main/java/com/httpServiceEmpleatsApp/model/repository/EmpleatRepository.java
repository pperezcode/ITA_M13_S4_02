package com.httpServiceEmpleatsApp.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.httpServiceEmpleatsApp.model.entity.Empleat;
import com.httpServiceEmpleatsApp.model.entity.TipusFeina;

@Repository
public class EmpleatRepository {
	
	private static int next_id = 0;
	
	// Llista d'empleats que es guardarà en memòria
	
	private static List<Empleat> llistaEmpleats = new ArrayList<>();
	
	// Mètodes propis del repositori
	
	public void save(Empleat empleat) {
		empleat.setId(next_id++);
		llistaEmpleats.add(empleat);
	}
	
	public List<Empleat> findAll() {
		return llistaEmpleats;		
	}	

	public Empleat findById(int id) {
		Empleat empleat = null;
		int i= 0;
		
		for (Empleat e: llistaEmpleats) {
			if (e.getId() == id) {
				empleat = llistaEmpleats.get(i);
			}
			i++;
		}
		return empleat;
	}
	
	public void deleteById(int id) {
		Empleat tmp = null;
		for (Empleat e: llistaEmpleats) {
			if (e.getId() == id) {
				tmp = e;
				break;
			}
		}
		llistaEmpleats.remove(tmp);
	}
	
	public List<Empleat> findByFilterFeina(TipusFeina feina) {
		return llistaEmpleats.stream()
				.filter(e -> e.getFeina().equals(feina))
				.collect(Collectors.toList());
	}
	
	public void updateById(int id, String nom, TipusFeina feina) {
		for (Empleat e: llistaEmpleats) {
			if (e.getId() == id) {
				if (!nom.equals("")) {
					e.setNom(nom);	
				}
				if (feina != null) {
					e.setFeina(feina);
					
					if (feina.equals(TipusFeina.ADMINISTRATIU)) {
						e.setSalari(18000);
					} else if (feina.equals(TipusFeina.MANAGER)) {
						e.setSalari(24000);
					} else if (feina.equals(TipusFeina.DIRECTIU)) {
						e.setSalari(40000);
					} else if (feina.equals(TipusFeina.CEO)) {
						e.setSalari(60000);
					}
				}
			}
		}
	}
	
	// Mètode sobreescrit	
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		String msg = "";
		
		for (Empleat e : llistaEmpleats) {
			msg = e.toString() + "\n";
			str.append(msg);
		}
		
		if (str.isEmpty()) {
			str.append("En aquests moments no hi ha cap empleat donat d'alta");
		}
		
		return "REPOSITORI D'EMPLEATS:\n" + str;
	}
	
}