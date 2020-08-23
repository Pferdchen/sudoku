package com.demo.sudokujsf.component;

import java.io.IOException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

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
