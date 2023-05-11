package BLL.UTIL;

import BE.User;

import java.util.ArrayList;
import java.util.List;

public class TechnicianSearcher {

    public List<User> search(ArrayList<User> searchBase, String query) {
        List<User> searchResult = new ArrayList<>();

        for (User user: searchBase) {
            if(compareToUsername(query,user)){
                searchResult.add(user);
            }
        }

        return searchResult;
    }

    private boolean compareToUsername(String query, User user){
        return user.getUsername().toLowerCase().contains(query.toLowerCase());
    }

}
