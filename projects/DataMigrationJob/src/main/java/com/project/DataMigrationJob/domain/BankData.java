package com.project.DataMigrationJob.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "bank_data")
public class BankData {

    @Id
    private Integer id;
    private Integer personId;
    private Integer account;
    private Integer bank;
    private Integer agency;

}
