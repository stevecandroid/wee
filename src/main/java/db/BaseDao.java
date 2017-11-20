package db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    public static String USER_MESSAGE="user_message";
    public static String CHAT_RECORD="chat_record";
    private static String USER = "root";
    private static String PASSWORD = "123456";
    private static String DB_URL = "jdbc:mysql://localhost:3306/WeeChat?characterEncoding=utf8&useSSL=true";
    private static String DB_DRIVER = "com.mysql.jdbc.Driver";
    //  private static String SQL = "";
    private static Connection connection = null;

    //连接数据库
    public static Connection getConnection(){

        if(connection == null) {
            synchronized (BaseDao.class) {
                if(connection == null) {
                    try {
                        Class.forName(DB_DRIVER);
                        connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                    } catch (Exception e) {
                        System.out.println("数据库连接异常");
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public  static void closeConnection(Connection connection){

        if(connection != null){
            try {
                connection.close(); // 关闭数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

