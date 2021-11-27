package main.graph;

import main.constants.Namable;

import java.io.Serializable;

/**
 * A vertex of a DirectedGraph.
 * This usually represents a topic in a field of knowledge.
 */
public class Vertex implements Serializable, Namable {
    private String name;
    // inLevel is a int representing the depth of this vertex in a DirectedGraph.
    private int inLevel;

    /**
     * The constructor of a vertex.
     * @param name the name of the vertex
     */
    public Vertex(String name){
        this.name = name;
//        CommunityLibrary.addCommunity(name);
    }

    /**
     * increase the inLevel of this vertex by one.
     */
    public void addInLevel(){
        inLevel += 1;
    }
    /**
     * decrease the inLevel of this vertex by one.
     */
    public void minusInLevel(){
        if(inLevel != 0){
            inLevel -= 1;
        }
    }

    /**
     * @return the inLevel of this Vertex.
     */
    public int getInLevel(){
        return inLevel;
    }

    /**
     * reset the name of this vertex.
     * @param name a new name for this vertex
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @return the name of this vertex
     */
    @Override
    public String getName(){
        return name;
    }

//    @Override
//    public int compareTo(Vertex o) {
//        if (inLevel < o.getInLevel()) {
//            return -1;
//        } else if (inLevel > o.getInLevel()) {
//            return 1;
//        } else {
//            return name.compareTo(o.getName());
//        }
//    }

    @Override
    public String toString(){
        return getName();
    }
}
