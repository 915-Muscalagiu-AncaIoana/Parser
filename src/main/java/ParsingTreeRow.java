public class ParsingTreeRow {
    private int index;
    private String info;
    private int parent;
    private int rightSibling;

    public ParsingTreeRow (int index, String info, int parent, int rightSibling) {
        this.index = index;
        this.info = info;
        this.parent = parent;
        this.rightSibling = rightSibling;
    }

    public int getIndex () {
        return index;
    }

    public String getInfo () {
        return info;
    }

    public int getParent () {
        return parent;
    }

    public int getRightSibling () {
        return rightSibling;
    }

    public void setIndex (int index) {
        this.index = index;
    }

    public void setInfo (String info) {
        this.info = info;
    }

    public void setParent (int parent) {
        this.parent = parent;
    }

    public void setRightSibling (int rightSibling) {
        this.rightSibling = rightSibling;
    }

    @Override
    public String toString () {
        return "ParsingTreeRow{" +
                "index=" + index +
                ", info='" + info + '\'' +
                ", parent=" + parent +
                ", rightSibling=" + rightSibling +
                '}';
    }
}
