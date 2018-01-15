package ua.com.testing.filter;

import ua.com.testing.handler.LogHandler;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            req.setCharacterEncoding("UTF-8");
            chain.doFilter(req, resp);
        } catch (IOException | ServletException e) {
            LogHandler.exceptionHandler(e);
        }
    }

    @Override
    public void destroy() {

    }
}
