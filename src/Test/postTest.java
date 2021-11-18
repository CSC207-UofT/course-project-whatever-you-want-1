package Test;

import Posts.Comment;
import Posts.HasPublishedContents;
import Posts.Post;
import User.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import User.UserInfo;

import java.util.HashMap;

import static org.junit.Assert.*;

public class postTest {
    Post post;
    User user;

    @Before
    public void setUp() {
        UserInfo userInfo = new UserInfo("Tong", "123@mail.com", "123");
        user = new User(userInfo);
        post = new Post("nothing", "0", user);
        post.addPublishedContent("testing", user);
    }

    @After
    public void tearDown() {

    }

    @Test(timeout = 500)
    public void testAddCommentSuccessful() {
        Comment c1 = new Comment("testing", "Comment #0", user);

        HashMap<String, Comment> mapToCompare = new HashMap<>();

        mapToCompare.put("Comment #0", c1);

        assertEquals(mapToCompare.toString(), post.getMapOfComments().toString());

        assertEquals(1, post.getNumberOfComments());
    }

    @Test(timeout = 500)
    public void testAddCommentUnsuccessful() {}

    /**
     * TODO: implement this test
     * This test contains a mistake
     * @throws HasPublishedContents.PostNotFoundException
     */
    @Test(timeout = 500)
    public void testDeleteComment() throws HasPublishedContents.PostNotFoundException {
//        Comment c = new Comment("testing", "Comment #0", user);
//
//        post.delete("Comment #0");
//
//        HashMap<String, Comment> mapToCompare = new HashMap<>();
//
//        mapToCompare.put("Comment #0", c);
//
//        assertEquals(mapToCompare.toString(), post.getMapOfComments().toString());
//
//        assertEquals(0, post.getNumberOfComments());
//
//        assertFalse(post.getMapOfComments().get("Comment #0").visulableStatus());
    }

    @Test(timeout = 500)
    public void testDeleteCommentUnsuccessful() {}

    @Test(timeout = 500)
    public void testNumberOfComments() {

    }

    @Test(timeout = 500)
    public void testGetNextId() {

    }

    @Test(timeout = 500)
    public void testGetContent() {

    }

    @Test(timeout = 500)
    public void testGetMapOfComments() {

    }
}


