package DAL;

import BE.JobImage;

import java.sql.SQLException;

public interface IAddImageDAO {
    void addImage(JobImage jobImage) throws SQLException;
}
