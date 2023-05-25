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

    /**
     * Creates a new customer in the database
     * @param name
     * @param phone
     * @param email
     * @param address
     * @throws SQLException
     */
    @Override
    public void createCustomer(String name, int phone, String email, String address) throws SQLException {
        try (Connection conn = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Customer (Name, Phone, Email, Address) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, name);
            stmt.setInt(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, address);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * Changes the values of a customer en the database
     * @param customer
     */
    @Override
    public void updateCustomer(Customer customer) {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "UPDATE Customer SET Name = ?, Phone = ?, Email = ?, Address = ? WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, customer.getName());
            stmt.setInt(2, customer.getPhone());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4,customer.getAddress());
            stmt.setInt(5,customer.getId());

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all the customers from the database
     * @return
     * @throws SQLException
     */
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
                String address = rs.getString("Address");

                Customer customer = new Customer(id, name, phone, email, address);
                customers.add(customer);

            }
        }
        return customers;
    }
}
