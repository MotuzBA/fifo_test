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

    public enum status {
        OK,
        ERROR
    }

    public void input() {
        while (!(line = scanner.nextLine()).isEmpty()){
            String[] lineCommand = line.split(" ");
            if (lineCommand.length != 2 || lineCommand.length != 5){
                System.out.println("ERROR");
            }else if (lineCommand[0].equals("NEWPRODUCT")) {
                newProduct(lineCommand[1]);
            } else if (lineCommand[0].equals("PURCHASE")) {
                setData(lineCommand);
                purchase(name, amount, price, date);
             }else if (lineCommand[0].equals("DEMAND")) {
                setData(lineCommand);
                demand(name, amount, price, date);
            } else if (lineCommand[0].equals("SALESREPORT")) {
                setData(lineCommand);
                salesReport(name, date);
            } else System.out.println("ERROR");
        }
    }

    public void output() {
        for (Product product: productList)
            System.out.println(product);
    }

    public status newProduct(String name){
        if ( !nameSet.contains(name)){
            productList.add(new Product(name));
            nameSet.add(name);
            System.out.println("OK");
            return  status.OK;
        } else {
            System.out.println("ERROR");
            return status.ERROR;
        }
    }

    public status purchase(String name, int amount, float price, Date date) {
        if (nameSet.contains(name)) {
            for (Product product: productList) {
                if (product.getName().equals(name)) {
                    product.buyProduct(amount, price, date);
                    System.out.println("OK");
                    return status.OK;
                }
            }
        }
        System.out.println("ERROR");
        return status.ERROR;
    }

    public status demand(String name, int amount, float price, Date date) {
        String status = "status";
        if (nameSet.contains(name)) {
            for (Product product: productList) {
                if (product.getName().equals(name)) {
                    status = product.sellProduct(amount, price, date);
                }
            }
        }
        if (status.equals("Product sold")) {
            System.out.println("OK");
            return FIFOModel.status.OK;
        }
        System.out.println("ERROR");
        return FIFOModel.status.ERROR;
    }

    private void setData(String [] lineCommand){
        String [] dateString;
        name = lineCommand[1];
        if (lineCommand.length == 5) {
            dateString = lineCommand[4].split("\\.");
            amount = Integer.parseInt(lineCommand[2]);
            price = Float.parseFloat(lineCommand[3]);
        } else {
            dateString = lineCommand[2].split("\\.");
        }
        int day = Integer.parseInt(dateString[0]);
        int month = Integer.parseInt(dateString[1]);
        int year = Integer.parseInt(dateString[2]);
        date = new Date(day, month, year);
    }

    public float salesReport(String name, Date date) {
        if (nameSet.contains(name)) {
            for (Product product: productList) {
                if (product.getName().equals(name)) {
                    return product.getProfit(date);
                }
            }
        }
        System.out.println("ERROR");
        return 0f;
    }
}
