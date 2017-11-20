package uitls;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Utils {

    public static String getRequestBody(InputStream is) {

        int len;
        StringBuilder rawReq = new StringBuilder();
        byte[] temp = new byte[1024];

        try {
            while ((len = is.read(temp)) != -1) {
                rawReq.append(new String(temp, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawReq.toString();
    }

    public static String parseString(String str){
        return "\'" + str + "\'";
    }


    public static JsonObject createJson(String root) {
        JsonPrimitive element = new JsonPrimitive(root);
        JsonArray filearray = new JsonArray();
        JsonArray dirarray = new JsonArray();
        JsonObject json = new JsonObject();
        File file = new File(root);
        json.add("path",element);
        File[] files = file.listFiles();
        for(File f : files ){
            if(f.isFile()){
                filearray.add(f.getPath());
            }else{
                dirarray.add(createJson(f.getPath()));
            }
        }
        json.add("files",filearray);
        json.add("directorys",dirarray);
        return json;
    }

    public class MDirectory {
        public String path;
        public List<String> files;
        public List<MDirectory> directorys;
    }

    public static byte[] base64toBytes(String str) {
          BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
