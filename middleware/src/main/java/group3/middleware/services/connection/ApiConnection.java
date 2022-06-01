package group3.middleware.services.connection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;

@Data
public class ApiConnection {
    private WebClient client;

    public ApiConnection(char conn){
        this.client = this.getConnection(conn);
    }

    private WebClient getConnection(char conn) {
        WebClient connection = null;
        try {
            switch (conn) {
                case 'p':
                    connection = this.createConection("http://localhost:8080/api");
                    break;
                case 'u':
                    connection = this.createConection("http://localhost:8080/api/users");
                    break;
                case 'm':
                    connection = this.createConection("http://localhost:8080/api/messages");
                    break;
                case 'g':
                    connection = this.createConection("http://localhost:8080/api/groups");
                    break;
                case 'a':
                    connection = this.createConection("http://localhost:8080/api/token");
                    break;

                case 't':
                    connection = this.createConection("https://just-translated.p.rapidapi.com/");
                    break;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return connection;
    }

    public WebClient createConection(String url) {
        return WebClient.builder().baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
