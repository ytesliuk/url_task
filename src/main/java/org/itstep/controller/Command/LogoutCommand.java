package org.itstep.controller.Command;

import org.itstep.controller.ServletUtility;
import org.itstep.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String name = ServletUtility.getUserName(request.getSession());
        ServletUtility.logOut(request,name);
        ServletUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");

        return "redirect: /index.jsp";
    }
}
