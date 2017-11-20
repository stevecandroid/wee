package Servlet;

import com.google.gson.Gson;
import db.UserDao;
import modules.request.RegisterRequest;
import modules.response.BaseResponse;
import uitls.ResponseHelper;
import uitls.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "register",urlPatterns = "/register")
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        System.out.println(req.getSession().getId());
        Gson gson = new Gson();
        String requestBody = Utils.getRequestBody(req.getInputStream());
        RegisterRequest request = gson.fromJson(requestBody,RegisterRequest.class);
        int id = 0;
        try {
            id = UserDao.addUser(request);
            resp.getWriter().write(gson.toJson(new BaseResponse(id)));
        } catch (SQLException e) {
            ResponseHelper.writeBasicError(resp,-1);
            e.printStackTrace();
        }

    }
}
