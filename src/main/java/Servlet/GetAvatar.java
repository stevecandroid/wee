package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "avatar",urlPatterns = "/avatar")
public class GetAvatar extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        File file = new File(getServletContext().getRealPath("/photo/"+id+".jpg"));
        FileInputStream is;
        if(file.exists()) {
            is = new FileInputStream(file);
        }else{
            is = new FileInputStream(new File(getServletContext().getRealPath("/photo/head.jpg")));
        }
        byte[] bytes = new byte[1024] ;
        int length ;
        while( (length = is.read(bytes)) != -1) {
            resp.getOutputStream().write(bytes,0,length);
        }
    }
}
