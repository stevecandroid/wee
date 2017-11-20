package db;

import modules.Message;
import modules.request.RegisterRequest;
import uitls.DbHelper;
import uitls.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class UserDao extends User {


    public static final int ONLINE = 1;
    public static final int OFFLINE = 0;
    //好友列表的分隔符
    private static final String SEPERATOR = "|";
    //随机ID的长度
    private static final int IDSIZE = 7;
    //数据库连接
    private static Connection conn = getConnection();

    private transient String avatar;

    public UserDao(int id, int age, String email, String password, String nickname, String avatar, String friends, int status) {
        super(id, age, email, password, nickname, avatar, friends, status);
    }

    public static UserDao query(int id) {
        String sql = "SELECT id,age,email,password,nickname,avatar,friends,status FROM "+USER_MESSAGE+" WHERE id=" + id;
        return UserMapper.queryUser(conn, sql);
    }

    public static UserDao query(String email) {
        String sql = "SELECT id,age,email,password,nickname,avatar,friends,status FROM "+USER_MESSAGE+" WHERE email=" + Utils.parseString(email);
        return UserMapper.queryUser(conn, sql);
    }

    /**
     *
     * @param nickname 查询的名字
     * @param execId 去除的ID
     */
    public static List<UserDao> fuzzyQuery(String nickname,int execId) {
        String sql = "SELECT id,age,email,password,nickname,avatar,friends,status FROM "+USER_MESSAGE+" WHERE nickname LIKE " + Utils.parseString('%' + nickname + '%');
        return UserMapper.queryUsers(conn, sql,execId);
    }

    //注册
    public static int addUser(RegisterRequest user) throws SQLException {

        int id = getRandomId();
        while (query(id) != null) {
            id = getRandomId();
        }

        String sql = "INSERT "+USER_MESSAGE+" (id,nickname,password) VALUES (" + id + ",\"" + user.getNickname() + "\",\"" + user.getPassword() + "\")";

        DbHelper.execute(conn, sql);

        return id;
    }

    /**
     * 添加好友
     *
     * @param user    主动申请加好友的用户
     * @param target  申请的好友的id
     * @param friends 原好友
     */
    public static boolean addFriend(int user, int target, String friends) {

        //自己不能添加自己
        if (user == target) return false;

        String tagetFriends = queryFriends(target);
        String sql = "UPDATE "+USER_MESSAGE+" SET friends = " + Utils.parseString(friends + target + SEPERATOR) + " WHERE id=" + user;
        String sql2 = "UPDATE "+USER_MESSAGE+" SET friends = " + Utils.parseString(tagetFriends + user + SEPERATOR) + " WHERE id=" + target;

        //判断是否有重复添加
        boolean allowAdd = true;
        for (String f : friends.split("\\|")) {
            if (f.equals((String.valueOf(target)))) {
                allowAdd = false;
            }
        }

        if (allowAdd) {
            try {
                DbHelper.execute(conn, sql2);
                DbHelper.execute(conn, sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }
        return allowAdd;
    }

    /**
     * 删除好友
     *
     * @param user    主动删除好友的用户
     * @param target  被删除好友的id
     * @param friends 原好友
     */
    public static boolean deleteFriends(int user, int target, String friends) {
        String toFriend = queryFriends(target); //获得被删好友信息

        //删除好友后的好友列表
        String change = deleteFriend(friends, String.valueOf(target));
        //修改主动删除好友的用户的信息
        String sql = "UPDATE user_message SET friends = " + Utils.parseString(change) + " WHERE id=" + user;

        //被删除的用户修改好友列表
        String fChange = deleteFriend(toFriend, String.valueOf(user));
        //修改被删除的用户的信息
        String sql2 = "UPDATE user_message SET friends = " + Utils.parseString(fChange) + " WHERE id=" + target;

        if (friends.equals(change)) {
            System.out.println("did'nt change");
            return false;
        } else {
            try {
                DbHelper.execute(conn, sql);
                DbHelper.execute(conn, sql2);
                return true;
            } catch (SQLException e) {
                System.out.println(e.toString());
                return false;
            }
        }

    }

    //聊天记录
    public static List<Message> queryRecord(int from, int to) {
        String sql = "SELECT time,message,direction FROM "+CHAT_RECORD+" WHERE id=" + Utils.parseString(from + "-" + to);
        System.out.println(sql);
        return UserMapper.queryRecord(conn, sql);
    }

    //删除某条聊天记录
    public static boolean deleteRecord(int from , int to , long time , int direction){
        String sql = "DELETE FROM "+CHAT_RECORD+" WHERE id="
                +Utils.parseString(from+"-"+to)
                +" AND time="+time
                +" AND direction="+direction;
        try {
            DbHelper.execute(conn,sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //修改用户头像信息
    public static void modifyNameAvatar(int id, String avatar, String nickname) throws SQLException {

            if (avatar != null && nickname != null) {
                String sql = "UPDATE "+USER_MESSAGE+" SET avatar = " + Utils.parseString(avatar) + ","
                        + "nickname = " + Utils.parseString(nickname)
                        + " WHERE  id=" + id;
                DbHelper.execute(conn, sql);
            }

    }

    //删除好友
    private static String deleteFriend(String init, String friendId) {
        String[] all = init.split("\\|");
        StringBuilder result = new StringBuilder();
        for (String t : all) {
            if (!t.equals(friendId)) {
                result.append(t + "|");
            }
        }
        return result.toString();
    }

    //根据id查找好友
    public static String queryFriends(int id) {
        return UserMapper.queryFriends(conn, id);
    }

    public static void setOnline(int id){
        String sql = "UPDATE "+USER_MESSAGE+" SET status= "+ONLINE +" WHERE id=" + id;
        try {
            DbHelper.execute(conn,sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setOffLine(int id){
        String sql = "UPDATE "+USER_MESSAGE+" SET status="+OFFLINE +" WHERE id=" + id;
        try {
            DbHelper.execute(conn,sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取随机的id
    private static int getRandomId() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        while (builder.length() < IDSIZE) {
            builder.append(random.nextInt(10));
        }
        return Integer.parseInt(builder.toString());
    }

    @Override
    public String toString() {
        return this.nickname + ":" + this.id + ":" + this.email;
    }
}
