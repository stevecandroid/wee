package view;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import db.UserDao;
import modules.response.BaseResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "friends" , urlPatterns = "/friends")
public class Friends extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao user = (UserDao) req.getSession().getAttribute("user");
//        user.getFriends()
        JsonObject json = new JsonObject();
        json.add("status",new JsonPrimitive(0));
        json.add("friends",new JsonPrimitive(user.getFriends()));
        resp.getWriter().write(new Gson().toJson(json));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao user = (UserDao) req.getSession().getAttribute("user");
        String id = (String) req.getParameter("id");
        System.out.println(id);
        UserDao.addFriend(user.getId(),Integer.parseInt(id),user.getFriends());
        resp.getWriter().write(new Gson().toJson(new BaseResponse(0)));
    }



}
