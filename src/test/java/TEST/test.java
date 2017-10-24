package TEST;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import db.BaseDao;
import uitls.DbHelper;
import uitls.Utils;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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



    }

    public static void show(Utils.MDirectory directory){
        System.out.println(directory.files);
        for (Utils.MDirectory d : directory.directorys) {
            if(d.directorys.size() >0){
                show(d);
            }
        }
    }


}