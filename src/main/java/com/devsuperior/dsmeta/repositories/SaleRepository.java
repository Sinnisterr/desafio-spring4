package com.devsuperior.dsmeta.repositories;
import com.devsuperior.dsmeta.dto.projections.SaleMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true,
            value = "SELECT s.id, s.amount, s.date, sel.name " +
                    "FROM tb_sales s " +
                    "LEFT JOIN tb_seller sel ON s.seller_id = sel.id " +
                    "WHERE s.date >= :dataInicial " +
                    "AND s.date <= :dataFinal " +
                    "AND UPPER(sel.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) " +
                    "ORDER BY s.date DESC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM tb_sales s " +
                    "LEFT JOIN tb_seller sel ON s.seller_id = sel.id " +
                    "WHERE s.date >= :dataInicial " +
                    "AND s.date <= :dataFinal " +
                    "AND UPPER(sel.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
    Page<SaleMinProjection> findSalesByDateRange(
            @Param("dataInicial")LocalDate dataInicial,
            @Param("dataFinal")LocalDate dataFinal,
            @Param("sellerName")String sellerName,
            Pageable pageable);
}
