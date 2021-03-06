package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Critere implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idCritere;
    private String nomCritere;

    //Parametrage
    private Boolean active = true;
    private Date dateCreation;
    private Date derniereModification;
    ////

}
