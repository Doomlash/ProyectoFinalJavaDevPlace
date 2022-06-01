package group3.mvc.services.connection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;

@Data
public class Connection{
    private WebClient client;

    private String token;

    public Connection(char conn){
        this.client = this.getConnection(conn);
    }

    private WebClient getConnection(char conn) {
        WebClient connection = null;
        try {
            switch (conn) {
                case 'a':
                    connection = this.createConection("http://localhost:8081/middle");
                    break;
                case 'u':
                    connection = this.createConection("http://localhost:8081/middle/users");
                    break;
                case 'm':
                    connection = this.createConection("http://localhost:8081/middle/messages");
                    break;
                case 'g':
                    connection = this.createConection("http://localhost:8081/middle/groups");
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
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public String getToken(){
        return token;
    }

    public String generateToken(){
        return token;
    }


}
