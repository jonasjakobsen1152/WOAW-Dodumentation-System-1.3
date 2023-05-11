package BLL.UTIL;

import BE.Job;
import BE.User;

import java.util.ArrayList;
import java.util.List;

public class JobSearcher {
    public List<Job> search(ArrayList<Job> searchBase, String query) {
        List<Job> searchResult = new ArrayList<>();

        for (Job job: searchBase) {
            if(compareToUsername(query,job)){
                searchResult.add(job);
            }
        }

        return searchResult;
    }

    private boolean compareToUsername(String query, Job job){
        return job.getTitle().toLowerCase().contains(query.toLowerCase());
    }
}
