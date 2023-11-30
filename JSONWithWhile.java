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
    int ch = 0;
    // JSONValue JSONValue = 

    while ((ch = skipWhitespace(source)) != -1) {
      determineWhichIf(source, ch);
    } // while

    return null;

  } // parseKernel(Reader)

  /*
   * When the value in JSON is a hash.
   */
  static JSONValue ifHash(Reader source, int ch) throws IOException {
    System.out.println("parsing hash");

    JSONString hashKey = new JSONString(null);

    while (ch != -1 && ch != '}') {
      while (ch != ':') {
        hashKey = (JSONString) determineWhichIf(source, ch);
      } // while


    } // while
    
    return null;
  } // ifHash(Reader, int)

  /*
   * When the value in JSON is a string.
   */
  static JSONString ifString(Reader source, int ch) throws IOException {
    System.out.println("parsing string");

    JSONString str = new JSONString("");

    while (ch != -1 && ch != '\"') {
      str = concatJSONStr(str, JSONString(String.valueOf((char) ch)));
      ch = skipWhitespace(source);
    } // while

    // return final JSONString as JSONValue
    return str;

  } // ifString(Reader, int)

  /*
   * Determine what JSONValue is being parsed, then call appropriate function to 
   * parse through for each case.
   */
  static JSONValue determineWhichIf(Reader source, int ch) throws IOException {
    switch (ch) {
      case '{':
        JSONHash jsonHash = new JSONHash();
        ifHash(source, ch = skipWhitespace(source));
      case '\"':
        JSONString jsonString = new JSONString(null);
        return ifString(source, ch = skipWhitespace(source));
      case '[':
    } // switch
    return null;
  } // determineWhichIf(Reader, int)
