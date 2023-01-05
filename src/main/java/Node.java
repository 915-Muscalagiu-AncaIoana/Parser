public class Node {
    Integer parent;
    Integer rightSibling;
    String value;
    Integer index;

    public Node (String value) {
        this.value = value;
        this.parent = -1;
        this.rightSibling = -1;
        this.index = 0;
    }

    @Override
    public String toString () {
        return "Node{" +
                "parent=" + parent +
                ", rightSibling=" + rightSibling +
                ", value='" + value + '\'' +
                ", index=" + index +
                '}';
    }
}
