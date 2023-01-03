import java.util.*;

public class ParserTest {
    public static void main (String[] args) {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g1.txt");
        LRParser parser = new LRParser(grammar);
        parser.computeCanonicalCollection();
        parser.createParsingTable();
        parser.isSequenceAccepted("abbc");
    }
}
