import java.io.IOException;
import java.text.ParseException;

/**
 * Test JSON parsing with a sample JSON string.
 *
 * @authors Jonathan Wang, Jinny Eo, Madel Sibal
 *          November 2023
 */
public class JSONExpt {
  public static void main(String[] args) {
    // Test JSON string
    String sampleJsonString = "{ \"a\": \"apple\", \"e\": [2,7,1,8], \"q\": { \"x\" : \"xerox\" } }";

    try {
      // Parse JSON string
      JSONValue jsonValue = JSON.parse(sampleJsonString);

      // Display the parsed JSON value
      System.out.println("Parsed JSON Value:");
      System.out.println(jsonValue);

    } catch (ParseException | IOException e) {
      System.err.println("Error parsing JSON: " + e.getMessage());
    }
  }
} // main
