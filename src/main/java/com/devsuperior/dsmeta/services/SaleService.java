package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.projections.SaleMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional
	public Page<SaleMinDTO> findSaleAll(Pageable pageable) {
		Page<Sale> result = repository.findAll(pageable);
		return result.map(x -> new SaleMinDTO(x));

	}

	@Transactional(readOnly = true)
	public Page<SaleMinDTO> findSalesByDateRange(String minDate, String maxDate, String sellerName, Pageable pageable) {

		LocalDate dataInicial;
		LocalDate dataFinal;

		if ((minDate == null || minDate.trim().isEmpty()) &&
				(maxDate == null || maxDate.trim().isEmpty())) {

			dataFinal = LocalDate.now();
			dataInicial = dataFinal.minusYears(1);

		} else {

			if (minDate == null || minDate.trim().isEmpty()) {
				throw new IllegalArgumentException("Data inicial é obrigatória quando data final é informada");
			}
			if (maxDate == null || maxDate.trim().isEmpty()) {
				throw new IllegalArgumentException("Data final é obrigatória quando data inicial é informada");
			}

			dataInicial = LocalDate.parse(minDate);
			dataFinal = LocalDate.parse(maxDate);
		}

		String nomeVendedor = (sellerName == null || sellerName.trim().isEmpty()) ? "" : sellerName;

		Page<SaleMinProjection> projections = repository.findSalesByDateRange(dataInicial, dataFinal, nomeVendedor, pageable);

		return projections.map(proj -> new SaleMinDTO(proj));
	}
}
