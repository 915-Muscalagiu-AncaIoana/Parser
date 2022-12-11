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
    String starterTerminal;

    void readFromFile(String fileName) {
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
                    starterTerminal = line.strip().toLowerCase(Locale.ROOT);
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
                    productionsSet.productions.put(key, value);
                }
                counter++;
            }
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }

    void printNonTerminals() {
        System.out.println(nonterminals);
    }

    void printTerminals() {
        System.out.println(terminals);
    }

    void printProductions() {
        System.out.println(productionsSet);
    }

    void printProductionsForGivenNonTerminal(String nonTerminal) {
        if (!this.nonterminals.contains(nonTerminal)) {
            System.out.println("This non terminal does not exist!");
        }
        if (isCFG()) {
            String set = "";
            for (Map.Entry<List<String>, List<String>> entry : productionsSet.productions.entrySet()) {
                if (entry.getKey().get(0).equals(nonTerminal)) {
                    for (String elem1 : entry.getKey()) {
                        set += elem1 + " ";
                    }
                    set += "-> ";
                    for (String elem1 : entry.getValue()) {
                        set += elem1 + " ";
                    }
                    set += "\n";
                }
            }
            if (set == "") {
                System.out.println("This non terminal doesn't have any productions!");
            } else {
                System.out.println(set);
            }
        } else {
            System.out.println("The grammar is not CFG!");
        }
    }

    Boolean isCFG() {
        for (Map.Entry<List<String>, List<String>> entry : productionsSet.productions.entrySet()) {
            if (entry.getKey().size() != 1)
                return false;
        }
        return true;
    }

}
