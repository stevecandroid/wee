package Servlet;

import com.google.gson.Gson;
import db.User;
import db.UserDao;
import uitls.ResponseHelper;
import uitls.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "modifyUser" , urlPatterns = "/user/modify")
public class ModifyUser extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = Utils.getRequestBody(req.getInputStream());

        User user = new Gson().fromJson(json, User.class);

        System.out.println(user);

        int userId = (int) req.getSession().getAttribute("user_id");

        String path = getServletContext().getRealPath("/photo");
        File file = new File(path+"/"+userId+".jpg");
        if(!file.exists()) file.createNewFile();
        byte[] avatar = Utils.base64toBytes(user.getAvatar());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(avatar);
        System.out.println(path);

        try {
            UserDao.modifyNameAvatar(userId,user.getAvatar(),user.getNickname());
            ResponseHelper.writeBasicSuccess(resp);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResponseHelper.writeBasicError(resp,-1);


    }
}
