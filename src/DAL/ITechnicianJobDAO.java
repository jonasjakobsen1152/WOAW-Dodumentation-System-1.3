package DAL;

import BE.Documentation;
import BE.Job;
import BE.User;

import java.sql.SQLException;
import java.util.List;

public interface ITechnicianJobDAO {
    List<Documentation> getDocumentation(Job selectedJob) throws SQLException;

    void deleteDocumentation(Documentation selectedDocumentation) throws SQLException;
}
