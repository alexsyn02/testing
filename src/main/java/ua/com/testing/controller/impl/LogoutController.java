package ua.com.testing.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.testing.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

import static ua.com.testing.util.UtilConstants.CURRENT_LOCALE;
import static ua.com.testing.util.UtilConstants.INDEX_PAGE;
import static ua.com.testing.util.UtilConstants.LOG_OUT_EVENT;

public class LogoutController implements Controller {

    private final Logger LOGGER = LogManager.getLogger();

    @Override
    public String processRequest(HttpServletRequest request) throws SQLException {

        HttpSession session = request.getSession();
        Object currentLocale = session.getAttribute(CURRENT_LOCALE);

        session.invalidate();

        session = request.getSession(true);
        session.setAttribute(CURRENT_LOCALE, currentLocale);

        LOGGER.info(LOG_OUT_EVENT);

        return INDEX_PAGE;
    }
}
