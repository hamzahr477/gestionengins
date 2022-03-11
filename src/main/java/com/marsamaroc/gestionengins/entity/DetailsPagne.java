package com.marsamaroc.gestionengins.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
public class DetailsPagne implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typePagne;

    private String observation;

    private String statut;

    @ManyToOne
    @JoinColumn(name = "pagne_id")
    private Pagne pagne;

    @ManyToOne
    @JoinColumn(name = "critere_id_critere")
    private Critere critere;


}
