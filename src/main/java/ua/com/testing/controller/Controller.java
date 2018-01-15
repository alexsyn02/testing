package ua.com.testing.controller;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface Controller {

    String processRequest(HttpServletRequest req) throws SQLException;
}
