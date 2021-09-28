package com.demo.sudokujsf.data;

import com.demo.sudokujsf.entity.Puzzle;
import java.util.List;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Stateful
public class EJBPuzzleDao implements PuzzleDao {

    @Inject
    private EntityManager em;

    @Override
    public List<Puzzle> getAllPuzzles() {
        return em.createNamedQuery("Puzzle.findAll", Puzzle.class)
                .getResultList();
    }

}
