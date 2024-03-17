package br.com.sefaz.pi.relatorio.cte.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CteDTO {

    private String chave;
    private String xml;
    private Timestamp dataProcessamento;


    public String getDataProcessamento(){
        // Definir o formato desejado
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Converter o Timestamp para uma String
        String timestampString = formatter.format(dataProcessamento);

        return timestampString;
    }
}
