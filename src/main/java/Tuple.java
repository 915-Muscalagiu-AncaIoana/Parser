public class Tuple <T,V>{
    T key;
    V value;

    public Tuple (T key, V value) {
        this.key = key;
        this.value = value;
    }

    public T getKey () {
        return key;
    }

    public void setKey (T key) {
        this.key = key;
    }

    public V getValue () {
        return value;
    }

    public void setValue (V value) {
        this.value = value;
    }
}
