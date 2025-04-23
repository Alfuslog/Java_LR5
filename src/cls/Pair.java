package cls;

public class Pair<T, U> {
    public T first;
    public U second;

    // Конструктор для инициализации пары
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    // Статический фабричный метод для создания пары
    public static <T, U> Pair<T, U> makePair(T first, U second) {
        return new Pair<>(first, second);
    }

    // Геттеры
    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    // Сеттеры
    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }


}