package org.itstep.controller;

import org.itstep.controller.Command.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand());
        commands.put("exception" , new ExceptionCommand());
        commands.put("admin", new AdminCommand());
        commands.put("user", new UserCommand());

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Command command = getCommand(request);
        String page = command.execute(request);

        if (page.contains("redirect: ")) {
            response.sendRedirect(request.getContextPath() + page.replaceAll("redirect: ", ""));
        } else {
            request.getRequestDispatcher(page).forward(request,response);
        }
    }

    private Command getCommand(HttpServletRequest request){
        String path = request.getRequestURI();
        path = path.substring(path.lastIndexOf("/") + 1);

        return commands.getOrDefault(path, (r)->"/index.jsp");
    }
}

