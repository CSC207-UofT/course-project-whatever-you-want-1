package CommunitySystem;

import Posts.HasPublishedContents;
import Posts.Post;
import User.User;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A community, where users add posts.
 */
public class Community implements HasPublishedContents, Serializable {
    private final HashMap<String, Post> mapOfPost = new HashMap<>();
    private final String nameOfCommunity;


    public Community(String name){
        nameOfCommunity = name;
    }

    /**
     * @return the string representation of this community.
     */
    @Override
    public String toString() {
        return "Community{" +
                "mapOfPost=" + mapOfPost +
                ", nameOfCommunity='" + nameOfCommunity + '\'' +
                '}';
    }

    /**
     * add a post into this community.
     * @param content content of the post being added
     * @param creator creator of the post being added
     * @return the id of the post being added.
     */
    @Override
    public String addPublishedContent(String content, User creator) {
        String postId = getNextId();
        mapOfPost.put(postId, new Post(content, postId, creator));
        return postId;
    }

    /**
     * delete a post in this community.
     * @param id the id of the post being deleted
     * @throws PostNotFoundException if the post with the inputted id is not found in this community.
     */
    @Override
    public void deletePublishedContent(String id) throws PostNotFoundException {
        if (mapOfPost.containsKey(id)) {
            mapOfPost.get(id).setInvisible();
        } else {
            throw ABSENT;
        }
    }

    /**
     * display all posts in this community in the form of text.
     * @return all posts in the form of text
     */
    public String displayPosts() {
        StringBuilder result = new StringBuilder();
        for (Post post : mapOfPost.values()) {
            if(post.visibility()) {
                result.append(post).append("\n");
            }
        }
        return result.toString();
    }


    /**
     * @return the mapOfPost instance variable
     */
    public HashMap<String, Post> getMapOfPost() {
        return mapOfPost;
    }

    /**
     * @return the size of the mapOfPost instance variable
     */
    public int getNumberOfPosts(){
        return mapOfPost.size();
    }

    /**
     * Generate the id of the next post
     * @return a string representation of the id of the next potential post
     */
    public String getNextId(){
        return "Post #" + mapOfPost.size();
    }
}
