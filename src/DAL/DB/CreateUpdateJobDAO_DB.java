package DAL.DB;

import BE.Customer;
import BE.User;
import DAL.ICreateUpdateJobDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
