package resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

/**
 * Test the ResourceManager class
 */
public class ResourceManagerTest {
    ResourceManager manager;
    User user;

    /**
     * The setup method that setup each test.
     * It creates a ResourceManager and a user.
     */
    @BeforeEach
    public void setUp() {
        user = new User("Tong", "123@mail.com", "123");
        manager = new ResourceManager();
        manager.setCurrentUser(user);
    }

    /**
     * Test if addResource method correctly add a resource and getMapOfResource functions as intended
     */
    @Test
     public void testAddAndGetMapOfResource() {
        Resource resource1 = new Resource("This is not visible until you paid", manager.getNextId(),
                12, "Please download it.", user);
        manager.addResource(resource1.getContent(), resource1.getPointsRequired(), resource1.getDescription());
        Resource resource2 = new Resource("nothing", manager.getNextId(),
                0, "Please do not download it.", user);
        manager.addResource(resource2.getContent(), resource2.getPointsRequired(), resource2.getDescription());
        Assertions.assertTrue(manager.getMapOfResource().containsKey("Resource #0"));
        Assertions.assertTrue(manager.getMapOfResource().containsKey("Resource #1"));
        Assertions.assertEquals( resource1.toString(),manager.getMapOfResource().get("Resource #0").toString());
        Assertions.assertEquals(resource2.toString(),manager.getMapOfResource().get("Resource #1").toString());
    }

    /**
     * Test if deleteResource method correctly delete a resource
     */
    @Test
    void testDelete() {
        Resource resource1 = new Resource("This is not visible until you paid", manager.getNextId(),
                12, "Please download it.", user);
        manager.addResource(resource1.getContent(), resource1.getPointsRequired(), resource1.getDescription());
        Resource resource2 = new Resource("nothing", manager.getNextId(),
                0, "Please do not download it.", user);
        manager.addResource(resource2.getContent(), resource2.getPointsRequired(), resource2.getDescription());
        manager.deleteResource("Resource #0");
        Assertions.assertTrue(manager.getMapOfResource().containsKey("Resource #1"));
        Assertions.assertFalse(manager.getMapOfResource().get("Resource #0").visibility());
        Assertions.assertEquals(manager.getMapOfResource().get("Resource #1").toString(), resource2.toString());
        Assertions.assertEquals( 2,manager.getNumberOfResources());
    }

    /**
     * Test if getNextId method correctly return the ID of the next resource
     */
    @Test
    void testGetNextId() {
        Assertions.assertEquals(manager.getNextId(), "Resource #0");
        Resource resource1 = new Resource("This is not visible until you paid", manager.getNextId(),
                12, "Please download it.", user);
        manager.addResource(resource1.getContent(), resource1.getPointsRequired(), resource1.getDescription());
        Assertions.assertEquals("Resource #1",manager.getNextId());
    }

    /**
     * Test if downloadResource method functions as intended
     */
    @Test
    void testDownloadResource() {
        User otherUser = new User("Su", "123@mail.com", "123");
        Resource resource1 = new Resource("This is not visible until you paid", manager.getNextId(),
                12, "Please download it.", otherUser);
        manager.addResource(resource1.getContent(), resource1.getPointsRequired(), resource1.getDescription());
        String message1 = manager.downloadResource("Resource #0");
        Assertions.assertEquals("Sorry, you do not have enough points", message1);
        user.setRewardPoints(300);
        String message2 = manager.downloadResource("Resource #0");
        Assertions.assertEquals(resource1.getContent(),message2);
    }
}
