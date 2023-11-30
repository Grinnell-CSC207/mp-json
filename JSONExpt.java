import java.io.IOException;
import java.text.ParseException;

public class JSONExpt {
    public static void main(String[] args) {
        // Test JSON string
        String jsonString = "{ \"a\": \"apple\"}";
  
        // Print the characters in the string
        for (int i = 0; i < jsonString.length(); i++) {
            System.out.println("Char at index " + i + ": " + jsonString.charAt(i));
        }
  
        try {
            // Parse JSON string
            JSONValue jsonValue = JSON.parse(jsonString);
  
            // Display the parsed JSON value
            System.out.println("Parsed JSON Value:");
            System.out.println(jsonValue);
  
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
  }
  