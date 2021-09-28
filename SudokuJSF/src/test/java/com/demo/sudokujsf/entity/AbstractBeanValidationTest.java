package com.demo.sudokujsf.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractBeanValidationTest {

    private static ValidatorFactory validatorFactory;
    static Validator validator;
    static final String NULL_STRING = null;
    static final String EMPTY_STRING = "";
    static final String BLANK_STRING = " ";

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

}
