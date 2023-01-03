import java.util.ArrayList;
import java.util.List;

public class ParsingStrategy {

    List<List<String>> workStack = new ArrayList<>();
    String inputStack= "";
    List<String> outputBand = new ArrayList<>();
    String sequence;

    public ParsingStrategy (String seq, Integer initialStateIndex) {
        this.sequence = seq;
        List<String> firstConfiguration = new ArrayList<>();
        firstConfiguration.add("$");
        firstConfiguration.add(String.valueOf(initialStateIndex));
        workStack.add(firstConfiguration);
        inputStack+=seq;
        inputStack+="$";
        outputBand.add("");
    }

}
