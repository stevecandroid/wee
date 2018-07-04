package db;

import com.google.gson.Gson;
import modules.Message;
import uitls.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static db.BaseDao.USER_MESSAGE;

public class UserMapper {

    public static UserDao queryUser(Connection conn,String sql)  {
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);

        while(rs.next()){
            return new UserDao(
                    rs.getInt("id"),
                    rs.getInt("age"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getString("avatar"),
                    rs.getString("friends"),
                    rs.getInt("status"));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<UserDao> queryUsers(Connection conn,String sql)  {
        ResultSet rs = null;
        List<UserDao> users = new ArrayList<>();
        try {
            rs = conn.createStatement().executeQuery(sql);

            while(rs.next()){
                users.add( new UserDao(
                        rs.getInt("id"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("avatar"),
                        rs.getString("friends"),
                        rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static List<UserDao> queryUsers(Connection conn,String sql,int execId)  {
        ResultSet rs = null;
        List<UserDao> users = new ArrayList<>();
        try {
            rs = conn.createStatement().executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("id");
                if(id != execId)
                users.add( new UserDao(
                        id,
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("avatar"),
                        rs.getString("friends"),
                        rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static List<Message> queryRecord(Connection conn , String sql){

        ResultSet rs = null;
        List<Message> list = new ArrayList<>();
        try {
            rs = conn.createStatement().executeQuery(sql);

            while(rs.next()){
                String message = rs.getString("message");
                Message msg=  new Gson().fromJson(message,Message.class);
                int id = rs.getInt("id");
                long time = rs.getLong("time");
                int direction = rs.getInt("direction");
                msg.setDirection(direction);
                msg.setTime(time);
                list.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String  queryFriends(Connection conn , int id){
        UserDao user = UserDao.query(id);
        return user.friends;
    }



}
