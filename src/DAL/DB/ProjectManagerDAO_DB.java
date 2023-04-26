package DAL.DB;

import BE.User;
import DAL.IProjectManagerDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectManagerDAO_DB implements IProjectManagerDAO {

    private MyDatabaseConnector databaseConnector;

    public ProjectManagerDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement()){
            String sql = "SELECT * FROM User";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String role = rs.getString("Role");

                User user = new User(id,username,password,role);
                users.add(user);
            }
        }catch (SQLException ex){
            throw new RuntimeException();
        }
        return users;
    }

    @Override
    public void deleteUser(User user) {

    }
}
