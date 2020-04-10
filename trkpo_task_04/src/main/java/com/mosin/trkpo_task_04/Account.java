package com.mosin.trkpo_task_04;

public class Account {

    private String id;

    private Integer balance;

    public Integer getBalance() {
        return this.balance;
    }

    public String getId() {
        return this.id;
    }

    public void decrement(int summ) {
        this.balance -= summ;
    }

    public void increment(int summ) {
        this.balance += summ;
    }

    public void transferMoney(Account acc1, Account acc2, int summ){
        acc1.decrement(summ);
        acc2.increment(summ);
    }
}
