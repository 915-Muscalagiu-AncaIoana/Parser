import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LRParser {
    Grammar grammar;
    List<State> canonicalCollection;
    ParsingTable parsingTable;

    ParsingStrategy parsingStrategy;

    public LRParser (Grammar grammar) {
        this.grammar = grammar;
        this.canonicalCollection = new ArrayList<>();
    }

    public boolean hasNonterminalAfterDot (Production production) {
        if (production.getNextSymbol() != null) {
            return grammar.nonterminals.contains(production.getNextSymbol());
        }
        return false;
    }

    State goTo (State state, String symbol) {
        Set<Production> productions = new HashSet<>();
        for (Production production : state.getProductionSet()) {
            String nextSymbol = production.getNextSymbol();
            if (nextSymbol != null && nextSymbol.equals(symbol)) {
                var copyProduction = new Production(production);
                productions.add(copyProduction);
                copyProduction.movePoint();
            }
        }
        return closure(productions);
    }

    State closure (Set<Production> productions) {
        State newState = new State();
        newState.productionSet.addAll(productions);
        Boolean isFinished = false;
        while (!isFinished) {
            isFinished = true;
            for (Production production : newState.productionSet) {
                if (hasNonterminalAfterDot(production)) {
                    String nonterminal = production.rhs.get(production.pointIndex);
                    List<Production> productionList = grammar.printProductionsForGivenNonTerminal(nonterminal);
                    for (Production production1 : productionList) {
                        if (!newState.productionSet.contains(production1)) {
                            newState.productionSet.add(production1);
                            isFinished = false;
                        }
                    }
                }
            }
        }
        return newState;
    }

    void computeCanonicalCollection () {
        State initialState = new State();
        initialState.productionSet.add(grammar.enhanceGrammar());
        initialState = closure(initialState.productionSet);
        canonicalCollection.add(initialState);
        System.out.println(initialState);
        boolean isFinished = false;
        List<String> symbols = new ArrayList<>();
        symbols.addAll(grammar.nonterminals);
        symbols.addAll(grammar.terminals);
        Integer lastIndex = 0;
        while (!isFinished) {
            isFinished = true;
            for (int i = lastIndex; i < canonicalCollection.size(); i++) {
                State state = canonicalCollection.get(i);
                for (String symbol : symbols) {
                    State newState = goTo(state, symbol);
                    if (!newState.productionSet.isEmpty()) {
                        System.out.println(newState);
                    }
                    if (!newState.productionSet.isEmpty() && !canonicalCollection.contains(newState)) {
                        canonicalCollection.add(newState);
                        isFinished = false;
                        lastIndex = i;
                        break;
                    }
                    if (!isFinished) {
                        break;
                    }
                }
            }
        }
        int index = 0;
        for (State state : canonicalCollection) {
            System.out.print("S" + index + ": ");
            System.out.println(state);
            index++;
        }
    }

    void createParsingTable () {
        List<String> symbols = new ArrayList<>();
        symbols.addAll(grammar.nonterminals);
        symbols.addAll(grammar.terminals);
        System.out.println(grammar.productionsSet);
        parsingTable = new ParsingTable(canonicalCollection.size(), symbols);
        int index = 0;
        for (State state : canonicalCollection) {
            List<String> actions = new ArrayList<>();
            for (Production production : state.productionSet) {
                if (production.isPointAtEnd()) {
                    if (production.lhs.contains("SS")) {
                        actions.add("acc");
                    } else {
                        actions.add("reduce " + grammar.productionsSet.getProductionIndex(production));
                        System.out.println("reduce " + grammar.productionsSet.getProductionIndex(production));
                        System.out.println(production);
                    }
                } else {
                    actions.add("shift");
                }
            }
            List<String> districtActions = actions.stream().distinct().collect(Collectors.toList());
            if (districtActions.size() != 1) {
                System.out.println("Conflict at row " + index);
                return;
            }
            parsingTable.setCell(index, "action", districtActions.get(0));
            index++;
        }
        Integer stateIndex = 0;

        for (State state : canonicalCollection) {
            for (String symbol : symbols) {
                State resultedState = goTo(state, symbol);
                int resultedStateIndex = 0;
                for (State existentState : canonicalCollection) {
                    if (resultedState.equals(existentState)) {
                        parsingTable.setCell(stateIndex, symbol, String.valueOf(resultedStateIndex));
                        break;
                    }
                    resultedStateIndex++;
                }
            }
            stateIndex++;

        }
        System.out.println(parsingTable.toString());
    }

    public Boolean isSequenceAccepted (String sequence) {
        Integer stateindex = 0;
        parsingStrategy = new ParsingStrategy(sequence, 0);
        Boolean isFinished = false;
        int parsingIndex = 1;
        while (!isFinished) {
            String action = parsingTable.getCell(stateindex, "action");
            if (action.equals("shift")) {
                String nextSymbol = parsingStrategy.inputStack.get(0);
                String nextStateIndex = parsingTable.getCell(stateindex, nextSymbol);
                List<String> newWorkStackConfig = new ArrayList<>();
                newWorkStackConfig.addAll(parsingStrategy.workStack.get(parsingIndex - 1));
                newWorkStackConfig.add(nextSymbol);
                newWorkStackConfig.add(nextStateIndex);
                parsingStrategy.workStack.add(newWorkStackConfig);
            } else {
                if (action.equals("acc")) {
                    isFinished = true;
                    System.out.println("Sequence is accepted!");
                    break;
                }
                else {

                }
            }


        }


        return false;
    }


}
