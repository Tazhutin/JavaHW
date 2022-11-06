package work;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.*;


public class UsersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

        PrintWriter pw = responce.getWriter();

        //ЗАГРУЗКА ДРАЙВЕРА org.postgresql ИЗ ЗАВИСИМОСТЕЙ MAVEN
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/homeWork" ,
                    "potsgres" , "rootroot");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM USERS");

            while (rs.next()) {
                pw.println(rs.getString("username"));
            }

            stmt.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
