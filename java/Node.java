
import java.util.*;

/*класс узла*/
class Node {
    private Object node;
    /*множество смежных узлов*/
    private Vector adjacentNodes = new Vector();

    public Node(Object node) {
        this.node = node;
    }

    public boolean equals(Object o) {
        if (node == null || o == null || !(o instanceof Node))
            return false;
        Node n = (Node)o;
        return node == n.getNode() || node.equals(n.getNode());
    }

    public Object getNode() {
        return node;
    }

    public Object[] getAdjacentNodes() {
        return adjacentNodes.toArray(new Object[adjacentNodes.size()]);
    }

    public Iterator<Node> CreateIterator() {
        return adjacentNodes.iterator();
    }

    /*добавление смежного узла = добавление ребра*/
    public void addArc(Object elem) {
        adjacentNodes.add(elem);
    }

    /*удаление смежного узла = удаление ребра*/
    public void delArc(Object elem) {
        adjacentNodes.removeElement(elem);
    }
}

