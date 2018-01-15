package ua.com.testing.tag;

import ua.com.testing.handler.LogHandler;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;

public class DateTag extends TagSupport {

    @Override
    public int doStartTag() {
        try {
            pageContext.getOut().print(LocalDate.now());
        } catch (IOException e) {
            LogHandler.exceptionHandler(e);
        }

        return SKIP_BODY;
    }
}