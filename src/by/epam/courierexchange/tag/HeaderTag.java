package by.epam.courierexchange.tag;

import by.epam.courierexchange.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HeaderTag extends TagSupport {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        StringBuilder header = new StringBuilder();
        header.append(user.getLastName());
        header.append(" (");
        header.append(user.getRole().getRole());
        header.append(")");
        try {
            out.write(header.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
