import java.io.PrintWriter;

/**
 * JSON strings.
 * 
 * @authors Jonathan Wang, Jinny Eo, Madel Sibal
 *          November 2023
 */
public class JSONString implements JSONValue {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The underlying string.
   */
  String value;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new JSON string for a particular string.
   */
  public JSONString(String value) {
    this.value = value;
  } // JSONString(String)

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  @Override
  public String toString() {
    return "\"" + value + "\"";
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other instanceof JSONString) {
      return this.value.equals(((JSONString) other).value);
    }
    return false;
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    if (this.value == null)
      return 0;
    return value.hashCode();
  } // hashCode()

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  // Update the writeJSON method in JSONString class
// Update the writeJSON method in JSONString class
public void writeJSON(PrintWriter pen) {
  pen.print("\"");

  for (char ch : value.toCharArray()) {
      switch (ch) {
          case '"':
              pen.print("\\\"");
              break;
          case '\\':
              pen.print("\\\\");
              break;
          case '\b':
              pen.print("\\b");
              break;
          case '\f':
              pen.print("\\f");
              break;
          case '\n':
              pen.print("\\n");
              break;
          case '\r':
              pen.print("\\r");
              break;
          case '\t':
              pen.print("\\t");
              break;
          default:
              pen.print(ch);
              break;
      }
  }

  pen.print("\"");
}

// writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public String getValue() {
    return this.value;
  } // getValue()

} // class JSONString
