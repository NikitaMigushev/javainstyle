package ru.javainstyle.speed2;

/**
 * Представляет контейнер для воды, который можно соединять с другими контейнерами.
 * Соединенные контейнеры образуют группу, в которой вода равномерно распределяется между всеми участниками.
 */
public class Container {
    private double amount;

    // Указывает на следующий контейнер в группе.
    // По умолчанию контейнер связан с самим собой, что позволяет работать с одиночным контейнером.
    private Container next = this;

    /**
     * Соединяет текущий контейнер с указанным контейнером.
     * После выполнения этого метода два контейнера и их группы объединяются в одну общую группу.
     * Перестраиваются ссылки, чтобы обеспечить замкнутый цикл между контейнерами.
     *
     * @param other контейнер, с которым нужно соединить текущий контейнер
     */
    public void connectTo(Container other) {
        Container oldNext = next;
        next = other.next;
        other.next = oldNext;
    }


    /**
     * Добавляет указанное количество воды в текущий контейнер.
     *
     * @param amount количество воды, которое нужно добавить
     */
    public void addWater(double amount) {
        this.amount += amount;
    }

    /**
     * Возвращает количество воды в текущем контейнере.
     * Перед возвратом выполняет обновление группы, чтобы равномерно распределить воду
     * между всеми контейнерами, связанными в группу.
     *
     * @return количество воды в текущем контейнере
     */
    public double getAmount() {
        updateGroup();
        return amount;
    }


    /**
     * Обновляет группу контейнеров, перераспределяя воду поровну между всеми участниками группы.
     *
     * Проходит по всем контейнерам в группе, подсчитывает общее количество воды
     * и размер группы, а затем распределяет воду равномерно между всеми контейнерами.
     */
    private void updateGroup() {
        Container current = this;
        double totalAmount = 0;
        int groupSize = 0;

        do {
            totalAmount += current.amount;
            groupSize++;
            current = current.next;
        } while (current != this);
        double newAmount = totalAmount / groupSize;

        current = this;
        do {
            current.amount = newAmount;
            current = current.next;
        } while (current != this);
    }
}
