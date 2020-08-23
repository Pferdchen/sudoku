package com.demo.sudokujsf.data;

import com.demo.sudokujsf.model.Puzzle;
import java.util.List;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateful
public class EJBPuzzleDao implements PuzzleDao {

    @Inject
    private EntityManager em;

    @Override
    public List<Puzzle> getAllPuzzles() {
        String query = "SELECT p FROM Puzzle p ORDER BY p.id";
        List<Puzzle> result = em.createQuery(query, Puzzle.class)
                .getResultList();
        return result;
    }

}
