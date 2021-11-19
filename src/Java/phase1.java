//package Java;
//
//import CommunitySystem.GraphCommunityFacade;
//import Graph.DirectedGraph;
//import Graph.Vertex;
//import java.util.Scanner;
//import User.UserActionFacade;
//
//import static Java.helper.*;
//
//public class phase1 {
//    /**
//     * Action by user is required
//     */
//    public static void init(){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please take an action(\"Sign up\" or \"Quit\")");
//        String action = scanner.nextLine();
//        while (!action.equals("Sign up") && !action.equals("Quit")) {
//            System.out.println("Please enter the correct command");
//            action = scanner.nextLine();
//        }
//        if(action.equals("Quit")){
//            System.exit(0);
//        }
//
//    }
//
//
//    public static void main(String[] args){
//        init();
//        UserActionFacade facade = newFacade();
//        while (newUser(facade).equals("The Username has already exists, please try again")){
//            newUser(facade);
//        }
//        GraphCommunityFacade facade1 = new GraphCommunityFacade(facade.getGraphManager(),
//                facade.getCommunityLibrary(), true);
//        DirectedGraph graph = getGraph(facade1.getGraphManager());
//        facade.addGraphToCurrentUser(graph);
//        DirectedGraph userGraph = facade.getUserGraph().get(0);
//        Vertex node = gotVertex(userGraph);
//        completeVertex(node, userGraph, facade);
//        Vertex node2 = gotVertex(userGraph);
//        completeVertex(node2, userGraph, facade);
//    }
//
//}
//
//
//
