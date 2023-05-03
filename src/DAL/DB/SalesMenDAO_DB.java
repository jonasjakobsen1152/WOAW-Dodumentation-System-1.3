package DAL.DB;

import BE.Customer;
import BE.Job;
import DAL.ISalesmenDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;

public class SalesMenDAO_DB implements ISalesmenDAO {

    private MyDatabaseConnector databaseConnector;

    public SalesMenDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
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
        }
        return customers;
    }

    @Override
    public ArrayList<Job> getAllJobs(Customer selectedCustomer) throws SQLException {
        ArrayList<Job> jobs = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Job WHERE ID = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedCustomer.getId());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                int userID = rs.getInt("UserID");
                int customerID = rs.getInt("CustomerID");


                Job job = new Job(id,title,userID,customerID);
                jobs.add(job);
            }
        }
        return jobs;
    }
    }
