/**
 * Дата в формате дд.мм.гггг.
 */
public class Date implements Comparable<Date> {
    /**
     * День.
     */
    private int day;
    /**
     * Месяц.
     */
    private int month;
    /**
     * Год.
     */
    private int year;

    public Date() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public int compareTo(Date o) {
        if (this.year < o.getYear()) {
            return -1;
        } else if (this.year > o.getYear()) {
            return 1;
        } else if (this.month < o.getMonth()) {
            return -1;
        } else if (this.month > o.getMonth()) {
            return 1;
        } else if (this.day < o.getDay()) {
            return -1;
        } else if (this.day > o.getDay()) {
            return 1;
        } else return 0;
    }

    @Override
    public String toString() {
        return (day + "." + month + "." +  year);
    }
}
