import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingTable {
    HashMap<Pair,String> table = new HashMap<>();
    int noOfStates;

    List<String> symbols ;

    public ParsingTable (int noOfStates, List<String> symbols) {
        this.noOfStates = noOfStates;
        this.symbols = symbols;
        addPredefinedHeaders();
    }

    public void setCell(int row, String column,String value){
        table.put(new Pair(row,column),value);
    }

    public String getCell(int row, String column){
        return table.get(new Pair(row,column));
    }

    private void addPredefinedHeaders(){
        for(int i = 0; i<noOfStates;i++){
            table.put(new Pair(i,"action"),null);
            for(String symbol: symbols){
                table.put(new Pair(i,symbol),null);
            }
        }
    }

    @Override
    public String toString () {
        String table = "";
        for (Map.Entry<Pair, String> entry : this.table.entrySet()){
            table+="("+entry.getKey().row+","+entry.getKey().column+") => "+entry.getValue();
            table+='\n';
        }
        return table;
    }
}
