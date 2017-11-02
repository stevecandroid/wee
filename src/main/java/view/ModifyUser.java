package view;

import com.google.gson.Gson;
import db.UserDao;
import modules.response.BaseResponse;
import uitls.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "modifyUser" , urlPatterns = "/user/modify")
public class ModifyUser extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = Utils.getRequestBody(req.getInputStream());

        UserDao user = new Gson().fromJson(json,UserDao.class);

        System.out.println(user);

        int userId = (int) req.getSession().getAttribute("user_id");

        UserDao.modifyNameAvatar(userId,user.getAvatar(),user.getNickname());

        resp.getWriter().write(new Gson().toJson(new BaseResponse(0)));

    }
}
