package br.com.sefaz.pi.relatorio.cte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "TAB_CTE", schema = "APL_NFE")
public class Cte {

    @Id
    @Column(name = "TCT_ID_CTE")
    private Long id;


    @Column(name = "TCT_DATA_PROC")
    private LocalDate dataProc;


    @Column(name = "TCT_XML_CTE")
    private String xml;



}
