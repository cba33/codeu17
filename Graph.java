import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by basescu on 06.06.17.
 */

class Node {
    public int key;     // letter index in initial matrix: for M[i][j], we have pos = i * cols + j
    public char value;

    public Node(int key, char value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return "key = " + key + " value = " + value;
    }
}

/*
Each letter is a node. The graph has edges for each diagonal & straight traversal.
Finding words means a graph traversal with word prunning. I chose DFS to
potentially find complete words faster.
 */
public class Graph {

    public final HashMap<Integer, Node> intToNode;
    public final HashMap<Node, LinkedList<Node>> edges;
    public final int nrNodes;

    public Graph(int rows, int cols, char[][] input) {
        intToNode = new HashMap<>();
        edges = new HashMap<>();
        nrNodes = rows * cols;
        for (int i = 0 ; i < rows ; i++) {
            for (int j = 0 ; j < cols ; j++) {
                int nodeKey = i * cols + j;
                Node n = fetchOrCreateNode(nodeKey, input[i][j]);
                LinkedList<Node> neigh = identifyNeighbors(input, i, j, rows, cols);
                edges.put(n, neigh);
            }
        }
    }

    /* Build words using depth-first search and prune search using word prefixes. */
    private void createWords(Node lastLetter, StringBuilder sb, HashSet visited, HashSet result) {

        // debug
        /*
        System.out.println("last letter "+ lastLetter);
        System.out.println("visited "+ visited);
        System.out.println("word so far "+ sb.toString());
        System.out.println("result so far "+ result);
        */

        for (Node neigh : edges.get(lastLetter)) {
            if (!visited.contains(neigh)) {
                String potentialWord = sb.toString() + neigh.value;
                if (Dictionary.isWord(potentialWord)) {
                    result.add(sb.toString() + neigh.value);
                }
                if (Dictionary.isPrefix(potentialWord)) {
                    visited.add(neigh);
                    sb.append(neigh.value);
                    createWords(neigh, sb, visited, result);
                    visited.remove(neigh);
                    sb.deleteCharAt(sb.length()-1);
                }
            }
        }
    }

    /* Initialize word creation from each node, if the dictioanry contains a prefix for that letter */
    public HashSet createWords() {
        HashSet visited = new HashSet();
        HashSet result = new HashSet();
        for (int i = 0 ; i < nrNodes ; i++) {
            Node firstLetter = intToNode.get(i);
            visited.clear();
            visited.add(firstLetter);
            StringBuilder sb = new StringBuilder(30);
            sb.append(firstLetter.value);
            if (Dictionary.isPrefix(sb.toString())) {
                createWords(firstLetter, sb, visited, result);
            }
        }
        return result;
    }


    private LinkedList<Node> identifyNeighbors(char[][] input, int i, int j, int rows, int cols) {
        LinkedList<Node> neighbors = new LinkedList<>();
        int pos;
        char c;

        if(i-1 >= 0) {
            // <i-1,j>
            pos = (i-1) * cols + j;
            c = input[i-1][j];
            neighbors.add(fetchOrCreateNode(pos, c));

            // i-1, j-1
            if(j-1 >= 0) {
                pos = (i-1) * cols + (j-1);
                c = input[i-1][j-1];
                neighbors.add(fetchOrCreateNode(pos, c));
            }

            // i-1, j+1
            if(j+1 < cols) {
                pos = (i-1) * cols + (j+1);
                c = input[i-1][j+1];
                neighbors.add(fetchOrCreateNode(pos, c));
            }
        }

        if(i+1 < rows) {
            // <i+1,j>
            pos = (i+1) * cols + j;
            c = input[i+1][j];
            neighbors.add(fetchOrCreateNode(pos, c));

            // i+1, j-1
            if(j-1 >= 0) {
                pos = (i+1) * cols + (j-1);
                c = input[i+1][j-1];
                neighbors.add(fetchOrCreateNode(pos, c));
            }

            // i+1, j+1
            if(j+1 < cols) {
                pos = (i+1) * cols + (j+1);
                c = input[i+1][j+1];
                neighbors.add(fetchOrCreateNode(pos, c));
            }
        }

        // i, j-1
        if(j-1 >= 0) {
            pos = i * cols + (j-1);
            c = input[i][j-1];
            neighbors.add(fetchOrCreateNode(pos, c));
        }

        // i, j+1
        if(j+1 < cols) {
            pos = i * cols + (j+1);
            c = input[i][j+1];
            neighbors.add(fetchOrCreateNode(pos, c));
        }

        return neighbors;
    }

    private Node fetchOrCreateNode(int pos, char c) {
        Node node = intToNode.get(pos);
        if (node == null) {
            node = new Node(pos, c);
            intToNode.put(pos, node);
        }
        return node;
    }
}
