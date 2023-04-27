package DAL.DB;

import BE.User;
import DAL.ILoginDAO;

import java.sql.*;
import java.util.ArrayList;

public class LoginDAO_DB implements ILoginDAO {

    private MyDatabaseConnector databaseConnector;

    public LoginDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> allUsers = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection()) {
            //SQL string that gets all the information from the User tabel
            String sql = "Select * From dbo.Users WHERE Username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //stmt.setString();

            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {
                int id = rs.getInt("ID");
                String userName = rs.getString("Username");
                String userPassWord = rs.getString("Password");
                String role = rs.getString("Role");

                User user = new User(id, userName, userPassWord, role);
                allUsers.add(user);
            }
        }
        return allUsers;
    }
    }

