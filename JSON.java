import java.io.IOException;
import java.text.ParseException;
import java.io.Reader;

/**
 * Utilities for our simple implementation of JSON.
 * 
 * @authors Jonathan Wang, Jinny Eo, Madel Sibal
 *          November 2023
 */
public class JSON {
  // +---------------+-----------------------------------------------
  // | Static fields |
  // +---------------+

  /**
   * The current position in the input.
   */
  static int pos;

  // +----------------+----------------------------------------------
  // | Static methods |
  // +----------------+

  /**
   * Parse a string into JSON.
   */
  public static JSONValue parse(String source) throws ParseException, IOException {
    try (Reader readerSource = new java.io.StringReader(source)) {
      return parse(readerSource);
    }
  } // parse(String)

  /**
   * Parse a file into JSON.
   */
  public static JSONValue parseFile(String filename) throws ParseException, IOException {
    try (java.io.FileReader reader = new java.io.FileReader(filename)) {
      return parse(reader);
    }
  } // parseFile(String)

  /**
   * Parse JSON from a reader.
   */
  public static JSONValue parse(Reader source) throws ParseException, IOException {
    pos = 0;
    JSONValue result = parseKernel(source);
    if (-1 != skipWhitespace(source)) {
      throw new ParseException("Characters remain at end", pos);
    }
    return result;
  } // parseFile(Reader)

  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  /**
   * Parse JSON from a reader, keeping track of the current position
   */
  private static JSONValue parseKernel(Reader source) throws ParseException, IOException {
    int ch = 0;

    while ((ch = skipWhitespace(source)) != -1) {
      return determineWhichIf(source, ch); // Return the parsed result
    }

    return null;
  } // parseKernel(Reader)

  /**
   * Handles case for parsing Array values
   */
  private static JSONValue ifArray(Reader source, int ch) throws IOException {
    System.out.println("parsing array");

    JSONArray jsonArray = new JSONArray();

    while (ch != -1 && ch != ']') {
      System.out.println("Current char: " + (char) ch);

      // Handle array elements
      JSONValue element = determineWhichIf(source, ch);

      if (element != null) {
        System.out.println("Element: " + element);
        jsonArray.add(element);

        // Move to the next character after the element
        ch = skipWhitespace(source);

      } else {
        // Handle the case where the element is null
        // You might want to log an error or throw an exception
        System.err.println("Error: Element is null");
        break; // Exit the loop or handle the error accordingly
      }
    }

    if (ch == ']') {
      System.out.println("Closing array");
      ch = skipWhitespace(source);
    } else {
      System.err.println("closing bracket is missing");
    }

    return jsonArray;
  } // ifArray(Reader, int)

  /**
   * Handles cases for parsing Hash values
   */
  private static JSONValue ifHash(Reader source, int ch) throws IOException {
    System.out.println("parsing hash");

    JSONHash jsonHash = new JSONHash();

    while (ch != -1 && ch != '}') {
      System.out.println("Current char: " + (char) ch);

      // Skip commas
      if (ch == ',') {
        ch = skipWhitespace(source);
        continue;
      }

      // Handle key-value pairs within the hash
      // For simplicity, assume keys are strings and values can be any JSON type
      JSONValue key = determineWhichIf(source, ch);

      if (key != null) {
        String keyString = key.toString(); // Convert key to string
        System.out.println("Key: " + keyString);
        ch = skipWhitespace(source);

        if (ch == ':') {
          ch = skipWhitespace(source);

          // Check if ':' is missing
          if (ch == -1) {
            System.err.println("Error: ':' is missing in key-value pair");
            break;
          }

          JSONValue value = determineWhichIf(source, ch);
          System.out.println("Value: " + value);

          // Add key-value pair to the hash
          jsonHash.set(new JSONString(keyString), value);

          // Move to the next character after the value
          ch = skipWhitespace(source);
        } else {
          // Handle the case where ':' is missing
          System.err.println("Error: ':' is missing in key-value pair");
          break; // Exit the loop or handle the error accordingly
        }
      } else {
        // Handle the case where the key is null
        // You might want to log an error or throw an exception
        System.err.println("Error: Key is null");
        break; // Exit the loop or handle the error accordingly
      }
    }

    return jsonHash;
  } // ifHash(Reader, int)

  /**
   * Handles cases for parsing String values
   */
  private static JSONString ifString(Reader source, int ch) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();

    // Loop through the JSON input and append stringBuilder for output
    while (ch != -1 && (Character.isLetterOrDigit(ch) || ch == '_' && ch != '[')) {
      stringBuilder.append((char) ch);
      ch = source.read();
    }

    String stringValue = stringBuilder.toString();
    return new JSONString(stringValue); // Return the stringBuilder
  } // ifString(Reader, int)

  /**
   * Determine which KVPair value is to be parsed
   */
  private static JSONValue determineWhichIf(Reader source, int ch) throws IOException {
    switch (ch) {
      case '{':
        return ifHash(source, skipWhitespace(source));
      case '[':
        return ifArray(source, skipWhitespace(source));
      case '\"':
        return ifString(source, skipWhitespace(source));
      // Add cases for other JSON types (e.g., numbers, boolean, null)
      default:
        // Handle numbers as keys
        if (Character.isDigit(ch)) {
          return ifString(source, ch);
        }
        // Handle other cases as needed
        return null;
    }
  } // determineWhichIf(Reader, int)

  /**
   * Get the next character from source, skipping over whitespace.
   */
  static int skipWhitespace(Reader source) throws IOException {
    int ch;
    do {
      ch = source.read();
      ++pos;
    } while (isWhitespace(ch));
    return ch;
  } // skipWhitespace(Reader)

  /**
   * Determine if a character is JSON whitespace (newline, carriage return,
   * space, or tab).
   */
  static boolean isWhitespace(int ch) {
    return (' ' == ch) || ('\n' == ch) || ('\r' == ch) || ('\t' == ch);
  } // isWhiteSpace(int)

} // class JSON
