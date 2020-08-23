package com.demo.sudokujsf.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * JPA Entity for the Puzzle table
 * <p>
 * <p>
 * Also uses Hibernate Validators
 * </p>
 */
@Entity
@Table(name = "PUZZLE", uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class Puzzle implements Serializable {

    /**
     * Default value included to remove warning. Remove or modify at will.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 81, max = 81)
    @Pattern(regexp = "^([1-9]|\\s){81}$", message = "must contain only digits and spaces")
    @Column(name = "PUZZLE_DATA")
    private String puzzleData;

    /**
     * Unidirectional OneToMany, No Inverse ManyToOne, No Join Table (JPA 2.x
     * ONLY). I've used a Set rather than a List, because the solutions are not
     * ordered.
     */
    @OneToMany
    @JoinColumn(name = "PUZZLE_ID", referencedColumnName = "ID")
    private Set<Solution> solutions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPuzzleData() {
        return puzzleData;
    }

    public void setPuzzleData(String puzzleData) {
        this.puzzleData = puzzleData;
    }

    public Set<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(Set<Solution> solutions) {
        this.solutions = solutions;
    }

}
