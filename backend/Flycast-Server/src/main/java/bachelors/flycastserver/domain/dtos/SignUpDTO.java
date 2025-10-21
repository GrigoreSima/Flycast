package bachelors.flycastserver.domain.dtos;

import bachelors.flycastserver.utils.enums.AppUseMotives;

import java.util.UUID;

public class SignUpDTO extends ClientDTO {
    private String password;

    public SignUpDTO() {
    }

    public SignUpDTO(UUID id, String username, Integer profilePhoto, String email, AppUseMotives motive, String password) {
        super(id, username, profilePhoto, email, motive);
        this.password = password;
    }

    public SignUpDTO(String username, Integer profilePhoto, String email, AppUseMotives motive, String password) {
        super(username, profilePhoto, email, motive);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
