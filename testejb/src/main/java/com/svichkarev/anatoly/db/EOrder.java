package com.svichkarev.anatoly.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "e_order")
public class EOrder extends com.svichkarev.anatoly.ejb.EOrder {

    private long id;

    public EOrder() {
    }

    public EOrder(com.svichkarev.anatoly.ejb.EOrder dto) {
        super(dto.getNumber(), dto.getDescription(), dto.getAmount(), dto.getCurrency());
    }

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static EOrder toDbEntity(com.svichkarev.anatoly.ejb.EOrder dto) {
        return new EOrder(dto);
    }
}
