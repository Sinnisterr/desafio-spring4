package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.dto.projections.SellerMinProjection;
import com.devsuperior.dsmeta.entities.Seller;

public class SellerMinDTO {

    private String sellerName;
    private Double total;

    public SellerMinDTO() {
    }

    public SellerMinDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SellerMinDTO(SellerMinProjection projection) {
        this.sellerName = projection.getName();
        this.total = projection.getTotal();

    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
