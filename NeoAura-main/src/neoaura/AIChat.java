package neoaura;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class AIChat {
    private Client aiClient;
    private String ApiKey;
    public AIChat(String apikey) {
        ApiKey=apikey;
        this.aiClient = new Client.Builder()
                .apiKey(ApiKey)
                .build();
    }
    public void setApiKey(String apiKey) {
        this.ApiKey = apiKey;
        this.aiClient = new Client.Builder()
                .apiKey(apiKey)
                .build();
    }

    public String getBotReply(String input) {
        try {
            GenerateContentResponse response = aiClient.models.generateContent(
                    "gemini-2.0-flash", input, null);
            return response.text();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Could not get response from AI.";
        }
    }
}
