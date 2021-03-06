package com.marsamaroc.gestionengins.controller;

import com.marsamaroc.gestionengins.dto.EnginDTO;
import com.marsamaroc.gestionengins.dto.EnginDispDTO;
import com.marsamaroc.gestionengins.dto.EnginSEDTO;
import com.marsamaroc.gestionengins.dto.PostDTO;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.Famille;
import com.marsamaroc.gestionengins.entity.Post;
import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.response.APIResponseEngins;
import com.marsamaroc.gestionengins.service.ControleService;
import com.marsamaroc.gestionengins.service.EnginService;
import com.marsamaroc.gestionengins.service.FamilleService;
import com.marsamaroc.gestionengins.service.ShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/engin")
public class EnginsController {
    @Autowired
    EnginService enginService;
    @Autowired
    ControleService controleService;
    @Autowired
    FamilleService familleService;

    @Autowired
    ShiftService shiftService;
    @PostMapping(value = "/addEngins")
    ResponseEntity<?> addEnginList(@RequestBody List<Engin> enginList){
        Famille famille_Old;
        for(Engin engin : enginList){
            if(engin.getFamille().getNomFamille()==null) {
                engin.getFamille().setNomFamille("autre");
                engin.getFamille().setCodeFamille("AT");
            }
            famille_Old = familleService.getFamilleByName(engin.getFamille().getNomFamille());
            if(famille_Old == null)
                famille_Old = familleService.saveFamille(engin.getFamille());
            engin.setFamille(famille_Old);
            engin.getFamille().setEngin(null);
            enginService.save(engin);
        }
        return new ResponseEntity<>(enginList, HttpStatus.CREATED);

    }
    
    
    @GetMapping(value="/listeEngins")
    ResponseEntity<?> listeEngins(){
        List<Engin> enginList = enginService.getAll();
        List<EnginDTO> enginDTOList =new ArrayList<>();
        for (Engin engin : enginList)
        	enginDTOList.add(new EnginDTO(engin,  engin.getDerniereAffectation()));
        return new ResponseEntity<>(enginDTOList, HttpStatus.OK);

    }

    @PostMapping(value="/add")
    ResponseEntity<?> saveOrUpdate(@RequestBody Engin enging){
        return new ResponseEntity<>(enginService.saveOrUpdate(enging), HttpStatus.CREATED);
    }
    
    @GetMapping(value="/listeEnginsSortie")
    ResponseEntity<?> listeEnginsSortie() throws ResourceNotFoundException{
        List<Engin> enginList = enginService.getEnginsSorties();
        List<EnginSEDTO> enginSEDTOList =new ArrayList<>();
        for (Engin engin : enginList){
        	if(engin.getCurrenteAffectation() != null) {
        		if(engin.getCurrenteAffectation().getEtat()==EtatAffectation.reserve &&  engin.getCurrenteAffectation().getDemande().isValableToTrait(Shift.nextShift(shiftService.findAll()).getHeureFin()))
        			enginSEDTOList.add(new EnginSEDTO(engin));
        	}
        	
        }
        return new ResponseEntity<>(enginSEDTOList, HttpStatus.OK);
    }
    @GetMapping(value="/listeEnginsEntree")
    ResponseEntity<?> listeEnginsEntree(){
        List<Engin> enginList = enginService.getEnginsEntrees();
        List<EnginSEDTO> enginSEDTOList =new ArrayList<>();
        for (Engin engin : enginList)
            enginSEDTOList.add(new EnginSEDTO(engin));
        return new ResponseEntity<>(enginSEDTOList, HttpStatus.OK);

    }
    @GetMapping(value="/listeEnginsDisponible/{famille}")
    ResponseEntity<?> listeEnginsEntreeByFamille(@PathVariable("famille") Long famille) throws ResourceNotFoundException {
        List<Engin> enginList = enginService.getEnginsEntreesByFamille(famille);
        List<APIResponseEngins<EnginDispDTO>> apiResponseEnginsList = new ArrayList<>();
        for (Engin engin : enginList) {
            APIResponseEngins apiResponseEngins = new APIResponseEngins<>(
                    new EnginDispDTO(engin),
                    engin.getDisponibiliteParck(),
                    engin.getState(Shift.nextShift(shiftService.findAll()).getHeureFin()),
                    null,
                    null
            );
            if(!apiResponseEngins.getEtat().equals("Disponible")) {
                apiResponseEngins.setCurrentEntite(engin.getCurrenteAffectation().getDemande().getEntite().getEntite());
                apiResponseEngins.setCurrentNumBCI(engin.getCurrenteAffectation().getDemande().getNumBCI());
            }
            apiResponseEnginsList.add(apiResponseEngins);
        }
        return new ResponseEntity<>(apiResponseEnginsList, HttpStatus.OK);

    }

    @GetMapping(value="/{idEngin}")
    ResponseEntity<?> getEngin(@PathVariable("idEngin") String idEngin){
        Engin engin = enginService.getById(idEngin);
        EnginDTO enginDTO = new EnginDTO(engin, engin.getDerniereAffectation());
        return new ResponseEntity<>(enginDTO, HttpStatus.OK);

    }



}
