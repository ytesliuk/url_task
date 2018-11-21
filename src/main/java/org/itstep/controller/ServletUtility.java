package org.itstep.controller;

import org.itstep.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.util.HashSet;
import java.util.Set;

public class ServletUtility {
    public static void setUserRole(HttpServletRequest request, User.ROLE role, String name) {
        HttpSession session = request.getSession();

        session.setAttribute("userName", name);
        session.setAttribute("role", role);
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        Set<String> loggedUsers = getLoggedUsers(request);
        return loggedUsers.stream().anyMatch(userName::equals);
    }

    public static void logIn(HttpServletRequest request, String userName){
        Set<String> loggedUsers = getLoggedUsers(request);
        loggedUsers.add(userName);
        setLoggedUsers(request, loggedUsers);
    }

    public static void logOut(HttpServletRequest request, String userName){
        Set<String> loggedUsers = getLoggedUsers(request);
        loggedUsers.remove(userName);
        setLoggedUsers(request, loggedUsers);
    }

    private static Set<String> getLoggedUsers(HttpServletRequest request) {
        return (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");
    }

    public static Set<String> getLoggedUsers(HttpSessionEvent httpSessionEvent) {
        return (HashSet<String>) httpSessionEvent.getSession().getServletContext()
                .getAttribute("loggedUsers");
    }

    private static void setLoggedUsers(HttpServletRequest request, Set<String> loggedUsers){
        request.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }

    public static void setLoggedUsers(HttpSession session, Set<String> loggedUsers){
        session.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }

    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("userName");
    }
}
