package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.dto.projections.SaleMinProjection;
import com.devsuperior.dsmeta.dto.projections.SellerMinProjection;
import com.devsuperior.dsmeta.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class SellerService {

@Autowired
private SellerRepository sellerRepository;

public List<SellerMinDTO> findSellerTotal(String minDate, String maxDate ) {

    LocalDate dataInicial;
    LocalDate dataFinal;

    if(maxDate == null || maxDate.trim().isEmpty()) {
        dataFinal = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
    } else {
        dataFinal = LocalDate.parse(maxDate);
    }

    if(minDate == null || minDate.trim().isEmpty()) {
        dataInicial = dataFinal.minusYears(1);
    } else {
        dataInicial = LocalDate.parse(minDate);
    }

    System.out.println("=== DEBUG DATAS ===");
    System.out.println("Data inicial: " + dataInicial);
    System.out.println("Data final: " + dataFinal);
    System.out.println("==================");

            List<SellerMinProjection> projections = sellerRepository.findSellerTotal(dataInicial, dataFinal);

    return projections.stream().map(x -> new SellerMinDTO(x)).toList();
}

}
