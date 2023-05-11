package BLL.UTIL;

import BE.Customer;
import BE.User;

import java.util.ArrayList;
import java.util.List;

public class UserSearcher {
    public List<User> search(ArrayList<User> searchBase, String query) {
        List<User> searchResult = new ArrayList<>();

        for (User user: searchBase) {
            if(compareToUsername(query,user) || compareToRole(query,user)){
                searchResult.add(user);
            }
        }

        return searchResult;
    }

    private boolean compareToUsername(String query, User user){
        return user.getUsername().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToRole(String query, User user){
        return user.getRole().toLowerCase().contains(query.toLowerCase());
    }
}
