package bachelors.flycastserver.controllers.responses;

public class JwtSignUpResponse {
    private String token;

    public JwtSignUpResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
