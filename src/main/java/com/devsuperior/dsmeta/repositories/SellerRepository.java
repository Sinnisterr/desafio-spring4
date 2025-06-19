package com.devsuperior.dsmeta.repositories;
import com.devsuperior.dsmeta.dto.projections.SellerMinProjection;
import com.devsuperior.dsmeta.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(nativeQuery = true, value = "SELECT se.name, SUM(sa.amount) AS total "
            + "FROM tb_seller se "
            + "INNER JOIN tb_sales sa ON se.id = sa.seller_id "
            + "WHERE sa.date >= :dataInicial "
            + "AND sa.date <= :dataFinal "
            + "GROUP BY se.name ")
    List<SellerMinProjection> findSellerTotal(
            @Param("dataInicial")LocalDate dataInicial,
            @Param("dataFinal")LocalDate dataFinal);

}
