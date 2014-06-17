
import java.lang.*;
import java.lang.Integer;
import java.lang.Object;
import java.lang.SafeVarargs;
import java.lang.String;
import java.lang.System;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/*класс узла*/
class Node {
    private String node;
    /*множество смежных узлов*/
    private Vector<Node> adjacentNodes = new Vector<Node>();
    /*номер компоненты*/
    private int componentNumber = 0;
    /*состояние просмотра*/
    private boolean visited = false;

    public Node(String node) {
        this.node = node;
    }

    public boolean equals(Object o) {
        if (node == null || o == null || !(o instanceof Node))
            return false;
        Node n = (Node) o;
        return node == n.getNode() || node.equals(n.getNode());
    }

    public String getNode() {
        return node;
    }

    /*возврат смежных узлов*/
    public String[] getAdjacentNodes() {
        String [] mas = new String[adjacentNodes.size()];
        int i = 0;
        for(Node node: adjacentNodes)
        {
           mas[i] = node.getNode();
            i++;
        }
                 return mas;
//        return adjacentNodes.//.toArray(new String[adjacentNodes.size()]);
    }


    /*добавление смежного узла = добавление ребра*/
    public void addArc(Node elem) {
        int nodeIndex = adjacentNodes.indexOf(elem);
        if (nodeIndex == -1)
            adjacentNodes.add(elem);
    }

    /*удаление смежного узла = удаление ребра*/
    public void delArc(Node elem) {
        adjacentNodes.removeElement(elem);
    }

    /*возврат номера компоненты*/
    public int GetComponentNumber() {
        return componentNumber;
    }

    /*установка номера компоненты*/
    public void SetComponentNumber(int number) {
        componentNumber = number;
    }

    /*запрос состояния обработки*/
    public boolean IsVisited() {
        return visited;
    }

    /*установка состояния просмотра узла*/
    public void SetVisited(boolean visited) {
        this.visited = visited;
    }

    /*раскраска узла*/
    public List<Node> Colorize() {
        List<Node> linkedNodes = new ArrayList<Node>();
        /*для всех смежных узлов*/
        for (Node node : adjacentNodes) {
            /*если узел не просмотрен*/
            if (!node.IsVisited()) {
                /*покрасить в цвет основного узла*/
                node.SetComponentNumber(componentNumber);
                /*добавить к раскрашенным*/
                linkedNodes.add(node);
            }
        }

        return linkedNodes;
    }


}

