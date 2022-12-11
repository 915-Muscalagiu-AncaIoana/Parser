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
        String set = "";
        for (Map.Entry<List<String>, List<String>> entry : productions.entrySet()) {
            for (String elem1 : entry.getKey()){
                set+= elem1+ " ";
            }
            set+="-> ";
            for (String elem1 : entry.getValue()){
                set+=elem1+" ";
            }
            set += "\n";
        }
        return set;
    }
}
