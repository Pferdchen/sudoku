/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

// FormRenderer.java

package renderkits.renderkit.svg;

import jakarta.faces.FactoryFinder;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIForm;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.lifecycle.Lifecycle;
import jakarta.faces.lifecycle.LifecycleFactory;
import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

/**
 * <B>FormRenderer</B> is a class that renders a <code>UIForm<code> as a Form.
 * This class specifically renders <code>SVG</code> markup - a <code><g></code>
 * element.  The element is rendered with the necessary attributes to facilitate
 * a form submission.
 */

public class FormRenderer extends BaseRenderer {

    private Lifecycle lifecycle = null;

    private static final String RENDERED_SCRIPT = "demo.RENDERED_SCRIPT";

    //
    // Protected Constants
    //

    //
    // Class Variables
    //

    //
    // Instance Variables
    //

    // Attribute Instance Variables

    // Relationship Instance Variables

    //
    // Constructors and Initializers    
    //

    public FormRenderer() {
        super();
    }

    //
    // Class methods
    //

    //
    // General Methods
    //

    //
    // Methods From Renderer
    //

    /**
     * Determine if this form caused the submission.
     * Install a <code>PhaseListener</code> that will listen for
     * <code>XMLHttpRequest(s)</code>.
     *
     * @see ResponsePhaseListener
     */
    public void decode(FacesContext context, UIComponent component) {
        // Was our form the one that was submitted?  If so, we need to set
        // the indicator accordingly..
        //
        String clientId = component.getClientId(context);
        if (logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER,
                       "Begin decoding component " + component.getId());
        }
        Map<String, String> requestParameterMap = context.getExternalContext()
              .getRequestParameterMap();
        if (requestParameterMap.containsKey(clientId)) {
            ((UIForm) component).setSubmitted(true);
        } else {
            ((UIForm) component).setSubmitted(false);
        }
        if (logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER,
                       "End decoding component " + component.getId());
        }
        getLifecycle(context).addPhaseListener(new ResponsePhaseListener());
    }


    /** Render the starting <code><g></code> element. */
    public void encodeBegin(FacesContext context, UIComponent component)
          throws IOException {
        String styleClass = null;

        if (context == null || component == null) {
            //PENDING - i18n
            throw new NullPointerException(
                  "'context' and/or 'component' is null");
        }
        if (logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER, "Begin encoding component " +
                                    component.getId());
        }
        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "Begin encoding component " +
                                        component.getId() + " since " +
                                        "rendered attribute is set to false ");
            }
            return;
        }
        ResponseWriter writer = context.getResponseWriter();

        // Only render the main script elements once per request.
        if (!context.getExternalContext().getRequestMap()
              .containsKey(RENDERED_SCRIPT)) {
            context.getExternalContext().getRequestMap().put(RENDERED_SCRIPT,
                                                             Boolean.TRUE);
            writer.startElement("script", component);
            writer.writeAttribute("xlink:href",
                                  context.getExternalContext()
                                        .getRequestContextPath()
                                  + "/src/script/http-svg.es", null);
            writer.endElement("script");
            writer.writeText("\n", null);
            writer.startElement("script", component);
            writer.writeAttribute("xlink:href",
                                  context.getExternalContext()
                                        .getRequestContextPath()
                                  + "/src/script/lifecycle.es", null);
            writer.endElement("script");
            writer.writeText("\n", null);
        }

        writer.startElement("g", component);
        writer.writeAttribute("id", component.getClientId(context), "clientId");
        writer.writeAttribute("method", "post", null);
        writer.writeAttribute("action", getActionStr(context), null);
        String acceptcharset = null;
        if (null != (acceptcharset = (String)
              component.getAttributes().get("acceptcharset"))) {
            writer.writeAttribute("accept-charset", acceptcharset,
                                  "acceptcharset");
        }

        writer.writeText("\n", null);
    }


    /**
     * <p>Return the value to be rendered as the <code>action</code> attribute
     * of the form generated for this component.</p>
     *
     * @param context FacesContext for the response we are creating
     */
    private String getActionStr(FacesContext context) {
        String viewId = context.getViewRoot().getViewId();
        String actionURL =
              context.getApplication().getViewHandler().
                    getActionURL(context, viewId);
        return (context.getExternalContext().encodeActionURL(actionURL));
    }


    /**
     * Render the necessary <code>ECMAScript</code> to facilitate a
     * form submission.  Render the closing <code><g></code> element.
     */
    public void encodeEnd(FacesContext context, UIComponent component)
          throws IOException {
        if (context == null || component == null) {
            //PENDING - i18n
            throw new NullPointerException(
                  "'context' and/or 'component' is null");
        }
        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "End encoding component " +
                                        component.getId() + " since " +
                                        "rendered attribute is set to false ");
            }
            return;
        }

        context.getApplication().getViewHandler().writeState(context);

        // Render the end tag for form
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("g");
        writer.writeText("\n", null);
        if (logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER, "End encoding component " +
                                    component.getId());
        }

        buildPost(context, component);
    }

    /** Helper method to render the <code>ECMAScript</code>. */
    private void buildPost(FacesContext context, UIComponent component)
          throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("script", component);
        writer.writeText("<![CDATA[\n", null);
        String formMethodName = component.getClientId(context) + "_post";
        String formMethodText = "function " + formMethodName + "(evt) {\n";

        // write out global variables

        writer.writeText(formMethodText, null);
        writer.writeText("  var control = evt.target;\n", null);
        writer.writeText("  var form = getForm(control);\n", null);
        writer.writeText("  var postData = getPostData(form, control);\n",
                         null);
        writer.writeText("  var url = \"", null);
        writer.writeText(getActionStr(context), null);
        writer.writeText("\";\n", null);
        writer.writeText("  sendRequest(url, postData);\n", null);
        writer.writeText("}\n", null);
        writer.writeText("//]]>\n", null);
        writer.endElement("script");
    }

    /** Helper method used to install <code>PhaseListener</code>. */
    private Lifecycle getLifecycle(FacesContext context) {
        if (null != lifecycle) {
            return lifecycle;
        }
        LifecycleFactory lifecycleFactory = (LifecycleFactory)
              FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        String lifecycleId =
              ((ServletContext) context.getExternalContext().getContext())
                    .getInitParameter
                          (FacesServlet.LIFECYCLE_ID_ATTR);
        if (lifecycleId == null) {
            lifecycleId = LifecycleFactory.DEFAULT_LIFECYCLE;
        }
        lifecycle = lifecycleFactory.getLifecycle(lifecycleId);

        return lifecycle;
    }


} // end of class FormRenderer