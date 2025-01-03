package ru.javainstyle.speed1;

import java.util.HashSet;
import java.util.Set;

public class Container {
    //Инициируем поле group и кладем туда текущий контейнер
    private Group group = new Group(this);


    //Инкапсулируем логику, связанную с группами контейнеров для минимизации области визимости
    private static class Group {
        double amountPerContainer;
        Set<Container> members;

        Group(Container c) {
            members = new HashSet<Container>();
            members.add(c);
        }
    }

    public double getAmount() {
        return group.amountPerContainer;
    }

    //когда мы добавляем воды, мы добавляем средний объем на один контейнер к среднему на группу
    public void addWater(double amount) {
        double amountPerContainer = amount / group.members.size();
        group.amountPerContainer += amountPerContainer;
    }


    /**
     * Метод соединяет две группы контейнеров (this.group и other.group) в одну. При этом:
     * Все контейнеры из обеих групп объединяются в одну.
     * Объем воды перераспределяется равномерно между всеми контейнерами объединенной группы.
     */
    public void connectTo(Container other) {

        //Если текущий контейнер (this) и другой контейнер (other) уже принадлежат одной группе (т.е. ссылаются на один и тот же объект group), ничего не нужно делать.
        if (group==other.group) return;

        int size1 = group.members.size(),
                size2 = other.group.members.size();
        double tot1 = group.amountPerContainer * size1,
                tot2 = other.group.amountPerContainer * size2,
                newAmount = (tot1 + tot2) / (size1 + size2);

        group.members.addAll(other.group.members);
        group.amountPerContainer = newAmount;

        //обновляет ссылки на группу (group) для всех контейнеров из группы other.group, чтобы они указывали на новую объединенную группу (this.group).
        for (Container x: other.group.members) x.group = group;
    }
}
