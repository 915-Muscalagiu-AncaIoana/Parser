import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Grammar {
    Set<String> terminals = new HashSet<>();

    Set<String> nonterminals = new HashSet<>();
    ProductionsSet productionsSet = new ProductionsSet();
    String starterNonterminal;

    void readFromFile (String fileName) {
        Path filePath = Paths.get(fileName);
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            int counter = 0;
            for (String line : lines) {
                if (counter == 0) {
                    String[] tokens = line.split(", ");
                    nonterminals.addAll(List.of(tokens).stream().map(token -> token.toUpperCase(Locale.ROOT)).collect(Collectors.toList()));

                } else if (counter == 1) {
                    String[] tokens = line.split(", ");
                    terminals.addAll(List.of(tokens));

                } else if (counter == 2) {
                    starterNonterminal = line.strip().toUpperCase(Locale.ROOT);
                } else {
                    String[] tokens = line.split("::=");
                    String lhs = tokens[0];
                    String rhs = tokens[1];
                    String[] lhsTerminals = lhs.split("[<> ]");
                    List<String> key = new ArrayList<>();
                    List<String> value = new ArrayList<>();
                    for (String terminal : lhsTerminals) {
                        if (!terminal.isEmpty()) {
                            key.add(terminal.toUpperCase(Locale.ROOT));
                        }
                    }
                    String[] rhsTokens = rhs.split(" ");
                    for (String token : rhsTokens) {
                        if (!token.isEmpty()) {
                            if (token.charAt(0) == '<' && token.charAt(token.length() - 1) == '>') {
                                token = token.substring(1, token.length() - 1);
                                value.add(token.toUpperCase(Locale.ROOT));
                            } else {
                                token = token.strip();
                                value.add(token);
                            }
                        }
                    }
                    if (!productionsSet.productions.containsKey(key)) {
                        productionsSet.productions.put(key, new ArrayList<>());
                    }
                    List<List<String>> list = productionsSet.productions.get(key);
                    list.add(value);

                }
                counter++;
            }
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }

    void printNonTerminals () {
        System.out.println(nonterminals);
    }

    void printTerminals () {
        System.out.println(terminals);
    }

    void printProductions () {
        System.out.println(productionsSet);
    }

    List<Production> printProductionsForGivenNonTerminal (String nonterminal) {
        List<Production> productions = new ArrayList<>();
        if (!this.nonterminals.contains(nonterminal)) {
            System.out.println("This terminal does not exist!");
        }
        if (isCFG()) {
            String set = "";
            for (Map.Entry<List<String>, List<List<String>>> entry : productionsSet.productions.entrySet()) {
                if (entry.getKey().get(0).equals(nonterminal)) {

                    for (String elem1 : entry.getKey()) {
                        set += elem1 + " ";
                    }
                    set += "-> ";
                    for (List<String> elem1 : entry.getValue()) {
                        productions.add(new Production(entry.getKey(), elem1));
                        for (String elem2 : elem1) {
                            set += elem2 + " ";
                        }
                    }
                    set += "\n";
                }
            }
            System.out.println(set);
            return productions;
        } else {
            System.out.println("The grammar is not CFG!");
            return null;
        }
    }

    Boolean isCFG () {
        for (Map.Entry<List<String>, List<List<String>>> entry : productionsSet.productions.entrySet()) {
            if (entry.getKey().size() != 1)
                return false;
        }
        return true;
    }

    Production enhanceGrammar () {
        String key = "SS";
        List<String> lhr = new ArrayList<>();
        lhr.add(key);
        List<String> rhs = new ArrayList<>();
        rhs.add(starterNonterminal);
        List<List<String>> rhsList = new ArrayList<>();
        rhsList.add(rhs);
        productionsSet.productions.put(lhr, rhsList);
        return new Production(lhr, rhs);
    }


}
