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
    return parse(new StringReader(source));
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
  public static JSONValue parse(Reader source) throws ParseException, IOException {
    pos = 0;
    JSONValue result = parseKernel(source);
    if (-1 != skipWhitespace(source, source.read())) {
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
    int ch = readChar(source);
    ch = skipWhitespace(source, ch);
    if (-1 == ch) {
        throw new ParseException("Unexpected end of file", pos);
    }

    if (ch == '\"') {
      System.out.println("parsing string");
        // Parse a string
        StringBuilder stringBuilder = new StringBuilder();
        while ((ch = source.read()) != -1) {
            ++pos;
            if (ch == '\"') {
                // System.out.println(stringBuilder.toString());
                JSONString JSONString = new JSONString(stringBuilder.toString());
                return (JSONValue) JSONString;
            } else { 
                stringBuilder.append((char) ch);
            }
        }
        throw new ParseException("Undetermined String", pos);
    } else if (ch == '[') {
        // Parse an array
        JSONArray jsonArray = new JSONArray();
        while ((ch = skipWhitespace(source, ch)) != -1 && ch != ']') {
            jsonArray.add(parseKernel(source));

            // Check for comma or closing bracket
            ch = skipWhitespace(source, ch);
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
      while ((ch = skipWhitespace(source, ch)) != -1 && ch != '}') {
        System.out.println((char) ch);
        JSONString hashKey = (JSONString) parseKernel(source);

        // Check for comma or closing bracket
        ch = skipWhitespace(source, ch);

        if (ch == ':') {
          pos++;
        }

        JSONValue hashVal = parseKernel(source);
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

      // Consume closing bracket
      pos++;
      return (JSONValue) jsonHash;
    }

    throw new ParseException("Unimplemented", pos);
}


  /**
   * Get the next character from source, skipping over whitespace.
   */
  static int skipWhitespace(Reader source, int ch) throws IOException {
    while (isWhitespace(ch)) {
      ch = source.read();
      pos++;
    } 
    return ch;
  } // skipWhitespace(Reader)

  /**
   * Determine if a character is JSON whitespace (newline, carriage return,
   * space, or tab).
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
