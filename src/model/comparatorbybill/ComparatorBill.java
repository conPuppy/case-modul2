package model.comparatorbybill;

import model.User;

import java.util.Comparator;

public class ComparatorBill implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return (int) (o2.getBill() - o1.getBill());
    }
}
