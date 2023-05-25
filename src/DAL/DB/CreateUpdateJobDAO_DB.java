package DAL.DB;

import BE.Customer;
import BE.User;
import DAL.ICreateUpdateJobDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;

public class CreateUpdateJobDAO_DB implements ICreateUpdateJobDAO {
    private MyDatabaseConnector databaseConnector;

    public CreateUpdateJobDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    /**
     * Creates a new job in the database
     * @param title
     * @param selectedTechnician
     * @param selectedCustomer
     * @throws SQLException
     */
    @Override
    public void createJob(String title, User selectedTechnician, Customer selectedCustomer) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()){
            // Turn off auto-commit mode to start a transaction
            conn.setAutoCommit(false);

            // Insert the job into the Job table
            String sql = "INSERT INTO Job (Title, CustomerID) VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,title);
            stmt.setInt(2,selectedCustomer.getId());
            stmt.executeUpdate();

            // Get the primary key of the newly created job
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                int jobId = rs.getInt(1);

                // Insert the user and job into the UserOnJob table
                String sql2 = "INSERT INTO UserOnJob(JobID, UserID) VALUES (?,?)";
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                stmt2.setInt(1, jobId);
                stmt2.setInt(2, selectedTechnician.getId());
                stmt2.executeUpdate();
            }

            // Commit the transaction
            conn.commit();
        }catch (SQLException e){
            throw new SQLException(e);
        }
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
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return customers;
    }
}
