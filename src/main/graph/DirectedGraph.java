package graph;

import constants.Exceptions;
import constants.HasName;
import constants.IterableMap;
import constants.VertexArrayDefaultComparator;

import java.io.Serializable;
import java.util.*;

import static constants.Algorithm.*;
import static constants.Exceptions.VERTEX_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A Directed Graph, which is the data structure used to represent a field of knowledge.
 */
public class DirectedGraph implements Serializable, Iterable<VertexArray>, HasName {

    /**
     * The key of VERTICES is a String which is the name of a Vertex, the value of the VERTICES is an Array of length
     * 2 where the first element is a Vertex which is the starting vertex of many edges and the second element is an
     * ArrayList containing all Vertices that is the ending vertex which the starting vertex points to.
     */
    private final IterableMap<String, VertexArray> VERTICES = new IterableMap<>();
    private final String NAME;
    private final List<String> CURRENTUNLOCK = new ArrayList<>();
    private final List<String> COMPLETED = new ArrayList<>();

    /**
     * get the number of completed vertex
     *
     * @return number of complete
     */
    public int getNumOfCOMPLETED() {
        return COMPLETED.size();
    }

    /**
     * set tree id
     */
    public void setTreeId() {
    }

    /**
     * The constructor of the DirectedGraph class.
     *
     * @param lstVertex A list of vertex to be added to the instance of DirectedGraph
     * @param name      The name of the DirectedGraph
     */
    public DirectedGraph(Vertex[] lstVertex, String name) {
        for (Vertex v : lstVertex) {

            addVertex(v);
            CURRENTUNLOCK.add(v.getName());
        }
        this.NAME = name;
    }

    /**
     * This method will add the provided directed edge into the main.graph.
     * the starting vertex and ending vertex will be added into the main. graph,
     * and the ending vertex will be added into the ArrayList of the
     * starting vertex containing all vertices it points to.
     *
     * @param edge An array of length 2 which represents a directed edge
     *             containing the starting vertex at index 0 and ending vertex at index 1.
     * @throws Exception if the name of the first vertex in edge does not exist in the DirectedGraph
     */
    public void addEdge(Vertex[] edge) throws Exception {
        if (!checkEdgeExistence(edge)) {
            addVertex(edge[0]);
            addVertex(edge[1]);
            edge[1].addInLevel();
            updateAll(edge[1]);
            getVertexArray(edge[0]).addEdge(edge[1]);
        } else {
            throw new Exception(Exceptions.EDGE_ALREADY_EXIST);
        }
    }

    public boolean checkEdgeExistence(Vertex[] edge) throws Exception {
        if (checkVertexExistence(edge[0])) {
            if (getVertexArray(edge[0]).isEnd(edge[1])) {
                return true;
            }
        }
        if (checkVertexExistence(edge[1])) {
            return getVertexArray(edge[1]).isEnd(edge[0]);
        }
        return false;
    }

    public boolean checkVertexExistence(Vertex vertex) {
        return VERTICES.containsKey(vertex.getName());
    }

    /**
     * This method will add the provided vertex into the main.graph.
     * the ArrayList which contains vertices it points to will
     * be set as an empty ArrayList
     *
     * @param vertex A vertex to be added into the main.graph.
     */
    public void addVertex(Vertex vertex) {
        String name = vertex.getName();
        if (!VERTICES.containsKey(name)) {
            VertexArray newEdge = new VertexArray(vertex);
            VERTICES.put(name, newEdge);
        }
    }

    /**
     * Delete the given edge from the main.graph. Though both
     * starting and ending vertices will remain in the main. graph,
     * the connection between them will be removed.
     *
     * @param edge An array of length 2 which represents a directed edge
     *             containing the starting vertex at index 0 and ending vertex at index 1.
     * @throws Exception if the name of the first vertex in edge does not exist in the DirectedGraph
     *                   or if the directed edge to be deleted does not exist.
     */
    public void deleteEdge(Vertex[] edge) throws Exception {
        getVertexArray(edge[0]).deleteThisEdge(edge[1]);
        edge[1].minusInLevel();
        updateAll(edge[1]);
        if (edge[1].getInLevel() == 0) {
            CURRENTUNLOCK.add(edge[1].getName());
        }
    }

    public void updateAll(Vertex vertex) throws Exception {
        for (String name : VERTICES) {
            if (getVertexArray(name).isEnd(vertex)) {
                getVertexArray(name).updateVertex(vertex);
            }
        }
    }

    /**
     * Delete the given Vertex from the main.graph. All edges having
     * this vertex as the ending vertex will also be removed.
     *
     * @param name The name of vertex
     * @throws Exception if the name of vertex does not exist in the DirectedGraph
     */
    public void deleteVertex(String name) throws Exception {
        Vertex delete = getVertexArray(name).getStart();
        for (Vertex v : getVertexArray(name)) {
            v.minusInLevel();
            updateAll(v);
            if (v.getInLevel() == 0) {
                CURRENTUNLOCK.add(v.getName());
            }
        }
        for (String vertexName : VERTICES) {
            if (getVertexArray(vertexName).isEnd(delete)) {
                Vertex[] edge = {getVertexArray(vertexName).getStart(), delete};
                deleteEdge(edge);
            }
        }
        VERTICES.remove(name);
        CURRENTUNLOCK.remove(name);
        COMPLETED.remove(name);
    }

    /**
     * An overloaded version of getVertexArray.
     * This implementation takes in the name of a vertex and return the VertexArray corresponding to this name.
     *
     * @param name the name of a vertex
     * @return a VertexArray that stores all DirectedEdges starting from this vertex
     * @throws Exception if the name of the vertex does not exist in the DirectedGraph
     */
    public VertexArray getVertexArray(String name) throws Exception {
        if (!VERTICES.containsKey(name)) {
            throw new Exception(Exceptions.VERTEX_NOT_FOUND);
        } else {
            return VERTICES.get(name);
        }
    }

    /**
     * An overloaded version of getVertexArray.
     * This implementation takes in a vertex and return the VertexArray corresponding to this vertex.
     *
     * @param vertex a vertex
     * @return a VertexArray that stores all DirectedEdges starting from this vertex
     * @throws Exception if the name of the vertex does not exist in the DirectedGraph
     */
    public VertexArray getVertexArray(Vertex vertex) throws Exception {
        String name = vertex.getName();
        if (!VERTICES.containsKey(name)) {
            throw new Exception(Exceptions.VERTEX_NOT_FOUND);
        } else {
            return VERTICES.get(name);
        }
    }

    /**
     * Given a name, return vertex
     *
     * @param name The name of Vertex
     * @return Return the vertex with name
     * @throws Exception if the name of the vertex does not exist in the DirectedGraph
     */
    public Vertex getVertex(String name) throws Exception {
        for (String vertexName : VERTICES) {
            if (vertexName.equals(name)) {
                return getVertexArray(name).getStart();
            }
        }
        throw new Exception(Exceptions.VERTEX_NOT_FOUND);
    }

    /**
     * To mark the complete for the vertex.
     *
     * @param name: The name for vertex
     * @throws Exception if the name of the vertex does not exist in the DirectedGraph
     *                   or if the vertex is currently locked or if the vertex is already completed.
     */
    public void complete(String name) throws Exception {
        if (!VERTICES.containsKey(name)) {
            throw new Exception(Exceptions.VERTEX_NOT_FOUND);
        } else if (COMPLETED.contains(name)) {
            throw new Exception(Exceptions.VERTEX_ALREADY_COMPLETED);
        } else if (!CURRENTUNLOCK.contains(name))
        {
        throw new Exception(Exceptions.VERTEX_LOCKED);
         } else {
            COMPLETED.add(name);
            CURRENTUNLOCK.remove(name);
            for (Vertex next : getVertexArray(name)) {
                next.minusInLevel();
                updateAll(next);
                if (next.getInLevel() == 0) {
                    CURRENTUNLOCK.add(next.getName());
                }
            }
        }
    }

    /**
     * Override ToString method
     *
     * @return the string representation of the TechTree(main.graph)
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name of TechTree: ");
        stringBuilder.append(NAME);
        stringBuilder.append("\n");
        for (VertexArray edges : arrangeArray()) {
            if (edges.getStart().getInLevel() == 0) {
                stringBuilder.append("\n");
                stringBuilder.append(singleVertexToString(edges, 1));
            }
        }
        return stringBuilder.toString();
    }

    public String singleVertexToString(VertexArray edge, int numInward) {
        if (edge.isEmpty()) {
            return edge.getStart().toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(edge.getStart().toString());
            for (Vertex vertex : edge) {
                try {
                    VertexArray vertexArray = getVertexArray(vertex);
                    stringBuilder.append("\n");
                    stringBuilder.append("    ".repeat(numInward));
                    stringBuilder.append("-> ");
                    stringBuilder.append(singleVertexToString(vertexArray, numInward + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();
        }
    }

    /**
     * Return the vertex available now.
     *
     * @return Vertices that available now.
     * @throws Exception if the name of the vertex does not exist in the DirectedGraph
     */
    public Map<String, Vertex> availableVertex() throws Exception {
        Map<String, Vertex> available = new HashMap<>();
        int count = 0;
        for (String name : CURRENTUNLOCK) {
            available.put(Integer.toString(count), getVertexArray(name).getStart());
            count++;
        }
        return available;
    }

    /**
     * Return all vertices in the TechTree
     * Only intended to be used for testing.
     * It should not be used for any other purpose.
     *
     * @return Vertices
     */
    public Map<String, VertexArray> getVertices() {
        return VERTICES;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Iterator<VertexArray> iterator() {
        return new GraphItr();
    }

    private List<VertexArray> arrangeArray() {
        List<VertexArray> vertexArray = new ArrayList<>();
        for (String vertexName : VERTICES) {
            try {
                vertexArray.add(getVertexArray(vertexName));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        if (vertexArray.isEmpty()) {
            return vertexArray;
        }
        int b = 0;
        int e = vertexArray.size() - 1;
        VertexArrayDefaultComparator vertexArrayDefaultComparator = new VertexArrayDefaultComparator();
        mergeSort(vertexArray, b, e, vertexArrayDefaultComparator);
        return vertexArray;
    }

    private class GraphItr implements Iterator<VertexArray> {

        private final List<VertexArray> arranged;
        private int index;

        public GraphItr() {
            arranged = arrangeArray();
        }

        @Override
        public boolean hasNext() {
            return index < arranged.size();
        }

        @Override
        public VertexArray next() {
            VertexArray toReturn = arranged.get(index);
            index++;
            return toReturn;
        }
    }

    /**
     * Check if the completed set is zero, in other word, this
     * method is used to check whether the tree/graph was
     * began to learn
     */
    public boolean isLearnedGraph() {
        int number = COMPLETED.size();
        return number != 0;
    }

    public static void main(String[] args) throws Exception {
        Vertex pythonIntro = new Vertex("Introductory Python");
        Vertex compIntro = new Vertex("Introductory Cobb");
        Vertex Intro165 = new Vertex("CSC165");
        Vertex JavaIntro = new Vertex("Introductory Java");
        Vertex CIntro = new Vertex("Introductory C++");
        Vertex Intro236 = new Vertex("CSC236");
        Vertex Intro209 = new Vertex("CSC209");
        Vertex Intro263 = new Vertex("CSC263");
        Vertex[] starter = {pythonIntro};
        DirectedGraph graph = new DirectedGraph(starter, "CS Introduction Series");
        Vertex[] v1 = {pythonIntro, JavaIntro};
        Vertex[] vx = {compIntro, JavaIntro};
        Vertex[] v2 = {pythonIntro, CIntro};
        Vertex[] v3 = {Intro165, Intro236};
        Vertex[] v4 = {Intro236, Intro263};
        Vertex[] v5 = {JavaIntro, Intro209};
        Vertex[] v6 = {pythonIntro, Intro165};
        graph.addEdge(v1);
        graph.addEdge(v2);
        graph.addEdge(v3);
        graph.addEdge(v4);
        graph.addEdge(v5);
        graph.addEdge(v6);
        graph.addEdge(vx);
        graph.complete("Introductory Python");
        graph.complete("Introductory Python");

    }
}
