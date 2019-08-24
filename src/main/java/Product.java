import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Товар.
 */
public class Product {
    /**
     * Наименование.
     */
    private String name;
    /**
     * Список количества товара, закупленного в конкретный день.
     */
    private List<Integer> amount = new LinkedList<Integer>();
    /**
     * Список стоимости товара, закупленного в конкретный день.
     */
    private List<Float> price = new LinkedList<Float>();
    /**
     * Список дат закупок.
     */
    private List<Date> date = new LinkedList<Date>();
    /**
     * Список, в котором хранится прибыль от продажи за конкретный день.
     */
    private List<Profitability> profitability = new LinkedList<Profitability>();

    public Product(String name) {
        this.name = name;
    }

    public void buyProduct(int amount, float price, Date date) {
        this.amount.add(amount);
        this.price.add(price);
        this.date.add(date);
    }

    public String sellProduct(int amount, float price, Date date) {
        int totalAmount = 0;
        int totalPrice = 0;
        int sellAmount = amount;
        float profit = 0f;
        int n = 0;
            while (n < this.date.size() && this.date.get(n).compareTo(date) <= 0 && totalAmount < sellAmount) {
                totalAmount += this.amount.get(n);
                n++;
            }
        if (totalAmount >= amount) {

            int currentAmount;
            for (int i = 0; i < this.amount.size(); i++) {
                currentAmount = this.amount.get(i);
                while (sellAmount > 0 && currentAmount > 0) {
                    totalPrice += this.price.get(i);
                    sellAmount--;
                    currentAmount--;
                }
                this.amount.set(i, currentAmount);
            }
            profit = amount * price - totalPrice;
            this.profitability.add(new Profitability(profit, date));
            return "Product sold";
        } return "Product not sold";

    }

    public String getName() {
        return name;
    }

    public float getProfit(Date date) {
        float profit = 0f;
        int i = 0;
        while (i < this.profitability.size() && this.profitability.get(i).getDate().compareTo(date) <= 0) {
            profit += this.profitability.get(i).getProfit();
            i++;
        }
        System.out.println(profit);
        return profit;
    }

    @Override
    public String toString() {
        return ("Name " + name + "; amount: " + amount + "; " + "price: " + price +
                "; Date: " + date + "; profitability" + profitability);
    }
}
