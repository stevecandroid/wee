package view;

import com.google.gson.Gson;
import db.UserDao;
import modules.request.LoginRequest;
import modules.request.RegisterRequest;
import modules.response.LoginResponse;
import modules.response.RegisterResponse;
import uitls.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "register",urlPatterns = "/register")
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getSession().getId());
        Gson gson = new Gson();
        String requestBody = Utils.getRequestBody(req.getInputStream());
        RegisterRequest request = gson.fromJson(requestBody,RegisterRequest.class);
        int id = UserDao.insert(request);
        RegisterResponse response = new RegisterResponse(0,id);
        resp.getWriter().write(gson.toJson(response));
    }
}
