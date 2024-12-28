package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    private static final String[] OPEN_PATHS = {"/login.jsp", "/register.jsp", "/test.jsp", "/profile.jsp"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();

        boolean isOpenPath = false;
        for (String openPath : OPEN_PATHS) {
            if (path.startsWith(openPath)) {
                isOpenPath = true;
                break;
            }
        }

        // Allow access if it's an open path or user is logged in
        if (isOpenPath || (httpRequest.getSession(false) != null && httpRequest.getSession().getAttribute("user") != null)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}