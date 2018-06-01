package by.zhuk.buber.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class FooterProjectInfoTag extends TagSupport {
    private String auth;
    private String description;

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("<div class='footer'><p>" + auth + "</p><p>" + description + "</p></div>");
        } catch (IOException e) {

            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

}