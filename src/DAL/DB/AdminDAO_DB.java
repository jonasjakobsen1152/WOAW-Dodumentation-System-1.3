package DAL.DB;

import BE.Customer;
import BE.Job;
import BE.User;
import DAL.IAdminDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO_DB implements IAdminDAO {
    private MyDatabaseConnector databaseConnector;

    public AdminDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public List<Job> getWork(User selectedUser) {
        ArrayList<Job> jobs = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT j.ID, j.Title, j.CustomerID " +
                             "FROM Job j " +
                             "INNER JOIN UserOnJob uoj ON j.ID = uoj.JobID " +
                             "WHERE uoj.UserID = ?")) {

            stmt.setInt(1, selectedUser.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                int customerId = rs.getInt("CustomerID");

                Job job = new Job(id, title, customerId);
                jobs.add(job);
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return jobs;
    }

    @Override
    public void deleteJob(Job selectedJob) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Job WHERE ID = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedJob.getId());


            stmt.executeUpdate();

        }catch (SQLException e){
            throw new SQLException(e);
        }
    }


    @Override
    public ArrayList<Job> getAllJobs() throws SQLException {
        ArrayList<Job> jobs = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Job";

            PreparedStatement stmt = conn.prepareStatement(sql);


            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                int customerID = rs.getInt("CustomerID");

                Job job = new Job(id,title,customerID);
                jobs.add(job);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        return jobs;
    }

    @Override
    public void deleteUser(User selectedUser) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Users WHERE ID = ? AND Username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedUser.getId());
            stmt.setString(2,selectedUser.getUsername());

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Customer WHERE ID = ? AND Name = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedCustomer.getId());
            stmt.setString(2,selectedCustomer.getName());

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomer() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement()){
            String sql = "Select * from Customer";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int phone = Integer.parseInt(rs.getString("Phone"));
                String email = rs.getString("Email");
                String address = rs.getString("Address");

                Customer customer = new Customer(id,name,phone,email, address);
                customers.add(customer);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        return customers;
    }


    @Override
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement()){
            String sql = "Select * from Users";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String role = rs.getString("Role");

                User user = new User(id,username,password,role);
                users.add(user);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        return users;
    }

}
