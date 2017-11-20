package Servlet;


import db.UserDao;
import uitls.ResponseHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteRecord",urlPatterns = "/record/delete")
public class Deleterecord extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        //删除对应聊天记录记录所对应的时间
        int userId = (int) req.getSession().getAttribute("user_id");
        int to = Integer.parseInt(req.getParameter("u"));
        long time = Long.parseLong(req.getParameter("t"));
        int direction = Integer.parseInt(req.getParameter("d"));

        if(UserDao.deleteRecord(userId,to,time,direction)){
            ResponseHelper.writeBasicSuccess(resp);
        }else{
            ResponseHelper.writeBasicError(resp,-1);
        }


    }
}
