package ma.nemo.assignment.exceptions;

public class ProductNotFound extends Exception {

  private static final long serialVersionUID = 1L;

  public ProductNotFound() {
  }

  public ProductNotFound(String message) {
    super(message);
  }
}
