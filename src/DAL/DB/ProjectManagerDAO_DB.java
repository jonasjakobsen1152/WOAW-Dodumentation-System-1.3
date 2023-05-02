package DAL.DB;

import BE.Job;
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

            ResultSet rs = stmt.executeQuery();

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
    public void deleteUser(User selectedUser) {
        try(Connection conn = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Users WHERE ID = ? AND Username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedUser.getId());
            stmt.setString(2,selectedUser.getUsername());

            stmt.executeUpdate();

        }catch (SQLException e){

        }
    }

    @Override
    public ArrayList<Job> getAllDocuments() {
        ArrayList<Job> jobs = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Job";

            PreparedStatement stmt = conn.prepareStatement(sql);


            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                int userID = rs.getInt("UserID");
                int customerID = rs.getInt("CustomerID");


                Job job = new Job(id,title,userID,customerID);
                jobs.add(job);
            }
        }catch (SQLException ex){
            throw new RuntimeException();
        }
        return jobs;
    }
    }

