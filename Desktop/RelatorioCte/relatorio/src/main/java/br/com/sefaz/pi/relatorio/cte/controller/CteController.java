package br.com.sefaz.pi.relatorio.cte.controller;

import br.com.sefaz.pi.relatorio.cte.dto.CteDTO;
import br.com.sefaz.pi.relatorio.cte.exportacao.ExcelExporter;
import br.com.sefaz.pi.relatorio.cte.service.CteService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/relatorio")
public class CteController {


    @Autowired
    private CteService cteService;


    @GetMapping("/cte")
    public void gerarRelatorio(HttpServletResponse response) throws IOException {

        Calendar calendar = Calendar.getInstance();



        LocalDate dataInicio = LocalDate.of(2023,1,1);
        LocalDate dataFinal = LocalDate.of(2023,1,10);

        long diferencaEmDias = ChronoUnit.DAYS.between(dataInicio, dataFinal);


        for (int i = 1; i < diferencaEmDias; i++){

            List<CteDTO> cteDTOS = cteService.buscaXmlCte(LocalDate.of(2023,1,i), LocalDate.of(2023,1,i+1),response);


            Integer index = cteDTOS.size();

            ExcelExporter excelExporter = new ExcelExporter();
            byte[] excelBytes = excelExporter.export(!cteDTOS.isEmpty() ? cteDTOS : null);

//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", "relatorio-cte.xlsx");

            String titulo = "relatorio_".concat(String.valueOf(index)).concat("_registros").concat(".xlsx");
            String path = "C:\\Users\\diegodias\\Documents\\dados\\".concat(titulo);


            try(FileOutputStream fileOutputStream = new FileOutputStream(path)){
                fileOutputStream.write(excelBytes);
            }catch (IOException e){
                e.printStackTrace();
            }


        }




        }






}
