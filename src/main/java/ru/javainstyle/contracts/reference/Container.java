package ru.javainstyle.contracts.reference;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Container {
    private Set<Container> group;
    private double amount;

    public Container() {
        group = new HashSet<Container>();
        group.add(this);
    }

    public double getAmount() {
        return amount;
    }

    public void connectTo(Container other) {
        Objects.requireNonNull(other, "Cannot connect to a null container");
        if (group == other.group) return;

        ConnectPostData postData = null;

        assert (postData = saveConnectPostData(other)) != null;

        int size1 = group.size(),
                size2 = other.group.size();
        double tot1 = amount * size1,
                tot2 = other.amount * size2,
                newAmount = (tot1 + tot2) / (size1 + size2);

        group.addAll(other.group);

        for (Container c : other.group) {
            c.group = group;
        }

        for (Container c : group) {
            c.amount = newAmount;
        }

        assert postConnect(postData) :
                "connectTo failed its postcondition!";
    }

    private ConnectPostData saveConnectPostData(Container other) {
        ConnectPostData data = new ConnectPostData();
        data.group1 = new HashSet<>(group);
        data.group2 = new HashSet<>(other.group);
        data.amount1 = amount;
        data.amount2 = other.amount;
        return data;
    }

    private boolean postConnect(ConnectPostData postData) {
        return areGroupMembersCorrect(postData)
                && isGroupNonNegative()
                && isGroupBalanced()
                && isGroupConsistent();
    }

    private boolean areGroupMembersCorrect(ConnectPostData postData) {
        return group.containsAll(postData.group1)
                && group.containsAll(postData.group2)
                && group.size() == postData.group1.size() +
                postData.group2.size();
    }

    private boolean isGroupBalanced() {
        for (Container x: group) {
            if (x.amount != amount) return false;
        }
        return true;
    }

    private boolean isGroupNonNegative() {
        for (Container x: group) {
            if (x.amount < 0) return false;
        }
        return true;
    }

    private boolean isGroupConsistent() {
        for (Container x: group) {
            if (x.group != group) return false;
        }
        return true;
    }

    public void addWater(double amount) {
        double amountPerContainer = amount / group.size();
        if (this.amount + amountPerContainer < 0) {
            throw new IllegalArgumentException(
                    "Not enough water to match the addWater request."
            );
        }

        double oldTotal = 0;
        assert (oldTotal = groupAmount()) >= 0;

        for (Container c : group) {
            c.amount += amountPerContainer;
        }
        assert invariantsArePreservedByAddWater() :
                "addWater broke an invariant!";
    }

    private boolean invariantsArePreservedByAddWater() {
        return isGroupNonNegative() && isGroupBalanced();
    }

    private double groupAmount() {
        double total = 0;
        for (Container c: group) {
            total += c.amount;
        }
        return total;
    }

    private boolean postAddWater(double oldTotal, double addedAmount) {
        return isGroupBalanced() &&
                almostEqual(groupAmount(), oldTotal + addedAmount);
    }


    private static boolean almostEqual(double x, double y) {
        final double EPSILON = 1E-4;
        return Math.abs(x - y) < EPSILON;
    }
}
