package DAL.DB;

import BE.Customer;
import DAL.ICustomerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO_DB implements ICustomerDAO {

    private MyDatabaseConnector databaseConnector;
    public CustomerDAO_DB(){
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
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(Customer customer) {

    }
}
