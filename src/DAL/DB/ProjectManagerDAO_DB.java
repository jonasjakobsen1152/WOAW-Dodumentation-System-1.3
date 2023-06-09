package DAL.DB;

import BE.Customer;
import BE.Job;
import BE.User;
import DAL.IProjectManagerDAO;

import java.sql.*;
import java.util.ArrayList;

public class ProjectManagerDAO_DB implements IProjectManagerDAO {

    private MyDatabaseConnector databaseConnector;

    public ProjectManagerDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    /**
     * Gets all the users from the database based on the role.
     * @param role
     * @return
     */
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

    /**
     * Deletes the selected user from the database
     * @param selectedUser
     */
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

    /**
     * Gets all the jobs from the database.
     * @return
     */
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
                int customerID = rs.getInt("CustomerID");


                Job job = new Job(id,title,customerID);
                jobs.add(job);
            }
        }catch (SQLException ex){
            throw new RuntimeException();
        }
        return jobs;
    }

    /**
     * Gets all the customers from the database.
     * @return
     * @throws SQLException
     */
    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "Select * from Customer";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int phone = rs.getInt("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");

                Customer customer = new Customer(id,name,phone,email,address);
                customers.add(customer);
            }
        }
        return customers;
    }

    /**
     * Deletes the selected job from the database.
     * @param selectedDocument
     */
    @Override
    public void deleteDocument(Job selectedDocument) {
        try(Connection conn = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Job WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,selectedDocument.getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    /**
     * Deletes the selected customer from the database.
     * @param selectedCustomer
     * @throws SQLException
     */
    @Override
    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "DELETE FROM Customer WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,selectedCustomer.getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    /**
     * Deletes the selected job from the database.
     * @param selectedJob
     * @throws SQLException
     */
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

    /**
     * This is used to add a technician to a job.
     * @param selectedTechnician
     * @param selectedJob
     * @throws SQLException
     */
    @Override
    public void addTechToJob(User selectedTechnician, Job selectedJob) throws SQLException {
        try (Connection conn = databaseConnector.getConnection()){
            String sql = "INSERT INTO UserOnJob (JobID, UserID) VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedJob.getId());
            stmt.setInt(2,selectedTechnician.getId());

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new SQLException(e);

        }

    }
}

