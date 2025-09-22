package local.code_compass_backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


@Component
public class SBAuthClient {
    @Value("${supabase.api-key}")
    private String supabaseApiKey;

    @Value("${supabase.auth-url}")
    private String supabaseAuthUrl;
    //todo get the .env to work

    private final RestTemplate restTemplate = new RestTemplate();



    public AuthenticationDetails authenticateAndGetUser(String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseApiKey);

        Map<String, Object> body = Map.of("email", email, "password", password);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        String url = supabaseAuthUrl + "/auth/v1/token?grant_type=password";

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<?, ?> resp = response.getBody();
            if (resp == null)
                throw new IllegalStateException("Lege response van Authentication API");
        //todo hier moet nog een mapper tussen

        Object tokenObj = resp.get("access_token");
        String accessToken = (tokenObj instanceof String s && !s.isBlank()) ? s : null;


        Object userObj = resp.get("user");
        String userId = null;
        if (userObj instanceof Map<?, ?> userMap) {
            Object idObj = userMap.get("id");
            if (idObj instanceof String id && !id.isBlank()) {
                userId = id;
            }
        }

        if (userId == null || accessToken == null) {
            throw new IllegalStateException("Ongeldige Authentication response: ontbrekende userId of access token");
        }

        return new AuthenticationDetails(userId, accessToken);
    }


    public final class AuthenticationDetails {
        private final String userId;
        private final String accessToken;

        public AuthenticationDetails(String userId, String accessToken) {
            this.userId = userId;
            this.accessToken = accessToken;
        }

        public String getUserId() {
            return userId;
        }

        public String getAccessToken() {
            return accessToken;
        }
    }
}

