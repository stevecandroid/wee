package Servlet;

import com.google.gson.Gson;
import db.User;
import db.UserDao;
import modules.response.SearchPeopleResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "searchPeople",urlPatterns = "/people/search")
public class SearchPeople extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        String id = req.getParameter("id");
        String email = req.getParameter("email");
        String nickname = req.getParameter("nickname");

        int from = (int) req.getSession().getAttribute("user_id");
        List<UserDao> users = new ArrayList<>();
        Set<Integer> addedUsers = new HashSet<>();

        if( nickname!= null){
            List<UserDao> ds = UserDao.fuzzyQuery(nickname,from);
            if(ds.size()>0)
            users.addAll(ds);

            for(User u : ds){
                addedUsers.add(u.getId());
                u.setAvatar("");
            }
        }

        //三种查询方法
        if(id != null) {
            int mid = Integer.parseInt(id);
            if (mid != -1) {
                UserDao d = UserDao.query(mid);
                if( null != d && !addedUsers.contains(d.getId()) )
                    users.add(d);
            }
        }


        if ( email != null){
            UserDao d = UserDao.query(email);
            if(d != null && !addedUsers.contains(d.getId()))
                users.add(d);
        }



//        System.out.println(users.size());

        SearchPeopleResponse response = new SearchPeopleResponse(0,users);
        if(users.size() > 0 ){
            resp.getWriter().write(new Gson().toJson(response));
        }else{
            response.setStatus(-1);
            resp.getWriter().write(new Gson().toJson(response));
        }

    }
}
