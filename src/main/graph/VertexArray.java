package graph;

import constants.Exceptions;
import constants.InLevelComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static constants.Algorithm.*;

/**
 * A special Array which stores a vertex and all DirectedEdges started from a vertex.
 */
public class VertexArray implements Iterable<Vertex>, Serializable {

    private final Vertex start;
    /**
     * sorted.
     */
    private final List<Vertex> END = new ArrayList<>();

    /**
     * the constructor of a VertexArray.
     * @param vert a vertex to be stored
     */
    public VertexArray(Vertex vert) {
        start = vert;
    }

    /**
     * add a DirectedEdge starting from the start to the VertexArray.
     * @param end the ending vertex of this VertexArray
     */
    public void addEdge(Vertex end) {
        int i = indexToInsert(end);
        if (i == END.size()) {
            END.add(end);
        } else {
            END.add(i, end);
        }
    }

    public void updateVertex(Vertex vertex) throws Exception {
        deleteThisEdge(vertex);
        addEdge(vertex);
    }


    public int indexToInsert(Vertex vertex) {
        if (END.isEmpty()) {
            return 0;
        }
        int b = 0;
        int e = END.size() - 1;
        InLevelComparator inLevelComparator = new InLevelComparator();
        return recBinaryInsertIndex(END, vertex, inLevelComparator, b, e);
    }

    public boolean endEqual(ArrayList<Vertex> other) {
        InLevelComparator inLevelComparator = new InLevelComparator();
        return pairwiseCompare(END, other, inLevelComparator) == 0;
    }

    /**
     * @return the instance variable start
     */
    public Vertex getStart() {
        return start;
    }

    /**
     * Delete a DirectedEdge.
     * @param end the ending vertex of the DirectedEdge to be deleted
     * @throws Exception if the DirectedEdge to be deleted does not exist
     */
    public void deleteThisEdge(Vertex end) throws Exception {
        if (isEnd(end)) {
            END.remove(end);
        } else {
            throw new Exception(Exceptions.EDGE_NOT_FOUND);
        }

    }

    /**
     * Check whether a vertex is reachable from start.
     * @param end a vertex to be checked
     * @return True if end is reachable from start, False otherwise
     */
    public boolean isEnd(Vertex end) {
        return END.contains(end);
    }

    /**
     * Apply Iterator design pattern to VertexArray.
     * @return an iterator class representation of VertexArray
     */
    @Override
    public Iterator<Vertex> iterator() {
        return new VertexItr();
    }

    private class VertexItr implements Iterator<Vertex> {

        private int curr;

        @Override
        public boolean hasNext() {
            return curr < END.size();
        }

        @Override
        public Vertex next() {
            Vertex toReturn;
            try{
                toReturn = END.get(curr);
            } catch (IndexOutOfBoundsException e){
                throw new NoSuchElementException();
            }
            curr += 1;
            return toReturn;
        }
    }

    /**
     * Helper method of toString.
     * For each ending vertices that start can reach,
     * return its name preceded by an arrow and followed by a newline character.
     * @return the name of each ending vertices preceded
     *         by an arrow and followed by a newline character
     */
    public String arrowEnd() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Vertex vertex : END) {
            stringBuilder.append("        ->    ");
            stringBuilder.append(vertex.getName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public String toString() {
        return start.toString() + "\n" + arrowEnd();
    }
}
