package view;

import com.google.gson.Gson;
import db.UserDao;
import modules.request.LoginRequest;
import modules.response.LoginResponse;
import uitls.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;

public class Login extends HttpServlet {

    private static final int PASSWORD_ERROR = -2;
    private static final int ACCOUNT_NOT_EXIST = -1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("testCookie", "!@#!@#@!#@!");
        resp.addCookie(cookie);
        resp.addCookie(new Cookie("ASDASDASDSA", "ASDASDSAD"));


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        System.out.println(req.getSession().getId());
//        System.out.println(req.getRemoteAddr());
//
//
//        while(name.hasMoreElements()){
//            System.out.println(name.nextElement());
//        }
            UserDao user;
            Gson gson = new Gson();
            LoginResponse response;
            String requestBody = Utils.getRequestBody(req.getInputStream());
            LoginRequest request = gson.fromJson(requestBody, LoginRequest.class);

            if(request.getEmail() != null){
                user = UserDao.query(request.getEmail());
            }else{
                user =  UserDao.query(request.getId());
            }

            if (user != null) {
                if (user.getPassword().equals(request.getPassword())) {
                    response = new LoginResponse(0);
                    req.getSession().setAttribute("user",user);
                } else {
                    response = new LoginResponse(PASSWORD_ERROR);
                }
            } else {
                response = new LoginResponse(ACCOUNT_NOT_EXIST);
            }

            resp.getWriter().write(gson.toJson(response));



    }
}
