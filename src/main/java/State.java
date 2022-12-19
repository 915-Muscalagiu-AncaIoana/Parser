import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class State {
    Set<Production> productionSet;

    public State () {
        productionSet = new HashSet<>();
    }

    public State(Set<Production> productions) {
        productionSet = new HashSet<>(productions);
    }

    public Set<Production> getProductionSet () {
        return productionSet;
    }

    public void setProductionSet (Set<Production> productionSet) {
        this.productionSet = productionSet;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(productionSet, state.productionSet);
    }

    @Override
    public int hashCode () {
        return Objects.hash(productionSet);
    }

    @Override
    public String toString () {
        return "State{" +
                "productionSet=" + productionSet +
                '}';
    }
}
