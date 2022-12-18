import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LRParser {
    Grammar grammar;
    List<State> canonicalCollection;

    public LRParser (Grammar grammar) {
        this.grammar = grammar;
        this.canonicalCollection = new ArrayList<>();
    }

    public boolean hasNonterminalAfterDot(Production production){
        if(production.getNextSymbol()!= null) {
            return grammar.nonterminals.contains(production.getNextSymbol());
        }
        return false;
    }
    State goTo(State state, String symbol){
        Set<Production> productions = new HashSet<>();
        for (Production production : state.getProductionSet()){
            String nextSymbol = production.getNextSymbol();
            if(nextSymbol != null && nextSymbol.equals(symbol)){
                var copyProduction = new Production(production);
                productions.add(copyProduction);
                copyProduction.movePoint();
            }
        }
        return closure(productions);
    }

    State closure(Set<Production> productions){
      State newState = new State();
      newState.productionSet.addAll(productions);
      Boolean isFinished = false;
      while(!isFinished) {
          isFinished = true;
          for (Production production : newState.productionSet) {
            if(hasNonterminalAfterDot(production)){
                String nonterminal = production.rhs.get(production.pointIndex);
                List<Production> productionList = grammar.printProductionsForGivenNonTerminal(nonterminal);
                for ( Production production1 : productionList){
                    if (!newState.productionSet.contains(production1)){
                        newState.productionSet.add(production1);
                        isFinished = false;
                    }
                }
            }
          }
      }
      return newState;
    }

    void computeCanonicalCollection(){
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
     while(!isFinished){
         isFinished = true;
         for (int i = lastIndex; i<canonicalCollection.size();i++){
             State state = canonicalCollection.get(i);
             for (String symbol : symbols){
                 State newState = goTo(state,symbol);
                 if(!newState.productionSet.isEmpty()){
                     System.out.println(newState);
                 }
                 if (!newState.productionSet.isEmpty()&& !canonicalCollection.contains(newState)){
                     canonicalCollection.add(newState);
                     isFinished = false;
                     lastIndex = i;
                     break;
                 }
                 if(!isFinished){
                     break;
                 }
             }
         }
     }
     int index = 0;
     for(State state: canonicalCollection){
         System.out.print("S"+index+": ");
         System.out.println(state);
         index++;
     }
    }


}
