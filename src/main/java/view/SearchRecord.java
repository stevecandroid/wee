package view;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import db.Message;
import db.UserDao;
import modules.response.SearchRecordResponse;
import uitls.DbHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "chatRecord",urlPatterns = "/record/search")
public class SearchRecord extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        System.out.print("get record");

        int from = ((UserDao)req.getSession().getAttribute("user")).getId();
        String to = req.getParameter("to");

        System.out.println("from" + from + " to " + to );
        List<Message> messages = UserDao.queryRecord(from,Integer.parseInt(to));


        System.out.println(new Gson().toJson(new SearchRecordResponse(0,messages)));
        resp.getWriter().write(new Gson().toJson(new SearchRecordResponse(0,messages)));

    }
}
