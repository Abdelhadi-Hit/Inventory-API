package ma.nemo.assignment.exceptions;

public class ProductAlreadyExists extends Exception {

  private static final long serialVersionUID = 1L;

  public ProductAlreadyExists() {
  }

  public ProductAlreadyExists(String message) {
    super(message);
  }
}
