package TEST;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import db.BaseDao;
import db.UserDao;
import modules.response.LoginResponse;
import sun.misc.BASE64Encoder;
import uitls.DbHelper;
import uitls.Utils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test {
    public static void main(String[] args) throws SQLException, IOException {
        Connection conn = BaseDao.getConnection();
//        String sql = "INSERT user_message (id,nickname,password) VALUES (3111,\"ad\",123456)";
//        Statement stmt = null;
//        ResultSet rs = null;
//        try{
//            stmt  = conn.createStatement();
//            stmt.execute(sql);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try {
//                stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }


//        Gson gson = new Gson();
//        File file = new File("/home/steve/桌面/out.txt");
//        file.createNewFile();
//        FileOutputStream fos = new FileOutputStream(file);
//        PrintWriter pr = new PrintWriter(fos);
//        pr.print(Utils.createJson("/home/steve/下载"));
//        String a = "ASD|BBB";
//        String[] t  = a.split("\\|");
//        for(String e : t){
//            System.out.println(e);
//        }

//        String photo = getImageStr("/home/steve/桌面/aa.png");
//        String sql = "UPDATE user_message SET avatar = "+Utils.parseString(photo) + " WHERE id=11111";
//        DbHelper.execute(UserDao.getConnection(),sql);



//        LoginResponse loginResponse = new LoginResponse(0,new UserDao(1,2,"asd","ASD","ASd","ASD,","SAD"));
//        System.out.println(new Gson().toJson(loginResponse));


    }

    static int   id = 111;


    public static void show(Utils.MDirectory directory){
        System.out.println(directory.files);
        for (Utils.MDirectory d : directory.directorys) {
            if(d.directorys.size() >0){
                show(d);
            }
        }
    }

    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


}