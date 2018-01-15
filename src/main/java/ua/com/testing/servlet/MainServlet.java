package ua.com.testing.servlet;

import ua.com.testing.controller.Controller;
import ua.com.testing.handler.LogHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static ua.com.testing.util.UtilConstants.CONTROLLER_KEY;
import static ua.com.testing.util.UtilConstants.MAIN_SERVLET;
import static ua.com.testing.util.UtilConstants.PAGE_404;
import static ua.com.testing.util.UtilData.CONTROLLERS_MAP;

@WebServlet(MAIN_SERVLET)
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processUser(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processUser(req, resp);
    }

    private void processUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            String controllerKey = req.getParameter(CONTROLLER_KEY);
            Controller controller = CONTROLLERS_MAP.get(controllerKey);
            String pageToForward;

            if (controller != null) {
                pageToForward = controller.processRequest(req);
            } else {
                pageToForward = PAGE_404;
            }

            getServletContext().getRequestDispatcher(pageToForward).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            LogHandler.exceptionHandler(e);
        }
    }
}
