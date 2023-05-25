package BLL.UTIL;

import BE.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserSearcherTest {
    @Test
    public void testSearch() {
        //Arrange
        ArrayList<User> searchBase = new ArrayList<>();
        searchBase.add(new User(1, "Peter", "1234", "admin"));
        searchBase.add(new User(2, "Emil", "1234", "projectManager"));
        searchBase.add(new User(3, "Karl", "1234", "technician"));
        //Act
        UserSearcher searcher = new UserSearcher();
        List<User> result = searcher.search(searchBase, "projectManager");
        //Assert
        assertEquals(1, result.size());
        assertEquals("Emil", result.get(0).getUsername());
    }


}