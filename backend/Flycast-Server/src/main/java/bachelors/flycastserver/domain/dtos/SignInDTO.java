package bachelors.flycastserver.domain.dtos;


import java.util.Objects;

public class SignInDTO {
    private String username;
    private String password;

    public SignInDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SignInDTO signInDTO = (SignInDTO) o;
        return Objects.equals(getUsername(), signInDTO.getUsername()) && Objects.equals(getPassword(), signInDTO.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }
}
