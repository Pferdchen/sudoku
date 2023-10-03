package com.demo.sudokujsf.renderer;

import com.demo.sudokujsf.component.SudokuSVG;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author gao
 */
@FacesRenderer(componentFamily = "SudokuSVG", rendererType = "SudokuSVG")
public class SudokuSVGRenderer extends Renderer {

    public SudokuSVGRenderer() {
    }

    // Renderer Methods
    /**
     * <p>
     * No decoding is required.</p>
     *
     * @param context <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>
     * No begin encoding is required.</p>
     *
     * @param context <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>
     * No children encoding is required.</p>
     *
     * @param context <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     * @throws java.io.IOException
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
    }

    /**
     * <p>
     * Encode this component.</p>
     *
     * @param context <code>FacesContext</code>for the current request
     * @param component <code>UIComponent</code> to be decoded
     * @throws java.io.IOException
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        // Read the SVG template file
        String svgTemplate = readSVGTemplate("/resources/svg/sudoku.svg");

        // Replace the placeholder with the component attribute value
        String svgContent = replacePlaceholder(svgTemplate, component);

        // Render the modified SVG content
        ResponseWriter writer = context.getResponseWriter();
        writer.write(svgContent);
    }

    private String readSVGTemplate(String filePath) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        InputStream inputStream = externalContext.getResourceAsStream(filePath);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = inputStream.read(buffer)) != -1;) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString(StandardCharsets.UTF_8.name());
    }

    private String replacePlaceholder(String svgTemplate, UIComponent component) {
        SudokuSVG sudokuSVG = (SudokuSVG) component;
        String puzzleString = (String) sudokuSVG.getValue();
        for (int i = 0; i < puzzleString.length(); i++) {
            String placeholder = "{CELL" + i + "}";
            svgTemplate = svgTemplate.replace(placeholder, String.valueOf(puzzleString.charAt(i)));
        }
        return svgTemplate;
    }

}
