package DAL.DB;

import BE.User;
import DAL.IProjectManagerDAO;

import javax.management.relation.Role;
import java.sql.*;
import java.util.ArrayList;

public class ProjectManagerDAO_DB implements IProjectManagerDAO {

    private MyDatabaseConnector databaseConnector;

    public ProjectManagerDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public ArrayList<User> getAllUsers(String role) {
        ArrayList<User> users = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Users WHERE Role = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, role);

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");


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
