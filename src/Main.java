
import java.util.*;

// ЗАДАНИЕ 1
class Pair<T1, T2> {
    public T1 first;
    public T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public static <T1, T2> Pair<T1, T2> makePair(T1 first, T2 second) {
        return new Pair<>(first, second);
    }
}

// ЗАДАНИЕ 2
class Bag {
    private final Object[] items;
    private int size;

    public Bag(int capacity) {
        items = new Object[capacity];
        size = 0;
    }

    public final void add(Object item) {
        if (size >= items.length) throw new IllegalStateException("Bag is full");
        int pos = (int)(Math.random() * (size + 1));
        if (pos != size) items[size] = items[pos];
        items[pos] = item;
        size++;
    }

    public final Object remove() {
        if (size == 0) return null;
        int pos = (int)(Math.random() * size);
        Object removed = items[pos];
        items[pos] = items[size - 1];
        items[size - 1] = null;
        size--;
        return removed;
    }

    public Object peek() {
        if (size == 0) return null;
        return items[(int)(Math.random() * size)];
    }

    public int size() {
        return size;
    }
}

// ЗАДАНИЕ 3
class PairBag {
    private final Bag bag;

    public PairBag(int capacity) {
        bag = new Bag(capacity);
    }

    public void add(Pair<?, ?> pair) {
        bag.add(pair);
    }

    public Pair<?, ?> remove() {
        return (Pair<?, ?>) bag.remove();
    }

    public int size() {
        return bag.size();
    }
}

// ЗАДАНИЕ 4
class GPairBag<T1, T2> {
    private final List<Pair<T1, T2>> items;

    public GPairBag() {
        items = new ArrayList<>();
    }

    public void add(Pair<T1, T2> pair) {
        items.add(pair);
    }

    public Pair<T1, T2> remove() {
        if (items.isEmpty()) return null;
        return items.remove((int)(Math.random() * items.size()));
    }

    public int size() {
        return items.size();
    }
}

// ЗАДАНИЕ 5
class GenericPairBag<T1, T2> {
    private final ArrayList<Pair<T1, T2>> pairs = new ArrayList<>();

    public void add(Pair<T1, T2> pair) {
        pairs.add(pair);
    }

    public Pair<T1, T2> remove() {
        if (pairs.isEmpty()) return null;
        return pairs.remove((int)(Math.random() * pairs.size()));
    }

    public int size() {
        return pairs.size();
    }
}

// ЗАДАНИЕ 6
class Tournament {
    public static void runTournament(int teamCount) {
        if (teamCount <= 1 || (teamCount & (teamCount - 1)) != 0) teamCount = 8;
        GenericPairBag<String, String> gameBag = new GenericPairBag<>();
        ArrayList<String> teams = new ArrayList<>();
        for (int i = 1; i <= teamCount; i++) {
            teams.add("Команда" + i);
        }
        Collections.shuffle(teams);
        Queue<String> queue = new LinkedList<>(teams);
        while (queue.size() >= 2) {
            gameBag.add(new Pair<>(queue.poll(), queue.poll()));
        }
        Scanner sc = new Scanner(System.in);
        while (gameBag.size() > 1 || queue.size() > 1) {
            GenericPairBag<String, String> nextRound = new GenericPairBag<>();
            while (gameBag.size() > 0) {
                Pair<String, String> match = gameBag.remove();
                System.out.println("Кто выиграл? 1 - " + match.first + ", 2 - " + match.second);
                int winner = sc.nextInt();
                queue.add(winner == 1 ? match.first : match.second);
            }
            while (queue.size() >= 2) {
                nextRound.add(new Pair<>(queue.poll(), queue.poll()));
            }
            gameBag = nextRound;
        }
        System.out.println("Победитель турнира: " + queue.peek());
    }
}

// ЗАДАНИЕ 7
class DList<T1, T2> {
    private final List<T1> keys = new ArrayList<>();
    private final List<List<T2>> values = new ArrayList<>();

    public void add(T1 key, List<T2> val) {
        keys.add(key);
        values.add(val);
    }


    public List<T2> getByKey(T1 key) {
        int index = keys.indexOf(key);
        if (index >= 0) return values.get(index);
        return null;
    }

    public List<T2> getByIndex(int i) {
        return values.get(i);
    }
}

// ЗАДАНИЕ 8
class CoinChange {
    public static DList<Integer, Integer> minCoins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        List<List<Integer>> ways = new ArrayList<>();
        DList<Integer, Integer> result = new DList<>();
        for (int i = 0; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
            ways.add(new ArrayList<>());
        }
        dp[0] = 0;
        ways.set(0, new ArrayList<>());

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                    if (dp[i] > dp[i - coin] + 1) {
                        dp[i] = dp[i - coin] + 1;
                        List<Integer> newList = new ArrayList<>(ways.get(i - coin));
                        newList.add(coin);
                        ways.set(i, newList);
                    }
                }
            }
            result.add(i, ways.get(i));
        }
        return result;
    }
}

// ЗАДАНИЕ 9
abstract class HashFunction<K> {
    protected int tableSize;

    public HashFunction(int tableSize) {
        this.tableSize = tableSize;
    }

    public abstract int hash(K key);
}

interface KeyContainable<K> {
    boolean contains(K key);
}

class HashTable<K, T extends KeyContainable<K>> {
    private final List<T>[] table;
    private final HashFunction<K> function;

    @SuppressWarnings("unchecked")
    public HashTable(int size, HashFunction<K> function) {
        table = new List[size];
        for (int i = 0; i < size; i++) table[i] = new LinkedList<>();
        this.function = function;
    }

    public void add(T item, K key) {
        int index = function.hash(key);
        table[index].add(item);
    }

    public T find(K key) {
        int index = function.hash(key);
        for (T item : table[index]) {
            if (item.contains(key)) return item;
        }
        return null;
    }
}

// ЗАДАНИЕ 10
class Person implements KeyContainable<String> {
    String surname;
    int age;

    public Person(String surname, int age) {
        this.surname = surname;
        this.age = age;
    }

    public boolean contains(String key) {
        return surname.equals(key);
    }
}

class PersonHashFunction extends HashFunction<String> {
    public PersonHashFunction(int size) {
        super(size);
    }

    @Override
    public int hash(String s) {
        return Math.abs(s.hashCode()) % tableSize;
    }
}


public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nВыберите задание для демонстрации (1-10) или 0 для выхода:");
            System.out.println("1. Класс Pair");
            System.out.println("2. Класс Bag");
            System.out.println("3. Класс PairBag");
            System.out.println("4. Класс GPairBag");
            System.out.println("5. Класс GenericPairBag");
            System.out.println("6. Турнир");
            System.out.println("7. Двухуровневый список DList");
            System.out.println("8. Размен монет CoinChange");
            System.out.println("9-10. Хеш-таблица и персона");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline


            switch (choice) {
                case 0 -> System.exit(0);
                case 1 -> demoPair();
                case 2 -> demoBag();
                case 3 -> demoPairBag();
                case 4 -> demoGPairBag();
                case 5 -> demoGenericPairBag();
                case 6 -> demoTournament();
                case 7 -> demoDList();
                case 8 -> demoCoinChange();
                case 9, 10 -> demoHashTable();
                default -> System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private static void demoPair() {
        System.out.println("\n=== Демонстрация класса Pair ===");
        System.out.println("Введите первое значение пары (строка):");
        String first = scanner.nextLine();
        System.out.println("Введите второе значение пары (число):");
        int second = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Pair<String, Integer> pair = Pair.makePair(first, second);
        System.out.println("Создана пара: [" + pair.first + ", " + pair.second + "]");
    }

    private static void demoBag() {
        System.out.println("\n=== Демонстрация класса Bag ===");
        System.out.println("Введите вместимость Bag:");
        int capacity = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Bag bag = new Bag(capacity);
        for (int i = 0; i < capacity; i++) {
            System.out.println("Введите элемент " + (i+1) + ":");
            String item = scanner.nextLine();
            bag.add(item);
        }

        System.out.println("Размер Bag: " + bag.size());
        System.out.println("Удаляем случайный элемент: " + bag.remove());
        System.out.println("Теперь размер Bag: " + bag.size());
    }

    private static void demoPairBag() {
        System.out.println("\n=== Демонстрация класса PairBag ===");
        System.out.println("Введите количество пар для добавления:");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline

        PairBag pairBag = new PairBag(count);
        for (int i = 0; i < count; i++) {
            System.out.println("Введите первую часть пары " + (i+1) + ":");
            String first = scanner.nextLine();
            System.out.println("Введите вторую часть пары " + (i+1) + ":");
            String second = scanner.nextLine();
            pairBag.add(new Pair<>(first, second));
        }

        System.out.println("Размер PairBag: " + pairBag.size());
        Pair<?, ?> removed = pairBag.remove();
        System.out.println("Удаленная пара: [" + removed.first + ", " + removed.second + "]");
    }

    private static void demoGPairBag() {
        System.out.println("\n=== Демонстрация класса GPairBag ===");
        GPairBag<String, Integer> gPairBag = new GPairBag<>();

        System.out.println("Введите количество пар для добавления:");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < count; i++) {
            System.out.println("Введите строку для пары " + (i+1) + ":");
            String str = scanner.nextLine();
            System.out.println("Введите число для пары " + (i+1) + ":");
            int num = scanner.nextInt();
            scanner.nextLine(); // consume newline
            gPairBag.add(new Pair<>(str, num));
        }

        System.out.println("Размер GPairBag: " + gPairBag.size());
        Pair<String, Integer> removed = gPairBag.remove();
        System.out.println("Удаленная пара: [" + removed.first + ", " + removed.second + "]");
    }

    private static void demoGenericPairBag() {
        System.out.println("\n=== Демонстрация класса GenericPairBag ===");
        GenericPairBag<Double, Boolean> genericPairBag = new GenericPairBag<>();

        System.out.println("Введите количество пар для добавления:");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline


        for (int i = 0; i < count; i++) {
            System.out.println("Введите число для пары " + (i+1) + ":");
            double num = scanner.nextDouble();
            System.out.println("Введите true/false для пары " + (i+1) + ":");
            boolean bool = scanner.nextBoolean();
            scanner.nextLine(); // consume newline
            genericPairBag.add(new Pair<>(num, bool));
        }

        System.out.println("Размер GenericPairBag: " + genericPairBag.size());
        Pair<Double, Boolean> removed = genericPairBag.remove();
        System.out.println("Удаленная пара: [" + removed.first + ", " + removed.second + "]");
    }

    private static void demoTournament() {
        System.out.println("\n=== Демонстрация турнира ===");
        System.out.println("Введите количество команд (степень 2, например 2, 4, 8...):");
        int teamCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Tournament.runTournament(teamCount);
    }

    private static void demoDList() {
        System.out.println("\n=== Демонстрация DList ===");
        DList<String, Integer> dList = new DList<>();

        System.out.println("Введите количество записей:");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < count; i++) {
            System.out.println("Введите ключ " + (i+1) + ":");
            String key = scanner.nextLine();
            System.out.println("Введите числа через пробел для значения:");
            String[] nums = scanner.nextLine().split(" ");
            List<Integer> values = new ArrayList<>();
            for (String num : nums) {
                values.add(Integer.parseInt(num));
            }
            dList.add(key, values);
        }

        System.out.println("Введите ключ для поиска:");
        String searchKey = scanner.nextLine();
        System.out.println("Результат поиска: " + dList.getByKey(searchKey));
    }

    private static void demoCoinChange() {
        System.out.println("\n=== Демонстрация размена монет ===");
        System.out.println("Введите номиналы монет через пробел (например: 1 2 5):");
        String[] coinStrs = scanner.nextLine().split(" ");
        int[] coins = new int[coinStrs.length];
        for (int i = 0; i < coinStrs.length; i++) {
            coins[i] = Integer.parseInt(coinStrs[i]);
        }

        System.out.println("Введите сумму для размена:");
        int amount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        DList<Integer, Integer> result = CoinChange.minCoins(coins, amount);
        System.out.println("Оптимальный размен для " + amount + ": " + result.getByKey(amount));
    }

    private static void demoHashTable() {
        System.out.println("\n=== Демонстрация хеш-таблицы ===");
        System.out.println("Введите размер хеш-таблицы:");
        int size = scanner.nextInt();
        scanner.nextLine(); // consume newline

        HashTable<String, Person> table = new HashTable<>(size, new PersonHashFunction(size));

        System.out.println("Введите количество людей для добавления:");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < count; i++) {
            System.out.println("Введите фамилию человека " + (i+1) + ":");
            String surname = scanner.nextLine();
            System.out.println("Введите возраст человека " + (i+1) + ":");
            int age = scanner.nextInt();
            scanner.nextLine(); // consume newline
            table.add(new Person(surname, age), surname);
        }

        System.out.println("Введите фамилию для поиска:");
        String searchSurname = scanner.nextLine();
        Person found = table.find(searchSurname);
        if (found != null) {
            System.out.println("Найден: " + found.surname + ", возраст " + found.age);
        } else {
            System.out.println("Человек с такой фамилией не найден");
        }
    }
}
