public class HelperNode {
    HelperNode parent;
    Integer rightSibling;
    String value;
    Integer index;
    Integer level;

    public HelperNode (String value) {
        this.parent = null;
        this.rightSibling = -1;
        this.value = value;
        this.index = 0;
        this.level = 0;
    }
}