import java.io.PrintWriter;

/**
 * JSON strings.
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
  public String toString() {
    return "";          // STUB
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    return true;        // STUB
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    return 0;           // STUB
  } // hashCode()

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen) {
                        // STUB
  } // writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public String getValue() {
    return this.value;
  } // getValue()

    /*
   * Concatonate two JSONStrings
   */
  public JSONString concatJSONStr(JSONString JSONStr1, JSONString JSONStr2) {
    String finalStr = "";

    String str1 = JSONStr1.toString();
    String str2 = JSONStr2.toString();

    finalStr = str1.concat(str2);

    return new JSONString(finalStr);
  } // concatJSONStr(JSONString, JSONString)

} // class JSONString
