package view;

import com.google.gson.Gson;
import db.UserDao;
import modules.response.SearchPeopleResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchPeople",urlPatterns = "/people/search")
public class SearchPeople extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String email = req.getParameter("email");
        String nickname = req.getParameter("nickname");
        System.out.println(id);
        System.out.println(email);
        System.out.println(nickname);
        List<UserDao> users = new ArrayList<UserDao>();
        if(id != null){
            users.add(UserDao.query(Integer.parseInt(id)));
        }else if ( email != null){
            users.add( UserDao.query(email));
        }else{
            users.addAll(UserDao.fuzzyQuery(nickname));
        }
        SearchPeopleResponse response = new SearchPeopleResponse(0,users);
        if(users.size() > 0 ){
            System.out.println(new Gson().toJson(response));
            resp.getWriter().write(new Gson().toJson(response));
        }else{
            response.setStatus(-1);
            resp.getWriter().write(new Gson().toJson(response));
        }

    }
}
