package ru.javainstyle.speed3;

/**
 * Класс Container представляет собой контейнер, который может содержать воду и соединяться с другими контейнерами.
 *
 * <p>Класс использует структуру данных "союз-находка" (union-find) с компрессией пути для управления
 * соединениями между контейнерами. Каждый контейнер имеет:
 * - Поле `amount`, которое хранит количество воды.
 * - Ссылку на `parent`, указывающую на родительский контейнер в группе.
 * - Поле `size`, представляющее количество контейнеров в объединённой группе.
 *
 * <p>Основные функциональные возможности:
 * - `addWater(double amount)`: Распределяет добавленную воду поровну между всеми соединёнными контейнерами.
 * - `connectTo(Container other)`: Соединяет два контейнера, объединяя их группы и перераспределяя воду пропорционально.
 * - `getAmount()`: Возвращает количество воды в группе, к которой принадлежит контейнер.
 *
 * <p>Класс подходит для моделирования распределения воды в сети соединённых контейнеров.
 */
public class Container {
    private double amount;
    private Container parent = this;
    private int size = 1;


    /**
     * Ищет корневой контейнер с использованием компрессии пути.
     * <p>Если текущий контейнер не является корнем, метод рекурсивно
     * находит корневой контейнер и обновляет ссылку на родителя, что
     * уменьшает глубину дерева и ускоряет будущие операции.</p>
     *
     * @return Корневой контейнер текущей группы.
     */
    private Container findRootAndCompress() {
        if (parent != this) {
            parent = parent.findRootAndCompress();
        }
        return parent;
    }

    /**
     * Возвращает количество воды в группе контейнеров.
     * <p>Метод сначала находит корневой контейнер группы с помощью
     * компрессии пути, а затем возвращает количество воды,
     * хранящееся в этом корневом контейнере.</p>
     *
     * @return Количество воды в группе контейнеров.
     */
    public double getAmount() {
        Container root = findRootAndCompress();
        return root.amount;
    }

    /**
     * Добавляет указанное количество воды в группу контейнеров.
     * <p>Метод сначала находит корневой контейнер группы с использованием
     * компрессии пути. Затем добавляет воду, распределяя её равномерно
     * между всеми контейнерами в группе.</p>
     *
     * @param amount Количество воды, которое нужно добавить.
     */
    public void addWater(double amount) {
        Container root = findRootAndCompress();
        root.amount += amount / root.size;
    }

    /**
     * Соединяет текущий контейнер с другим контейнером, объединяя их группы.
     * <p>Метод находит корневые контейнеры обеих групп с помощью компрессии пути.
     * Если контейнеры уже принадлежат одной группе, операция не выполняется.
     * В противном случае группы объединяются, вода перераспределяется пропорционально
     * размеру групп, а размер новой группы обновляется.</p>
     *
     * @param other Контейнер, с которым нужно соединить текущий контейнер.
     */
    public void connectTo(Container other) {
        Container root1 = findRootAndCompress(),
                root2 = other.findRootAndCompress();
        if (root1==root2) {
            return;
        }

        int size1 = root1.size, size2 = root2.size;
        double newAmount = ((root1.amount * size1) +
                (root2.amount * size2)) / (size1 + size2);

        if (size1 <= size2) {
            root1.parent = root2;
            root2.amount = newAmount;
            root2.size += size1;
        } else {
            root2.parent = root1;
            root1.amount = newAmount;
            root1.size += size2;
        }
    }
}
