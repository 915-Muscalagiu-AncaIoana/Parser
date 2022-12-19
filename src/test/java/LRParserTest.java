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
    @DisplayName("Canonical collection")
    void testCanonicalCollection() {
        List<State> canonicalCollection = new ArrayList<>();
        lrParser.computeCanonicalCollection();
        canonicalCollection = lrParser.canonicalCollection;
        List<State> expectedCanonicalCollection = new ArrayList<>();
        State initialState = new State();
        initialState.productionSet.add(lrParser.grammar.enhanceGrammar());
        initialState = lrParser.closure(initialState.productionSet);
        expectedCanonicalCollection.add(initialState);
        // s1
        Set<Production> set = new HashSet<>();
        List<String> lhs = new ArrayList<>();
        List<String> rhs = new ArrayList<>();
        lhs.add("SS");
        rhs.add("S");
        Integer pointIndex = 1;
        Production production = new Production(lhs,rhs);
        production.pointIndex = pointIndex;
        set.add(production);
        State state = new State(set);
        expectedCanonicalCollection.add(state);
        // s2
        set = new HashSet<>();
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("S");
        rhs.add("a");
        rhs.add("A");
        pointIndex = 1;
        production = new Production(lhs,rhs);
        production.pointIndex = pointIndex;
        set.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("b");
        rhs.add("A");
        production = new Production(lhs,rhs);
        set.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("c");
        production = new Production(lhs,rhs);
        set.add(production);
        state = new State(set);
        expectedCanonicalCollection.add(state);
        // s3
        set = new HashSet<>();
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("S");
        rhs.add("a");
        rhs.add("A");
        pointIndex = 2;
        production = new Production(lhs,rhs);
        production.pointIndex = pointIndex;
        set.add(production);
        state = new State(set);
        expectedCanonicalCollection.add(state);
        // s4
        set = new HashSet<>();
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("b");
        rhs.add("A");
        pointIndex = 1;
        production = new Production(lhs,rhs);
        production.pointIndex = pointIndex;
        set.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("b");
        rhs.add("A");
        production = new Production(lhs,rhs);
        set.add(production);
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("c");
        production = new Production(lhs,rhs);
        set.add(production);
        state = new State(set);
        expectedCanonicalCollection.add(state);
        // s5
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("c");
        production = new Production(lhs,rhs);
        set.add(production);
        state = new State(set);
        expectedCanonicalCollection.add(state);
        // s6
        set = new HashSet<>();
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();
        lhs.add("A");
        rhs.add("b");
        rhs.add("A");
        pointIndex = 2;
        production = new Production(lhs,rhs);
        production.pointIndex = pointIndex;
        set.add(production);
        state = new State(set);
        expectedCanonicalCollection.add(state);
        assertEquals(expectedCanonicalCollection, canonicalCollection);
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
