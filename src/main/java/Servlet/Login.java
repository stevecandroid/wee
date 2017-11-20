package Servlet;

import com.google.gson.Gson;
import db.UserDao;
import modules.request.LoginRequest;
import modules.response.LoginResponse;
import uitls.ResponseHelper;
import uitls.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends HttpServlet {

    private static final int PASSWORD_ERROR = -2;
    private static final int ACCOUNT_NOT_EXIST = -1;
    private static final int  ALREAD_LOGIN = -3;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        if(!req.getSession().isNew()) {
            ResponseHelper.write(resp,new LoginResponse(0, (Integer) req.getSession().getAttribute("user_id")));
        }else{
            ResponseHelper.write(resp,new LoginResponse(-1,-1));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

            UserDao user;
            Gson gson = new Gson();
            LoginResponse response;
            String requestBody = Utils.getRequestBody(req.getInputStream());
            LoginRequest request = gson.fromJson(requestBody, LoginRequest.class);

            System.out.println("login " +  requestBody);

            if(request.getEmail() != null){
                user = UserDao.query(request.getEmail());
            }else {
                user =  UserDao.query(request.getId());
            }

            if (user != null) {
                if (user.getPassword().equals(request.getPassword()) ) {
                    response = new LoginResponse(0,user.getId());
                    req.getSession().setAttribute("user_id",user.getId());
                } else if (user.getStatus() == 0 ) {
                    response = new LoginResponse(PASSWORD_ERROR,-1);
                }else{
                    response = new LoginResponse(ALREAD_LOGIN,user.getId());
                }
            } else {
                response = new LoginResponse(ACCOUNT_NOT_EXIST,user.getId());
            }

//            resp.getWriter().write(gson.toJson(response));
             ResponseHelper.write(resp,response);


    }
}
