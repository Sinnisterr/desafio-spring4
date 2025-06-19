package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.dto.projections.SellerMinProjection;
import com.devsuperior.dsmeta.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;

	@Autowired
	private SellerService sellerService;

	@GetMapping(value = "/report")
	public ResponseEntity<List<SaleMinDTO>> getReport(
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate,
			@RequestParam(value = "name", defaultValue = "") String sellerName) {

		List<SaleMinDTO> result = service.findSalesByDateRange(minDate, maxDate, sellerName);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SellerMinDTO>> getSumary(
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) {

		List<SellerMinDTO> result = sellerService.findSellerTotal(minDate, maxDate);
		return ResponseEntity.ok(result);

	}

	@GetMapping(value = "/all")
	public ResponseEntity<Page<SaleMinDTO>> findSaleAll(Pageable pageable) {
		Page<SaleMinDTO> result = service.findSaleAll(pageable);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}



}
