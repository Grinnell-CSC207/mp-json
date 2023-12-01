import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * JSON arrays.
 * 
 * @authors Jonathan Wang, Jinny Eo, Madel Sibal
 *          November 2023
 */
public class JSONArray implements JSONValue {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The underlying array.
   */
  ArrayList<JSONValue> values;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new array.
   */
  public JSONArray() {
    this.values = new ArrayList<JSONValue>();
  } // JSONArray() 

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  public String toString() {
    if (values == null) {
      return "null";
    }
    
    return this.values.toString();
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true; 
    }
         
    if (other instanceof JSONInteger) {
      return this.values.equals(((JSONArray) other).values);
    }
    return false;
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    if (this.values == null)
      return 0;
    return values.hashCode();
  } // hashCode()

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen) {
    pen.print("["); // Start the array

    Iterator<JSONValue> iterator = this.values.iterator();
    while (iterator.hasNext()) {
      JSONValue value = iterator.next();
      value.writeJSON(pen); // Write each element
      if (iterator.hasNext()) {
        pen.print(", "); // Add comma if there are more elements
      }
    }

    pen.print("]"); // End the array
  } // writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public ArrayList<JSONValue> getValue() {
    return this.values;
  } // getValue()

  // +---------------+-----------------------------------------------
  // | Array methods |
  // +---------------+

  /**
   * Add a value to the end of the array.
   */
  public void add(JSONValue value) {
    this.values.add(value);
  } // add(JSONValue)

  /**
   * Get the value at a particular index.
   */
  public JSONValue get(int index) throws IndexOutOfBoundsException {
    return this.values.get(index);
  } // get(int)

  /**
   * Get the iterator for the elements.
   */
  public Iterator<JSONValue> iterator() {
    return this.values.iterator();
  } // iterator()

  /**
   * Set the value at a particular index.
   */
  public void set(int index, JSONValue value) throws IndexOutOfBoundsException {
    this.values.set(index, value);
  } // set(int, JSONValue)

  /**
   * Determine how many values are in the array.
   */
  public int size() {
    return this.values.size();
  } // size()
} // class JSONArray
