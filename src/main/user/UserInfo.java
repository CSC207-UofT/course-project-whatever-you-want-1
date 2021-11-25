package user;

import constants.HasName;
import graph.DirectedGraph;
import resource.Resource;
import constants.Achievements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The class that stores all information of a main.user.
 */
public class UserInfo implements HasName {
    private String username;
    private String email;
    private String password;
    private int rewardPoints;
    // TODO： How do we update lastlogin?
    private LocalDate lastLogin;
    private int totalLogins;
    private ArrayList<String> listOfPostId = new ArrayList<>();
    private Map<String, DirectedGraph> mapOfGraph = new HashMap<>();
    private Map<String, Resource> listOfResource = new HashMap<>();
    private Map<String, Boolean> mapOfAchievement = new HashMap<>();

 
    public UserInfo(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rewardPoints = 0;
        this.lastLogin = LocalDate.now();
        this.totalLogins = 0;
        initializeMapOfAchievement();
    }


    /**
     * add a post id.
     * @param id the id of the post being added.
     */
    public void addToListOfPostId(String id) {
        this.listOfPostId.add(id);
    }

    /**
     * increase the total number of logins by 1.
     */
    public void incrementTotalLogins() {
        totalLogins += 1;
    }

    /**
     * Initialize mapOfAchievement, with all achievement not received.
     */
    public void initializeMapOfAchievement() {
        for (String achievement : Achievements.ARRAY_OF_ALL_ACHIEVEMENTS) {
            mapOfAchievement.put(achievement, false);
        }
    }

    @Override
    public String getName() {
        return username;
    }

    public void setUsername(String newName) {
        this.username = newName;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public int getRewardPoints() {
        return this.rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public void addRewardPoints(int rewardPoints) {
        this.rewardPoints += rewardPoints;
    }

    public void addGraph(DirectedGraph graph){
        mapOfGraph.put(graph.getName(), graph);
    }

    public Map<String, DirectedGraph> getMapOfGraph(){
        return this.mapOfGraph;
    }

    public void addResource(Resource resource){
        listOfResource.put(resource.getId(), resource);
    }

    public Map<String, Resource> getListOfResource(){
        return this.listOfResource;
    }



    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rewardPoints=" + rewardPoints +
                ", lastLogin=" + lastLogin +
                ", totalLogins=" + totalLogins +
                ", listOfPostId=" + listOfPostId +
                ", listOfGraph=" + mapOfGraph +
                ", listOfResource=" + listOfResource +
                ", mapOfAchievement=" + mapOfAchievement +
                '}';
    }

    public ArrayList<String> getListOfPostId() {
        return listOfPostId;
    }

    public int getTotalLogins() {
        return totalLogins;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public Map<String, Boolean> getMapOfAchievement() {
        return mapOfAchievement;
    }

    public boolean hasGraph(String graphName) {
        return mapOfGraph.containsKey(graphName);
    }

    public String displayAchievement() {
        StringBuilder achievements = new StringBuilder();
        for(Map.Entry<String, Boolean> achievement: mapOfAchievement.entrySet()){
            String name = achievement.getKey();
            Boolean status = achievement.getValue();
            if(status) {
                achievements.append(name).append(": acquired").append("\n");
            }
            achievements.append(name).append(": not acquired").append("\n");
        }
        return achievements.toString();
    }
}
