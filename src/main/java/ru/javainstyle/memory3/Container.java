package ru.javainstyle.memory3;

import java.util.Arrays;

public class Container {
    private static int group[] = new int[0];
    private static float amount[] = new float[0];

    public static float getAmount(int containerID) {
        int groupID = group[containerID];
        return amount[groupID];
    }

    public static void addWater(int containerID, float water) {
        int groupID = group[containerID];
        int size = groupSize(groupID);

        // Добавляем воду в группу, равномерно распределяя её
        float totalAmount = amount[groupID] * size + water;
        amount[groupID] = totalAmount / size;
    }

    public static int newContainer() {
        int nContainers = group.length,
                nGroups = amount.length;

        amount = Arrays.copyOf(amount, nGroups + 1);
        group = Arrays.copyOf(group, nContainers + 1);
        group[nContainers] = nGroups;

        return nContainers;
    }

    public static void connect(int containerID1, int containerID2) {
        int groupID1 = group[containerID1],
                groupID2 = group[containerID2],
                size1 = groupSize(groupID1),
                size2 = groupSize(groupID2);

        if (groupID1 == groupID2) return;

        float amount1 = amount[groupID1] * size1,
                amount2 = amount[groupID2] * size2;
        amount[groupID1] = (amount1 + amount2) / (size1 + size2);

        for (int i=0; i<group.length; i++) {
            if (group[i] == groupID2) {
                group[i] = groupID1;
            }
        }
        removeGroupAndDefrag(groupID2);
    }

    private static int groupSize(int groupID) {
        int size = 0;
        for (int otherGroupID: group) {
            if (otherGroupID == groupID) {
                size++;
            }
        }
        return size;
    }

    private static void removeGroupAndDefrag(int groupID) {
        for (int containerID=0; containerID<group.length; containerID++) {
            if (group[containerID] == amount.length-1) {
                group[containerID] = groupID;
            }
        }
        amount[groupID] = amount[amount.length-1];
        amount = Arrays.copyOf(amount, amount.length-1);
    }
}
