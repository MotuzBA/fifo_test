import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FIFOModelTest {

    FIFOModel fifoModel = new FIFOModel();
    Date day1 = new Date(12,12,2011);
    Date day2 = new Date(12,12,2012);
    Date day3 = new Date(13,12,2012);
    Date day4 = new Date(14,12,2012);
    Date day5 = new Date(15,12,2012);
    /**
     * Попытка купить, продать или получить прибыльность для несозданного товара.
     */
    @Test
    public void notCreatedProduct() {
        FIFOModel.status result = fifoModel.purchase("iphone", 2, 1000f, day2);
        assertEquals(FIFOModel.status.ERROR, result);

        result = fifoModel.demand("iphone", 2, 1000f, day2);
        assertEquals(FIFOModel.status.ERROR, result);

        float salesResult = fifoModel.salesReport("iphone", day2);
        assertEquals(0f, salesResult, 0f);
    }

    /**
     * Попытка купить, продать или получить прибыльность для созданного товара.
     */
    @Test
    public void createdProduct() {
        fifoModel.newProduct("iphone");
        FIFOModel.status result = fifoModel.purchase("iphone", 2, 1000f, day2);
        assertEquals(FIFOModel.status.OK, result);

        result = fifoModel.demand("iphone", 2, 5000f, day2);
        assertEquals(FIFOModel.status.OK, result);

        float salesResult = fifoModel.salesReport("iphone", day2);
        assertEquals(8000f, salesResult, 0f);
    }

    /**
     * Проверка правильности расчета прибыльности.
     * NEWPRODUCT iphone
     * PURCHASE iphone 2 1000 12.12.2012
     * DEMAND iphone 2 5000 12.12.2012
     * SALESREPORT iphone 12.12.2012
     * Ожидание: 8000.
     */
    @Test
    public void profitVerification() {
        fifoModel.newProduct("iphone");
        fifoModel.purchase("iphone", 2, 1000f, day2);
        fifoModel.demand("iphone", 2, 5000f, day2);
        float salesResult = fifoModel.salesReport("iphone", day2);
        assertEquals(8000f, salesResult, 0f);
    }

    /**
     * Проверка правильности расчета прибыльности.
     * NEWPRODUCT iphone
     * PURCHASE iphone 2 2000 12.12.2012
     * DEMAND iphone 1 5000 13.12.2012
     * DEMAND iphone 1 5000 15.12.2012
     * SALESREPORT iphone 14.12.2012
     * Ожидание: 3000.
     */
    @Test
    public void profitVerification2() {
        fifoModel.newProduct("iphone");
        fifoModel.purchase("iphone", 2, 2000f, day2);
        fifoModel.demand("iphone", 1, 5000f, day3);
        fifoModel.demand("iphone", 1, 5000f, day5);
        float salesResult = fifoModel.salesReport("iphone", day4);
        assertEquals(3000f, salesResult, 0f);
    }

    /**
     * Попытка продать раньше покупки.
     * NEWPRODUCT iphone
     * PURCHASE iphone 2 2000 12.12.2012
     * DEMAND iphone 2 5000 12.12.2011
     */
    @Test
    public void sellBeforeBuy() {
        fifoModel.newProduct("iphone");
        fifoModel.purchase("iphone", 2, 1000f, day2);
        FIFOModel.status result = fifoModel.demand("iphone", 2, 5000f, day1);
        assertEquals(FIFOModel.status.ERROR, result);
    }

    /**
     * Попытка продать больше, чем куплено.
     * NEWPRODUCT iphone
     * PURCHASE iphone 2 2000 12.12.2012
     * DEMAND iphone 5 5000 13.12.2012
     */
    @Test
    public void sellMoreThanBuy() {
        fifoModel.newProduct("iphone");
        fifoModel.purchase("iphone", 2, 1000f, day2);
        FIFOModel.status result = fifoModel.demand("iphone", 5, 5000f, day3);
        assertEquals(FIFOModel.status.ERROR, result);
    }

    /**
     * Попытка продать больше, чем куплено. В два захода.
     * NEWPRODUCT iphone
     * PURCHASE iphone 2 2000 12.12.2012
     * DEMAND iphone 2 5000 13.12.2012
     * DEMAND iphone 2 5000 13.12.2012
     */
    @Test
    public void sellMoreThanBuy2() {
        fifoModel.newProduct("iphone");
        fifoModel.purchase("iphone", 2, 1000f, day2);
        FIFOModel.status result = fifoModel.demand("iphone", 2, 5000f, day3);
        result = fifoModel.demand("iphone", 2, 5000f, day3);
        assertEquals(FIFOModel.status.ERROR, result);
    }

    /**
     * Попытка создать существующий товар.
     */
    @Test
    public void dublicate() {
        fifoModel.newProduct("iphone");
        FIFOModel.status result = fifoModel.newProduct("iphone");
        assertEquals(FIFOModel.status.ERROR, result);
    }
}