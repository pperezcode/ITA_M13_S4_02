package com.httpServiceEmpleatsApp.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.httpServiceEmpleatsApp.model.entity.Empleat;
import com.httpServiceEmpleatsApp.model.entity.TipusFeina;
import com.httpServiceEmpleatsApp.model.repository.EmpleatRepository;

@Service
public class EmpleatServiceImpl implements EmpleatService {
	
	@Autowired
	EmpleatRepository empleatRepository;
	
	// Carpeta on es guardaran les imatges
	private String upload_folder = ".//src//main//resources//static//files//";

	
	// Mètodes sobreescrits de la interfície EmpleatService
	
	@Override
	public List<Empleat> getEmpleats(TipusFeina feina) {
		List<Empleat> llistaEmpleats = null;
		if (feina == null) {
			llistaEmpleats = empleatRepository.findAll();
		} else {
			llistaEmpleats = empleatRepository.findByFilterFeina(feina);
		}
		
		return llistaEmpleats;
	}
	
	@Override
	public Empleat getEmpleatById(int id) {
		return empleatRepository.findById(id);
	}
	
	@Override
	public void guardarEmpleat(Empleat empleat) {
		int id = empleat.getId(); 
		if (id == -1 || empleatRepository.findById(id) == null) {
			empleatRepository.save(empleat);
		} else {
			empleatRepository.updateById(id, empleat.getNom(), empleat.getFeina());
		}	
	}

	@Override
	public void eliminarById(int id) {
		empleatRepository.deleteById(id);
	}
	
	// Mètode per pujar imatges

	@Override
	public void saveFile(MultipartFile file, int id) throws IOException {
		String fileName = "foto.jpg";
		File directori = new File(upload_folder + id);
		directori.mkdir();
		
        if(!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Path path = Paths.get(upload_folder + id + "//" + fileName);
            Files.write(path,bytes);
        }
    }
	
}
