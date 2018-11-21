package org.itstep.controller.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yuliia Tesliuk on 11/20/2018
 */
public class UserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/user/userbasis.jsp";
    }
}
