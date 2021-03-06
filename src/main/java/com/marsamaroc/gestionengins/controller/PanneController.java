package com.marsamaroc.gestionengins.controller;

import com.marsamaroc.gestionengins.dto.PanneDTO;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.Panne;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.service.DemandeService;
import com.marsamaroc.gestionengins.service.EnginService;
import com.marsamaroc.gestionengins.service.PanneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/pagnes")
public class PanneController {
    @Autowired
    PanneService panneService;

    @Autowired
    EnginService enginService;
    @Autowired
    DemandeService demandeService;

    @PostMapping(value = "/addPagne")
    PanneDTO addPAgne(@RequestBody Panne panne){
        Engin engin = enginService.getById(panne.getEngin().getCodeEngin());
        panne.setCurrentDemande(demandeService.getById(panne.getCurrentDemande().getNumBCI()));
        panne.setDernierAffectation(enginService.getById(panne.getEngin().getCodeEngin()).getDerniereAffectation());
        panne.setEngin(engin);
        return new PanneDTO(panneService.saveOrUpdatePagne(panne));
    }
}
