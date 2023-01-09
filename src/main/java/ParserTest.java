import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParserTest {
    static Scanner scanner = new Scanner(System.in);
    public static void printMenu(){
        System.out.println("1 - Use g1.txt with seq.txt");
        System.out.println("2 - Use g2.txt with PIF");
    }
    
    public static void useG1(){
        Grammar grammar = new Grammar();
        grammar.readFromFile("g1.txt");
        LRParser parser = new LRParser(grammar);
        parser.computeCanonicalCollection();
        parser.createParsingTable();
        Path filePath = Paths.get("seq.txt");
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath, charset);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parser.isSequenceAccepted(lines.get(0))) {
            parser.parsingOutput.printParsingTreeToScreen(parser.parsingStrategy.outputBand, grammar,"out1.txt");
        }
    }
    
    public static void useG2() {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g2.txt");
        System.out.println(grammar.productionsSet);
        LRParser parser = new LRParser(grammar);
        parser.computeCanonicalCollection();
        parser.createParsingTable();
        if (parser.isSequenceAccepted(getSequenceFromPif())) {
            parser.parsingOutput.printParsingTreeToScreen(parser.parsingStrategy.outputBand, grammar,"out2.txt");
        }
    }

    public static List<String> getSequenceFromPif(){
        List<String> sequence = new ArrayList<>();
        Path filePath = Paths.get("PIF.OUT");
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath, charset);
            lines.remove(0);
            for (String line : lines){
                sequence.add(line.split("[ ]+")[1]);
            }
            return sequence;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main (String[] args) {

        printMenu();
        Integer option = scanner.nextInt();
        switch (option){
            case 1:
                useG1();
                break;
            case 2:
                useG2();
                break;
            default:
                System.out.println("Invalid option");
                
        }
       
    }
}
