package com.httpServiceEmpleatsApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.httpServiceEmpleatsApp.model.entity.Empleat;
import com.httpServiceEmpleatsApp.model.entity.TipusFeina;
import com.httpServiceEmpleatsApp.model.service.EmpleatService;

import io.swagger.annotations.ApiOperation;

@RestController
public class EmpleatController {
	
	private static final String MODEL_LLISTA = "EmpleatLlista";
	private static final String MODEL_DETALL = "EmpleatDetall";
	private static final String MODEL_IMATGE = "EmpleatImatge";

	@Autowired
	private EmpleatService empleatService;
	
	@GetMapping("/")
	@ApiOperation(value = "Home", notes = "Redirecciona al gestor d'empleats")
	public ModelAndView home() {
			return new ModelAndView("redirect:/v1/empleats/");
	}

	// MOSTRAR EMPLEATS (AMB O SENSE FILTRE) ------------------------------------------------
	// GET /v1/empleats/
	// GET /v1/empleats/?feina=CEO
	
	@GetMapping("/v1/empleats/")
	@ApiOperation(value = "Mostra empleats", notes = "Mostra els empleats donats d'alta i ens permet filtrar-los per feina")
	public ModelAndView llistaEmpleats(@RequestParam(required = false) TipusFeina feina) {
		ModelAndView mv = new ModelAndView();

		List<Empleat> empleats = empleatService.getEmpleats(feina);
		
		mv.addObject(MODEL_LLISTA, empleats);
		mv.addObject("filtre", feina);
		
		mv.setViewName("LlistaEmpleats");
		return mv;
	}

	// MOSTRAR FORMULARI EMPLEAT BUIT Y GUARDAR EMPLEAT -------------------------------------	
	@GetMapping("/v1/empleats/empleat/")
	@ApiOperation(value = "Mostra formulari empleat", notes = "Mostra el formulari per enregistrar un nou empleat")
	public ModelAndView formNouEmpleat() {
		ModelAndView mv = new ModelAndView();
		mv.addObject(MODEL_DETALL, new Empleat("", null));
		//mv.addObject("FormType", "new");
		mv.setViewName("DetallEmpleat");
		return mv;
	}
	
	@PostMapping("/v1/empleats/empleat/")
	@ApiOperation(value = "Guarda empleats", notes = "Guarda els empleats nous i els editats")
	public ModelAndView guardarEmpleat(@ModelAttribute(MODEL_DETALL) Empleat empleat, RedirectAttributes redirectAttrs) {
		
		if(!empleat.isValid()) {
			redirectAttrs.addFlashAttribute("error", "Empleat no vàlid! Has d'introduir el nom de l'empleat.");
			return new ModelAndView("redirect:/v1/empleats/empleat/");
		}
			
		empleatService.guardarEmpleat(empleat);	
		redirectAttrs.addFlashAttribute("success", "Empleat enregistrat correctament!");
		return new ModelAndView("redirect:/v1/empleats/");
	}
	
	// MOSTRAR EMPLEAT PER ID Y ACTUALITZAR EMPLEAT -----------------------------------------		
	@GetMapping("/v1/empleats/empleat/{id}")
	@ApiOperation(value = "Detall empleats", notes = "Mostra la informació de l'empleat i permet accedir als camps editables")
	public ModelAndView veureEmpleat(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView();
		Empleat empleat = empleatService.getEmpleatById(id);
		mv.addObject(MODEL_DETALL, empleat);
		mv.addObject("FormType", "view");
		mv.setViewName("DetallEmpleat");
		return mv;
	}

	// ELIMINAR EMPLEAT ---------------------------------------------------------------------	
	@GetMapping("/v1/empleats/{id}/delete")
	@ApiOperation(value = "Elimina empleats", notes = "Elimina l'empleat seleccionat")
	public ModelAndView eliminaEmpleat(@PathVariable("id") int id, RedirectAttributes redirectAttrs) {
		empleatService.eliminarById(id);
		redirectAttrs.addFlashAttribute("success", "Empleat eliminat correctament!");
		return new ModelAndView("redirect:/v1/empleats/");
	}

	// MOSTRAR PUJAR/BAIXAR IMATGE ----------------------------------------------------------

	@GetMapping("/v1/empleats/{id}/upload")
	public ModelAndView pujarImatge(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView();
		Empleat empleat = empleatService.getEmpleatById(id);
		mv.addObject(MODEL_IMATGE, empleat);
		mv.addObject("FormType", "upload");
		mv.setViewName("ImatgeEmpleat");
		return mv;
	}
		
	@PostMapping("/v1/empleats/empleat/upload")
	public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, int id, RedirectAttributes redirectAttrs) {
		try {
			if (file.isEmpty()) {
				redirectAttrs.addFlashAttribute("errorImatge", "No has seleccionat cap arxiu!");
			} else {
	        	empleatService.saveFile(file, id);
	    		empleatService.getEmpleatById(id).setImatge(file);
				redirectAttrs.addFlashAttribute("success", "Arxiu pujat correctament!");
			}
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
		return new ModelAndView("redirect:/v1/empleats/");
	}
	
	@GetMapping("/v1/empleats/{id}/download/")
	public ModelAndView baixarImatge(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView();
		Empleat empleat = empleatService.getEmpleatById(id);
		mv.addObject(MODEL_IMATGE, empleat);
		mv.addObject("FormType", "download");
		mv.setViewName("ImatgeEmpleat");
		return mv;
	}
}
