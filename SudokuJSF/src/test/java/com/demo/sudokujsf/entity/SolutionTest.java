package com.demo.sudokujsf.entity;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEqualsFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCodeFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Solution should")
public class SolutionTest extends AbstractBeanValidationTest {

    private Solution solution;

    @BeforeEach
    public void setup() {
        solution = new Solution();
    }

    @Test
    @DisplayName("be a valid Bean")
    public void testBean() {
        assertThat(Solution.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCodeFor("id"),
                hasValidBeanEqualsFor("id"),
                hasValidBeanToString()
        ));
    }

    @Test
    @DisplayName("not be valid if attributes are missing")
    public void whenAttributesAreMissing_thenOneConstraintViolation() {
        //when:
        Set<ConstraintViolation<Solution>> violations
                = validator.validate(solution);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Solution> violation = violations.iterator().next();
        assertThat("Solution data may not be empty", is(violation.getMessage()));
        assertThat("solutionData", is(violation.getPropertyPath().toString()));
        assertThat(violation.getInvalidValue(), nullValue());
    }

    @Test
    @DisplayName("have no constraint violations")
    public void haveNoConstraintViolations() {
        //given:
        solution.setId(VALID_SOLUTION_ID);
        solution.setSolutionData(VALID_SOLUTION_DATA);
        solution.setPuzzleId(VALID_PUZZLE_ID);
        //when:
        Set<ConstraintViolation<Solution>> violations
                = validator.validate(solution);
        //then:
        assertThat(violations.size(), is(0));
    }

    @Test
    @DisplayName("detect null solution data")
    public void whenNullSolutionData_thenOneConstraintViolation() {
        //given:
        solution.setId(VALID_SOLUTION_ID);
        solution.setSolutionData(NULL_STRING);
        solution.setPuzzleId(VALID_PUZZLE_ID);
        //when:
        Set<ConstraintViolation<Solution>> violations
                = validator.validate(solution);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Solution> violation = violations.iterator().next();
        assertThat("Solution data may not be empty", is(violation.getMessage()));
        assertThat("solutionData", is(violation.getPropertyPath().toString()));
        assertThat(violation.getInvalidValue(), nullValue());
    }

    @Test
    @DisplayName("detect empty solution data")
    public void whenEmptySolutionData_thenOneConstraintViolation() {
        //given:
        solution.setId(VALID_SOLUTION_ID);
        solution.setSolutionData(EMPTY_STRING);
        solution.setPuzzleId(VALID_PUZZLE_ID);
        //when:
        Set<ConstraintViolation<Solution>> violations
                = validator.validate(solution);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Solution> violation = violations.iterator().next();
        assertThat("Solution data must contain 81 digits in [1..9]",
                is(violation.getMessage()));
        assertThat("solutionData", is(violation.getPropertyPath().toString()));
        assertThat(EMPTY_STRING, is(violation.getInvalidValue()));
    }

    @Test
    @DisplayName("detect solution data with wrong size")
    public void whenSolutionDataHasWrongSize_thenOneConstraintViolation() {
        //given:
        solution.setId(VALID_SOLUTION_ID);
        solution.setSolutionData(INVALID_SOLUTION_DATA_WITH_SIZE_80);
        solution.setPuzzleId(VALID_PUZZLE_ID);
        //when:
        Set<ConstraintViolation<Solution>> violations
                = validator.validate(solution);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Solution> violation = violations.iterator().next();
        assertThat("Solution data must contain 81 digits in [1..9]",
                is(violation.getMessage()));
        assertThat("solutionData", is(violation.getPropertyPath().toString()));
        assertThat(((String) violation.getInvalidValue()).length(), not(81));
    }

    @Test
    @DisplayName("detect, there is a zero in solution data")
    public void whenSolutionDataHasZero_thenOneConstraintViolation() {
        //given:
        solution.setId(VALID_SOLUTION_ID);
        solution.setSolutionData(INVALID_SOLUTION_DATA_WITH_ZERO);
        solution.setPuzzleId(VALID_PUZZLE_ID);
        //when:
        Set<ConstraintViolation<Solution>> violations
                = validator.validate(solution);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Solution> violation = violations.iterator().next();
        assertThat("Solution data must contain 81 digits in [1..9]",
                is(violation.getMessage()));
        assertThat("solutionData", is(violation.getPropertyPath().toString()));
        assertThat(((String) violation.getInvalidValue()), containsString("0"));
    }

    @Test
    @DisplayName("detect, there is an alphabet in solution data")
    public void whenSolutionDataHasAlphabet_thenOneConstraintViolation() {
        //given:
        solution.setId(VALID_SOLUTION_ID);
        solution.setSolutionData(INVALID_SOLUTION_DATA_WITH_ALPHABET);
        solution.setPuzzleId(VALID_PUZZLE_ID);
        //when:
        Set<ConstraintViolation<Solution>> violations
                = validator.validate(solution);
        //then:
        assertThat(violations.size(), is(1));
        ConstraintViolation<Solution> violation = violations.iterator().next();
        assertThat("Solution data must contain 81 digits in [1..9]",
                is(violation.getMessage()));
        assertThat("solutionData", is(violation.getPropertyPath().toString()));
        assertThat((String) violation.getInvalidValue(), containsString("a"));
    }

    private static final Long VALID_SOLUTION_ID = 1L;
    private static final Long VALID_PUZZLE_ID = 1L;
    private static final String VALID_SOLUTION_DATA
            = "534678912672195348198342567859761423426853791713924856961537284287419635345286179";
    private static final String INVALID_SOLUTION_DATA_WITH_SIZE_80
            = "53467891267219534819834256785976142342685379171392485696153728428741963534528617";
    private static final String INVALID_SOLUTION_DATA_WITH_ALPHABET
            = "53467891267219534819834256785976142342685379171392485696153728428741963534528617a";
    private static final String INVALID_SOLUTION_DATA_WITH_ZERO
            = "534678912672195348198342567859761423426853791713924856961537284287419635345286170";

}
