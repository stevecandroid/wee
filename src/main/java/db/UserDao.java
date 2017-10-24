package db;

import modules.request.RegisterRequest;
import uitls.DbHelper;
import uitls.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

public class UserDao extends BaseDao {

    private int id;
    private int age;
    private String email;
    private transient String password;
    private String nickname;
    private String avatar;
    private transient String friends;

    public UserDao(int id, int age, String email, String password, String nickname, String avatar, String friends) {
        this.id = id;
        this.age = age;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.friends = friends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    private static Connection conn = getConnection() ;

    public static UserDao query(int id) {
        String sql = "SELECT id,age,email,password,nickname,avatar,friends FROM user_message WHERE id=" + id;
        return UserMapper.queryUser(conn, sql);
    }

    public static UserDao query(String email){
        String sql = "SELECT id,age,email,password,nickname,avatar,friends FROM user_message WHERE email="+ Utils.parseString(email);
        return UserMapper.queryUser(conn,sql);
    }

    public static List<UserDao> fuzzyQuery(String nickname){
        String sql = "SELECT id,age,email,password,nickname,avatar,friends FROM user_message WHERE nickname LIKE " +Utils.parseString('%'+nickname+'%');
        return  UserMapper.queryUsers(conn,sql);
    }

    public static int insert(RegisterRequest user){

        int id = getRandomId();
        while(query(id) != null){
            id = getRandomId();
        }

        String sql = "INSERT user_message (id,nickname,password) VALUES (" + id +",\"" + user.getNickname() + "\",\""+user.getPassword()+"\")";
        try{
            DbHelper.execute(conn,sql);
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public static void addFriend(int toUser , int friend , String friends){
        UserDao toFriend = query(friend);
        String change = friends+friend+SEPERATOR;
        String sql = "UPDATE user_message SET friends = " + Utils.parseString(change) + " WHERE id="+ toUser;
        String sql2 = "UPDATE user_message SET friends = " + Utils.parseString(toFriend.friends+toUser+SEPERATOR) + " WHERE id="+ toFriend.id;
        try {
            DbHelper.execute(conn,sql2);
            DbHelper.execute(conn,sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    private static final char SEPERATOR = '|';

    private static final int IDSIZE = 7;

    private static int getRandomId(){
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        while(builder.length() < IDSIZE) {
            builder.append(random.nextInt(10));
        }
        return Integer.parseInt(builder.toString());
    }


    @Override
    public String toString() {
        return this.nickname + ":" + this.id;
    }
}
