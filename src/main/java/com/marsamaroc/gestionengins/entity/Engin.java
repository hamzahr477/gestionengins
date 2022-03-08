package com.marsamaroc.gestionengins.entity;


import com.marsamaroc.gestionengins.enums.EtatEngin;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Engin implements Serializable {
    @Id
    String codeEngin;
    String typeEngin;
    String capacite;
    Long compteur;
    @ManyToOne
    @JoinColumn(name = "id_famille")
    Famille famille;

    @Enumerated(EnumType.STRING)
    EtatEngin etat = EtatEngin.disponible;

    @OneToMany(mappedBy = "engin")
    List<EnginAffecte> enginAffecteList;



    public EnginAffecte getDerniereAffectation(){
        EnginAffecte dernierEnginAffecte = null;
        if(enginAffecteList!=null){
            dernierEnginAffecte = enginAffecteList.isEmpty() ? null : enginAffecteList.get(0);
            for(EnginAffecte enginAffecteitm : enginAffecteList){
                if(enginAffecteitm.getDateAffectation().compareTo(dernierEnginAffecte.getDateAffectation())>=1)
                    dernierEnginAffecte = enginAffecteitm;
            }
            return null;
        }
        return dernierEnginAffecte;
    }
}
