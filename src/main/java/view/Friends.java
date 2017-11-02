package view;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import db.UserDao;
import modules.response.BaseResponse;
import uitls.ResponseHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "friends" , urlPatterns = "/friends")
public class Friends extends HttpServlet {

    public static final int DELETE_FAIL = -2;
    public static final int ALREAD_ADD = -1 ;
    public static final String ACTION_ADD = "1";
    public static final String ACTION_DELETE = "2";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        int userId = (int) req.getSession().getAttribute("user_id");
        String friends = UserDao.queryFriends(userId);


        JsonObject json = new JsonObject();
        json.add("status",new JsonPrimitive(0));
        json.add("friends",new JsonPrimitive(friends));
        resp.getWriter().write(new Gson().toJson(json));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        //发起着id
        int userId = (int) req.getSession().getAttribute("user_id");

        //请求参数
        String action = req.getParameter("action");//添加还是删除 1添加 2删除
        String id = req.getParameter("id");// 添加或删除对象的id
        String friends = UserDao.queryFriends(userId); //得到发起者的好友列表,用于比较修改

        switch (action){
            case ACTION_ADD :
                //添加好友
                boolean success = UserDao.addFriend(userId, Integer.parseInt(id), friends);
                //状态回应
                if (success) ResponseHelper.writeBasicSuccess(resp);
                else ResponseHelper.writeBasicError(resp,ALREAD_ADD);

                break;

            case ACTION_DELETE:
                //删除好友
                boolean succ = UserDao.deleteFriends(userId,Integer.parseInt(id),friends);
                //状态回应
                if(succ) ResponseHelper.writeBasicSuccess(resp);
                else ResponseHelper.writeBasicError(resp,DELETE_FAIL);
                break;


        }







    }



}
