import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LRParserTest {
    LRParser lrParser;

    @BeforeEach
    void setUp() {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g1.txt");
        lrParser = new LRParser(grammar);
    }

    @Test
    @DisplayName("Closure")
    void testClosureWithNonterminalBeforeDot() {
        Set<Production> productions = new HashSet<>();
        List<String> lhs = new ArrayList<>();
        List<String> rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("b");
        rhs.add("A");
        Integer pointIndex = 1;
        Production production = new Production(lhs,rhs);
        production.pointIndex = pointIndex;
        productions.add(production);
        State actualState=lrParser.closure(productions);
        State expectedState = new State();
        expectedState.productionSet.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("b");
        rhs.add("A");
        production = new Production(lhs,rhs);
        expectedState.productionSet.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("c");
        production = new Production(lhs,rhs);
        expectedState.productionSet.add(production);
        assertEquals(expectedState,actualState);
    }
    @Test
    @DisplayName("Goto")
    void testGoToWithInitialAState(){
        State initialState = new State();
        initialState.productionSet.add(lrParser.grammar.enhanceGrammar());
        initialState = lrParser.closure(initialState.productionSet);
        State actualState = lrParser.goTo(initialState,"a");
        State expectedState = new State();
        List<String> lhs = new ArrayList<>();
        List<String> rhs = new ArrayList<>();
        lhs.add("S");
        rhs.add("a");
        rhs.add("A");
        Integer pointIndex = 1;
        Production production = new Production(lhs,rhs);
        production.pointIndex = pointIndex;
        expectedState.productionSet.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("b");
        rhs.add("A");
        production = new Production(lhs,rhs);
        expectedState.productionSet.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("c");
        production = new Production(lhs,rhs);
        expectedState.productionSet.add(production);
        assertEquals(expectedState,actualState);
    }

}
