package games.android.trivia;

/**
 * Created by Erez on 04/10/2017.
 */

public class Wallet {

    private int money;

    public Wallet(int startMoney){
        this.money = startMoney;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void reduceMoney(int amount) {
        this.money -= amount;
    }
}
