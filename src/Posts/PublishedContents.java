package Posts;

import RewardSystem.RewardManager;
import User.User;

import java.io.Serializable;

public class PublishedContents implements Likable, Visualable, Serializable {
    private String id;
    private int numLike;
    private boolean visual = true;
    private User creator;

    public PublishedContents(String id, User creator) {
        this.id = id;
        this.creator = creator;
    }

    public String getId() {
        return id;
    }


    @Override
    public int getNumberOfLike() {
        return numLike;
    }

    @Override
    public void like() {
        numLike += 1;
        RewardManager.addRewardPoint(creator, RewardManager.getPointsRewardedPerLike());
    }

    @Override
    public void setAsVisualable() {
        visual = true;
    }

    @Override
    public void setAsUnvisualable() {
        visual = false;
    }

    @Override
    public boolean visulableStatus() {
        return visual;
    }
}
