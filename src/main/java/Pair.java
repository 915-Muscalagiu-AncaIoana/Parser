import java.util.Objects;

public class Pair {
        Integer row;
        String column;

    public Pair (Integer row, String column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow () {
        return row;
    }

    public void setRow (Integer row) {
        this.row = row;
    }

    public String getColumn () {
        return column;
    }

    public void setColumn (String column) {
        this.column = column;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(row, pair.row) && Objects.equals(column, pair.column);
    }

    @Override
    public int hashCode () {
        return Objects.hash(row, column);
    }
}
