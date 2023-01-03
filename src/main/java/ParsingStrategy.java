import java.util.ArrayList;
import java.util.List;

public class ParsingStrategy {

    List<List<String>> workStack = new ArrayList<>();
    List<String> inputStack= new ArrayList<>();
    List<String> outputBand = new ArrayList<>();

    public ParsingStrategy (String seq, Integer initialStateIndex) {
        List<String> firstConfiguration = new ArrayList<>();
        firstConfiguration.add("$");
        firstConfiguration.add(String.valueOf(initialStateIndex));
        workStack.add(firstConfiguration);
        for(int index = 0;index < seq.length();index++){
            inputStack.add(String.valueOf(seq.charAt(index)));
        }
        inputStack.add("$");
        outputBand.add("");
    }
    public ParsingStrategy (List<String> seq, Integer initialStateIndex) {
        List<String> firstConfiguration = new ArrayList<>();
        firstConfiguration.add("$");
        firstConfiguration.add(String.valueOf(initialStateIndex));
        workStack.add(firstConfiguration);
        inputStack = seq;
        inputStack.add("$");
    }


}
