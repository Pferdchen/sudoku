package com.demo.sudokujsf.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEqualsFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCodeFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringExcluding​;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Puzzle should")
public class PuzzleTest extends AbstractBeanValidationTest {

    private Puzzle puzzle;

    @BeforeEach
    public void setup() {
        puzzle = new Puzzle();
    }

    @Test
    @DisplayName("be a valid Bean")
    public void testBean() {
        assertThat(Puzzle.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCodeFor("id"),
                hasValidBeanEqualsFor("id"),
                hasValidBeanToStringExcluding​("solutions")
        ));
    }

    @Test
    @DisplayName("not be valid if attributes are missing")
    public void whenAttributesAreMissing_then4ConstraintViolations() {
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);

        assertThat(violations.size(), is(2));
        for (final String message : new String[]{
            "Name may not be empty",
            "Puzzle data may not be empty"}) {
            assertThat("Puzzle should check constraint: " + message,
                    violations.stream()
                            .anyMatch(v -> v.getMessage().equals(message)),
                    is(true));
        }
    }

    @Test
    @DisplayName("have no constraint violations")
    public void haveNoConstraintViolations() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName(VALID_PUZZLE_NAME);
        puzzle.setPuzzleData(VALID_PUZZLE_DATA);
        //when:
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(0));
    }

    @Test
    @DisplayName("detect null name")
    public void whenNullName_thenOneConstraintViolation() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName(null);
        puzzle.setPuzzleData(VALID_PUZZLE_DATA);
        //when:
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Puzzle> violation = violations.iterator().next();
        assertThat("Name may not be empty", is(violation.getMessage()));
        assertThat("name", is(violation.getPropertyPath().toString()));
        assertThat(null, is(violation.getInvalidValue()));
    }

    @Test
    @DisplayName("detect empty name")
    public void whenEmptyName_thenOneConstraintViolation() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName("");
        puzzle.setPuzzleData(VALID_PUZZLE_DATA);
        //when:
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Puzzle> violation = violations.iterator().next();
        assertThat("Name may not be empty", is(violation.getMessage()));
        assertThat("name", is(violation.getPropertyPath().toString()));
        assertThat("", is(violation.getInvalidValue()));
    }

    @Test
    @DisplayName("detect null puzzle data")
    public void whenNullPuzzleData_thenOneConstraintViolation() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName(VALID_PUZZLE_NAME);
        puzzle.setPuzzleData(null);
        //when:
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Puzzle> violation = violations.iterator().next();
        assertThat("Puzzle data may not be empty", is(violation.getMessage()));
        assertThat("puzzleData", is(violation.getPropertyPath().toString()));
        assertThat(violation.getInvalidValue(), nullValue());
    }

    @Test
    @DisplayName("detect empty puzzle data")
    public void whenEmptyPuzzleData_then2ConstraintViolations() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName(VALID_PUZZLE_NAME);
        puzzle.setPuzzleData("");
        //when:
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(2));
        for (final String message : new String[]{
            "Puzzle data may not be empty",
            "Puzzle data must contain 81 digits and spaces"}) {
            assertThat("Puzzle should check contraint: " + message,
                    violations.stream()
                            .anyMatch(v -> v.getMessage().equals(message)),
                    is(true));
        }
    }

    @Test
    @DisplayName("detect puzzle data with wrong size")
    public void whenPuzzleDataHasWrongSize_thenOneConstraintViolations() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName(VALID_PUZZLE_NAME);
        puzzle.setPuzzleData(INVALID_PUZZLE_DATA_WITH_SIZE_80);
        //when:
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Puzzle> violation = violations.iterator().next();
        assertThat("Puzzle data must contain 81 digits and spaces",
                is(violation.getMessage()));
        assertThat("puzzleData", is(violation.getPropertyPath().toString()));
        assertThat(((String) violation.getInvalidValue()).length(), not(81));
    }

    @Test
    @DisplayName("detect, there is a zero in puzzle data")
    public void whenSolutionDataHasZero_thenOneConstraintViolation() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName(VALID_PUZZLE_NAME);
        puzzle.setPuzzleData(INVALID_PUZZLE_DATA_WITH_ZERO);
        //when:
        Set<ConstraintViolation<Puzzle>> violations
                = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Puzzle> violation = violations.iterator().next();
        assertThat("Puzzle data must contain 81 digits and spaces",
                is(violation.getMessage()));
        assertThat("puzzleData", is(violation.getPropertyPath().toString()));
        assertThat(((String) violation.getInvalidValue()), containsString("0"));
    }

    @Test
    @DisplayName("detect, there is an alphabet in puzzle data")
    public void whenPuzzleDataHasAlphabet_thenOneConstraintViolation() {
        //given:
        puzzle.setId(VALID_PUZZLE_ID);
        puzzle.setName(VALID_PUZZLE_NAME);
        puzzle.setPuzzleData(INVALID_PUZZLE_DATA_WITH_ALPHABET);
        //when:
        Set<ConstraintViolation<Puzzle>> violations = validator.validate(puzzle);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Puzzle> violation = violations.iterator().next();
        assertThat("Puzzle data must contain 81 digits and spaces",
                is(violation.getMessage()));
        assertThat("puzzleData", is(violation.getPropertyPath().toString()));
        assertThat((String) violation.getInvalidValue(), containsString("a"));
    }

    private static final Long VALID_PUZZLE_ID = 1L;
    private static final String VALID_PUZZLE_NAME = "Valid name";
    private static final String VALID_PUZZLE_DATA
            = "53  7    6  195    98    6 8   6   34  8 3  17   2   6 6    28    419  5    8  79";
    private static final String INVALID_PUZZLE_DATA_WITH_SIZE_80
            = "53  7    6  195    98    6 8   6   34  8 3  17   2   6 6    28    419  5    8  7";
    private static final String INVALID_PUZZLE_DATA_WITH_ZERO
            = "53  7    6  195    98    6 8   6   34  8 3  17   2   6 6    28    419  5    8  70";
    private static final String INVALID_PUZZLE_DATA_WITH_ALPHABET
            = "53  7    6  195    98    6 8   6   34  8 3  17   2   6 6    28    419  5    8  7a";

}
