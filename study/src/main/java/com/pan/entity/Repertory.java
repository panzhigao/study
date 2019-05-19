package com.pan.entity;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库存表
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Repertory extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2634671635419298942L;
	
    private String specification;

    private String unit;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal amount;

    private BigDecimal tax;

    private BigDecimal totalAmount;

}