import java.io.PrintWriter;
import java.util.Iterator;

import java.util.ArrayList;

/**
 * JSON hashes/objects.
 * 
 * @authors Jonathan Wang, Jinny Eo, Madel Sibal
 * November 2023
 */ 
public class JSONHash implements JSONValue {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+
  private ArrayList<KVPair<JSONString, JSONValue>> kvPairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  public JSONHash() {
    this.kvPairs = new ArrayList<>();
  }

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  public String toString() {
    StringBuilder result = new StringBuilder("{");

    Iterator<KVPair<JSONString, JSONValue>> iterator = kvPairs.iterator();
    while (iterator.hasNext()) {
      KVPair<JSONString, JSONValue> kvPair = iterator.next();
      result.append("\"").append(kvPair.key()).append("\": ").append(kvPair.value());
      if (iterator.hasNext()) {
        result.append(", ");
      }
    }

    result.append("}");
    return result.toString();
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other instanceof JSONHash) {
      JSONHash otherHash = (JSONHash) other;
      return this.kvPairs.equals(otherHash.kvPairs);
    }

    return false;
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    return kvPairs.hashCode();
  } // hashCode()

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen) {
    pen.print(this.toString());
  } // writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public Iterator<KVPair<JSONString, JSONValue>> getValue() {
    return this.kvPairs.iterator();
  } // getValue()

  // +-------------------+-------------------------------------------
  // | Hashtable methods |
  // +-------------------+

  /**
   * Get the value associated with a key.
   */
  public JSONValue get(JSONString key) {
    for (KVPair<JSONString, JSONValue> kvPair : kvPairs) {
      if (kvPair.key().equals(key)) {
        return kvPair.value();
      }
    }
    return null;
  } // get(JSONString)

  /**
   * Get all of the key/value pairs.
   */
  public Iterator<KVPair<JSONString, JSONValue>> iterator() {
    return getValue();
  } // iterator()

  /**
   * Set the value associated with a key.
   */
  public void set(JSONString key, JSONValue value) {
    for (KVPair<JSONString, JSONValue> kvpair : kvPairs) {
      if (kvpair.key().equals(key)) {
        kvpair = new KVPair<>(key, value);
        return;
      }
    }

    kvPairs.add(new KVPair<>(key, value));
  } // set(JSONString, JSONValue)

  /**
   * Find out how many key/value pairs are in the hash table.
   */
  public int size() {
    return kvPairs.size();
  } // size()

} // class JSONHash
