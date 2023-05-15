package DAL;

import BE.Documentation;
import BE.JobImage;
import BE.Job;

import java.sql.SQLException;
import java.util.List;

public interface ITechnicianJobDAO {
    List<Documentation> getDocumentation(Job selectedJob) throws SQLException;

    void deleteDocumentation(Documentation selectedDocumentation) throws SQLException;

    List<JobImage> getImages(Job selectedJob) throws SQLException;

    void deleteImage(JobImage selectedJobImage);
}
