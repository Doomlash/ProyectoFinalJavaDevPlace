package group3.mvc.services.connection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.WebClient;

import group3.mvc.model.LoginResponse;
import group3.mvc.model.UserHolder;
import lombok.Data;

@Data
public class Connection{
    private static WebClient client;

    private static String token;


    private static void getConnection() {
        try {
                    client = createConection("http://localhost:8081/middle");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static WebClient getClient(){
        if(client == null){
            getConnection();
        }
        return client;
    }

    public static WebClient createConection(String url) {
        return WebClient.builder().baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public static String getToken(){
        return token;
    }

    public static String generateToken(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginResponse response = getClient().post()
                    .uri("/security/token/")
                    .headers(headers -> headers.setBasicAuth("admin", "admin_otaku"))
                    .retrieve()
                    .bodyToMono(LoginResponse.class)
                    .block();
        UserHolder.setCurrentUser(response.getUser());
        Connection.setToken(response.getToken());
        return token;
    }

    public static void setToken(String newToken){
        token = newToken;
    }   


}
