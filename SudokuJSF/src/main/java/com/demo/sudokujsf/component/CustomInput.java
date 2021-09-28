package com.demo.sudokujsf.component;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Map;

@FacesComponent(createTag = true, namespace = "https://sudokujsf.com/demo",
        tagName = "custom-input", value = "com.demo.sudokujsf.component.CustomInput")
public class CustomInput extends UIInput {

    @Override
    public void decode(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        String clientId = getClientId(context);
        char sep = UINamingContainer.getSeparatorChar(context);
        String submitted_hello_msg = ((String) requestMap.get(clientId + sep + "inputfield"));
        setSubmittedValue(submitted_hello_msg);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        String clientId = getClientId(context);
        char sep = UINamingContainer.getSeparatorChar(context);
        encodeInputField(context, clientId + sep + "inputfield");
        encodeSubmitButton(context, clientId + sep + "submit");
        encodeOutputField(context);
    }

    private void encodeInputField(FacesContext context, String clientId) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("input", this);
        writer.writeAttribute("type", "text", null);
        writer.writeAttribute("name", clientId, "clientId");
        writer.writeAttribute("size", "30", null);
        Object value = getValue();
        if (value != null) {
            writer.writeAttribute("value", value.toString(), "value");
        }
        writer.writeAttribute("size", "6", null);
        writer.endElement("input");
    }

    private void encodeSubmitButton(FacesContext context, String clientId) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("input", this);
        writer.writeAttribute("type", "Submit", null);
        writer.writeAttribute("name", clientId, "clientId");
        writer.writeAttribute("value", "Click Me!", null);
        writer.endElement("input");
    }

    private void encodeOutputField(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String hellomsg = (String) getAttributes().get("value");
        writer.startElement("p", this);
        writer.writeText("You entered: " + hellomsg, null);
        writer.endElement("p");
    }

}
