package com.refunits.database.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class BaseEntity<P extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private P id;
}