import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FIFOModel {
    Set<String> nameSet = new LinkedHashSet<String>();
    List<Product> productList = new LinkedList<Product>();
    Scanner scanner = new Scanner(System.in);
    String line;
    String name;
    int amount;
    float price;
    Date date;

    public enum Status {
        OK,
        ERROR
    }

    public void input() {
        while (!(line = scanner.nextLine()).isEmpty()) {
            String[] lineCommand = line.split(" ");
            if (lineCommand[0].equals("NEWPRODUCT") & lineCommand.length == 2) {
                newProduct(lineCommand[1]);
            } else if (lineCommand[0].equals("PURCHASE") & lineCommand.length == 5) {
                if (setData(lineCommand).equals(Status.OK)) {
                    purchase(name, amount, price, date);
                } else System.out.println("ERROR");
            } else if (lineCommand[0].equals("DEMAND") & lineCommand.length == 5) {
                if (setData(lineCommand).equals(Status.OK)) {
                    demand(name, amount, price, date);
                } else System.out.println("ERROR");
            } else if (lineCommand[0].equals("SALESREPORT") & lineCommand.length == 3) {
                if (setData(lineCommand).equals(Status.OK)) {
                    salesReport(name, date);
                } else System.out.println("ERROR");
            }
        }
    }

    public void output() {
        for (Product product : productList)
            System.out.println(product);
    }

    public Status newProduct(String name) {
        if (!nameSet.contains(name)) {
            productList.add(new Product(name));
            nameSet.add(name);
            System.out.println("OK");
            return Status.OK;
        } else {
            System.out.println("ERROR");
            return Status.ERROR;
        }
    }

    public Status purchase(String name, int amount, float price, Date date) {
        if (nameSet.contains(name)) {
            for (Product product : productList) {
                if (product.getName().equals(name)) {
                    product.buyProduct(amount, price, date);
                    System.out.println("OK");
                    return Status.OK;
                }
            }
        }
        System.out.println("ERROR");
        return Status.ERROR;
    }

    public Status demand(String name, int amount, float price, Date date) {
        String status = "Status";
        if (nameSet.contains(name)) {
            for (Product product : productList) {
                if (product.getName().equals(name)) {
                    status = product.sellProduct(amount, price, date);
                }
            }
        }
        if (status.equals("Product sold")) {
            System.out.println("OK");
            return Status.OK;
        }
        System.out.println("ERROR");
        return Status.ERROR;
    }

    private Status setData(String[] lineCommand) {
        String dateString;
        name = lineCommand[1];
        if (lineCommand.length == 5) {
            dateString = lineCommand[4];
            try {
                amount = Integer.parseInt(lineCommand[2]);
                price = Float.parseFloat(lineCommand[3]);
            } catch (NumberFormatException e) {
                return Status.ERROR;
            }
        } else {
            dateString = lineCommand[2];
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            return Status.ERROR;
        }
        return Status.OK;
    }

    public float salesReport(String name, Date date) {
        if (nameSet.contains(name)) {
            for (Product product : productList) {
                if (product.getName().equals(name)) {
                    return product.getProfit(date);
                }
            }
        }
        System.out.println("ERROR");
        return 0f;
    }
}
