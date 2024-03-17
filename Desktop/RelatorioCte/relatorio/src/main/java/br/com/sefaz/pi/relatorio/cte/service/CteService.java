package br.com.sefaz.pi.relatorio.cte.service;

import br.com.sefaz.pi.relatorio.cte.dto.CteDTO;
import br.com.sefaz.pi.relatorio.cte.exportacao.ExcelExporter;
import br.com.sefaz.pi.relatorio.cte.model.Cte;
import br.com.sefaz.pi.relatorio.cte.repository.CteRepository;
import jakarta.persistence.Tuple;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CteService {

    private static final Logger log = LoggerFactory.getLogger(CteService.class);

    @Autowired
    private CteRepository cteRepository;



    public List<CteDTO> buscaXmlCte(LocalDate inicio, LocalDate dataFinal,HttpServletResponse response){



        List<CteDTO> cteDTOS = new ArrayList<>();



        int pageNumber2 = 0;
        int pageSize2 = 100;

        int[] index = { 0 };

        Integer totalElementos = cteRepository.buscarTotalRegistros(inicio,dataFinal);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(0);


            while (index[0] < totalElementos){

                Pageable pageable = PageRequest.of(pageNumber2,pageSize2);

                Page<Tuple> list = cteRepository.buscarRelatorioCte(inicio,dataFinal, pageable);



            list.forEach(c->{
                if (c != null ){
                    CteDTO cteDTO = new CteDTO();
                      cteDTO.setXml(clobToString(c.get("xml", Clob.class)));
//                    cteDTO.setChave(c.get("CHAVE", String.class));
//                    cteDTO.setDataProcessamento(c.get("DATA",Timestamp.class));
                    cteDTOS.add(cteDTO);

                }

                if (cteDTOS.size() == 100000){
                    try {
                        gerarRelatorio(cteDTOS,response,index[0]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    cteDTOS.clear();
                }

                log.info(index[0] + " relatorios gerados"+" | "+"Qtd prevista: "+ totalElementos);


                index[0]++;
            });




                pageNumber2 = pageNumber2+1;
//                pageSize2 = pageSize2+100;



            }




        return cteDTOS;
    }

    public void gerarRelatorio(List<CteDTO> cteDTOS, HttpServletResponse response,Integer index) throws IOException {

        ExcelExporter excelExporter = new ExcelExporter();
        byte[] excelBytes = excelExporter.export(!cteDTOS.isEmpty() ? cteDTOS : null);

//        caso o usuario queira que o relatorio seja aberto no navegador

//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader("Content-Disposition", "attachment; filename=\"relatorio-cte.xlsx\"");
//
//        try (OutputStream outputStream = response.getOutputStream()) {
//            outputStream.write(excelBytes);
//            outputStream.flush();
//        }
        String titulo = "relatorio_".concat(String.valueOf(index)).concat("_registros").concat(".xlsx");

        String path = "C:\\Users\\diegodias\\Documents\\dados\\".concat(titulo);
        try(FileOutputStream outputStream = new FileOutputStream(path)){
            outputStream.write(excelBytes);
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public  String clobToString(Clob clob)  {
        StringBuilder sb = new StringBuilder();
        if (clob != null) {
            try (java.io.Reader reader = clob.getCharacterStream()) {
                char[] buffer = new char[4096];
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    sb.append(buffer, 0, bytesRead);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }

}
