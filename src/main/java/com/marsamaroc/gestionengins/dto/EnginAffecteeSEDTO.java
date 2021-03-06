package com.marsamaroc.gestionengins.dto;

import com.marsamaroc.gestionengins.entity.Controle;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.entity.Utilisateur;
import lombok.Data;

import java.util.List;

@Data
public class EnginAffecteeSEDTO {
    private Long idDemandeEngin;
    private Utilisateur responsableAffectation;
    private Utilisateur conducteur;
    private Long compteur;
    private List<Controle> controleEngin;
    private String observation;

    public EnginAffecte toEntity(){
        EnginAffecte enginAffecte = new EnginAffecte();
        enginAffecte.setIdDemandeEngin(idDemandeEngin);
        enginAffecte.setResponsableAffectation_entree(responsableAffectation);
        enginAffecte.setConducteur_entree(conducteur);
        return enginAffecte;
    }

}
