package com.demo.sudokujsf.component;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIOutput;

/**
 *
 * @author gao
 */
@FacesComponent("SudokuSVG")
public class SudokuSVG extends UIOutput {

    private enum PropertyKeys {
    }

    /**
     * @return the component family for this component
     */
    @Override
    public String getFamily() {
        return ("SudokuSVG");
    }

}
