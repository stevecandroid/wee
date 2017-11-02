package uitls;

import com.google.gson.Gson;
import modules.response.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseHelper {

    public static void write(HttpServletResponse resp,Object o) throws IOException {
        resp.getWriter().write(new Gson().toJson(o));
    }

    public static void writeBasicError(HttpServletResponse resp,int errorcode ) throws IOException {
         write(resp,new BaseResponse(errorcode));
    }

    public static void writeBasicSuccess(HttpServletResponse resp) throws IOException {
        write(resp,new BaseResponse(0));
    }
}
