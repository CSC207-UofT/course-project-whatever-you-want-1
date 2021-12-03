package commandline;

import achievementsystem.AchievementManager;
import constants.Achievements;
import graph.GraphManager;
import communitysystem.CommunityLibrary;
import graph.Vertex;
import resource.ResourceManager;
import rewardsystem.RewardManager;
import user.UserManager;
import constants.Exceptions;

import java.util.Objects;
import java.util.Scanner;

public class SystemInOut {
    private UserManager userManager;
    private GraphManager graphManager;
    private RewardManager rewardManager;
    private AchievementManager achievementManager;
    private CommunityLibrary communityLibrary;
    private ResourceManager resourceManager;
    private Scanner scanner = new Scanner(System.in);
    private Presenter presenter = new Presenter(userManager, resourceManager);

    public SystemInOut() throws Exception {
        graphManager = new GraphManager();
        userManager = new UserManager();
        userManager.addNewUserInfo("alfred", "@", "123");
        resourceManager = new ResourceManager();
        resourceManager.addDefault();
        rewardManager = new RewardManager();
        achievementManager = new AchievementManager();
        communityLibrary = new CommunityLibrary();
        graphManager.addBuiltInGrpah(communityLibrary);
        presenter = new Presenter(userManager, resourceManager);
    }

    public void run() {
        load();
        logIn();
        mainMenu();
        scanner.close();
    }

    public void mainMenu() {
            System.out.println("Main Menu: 1.Technical Tree, 2.Resource, 3.Achievement, or enter \"exit\" to exit program");
            String input = scanner.nextLine();

            while (!(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("exit"))) {
                System.out.println("Incorrect input, please try again.");
                input = scanner.nextLine();
            }

            switch (input) {
                case "1":
                    try {
                        technicalTreeMainPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    resourcePage();
                    break;
                case "3":
                    achievementPage();
                    break;
                case "exit":
                    exitProgram();
                    break;
            }
    }

    private void achievementPage() {
        System.out.println(userManager.displayAchievement());
        presenter.mainMenuReturn();
        String input = scanner.nextLine();
        mainMenu();
    }

    private void resourcePage() {
        presenter.rewardPoints();
        presenter.resourcePage();
        String input = scanner.nextLine();
        while (!(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("0"))) {
            presenter.incorrectInput();
            input = scanner.nextLine();
        }

        switch (input) {
            case "1":
                myResource();
                break;
            case "2":
                downloadResources();
                break;
            case "3":
                createResource();
                break;
            case "0":
                mainMenu();
        }
    }



    private void myResource() {
        presenter.currentResource();
        presenter.mainMenuReturn();
        String input = scanner.nextLine();
        mainMenu();
        }

    private void downloadResources() {
        presenter.resourceChoose();
        String content = scanner.nextLine();
        while(resourceManager.downloadResource(content).equals("Sorry, you do not have enough points")){
            presenter.insufficientPoints();
            presenter.resourceChoose();
            content = scanner.nextLine();
        }
        while(!resourceManager.downloadResource(content).equals("Sorry, you do not have enough points")){
            presenter.downloadSuccessfully();
            resourceManager.downloadResource(content);
            presenter.mainMenuReturn();
            String input = scanner.nextLine();
            mainMenu();
        }
    }

    private void createResource() {
        Scanner scanner = new Scanner(System.in);
        presenter.resourceDescription();
        String description = scanner.nextLine();
        presenter.resourcePoints();
        String point = scanner.nextLine();
        presenter.resourceContents();
        String content = scanner.nextLine();
        resourceManager.addResource(content, Integer.parseInt(point), description);
        presenter.resourceCreateSuccessfully();
        presenter.mainMenuReturn();
        String input = scanner.nextLine();
        mainMenu();
    }


    private void technicalTreeMainPage() throws Exception {
        System.out.println("Hi! Now you've entered the technical tree page");
        System.out.println("Select the tree you want to study!");
        System.out.println("Tech Trees: " + graphManager.getAllGraphs());
        System.out.println("Enter \"main\" to return to main page.");
        String input = scanner.nextLine();

        while (!graphManager.getAllGraphs().containsKey(input) && !input.equals("main")) {
            System.out.println("You have input an invalid number, try again :(");
            input = scanner.nextLine();
        }

        if (input.equals("main")){
            mainMenu();
        }

        else {
            technicalTreePage(input);
        }

    }

    private void technicalTreePage(String treeId) throws Exception {
        graphManager.setCurrentGraph(treeId);
        System.out.println(graphManager.displayCurrentGraph());

        System.out.println("Please choose the node you want to start with" +
                graphManager.getCurrentGraph().availableVertex() + "or enter \"main\" to return to home page");
        String input = scanner.nextLine();
        while (!graphManager.getCurrentGraph().availableVertex().containsKey(input) && !input.equals("main")){
            System.out.println("You have input an invalid number. Please try again :(");
            input = scanner.nextLine();
        }

        if (input.equals("main")){
            mainMenu();
        }
        else {

            String vertexName = graphManager.getCurrentGraph().availableVertex().get(input).toString();

            studyVertex(vertexName, treeId);
        }

    }

    private void studyVertex(String vertexName, String treeId) throws Exception {

        System.out.println("Now study the node you have chosen, once you're finished, type \"Yes\" below");
        String input = scanner.nextLine();
        while (!input.equals("Yes")){
            System.out.println("It seems like you have not finished your study yet, keep working on it!" +
                    "Once you finished, type \"Yes\" below");
            input = scanner.nextLine();
        }

        graphManager.setCurrentGraph(treeId);

        graphManager.complete(vertexName); // Marking the given vertex as completed


        System.out.println("Congratulations! You've made one giant step toward success! Now let's make some posts " +
                "on what you've just learned.");
        userManager.getCurrentUserInfo().addRewardPoints(5);
        System.out.println("Please enter the content you want to publish below: ");
        String publishedContent = scanner.nextLine();

        communityLibrary.setCurrentCommunity(vertexName);
        communityLibrary.createPost(publishedContent, achievementManager, rewardManager);
        boolean achievementAwarded = achievementManager.requestAchievement(
                Achievements.ARRAY_OF_POST_THRESHOLDS,
                Achievements.MAP_POST_THRESHOLDS_TO_ACHIEVEMENT,
                userManager.getListOfPostId().size());
        if (achievementAwarded) {
            rewardManager.addRewardPoint(
                    Achievements.MAP_POST_THRESHOLDS_TO_REWARD.get(userManager.getListOfPostId().size()));
        }
        System.out.println("Congratulations! You've successfully published a post :)");
        System.out.println("You have completed this node, you can now proceed to the next " +
                "node you want to study. Press any key to continue");
        scanner.nextLine(); // Let the user enter anything they want here to proceed

        technicalTreePage(treeId);

    }


    public void logIn() {
        String input = presenter.LoginOptions();

        input = presenter.getCorrectLoginOption(input);

        switch (input) {
            case Presenter.ONE:
                if (!signIn()) {
                    logIn();
                } else {
                    userManager.incrementTotalLogins();
                }
                break;
            case Presenter.TWO:
                if (!register()) {
                    logIn();
                } else {
                    userManager.incrementTotalLogins();
                }
                break;
            case Presenter.EXIT:
                exitProgram();
                break;
        }
    }

    public boolean signIn() {
        String username = getUsernameSignIn();
        if (username.equals(Presenter.RETURN)) {
            return false;
        }
        if (!enterPassword(username)) {
            return false;
        }
        setCurrentUser(username);
        return true;
    }

    /**
     * fully implement this.
     * @param username
     */
    public void setCurrentUser(String username) {
        try {
            userManager.setCurrentUserInfoTo(username);
            achievementManager.setCurrentUserInfo(userManager.getCurrentUserInfo());
            rewardManager.setCurrentUserInfo(userManager.getCurrentUserInfo());
            communityLibrary.setCurrentUserInfo(userManager.getCurrentUserInfo());
            resourceManager.setCurrentUserInfo(userManager.getCurrentUserInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean enterPassword(String username) {
        String password = presenter.getNonEmptyPassword();
        while (!getCorrectPassword(username).equals(password)) {
            presenter.incorrectPassword();
            password = presenter.getNonEmptyPassword();
            if (password.equals(Presenter.RETURN)) {
                return false;
            }
        }
        return true;
    }

    public String getCorrectPassword(String username) {
        String password = null;
        try {
            password = userManager.getAUserInfo(username).getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    private String getUsernameSignIn() {
        String username = presenter.getNonEmptyUsername();
        while (!userManager.containsUsername(username)) {
            presenter.incorrectUsername();
            username = presenter.getNonEmptyUsername();
            if (username.equals(Presenter.RETURN)) {
                return username;
            }
        }
        return username;
    }

    public String getUsernameRegister() {
        String username = presenter.getNonEmptyUsername();
        while (userManager.containsUsername(username)) {
            presenter.usernameTaken();
            username = presenter.getNonEmptyUsername();
            if (username.equals(Presenter.RETURN)) {
                return username;
            }
        }
        return username;
    }

    public String getEmailRegister() {
        presenter.getEmail();
        String email = scanner.nextLine();
        while (!email.contains(Presenter.AT)) {
            presenter.incorrectEmail();
            email = scanner.nextLine();
            if (email.equals(Presenter.RETURN)) {
                return email;
            }
        }
        return email;
    }

    public String getPasswordRegister() {
        return presenter.getNonEmptyPassword();
    }


    private boolean register() {
        String username = getUsernameRegister();
        if (username.equals(Presenter.RETURN)) {
            return false;
        }
        String email = getEmailRegister();
        if (email.equals(Presenter.RETURN)) {
            return false;
        }
        String password = getPasswordRegister();
        try {
            userManager.addNewUserInfo(username, email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCurrentUser(username);
        return true;
    }




    public void exitProgram() {
        save();
        System.exit(0);
    }

    private void save() {
        // uses an interface to save.
    }

    private void load() {

    }
}
