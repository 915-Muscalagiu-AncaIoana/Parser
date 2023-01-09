import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Production {
    List<String> lhs;
    List<String> rhs;
    int pointIndex;

    public Production (List<String> lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.pointIndex = 0;
    }

    public Production (Production production) {
        this.lhs = new ArrayList<>();
        this.lhs.addAll(production.lhs);
        this.rhs = new ArrayList<>();
        this.rhs.addAll(production.rhs);
        this.pointIndex = production.pointIndex;
    }

    public List<String> getLhs () {
        return lhs;
    }

    public void setLhs (List<String> lhs) {
        this.lhs = lhs;
    }

    public List<String> getRhs () {
        return rhs;
    }

    public void setRhs (List<String> rhs) {
        this.rhs = rhs;
    }

    public int getPointIndex () {
        return pointIndex;
    }

    public void setPointIndex (int pointIndex) {
        this.pointIndex = pointIndex;
    }
    public String getNextSymbol(){
        if(pointIndex < rhs.size()) {
            return rhs.get(pointIndex);
        }
        return null;
    }

    public boolean isPointAtEnd(){
        return getNextSymbol()== null;
    }
    public void movePoint(){
        pointIndex++;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Production that = (Production) o;
        Boolean lhs;
        Boolean rhs;
        if(this.lhs.size()!= that.lhs.size())
            return false;
        if(this.rhs.size()!= that.rhs.size())
            return false;
        for(String symbol: this.lhs){
            if(!that.lhs.contains(symbol))
                return false;
        }
        for(String symbol: this.rhs){
            if(!that.rhs.contains(symbol))
                return false;
        }
        return pointIndex == that.pointIndex ;
    }

    @Override
    public int hashCode () {
        return Objects.hash(lhs, rhs, pointIndex);
    }

    @Override
    public String toString () {
        return "Production{" +
                "lhs=" + lhs +
                ", rhs=" + rhs +
                ", pointIndex=" + pointIndex +
                '}';
    }
}
