package com.demo.sudokujsf.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * JPA Entity for the Solution table
 * <p>
 * <p>
 * Also uses Hibernate Validators
 * </p>
 */
@Entity
@Table(name = "SOLUTION",
        uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class Solution implements Serializable {

    /**
     * Default value included to remove warning. Remove or modify at will.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Solution data may not be empty")
    @Pattern(regexp = "^([1-9]){81}$",
            message = "Solution data must contain 81 digits in [1..9]")
    @Column(name = "SOLUTION_DATA")
    private String solutionData;

    /**
     * Because it generates automatically, no Bean Validation @NotNull required
     */
    @Column(name = "PUZZLE_ID", nullable = false)
    private long puzzleId;

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

    public long getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(long puzzleId) {
        this.puzzleId = puzzleId;
    }

    @Override
    public String toString() {
        return "Solution{" + "id=" + id + ", solutionData=" + solutionData
                + ", puzzleId=" + puzzleId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Solution)) {
            return false;
        }
        Solution other = (Solution) obj;
        return Objects.equals(this.id, other.id);
    }

}
