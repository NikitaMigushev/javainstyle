package ru.javainstyle.concurrent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Container {
    private static final AtomicLong groupIdCounter = new AtomicLong(0);
    private Group group = new Group(this);

    private static class Group {
        Long id;
        double amountPerContainer;
        Set<Container> members;

        Group(Container c) {
            this.id = groupIdCounter.incrementAndGet();
            members = new HashSet<Container>();
            members.add(c);
        }
    }

    public double getAmount() {
        synchronized (group) {
            return group.amountPerContainer;
        }
    }

    /**
     * ДОбавляет воду и распределяет ее равномерно между контейнерами
     * @param amount
     */
    public void addWater(double amount) {
        while (true) {
            Object monitor = group;
            synchronized (monitor) {
                if (monitor == group) {
                    double amountPerContainer = amount / group.members.size();
                    group.amountPerContainer += amountPerContainer;
                    return;
                }
            }
        }
    }

    /**
     * Метод соединяет две группы контейнеров (this.group и other.group) в одну. При этом:
     * Все контейнеры из обеих групп объединяются в одну.
     * Объем воды перераспределяется равномерно между всеми контейнерами объединенной группы.
     */
    public void connectTo(Container other) {
        while (true) {
            if (group==other.group) return;
            Object firstMonitor, secondMonitor;
            if (group.id < other.group.id) {
                firstMonitor = group;
                secondMonitor = other.group;
            } else {
                firstMonitor = other.group;
                secondMonitor = group;
            }
            synchronized (firstMonitor) {
                synchronized (secondMonitor) {
                    if ((firstMonitor == group && secondMonitor == other.group) ||
                            (secondMonitor == group && firstMonitor == other.group)) {
                        int size1 = group.members.size(),
                                size2 = other.group.members.size();
                        double tot1 = group.amountPerContainer * size1,
                                tot2 = other.group.amountPerContainer * size2,
                                newAmount = (tot1 + tot2) / (size1 + size2);

                        group.members.addAll(other.group.members);
                        group.amountPerContainer = newAmount;

                        for (Container x: other.group.members) x.group = group;
                        return;
                    }
                }
            }
        }
    }
}
