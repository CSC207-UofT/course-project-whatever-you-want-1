package Posts;

import User.User;

public interface HasPost {
    class PostNotFoundException extends Exception {
        public PostNotFoundException () {
            super();
        }
    }

   PostNotFoundException ABSENT = new PostNotFoundException ();
    String add(String content, User creator);
    void delete(String id) throws  PostNotFoundException;
}
