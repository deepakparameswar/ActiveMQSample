

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author joshy
 */
public class JsonUtil {
    
    
    public static String  getJson(String path) {
        
        JSONParser jsonParser = new JSONParser();
        

        JSONObject json = new JSONObject();
        try {
            FileReader reader = new FileReader(path);
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            json = (JSONObject) obj;
            return json.toJSONString();

        } catch (FileNotFoundException e) {
            // logger.error("read settings.json error-File not found");
            //System.exit(0);
        } catch (IOException e) {
            // logger.error("read settings.json error-" + e.getMessage());
            // System.exit(0);
        } catch (ParseException e) {
            // logger.error("read settings.json error-" + e.getMessage());
            // System.exit(0);
        }
        return json.toJSONString();
    }
}
