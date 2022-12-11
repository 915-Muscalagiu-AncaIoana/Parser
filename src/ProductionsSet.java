import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductionsSet {
    HashMap<List<String>, List<String>> productions;

    public ProductionsSet () {
        this.productions = new LinkedHashMap<>();
    }

    @Override
    public String toString () {
        StringBuilder set = new StringBuilder();
        for (Map.Entry<List<String>, List<String>> entry : productions.entrySet()) {
            for (String elem1 : entry.getKey()){
                set.append(elem1).append(" ");
            }
            set.append("-> ");
            for (String elem1 : entry.getValue()){
                set.append(elem1).append(" ");
            }
            set.append("\n");
        }
        return set.toString();
    }
}
