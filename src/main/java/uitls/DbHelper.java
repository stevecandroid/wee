package uitls;

import java.sql.Connection;
import java.sql.SQLException;

public class DbHelper {
    public static void execute(Connection conn , String sql) throws SQLException {
            conn.createStatement().execute(sql);
    }
}
