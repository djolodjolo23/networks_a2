
  /**
   * The interface with a default method for checking if the input value is integer or not.
   */
  public interface IntegerChecker {

    static boolean integerCheck(String numberAsString) {
      try {
        Integer.parseInt(numberAsString);
        return true;
      } catch (NumberFormatException ex) {
        return false;
      }
    }
  }
