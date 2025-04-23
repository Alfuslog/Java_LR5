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

    // Пример использования
    public static void main(String[] args) {
        // Создание пары с помощью конструктора
        Pair<String, Integer> pair1 = new Pair<>("Строка", 42);
        System.out.println("Пара 1: " + pair1);

        // Создание пары с помощью статического метода
        Pair<Double, Boolean> pair2 = Pair.makePair(3.14, true);
        System.out.println("Пара 2: " + pair2);

        // Изменение значений
        pair1.setFirst("Новая строка");
        pair1.setSecond(100);
        System.out.println("Измененная пара 1: " + pair1);

        // Пример с поиском максимального элемента
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        Pair<Integer, Integer> maxPair = findMax(array);
        System.out.println("Максимальный элемент: " + maxPair.first +
                " на позиции: " + maxPair.second);
    }

    // Пример метода, возвращающего пару значений
    public static Pair<Integer, Integer> findMax(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int max = array[0];
        int index = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                index = i;
            }
        }

        return new Pair<>(max, index);
    }
}