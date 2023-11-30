import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;

/**
 * Utilities for our simple implementation of JSON.
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
    Reader readerSource = new StringReader(source);
    return parse(readerSource);
  } // parse(String)

  /**
   * Parse a file into JSON.
   */
  public static JSONValue parseFile(String filename) throws ParseException, IOException {
    FileReader reader = new FileReader(filename);
    JSONValue result = parse(reader);
    reader.close();
    return result;
  } // parseFile(String)

  /**
   * Parse JSON from a reader.
   */
  /*
   * public static JSONValue parse(Reader source) throws ParseException, IOException { pos = 0;
   * JSONValue result = parseKernel(source); if (-1 != skipWhitespace(source, source.read())) {
   * throw new ParseException("Characters remain at end", pos); } return result; } // parse(Reader)
   */
  public static JSONValue parse(Reader source) throws ParseException, IOException {
    pos = 0;
    JSONValue result = parseKernel(source);
    if (-1 != skipWhitespace(source)) {
      throw new ParseException("Characters remain at end", pos);
    }
    return result;
  } // parse(Reader)

  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  /**
   * Parse JSON from a reader, keeping track of the current position
   */
  static JSONValue parseKernel(Reader source) throws ParseException, IOException {
    int ch = skipWhitespace(source);
    if (-1 == ch) {
      throw new ParseException("Unexpected end of file", pos);
    }

    if (ch == '\"') {
      System.out.println("parsing string");
      // Parse a string
      String str = new String("");
      // JSONString JSONString = new JSONString("");
      while ((ch = source.read()) != -1) {
        ++pos;
        if (ch == '\"') {
          // System.out.println(stringBuilder.toString());
          JSONString JSONString = new JSONString(str);
          return (JSONValue) JSONString;
        } else {
          System.out.println((char) ch);
          // str += String.valueOf(ch); // TODO: Change
          str.concat(String.valueOf(ch));
        }
      }
      throw new ParseException("Undetermined String", pos);
    } else if (ch == '[') {
      // Parse an array
      JSONArray jsonArray = new JSONArray();
      while ((ch = skipWhitespace(source)) != -1 && ch != ']') {
        jsonArray.add(parseKernel(source));

        // Check for comma or closing bracket
        ch = skipWhitespace(source);
        if (ch == ',') {
          // Consume comma
          pos++;
        } else if (ch != ']') {
          throw new ParseException("Expected ',' or ']' after array element", pos);
        }
      }

      if (ch == -1) {
        throw new ParseException("Unexpected end of file in array", pos);
      }

      // Consume closing bracket
      pos++;
      return (JSONValue) jsonArray;
    } else if (ch == '{') { 
      System.out.println("parsing hash");
      // Parse an array
      JSONHash jsonHash = new JSONHash();
      while ((ch = skipWhitespace(source)) != -1 && ch != '}') {
        System.out.println((char) ch);
        JSONString hashKey = (JSONString) parseKernel(source);

        // Check for comma or closing bracket
        ch = skipWhitespace(source);

        // if (ch == ':') {
        // pos++;
        // }
        System.out.println((char) ch);
        JSONValue hashVal = parseKernel(source);
        pos++;
        System.out.println(ch);
        jsonHash.set(hashKey, hashVal);

        if (ch == ',') {
          // Consume comma
          pos++;
        } else if (ch != '}') {
          throw new ParseException("Expected ',' or ']' after array element", pos);
        }
      }

      if (ch == -1) {
        throw new ParseException("Unexpected end of file in array", pos);
      }
    }
    throw new ParseException("Unimplemented", pos);
  }


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
   * Determine if a character is JSON whitespace (newline, carriage return, space, or tab).
   */
  static boolean isWhitespace(int ch) {
    return (' ' == ch) || ('\n' == ch) || ('\r' == ch) || ('\t' == ch);
  } // isWhiteSpace(int)

  /*
   * Read character.
   */
  static int readChar(Reader source) throws IOException {
    return source.read();
  } // readChar(source)

} // class JSON
