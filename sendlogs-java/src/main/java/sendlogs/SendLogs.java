package sendlogs;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.ThreadLocalRandom;

public class SendLogs {

    final static Logger logger = Logger.getLogger(SendLogs.class);

    static String jsonTemplate = "{\n" +
            "           \"privateKey\": \"24cdd646-9f5d-3ff4-c567-d78496d27669\",\n" +
            "           \"applicationName\": \"JAVA sending logs\",\n" +
            "           \"subsystemName\": \"JAVA subsystem name\",\n" +
            "           \"computerName\": \"JAVA computer name\",\n" +
            "           \"logEntries\": [\n" +
            "             {\n" +
            "               \"timestamp\": 1457827957703.342, \n" +
            "               \"severity\": 4,\n" +
            "               \"text\": \"Encountered an error while registering the user john123\",\n" +
            "               \"category\": \"DAL\",\n" +
            "               \"className\": \"UserManager\",\n" +
            "               \"methodName\": \"RegisterUser\",\n" +
            "               \"threadId\": \"a-352\"\n" +
            "             }\n" +
            "           ]\n" +
            "        }";

    public static void main(String[] args) throws ParseException {
        JSONObject root = makeNewJson();
        logger.info("Big JSON created");
        for (int i=0; i<3; i++) {
            sendLogs(root);
        }
    }

    private static JSONObject makeNewJson() throws ParseException {
        JSONObject root = (JSONObject) new JSONParser().parse(jsonTemplate);
        JSONArray newLogEntries = genLogs();
        root.put("logEntries", newLogEntries);
        return root;
    }

    private static void sendLogs(JSONObject root) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(root.toString(), headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange("https://api.coralogix.com/api/v1/logs" /* "http://localhost:8080/api/v1/logs" */, HttpMethod.POST, entity,  String.class);
            logger.info(response.getStatusCode());
        } catch (HttpClientErrorException e) {
            String s = e.getResponseBodyAsString();
            logger.error("Error sending POST: " + s, e);
            System.out.println(s);
            System.out.println(e.getStatusCode() +", " + e.getStatusText());
        }
    }

    private static JSONArray genLogs() {
        JSONArray logEntries = new JSONArray();
        for (int i=0; i<1000; i++){
            JSONObject logEnrty = new JSONObject();
            logEnrty.put("timestamp", System.currentTimeMillis());
            logEnrty.put("severity", ThreadLocalRandom.current().nextInt(1, 6));
            logEnrty.put("text", "Encountered an error while operation " + RandomStringUtils.randomAlphabetic(10));
            logEnrty.put("category", RandomStringUtils.randomAlphabetic(10).substring(0,3).toUpperCase());
            logEnrty.put("className", "className-" + RandomStringUtils.randomAlphabetic(10));
            logEnrty.put("methodName", "methodName-" + RandomStringUtils.randomAlphabetic(10));
            logEnrty.put("threadId", RandomStringUtils.randomAlphabetic(1) + "-" + ThreadLocalRandom.current().nextInt(100, 999));
            logEntries.add(logEnrty);
        }
        return logEntries;
    }
}
