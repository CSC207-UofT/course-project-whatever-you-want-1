package user;

import graph.DirectedGraph;
import graphbuilders.GraphArchitect;
import user.User;
import user.UserManager;
import constants.BuiltInGraphs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * implement this test
 */
class UserManagerTest {
    final String USERNAME = "userName";
    final String EMAIL = "email@.com";
    final String PASSWORD = "password";
    final String ANOTHER_USERNAME = "anotherUsername";
    final String ANOTHER_EMAIL = "anotherEmail@.com";
    final String ANOTHER_PASSWORD = "anotherPassword";
    final String GRAPH = BuiltInGraphs.INTRODUCTORY_CS_SERIES;
    User user = new User(USERNAME, EMAIL, PASSWORD);

    UserManager userManager;
    Map<String, User> mapOfUser;

    DirectedGraph graph1;
    DirectedGraph graph2;
    DirectedGraph graph3;

    @BeforeEach
    void setUp() throws Exception {

        mapOfUser = new HashMap<>();
        mapOfUser.put(USERNAME, user);
        userManager = new UserManager();
        userManager.setCurrentUser(USERNAME);

        graph1 = GraphArchitect.setBuilderAndBuildGraph("Introductory CS Series");
        graph2 = GraphArchitect.setBuilderAndBuildGraph("Introductory CS Series");
        graph3 = GraphArchitect.setBuilderAndBuildGraph("Introductory CS Series");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddNewUse() throws Exception {
        userManager.addNewUser(ANOTHER_USERNAME, ANOTHER_EMAIL, ANOTHER_PASSWORD);

        User userToCompare = new User(ANOTHER_USERNAME, ANOTHER_EMAIL, ANOTHER_PASSWORD);
        assertEquals(userManager.getMapOfUser().get(ANOTHER_USERNAME).toString(), userToCompare.toString());
    }

    /**
     * test if the userName has been taken
     */
    @Test
    void testAddNewUserUnsuccessful() throws Exception {

        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                userManager.addNewUser("userName", "antherEmail@", "anotherPassword"));
        Assertions.assertEquals("This username has already been taken", thrown.getMessage());

        User userToCompare = new User(USERNAME, EMAIL, PASSWORD);
        assertEquals(userManager.getMapOfUser().get(USERNAME).toString(), userToCompare.toString());


    }


    @Test
    void testGetAUser() throws Exception {
        User anotherUser = new User(ANOTHER_USERNAME, ANOTHER_EMAIL, ANOTHER_PASSWORD);
        userManager.addNewUser(ANOTHER_USERNAME, ANOTHER_EMAIL, ANOTHER_PASSWORD);

        User userActual = userManager.getAUser(USERNAME);
        assertEquals(user, userActual);
        assertEquals(anotherUser.toString(), userManager.getAUser(ANOTHER_USERNAME).toString());
    }

    @Test
    void testGetAUserUnsuccessful() throws Exception {

        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                userManager.getAUser("anotherUserName"));
        Assertions.assertEquals("Cannot recognize this main.user", thrown.getMessage());
    }

    @Test
    void testAddGraphToCurrent() throws Exception {
        userManager.addGraphToCurrent(GRAPH);

        Map<String, DirectedGraph> mapToCompare = new HashMap<>();
        mapToCompare.put(GRAPH, graph1);

        assertEquals(mapToCompare.toString(), userManager.getCurrentUser().getMapOfGraph().toString());
        assertEquals(GRAPH, userManager.getCurrentUser().getMapOfGraph().get(GRAPH).toString());
        assertEquals(1, userManager.getCurrentUser().getMapOfGraph().size());
    }

    @Test
    void testAddGraphToCurrentUnsuccessful() throws Exception {
        userManager.addGraphToCurrent(GRAPH);

        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                userManager.addGraphToCurrent(GRAPH));
        Assertions.assertEquals("This main.graph already exists in this userinfo", thrown.getMessage());
    }

    @Test
    void testSetUserNameOfCurrent() throws Exception {
        userManager.setUserNameOfCurrent("newUserName");
        assertEquals("newUserName", userManager.getCurrentUser().getName());
    }

    @Test
    void testSetUserNameOfCurrentUnsuccessfulWithSameName() throws Exception {

        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                userManager.setUserNameOfCurrent("userName"));
        Assertions.assertEquals("Same username as your current one", thrown.getMessage());

    }

    @Test
    void testSetUserNameOfCurrentUnsuccessfulWithDifferentName() throws Exception {

        userManager.addNewUser("anotherUsername", "anotherEmail", "anotherPassword");
        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                userManager.setUserNameOfCurrent("anotherUsername"));
        Assertions.assertEquals("This username has already been taken", thrown.getMessage());
    }

    @Test
    void testSetCurrentUser() throws Exception {
        userManager.addNewUser(ANOTHER_USERNAME, ANOTHER_EMAIL, ANOTHER_PASSWORD);
        assertEquals(user, userManager.getCurrentUser());
        userManager.setCurrentUser(ANOTHER_USERNAME);
        assertEquals(ANOTHER_USERNAME, userManager.getCurrentUser().getName());
    }

    @Test
    void testSetCurrentUserUnsuccessful() throws Exception {
        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                userManager.setCurrentUser(ANOTHER_USERNAME));
        Assertions.assertEquals("Cannot recognize this main.user", thrown.getMessage());
    }
}