package com.narwhal.authorization.web.index;

import com.google.inject.Singleton;
import com.narwhal.authorization.web.utils.HealthWebConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tomas de Priede
 */
@Singleton
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(HealthWebConstants.HOME_URL);
        dispatcher.include(req, resp);
    }
}
