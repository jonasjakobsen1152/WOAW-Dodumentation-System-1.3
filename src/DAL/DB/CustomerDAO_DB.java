package DAL.DB;

import BE.Customer;
import BE.User;
import DAL.ICustomerDAO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO_DB implements ICustomerDAO {

    private MyDatabaseConnector databaseConnector;

    public CustomerDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public void createCustomer(String name, int phone, String email) throws SQLException {
        try (Connection conn = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Customer (Name, Phone, Email) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, name);
            stmt.setInt(2, phone);
            stmt.setString(3, email);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "UPDATE Customer SET Name = ?, Phone = ?, Email = ? WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, customer.getName());
            stmt.setInt(2, customer.getPhone());
            stmt.setString(3, customer.getEmail());
            stmt.setInt(4,customer.getId());

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public ArrayList<Customer> getAllCustomer() throws SQLException {
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
        }
        return customers;
    }
}
