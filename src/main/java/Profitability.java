/**
 * Прибыльность.
 */
public class Profitability {
    /**
     * Прибыль за конкретный день.
     */
    private float profit;
    /**
     * Дата продажи.
     */
    private Date date;

    public Profitability(float profit, Date date) {
        this.profit = profit;
        this.date = date;
    }

    public float getProfit() {
        return profit;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        return ("date: " + date + "; profit: " + profit);
    }
}
