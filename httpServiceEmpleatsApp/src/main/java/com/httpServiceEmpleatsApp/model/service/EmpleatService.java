package com.httpServiceEmpleatsApp.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.httpServiceEmpleatsApp.model.entity.Empleat;
import com.httpServiceEmpleatsApp.model.entity.TipusFeina;

public interface EmpleatService {
	
	// Métodos que nos servirán para operar con el repository
		
	public List<Empleat> getEmpleats(TipusFeina feina);
	
	public Empleat getEmpleatById(int id);
	
	public void guardarEmpleat(Empleat empleat);
		
	public void eliminarById(int id);
	
	public void saveFile(MultipartFile file, int id) throws IOException;
	
}
