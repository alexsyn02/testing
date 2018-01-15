package ua.com.testing.filter;

import ua.com.testing.entity.user.User;
import ua.com.testing.entity.user.UserRole;
import ua.com.testing.handler.LogHandler;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static ua.com.testing.util.UtilConstants.*;
import static ua.com.testing.util.UtilData.*;

/**
 * The {@code LoginRequiredFilter} class is the filter, that checks
 * if current user has rights to access the requested url
 */
@WebFilter("*" + EXTENSION)
public class LoginRequiredFilter implements Filter {
    private RequestDispatcher requestDispatcher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        try {
            if (checkUserRights(httpServletRequest)) {
                chain.doFilter(servletRequest, servletResponse);
            } else {
                requestDispatcher.forward(servletRequest, servletResponse);
            }
        } catch (IOException | ServletException e) {
            LogHandler.exceptionHandler(e);
        }
    }

    private boolean checkUserRights(HttpServletRequest httpServletRequest) {

        boolean checkingResult = false;
        HttpSession session = httpServletRequest.getSession(true);
        User currentUser = (User) session.getAttribute(CURRENT_USER);
        String url = substringRequestURI(httpServletRequest);

        if (currentUser == null) {
            requestDispatcher = httpServletRequest.getRequestDispatcher(INDEX_PAGE);
            checkingResult = checkUrlPresenceInUrlSet(GUEST_URL_SET, url);
            return checkingResult;
        }

        UserRole userRole = currentUser.getUserRole();

        if (userRole == UserRole.TUTOR) {
            requestDispatcher = httpServletRequest.getRequestDispatcher(TUTOR_TESTS_PAGE);
            checkingResult = checkUrlPresenceInUrlSet(TUTOR_URL_SET, url);
        } else {
            requestDispatcher = httpServletRequest.getRequestDispatcher(STUDENT_PAGE);
            checkingResult = checkUrlPresenceInUrlSet(STUDENT_URL_SET, url);
        }

        return checkingResult;
    }

    private boolean checkUrlPresenceInUrlSet(Set<String> userUrlSet, String url) {
        return userUrlSet.contains(url);
    }

    private String substringRequestURI(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
    }

    @Override
    public void destroy() {}
}