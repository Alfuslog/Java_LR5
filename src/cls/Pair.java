package cls;

public class Pair<S, T> {
    private S first;
    private T second;


    public Pair(S first, T second){
        this.first = first;
        this.second = second;
    }

    public static <S, T> Pair<S, T> make_pair(S first, T second){
        return new Pair<>(first, second);
    }

}
