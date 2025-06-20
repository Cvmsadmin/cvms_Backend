package org.ss.vendorapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "line_item")
public class LineItemEntity {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;
    private double budgetCost;
    private double actualCost;
    private double budgetIncome;
    private double actualIncome;

    @ManyToOne
    @JoinColumn(name = "financial_year_id")
    private FinancialYearEntity financialYear;

}
