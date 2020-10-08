package com.demo.sudokujsf.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * JPA Entity for the Puzzle table
 * <p>
 * <p>
 * Also uses Hibernate Validators
 * </p>
 */
@Entity
@Table(name = "PUZZLE",
        uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
@NamedQueries({
    @NamedQuery(name = "Puzzle.findAll",
            query = "FROM Puzzle p ORDER BY p.id"),
    @NamedQuery(name = "Puzzle.findById",
            query = "FROM Puzzle p WHERE p.id = :id"),
    @NamedQuery(name = "Puzzle.findAllWithName",
            query = "FROM Puzzle p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
})
public class Puzzle implements Serializable {

    /**
     * Default value included to remove warning. Remove or modify at will.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name may not be empty")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "Puzzle data may not be empty")
    @Pattern(regexp = "^([1-9]|\\s){81}$",
            message = "Puzzle data must contain 81 digits and spaces")
    @Column(name = "PUZZLE_DATA")
    private String puzzleData;

    /**
     * Unidirectional OneToMany, No Inverse ManyToOne, No Join Table (JPA 2.x
     * ONLY). Use a Set rather than a List, because the possible multiple
     * solutions of one puzzle are not ordered.
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Puzzle{" + "id=" + id + ", name=" + name
                + ", puzzleData=" + puzzleData + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Puzzle)) {
            return false;
        }
        Puzzle other = (Puzzle) obj;
        return Objects.equals(this.id, other.id);
    }

}
