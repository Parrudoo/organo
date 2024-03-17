package br.com.sefaz.pi.relatorio.cte.repository;

import br.com.sefaz.pi.relatorio.cte.dto.CteDTO;
import br.com.sefaz.pi.relatorio.cte.model.Cte;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Clob;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CteRepository extends PagingAndSortingRepository<Cte,Long> {

    @Query(nativeQuery = true, value = "SELECT XMLSerialize (DOCUMENT tc.TCT_XML_CTE ) as xml,tc.TCT_CHAVE_CTE FROM APL_NFE.TAB_CTE tc\n" +
            "WHERE tc.TCT_DATA_PROC BETWEEN to_date('01/01/2023', 'DD-MM-YYYY') AND TO_DATE('02/01/2023', 'DD-MM-YYYY')")
    Page<Tuple> buscarRelatorioCte(@Param("inicio") LocalDate inicio, @Param("dataFinal") LocalDate dataFinal,Pageable pagina);


    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM  APL_NFE.TAB_CTE tc \n" +
            "WHERE tc.TCT_DATA_PROC BETWEEN :inicio AND :dataFinal")
    Integer buscarTotalRegistros(@Param("inicio") LocalDate inicio, @Param("dataFinal") LocalDate dataFinal);

}
