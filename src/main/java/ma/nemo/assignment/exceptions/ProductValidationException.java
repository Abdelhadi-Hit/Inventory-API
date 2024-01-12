package ma.nemo.assignment.exceptions;

public class ProductValidationException extends Exception {

  private static final long serialVersionUID = 1L;

  public ProductValidationException() {
  }

  public ProductValidationException(String message) {
    super(message);
  }
}
