import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws IOException {

        String serverUrl = "jdbc:sqlserver://127.0.0.1";
        String dbUrl = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=watson";
        String user = "sa";
        String password = "topcon";

        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "net stop mssqlserver && ping 127.0.0.1 -n 2 > nul && net start mssqlserver");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }

        try {
            Connection conn = DriverManager.getConnection(dbUrl, user, password);  //  Establish Connection Object
            Statement statement = conn.createStatement();                        //  Create a SQL statement object to send to the database
            //  WORKS!
            statement.addBatch("drop database watson;"
                    + "create database watson;"
                    + "use watson;"
                    + "create table Words_tbl (word varchar(5) primary key(word));");
            statement.executeBatch();

        } catch (SQLException e) {
//					  e.printStackTrace();
        }

    }
}