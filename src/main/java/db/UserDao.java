package db;

import modules.request.RegisterRequest;
import uitls.DbHelper;
import uitls.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class UserDao extends BaseDao {


    public static final int ONLINE = 1;
    public static final int OFFLINE = 0;
    //好友列表的分隔符
    private static final String SEPERATOR = "|";
    //随机ID的长度
    private static final int IDSIZE = 7;
    //数据库连接
    private static Connection conn = getConnection();

    private int id;
    private int age;
    private String email;
    private String nickname;
    private String avatar;
    private int status;
    private transient String password;
    private transient String friends;
    public UserDao(int id, int age, String email, String password, String nickname, String avatar, String friends,int status) {
        this.id = id;
        this.age = age;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.friends = friends;
        this.status = status;
    }

    public static UserDao query(int id) {
        String sql = "SELECT id,age,email,password,nickname,avatar,friends,status FROM user_message WHERE id=" + id;
        return UserMapper.queryUser(conn, sql);
    }

    public static UserDao query(String email) {
        String sql = "SELECT id,age,email,password,nickname,avatar,friends,status FROM user_message WHERE email=" + Utils.parseString(email);
        return UserMapper.queryUser(conn, sql);
    }

    public static List<UserDao> fuzzyQuery(String nickname) {
        String sql = "SELECT id,age,email,password,nickname,avatar,friends,status FROM user_message WHERE nickname LIKE " + Utils.parseString('%' + nickname + '%');
        return UserMapper.queryUsers(conn, sql);
    }

    //注册
    public static int addUser(RegisterRequest user) {

        int id = getRandomId();
        while (query(id) != null) {
            id = getRandomId();
        }

        String sql = "INSERT user_message (id,nickname,password) VALUES (" + id + ",\"" + user.getNickname() + "\",\"" + user.getPassword() + "\")";
        try {
            DbHelper.execute(conn, sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String sql = "UPDATE user_message SET friends = " + Utils.parseString(friends + target + SEPERATOR) + " WHERE id=" + user;
        String sql2 = "UPDATE user_message SET friends = " + Utils.parseString(tagetFriends + user + SEPERATOR) + " WHERE id=" + target;

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
        String sql = "SELECT time,message,direction FROM chat_record WHERE id=" + Utils.parseString(from + "-" + to);
        System.out.println(sql);
        return UserMapper.queryRecord(conn, sql);
    }

    //修改用户头像信息
    public static void modifyNameAvatar(int id, String avatar, String nickname) {
        try {

            if (avatar != null && nickname != null) {
                String sql = "UPDATE user_message SET avatar = " + Utils.parseString(avatar) + ","
                        + "nickname = " + Utils.parseString(nickname)
                        + " WHERE  id=" + id;
                DbHelper.execute(conn, sql);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        String sql = "UPDATE user_message SET status= "+ONLINE +" WHERE id=" + id;
        try {
            DbHelper.execute(conn,sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setOffLine(int id){
        String sql = "UPDATE user_message SET status="+OFFLINE +" WHERE id=" + id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return this.nickname + ":" + this.id + ":" + this.email;
    }
}
