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

    @Override
    public void createJob(String title, User selectedTechnician, Customer selectedCustomer) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "INSERT INTO Job (Title, UserID, CustomerID) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(selectedCustomer.getId());
            System.out.println(selectedTechnician.getId());

            stmt.setString(1,title);
            stmt.setInt(2,selectedTechnician.getId());
            stmt.setInt(3,selectedCustomer.getId());

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }

    }

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

                Customer customer = new Customer(id,name,phone,email);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return customers;
    }
}
