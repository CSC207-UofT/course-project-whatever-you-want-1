package Posts;

import User.UserInfo;

/**
 * A comment.
 */
public class Comment extends PublishedContents {
    /**
     * the content of a comment
     */
    String content;

    public Comment(String content, String id, UserInfo creator) {
        super(id, creator);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return '\n' + this.getCreator().getUsername() + '\n' + content;
    }
}
