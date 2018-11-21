package org.itstep.controller.Command;

import org.itstep.controller.ServletUtility;
import org.itstep.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");


        if (name == null || name.equals("") || pass == null || pass.equals("")) {
            return "/login.jsp";
        }
        if (ServletUtility.checkUserIsLogged(request, name)) {
            return "/WEB-INF/error.jsp";
        }

        User.ROLE role = getUserRole(name);
        ServletUtility.logIn(request, name);
        ServletUtility.setUserRole(request, role, name);

        return getRedirectPath(role);
    }

    private User.ROLE getUserRole(String name) {
        try {
            Properties properties = new Properties();
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream is = cl.getResourceAsStream("users.properties");
            properties.load(is);

            String roleStr = properties.getProperty(name, User.ROLE.UNKNOWN.toString());
            return User.ROLE.valueOf(roleStr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return User.ROLE.UNKNOWN;
    }

    private String getRedirectPath(User.ROLE role) {
        if (role == User.ROLE.ADMIN) {
            return "redirect: /admin";
        } else if (role == User.ROLE.USER) {
            return "redirect: /user";
        } else return "/login.jsp";
    }
}