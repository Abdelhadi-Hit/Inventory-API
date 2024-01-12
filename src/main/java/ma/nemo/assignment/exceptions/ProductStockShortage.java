package ma.nemo.assignment.exceptions;

public class ProductStockShortage extends Exception {

    private static final long serialVersionUID = 1L;

    public ProductStockShortage() {
    }

    public ProductStockShortage(String message) {
        super(message);
    }
}
