package com.demo.sudokujsf.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * JPA Entity for the Solution table
 * <p>
 * <p>
 * Also uses Hibernate Validators
 * </p>
 */
@Entity
@Table(name = "SOLUTION", uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class Solution implements Serializable {

    /**
     * Default value included to remove warning. Remove or modify at will.
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 81, max = 81)
    @Digits(fraction = 0, integer = 81)
    @Pattern(regexp = "^([1-9]){81}$", message = "must contain 81 digits in [1..9]")
    @Column(name = "SOLUTION_DATA")
    private String solutionData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolutionData() {
        return solutionData;
    }

    public void setSolutionData(String solutionData) {
        this.solutionData = solutionData;
    }
}
