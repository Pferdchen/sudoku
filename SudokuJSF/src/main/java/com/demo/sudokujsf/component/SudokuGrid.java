package com.demo.sudokujsf.component;

import java.io.IOException;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIOutput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(createTag = true, namespace = "https://sudokujsf.com/demo",
        tagName = "sudoku-grid", value = "com.demo.sudokujsf.component.SudokuGrid")
public class SudokuGrid extends UIOutput {

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("Hello World!");
    }

    /**
     * @return the component family for this component
     */
    @Override
    public String getFamily() {
        return ("SudokuGrid");
    }

}
