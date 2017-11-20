package Servlet;


import com.google.gson.Gson;
import db.UserDao;
import modules.Message;
import modules.response.SearchRecordResponse;
import uitls.ResponseHelper;

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

        int from = (int) req.getSession().getAttribute("user_id");
        String to = req.getParameter("to");

        List<Message> messages = UserDao.queryRecord(from,Integer.parseInt(to));

        System.out.println(new Gson().toJson(new SearchRecordResponse(0,messages)));
        ResponseHelper.write(resp,new SearchRecordResponse(0,messages));

    }
}
