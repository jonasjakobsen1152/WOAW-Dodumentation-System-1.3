package DAL.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class MyDatabaseConnector {
    //Class will easv.mrs.be included when we start working on DATABASES
    private SQLServerDataSource dataSource;

    /**
     * Connects to the database
     */
    public MyDatabaseConnector()
    {
        //Gets connection to the database
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.34");
        dataSource.setDatabaseName("WUAV_Special_Koderne");
        dataSource.setUser("CSe2022A_e_32");
        dataSource.setPassword("CSe2022AE32#");
        dataSource.setTrustServerCertificate(true);

    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }


    public static void main(String[] args) throws SQLException {

        MyDatabaseConnector databaseConnector = new MyDatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            System.out.println("Is it open? " + !connection.isClosed());

        } //Connection gets closed here
    }
}
