package DAL;

import BE.Job;
import BE.User;

import java.util.List;

public interface ITechnicianDAO {
    List<Job> getWork(User selectedUser);

    void finishJob(Job selectedJob);
}
