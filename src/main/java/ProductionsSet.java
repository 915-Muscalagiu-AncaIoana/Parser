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

            for (List<String> elem1 : entry.getValue()) {
                for (String elem3 : entry.getKey()) {
                    set += elem3 + " ";
                }
                set += "-> ";
                for (String elem2 : elem1) {
                    set += elem2 + " ";
                }
                set += "\n";
            }

        }
        return set;
    }

    Integer getProductionIndex (Production production) {
        int index = 1;
        for (Map.Entry<List<String>, List<List<String>>> entry : productions.entrySet()) {

            for (List<String> list : entry.getValue()) {
                if (production.lhs.equals(entry.getKey()) && production.rhs.equals(list)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }
    Production getProductionFromIndex (Integer productionIndex) {
        int index = 1;
        for (Map.Entry<List<String>, List<List<String>>> entry : productions.entrySet()) {

            for (List<String> list : entry.getValue()) {
                if (index == productionIndex) {
                    return new Production(entry.getKey(),list);
                }
                index++;
            }
        }
        return null;
    }
}
