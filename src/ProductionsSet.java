import java.util.*;

public class ProductionsSet {
    HashMap<List<String>, List<List<String>>> productions;

    public ProductionsSet () {
        this.productions = new LinkedHashMap<>();
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionsSet that = (ProductionsSet) o;

        return Objects.equals(productions, that.productions);
    }

    @Override
    public int hashCode () {
        return Objects.hash(productions);
    }

    @Override
    public String toString () {
        String set = "";
        for (Map.Entry<List<String>, List<List<String>>> entry : productions.entrySet()) {
            for (String elem1 : entry.getKey()) {
                set += elem1 + " ";
            }
            set += "-> ";
            for (List<String> elem1 : entry.getValue()) {
                for (String elem2 : elem1) {
                    set += elem2 + " ";
                }
            }
            set += "\n";
        }
        return set;
    }
}
