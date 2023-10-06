package com.demo.sudokujsf.renderer;

import com.demo.sudokujsf.component.SudokuSVG;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;
import java.io.ByteArrayOutputStream;
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
     * @throws java.io.IOException
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
        String svgContent = drawNumbers(svgTemplate, component);

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

    /**
     * Regex for replacing XML comments
     * <pre>
     * {@code
     * <!--(?!-?>)(?:[^<-]|<(?!!--(?!>))|-(?!-!>))*?(?<!<!-)-->
     * }
     * </pre>
     *
     * @see https://skeptric.com/html-comment-regexp/index.html
     */
    private static final String XML_COMMENT_REGEX
            = "<!--(?!-?>)(?:[^<-]|<(?!!--(?!>))|-(?!-!>))*?(?<!<!-)-->";

    /**
     * <pre>
     * {@code
     * <g font-size="1129px" font-style="normal" font-weight="400">
     * }
     * </pre>
     */
    private static final String NUMBER_BLOCK_BEGIN
            = "<g font-size=\"1129px\" font-style=\"normal\" font-weight=\"400\">";

    /**
     * <pre>
     * {@code
     * <g fill="rgb(0,0,0)" stroke="none">
     *     <text x="939" y="1600">{CELL0}</text>
     * </g>
     * }
     * </pre>
     */
    private static final String X_Y_FORMAT
            = "<g fill=\"rgb(0,0,0)\" stroke=\"none\">"
            + "<text x=\"%d\" y=\"%d\">%c</text>"
            + "</g>";

    private static final int START_X = 900;

    private static final int START_Y = 1600;

    private static final String NUMBER_BLOCK_END = "</g>";

    private static final String NUMBER_BLOCK_PLACEHOLDER = "{NUMBER_BLOCK}";

    private String drawNumbers(String svgTemplate, UIComponent component) {
        String result = svgTemplate.replaceAll(XML_COMMENT_REGEX, "");
        SudokuSVG sudokuSVG = (SudokuSVG) component;
        String puzzleString = (String) sudokuSVG.getValue();
        StringBuilder numbers = new StringBuilder(NUMBER_BLOCK_BEGIN);
        for (int i = 0; i < puzzleString.length(); i++) {
            int x = START_X + 1445 * (i % 9);
            int y = START_Y + 1447 * (i / 9);
            String n = String.format(X_Y_FORMAT, x, y, puzzleString.charAt(i));
            numbers.append(n);
        }
        numbers.append(NUMBER_BLOCK_END);
        return result.replace(NUMBER_BLOCK_PLACEHOLDER, numbers.toString());
    }

}
