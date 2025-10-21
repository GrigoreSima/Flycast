package bachelors.flycastserver.domain.dtos;

import bachelors.flycastserver.utils.enums.AppUseMotives;

import java.util.Objects;
import java.util.UUID;

public class ClientDTO {
    private UUID id;
    private String username;

    private Integer profilePhoto;
    private String email;
    private AppUseMotives motive;

    public ClientDTO() {
    }

    public ClientDTO(UUID id, String username, Integer profilePhoto, String email, AppUseMotives motive) {
        this.id = id;
        this.username = username;
        this.profilePhoto = profilePhoto;
        this.email = email;
        this.motive = motive;
    }

    public ClientDTO(String username, Integer profilePhoto, String email, AppUseMotives motive) {
        this.username = username;
        this.profilePhoto = profilePhoto;
        this.email = email;
        this.motive = motive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Integer profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppUseMotives getMotive() {
        return motive;
    }

    public void setMotive(AppUseMotives motive) {
        this.motive = motive;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", profilePhoto=" + profilePhoto +
                ", email='" + email + '\'' +
                ", motive=" + motive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(getId(), clientDTO.getId()) && Objects.equals(getUsername(), clientDTO.getUsername()) && Objects.equals(profilePhoto, clientDTO.profilePhoto) && Objects.equals(getEmail(), clientDTO.getEmail()) && getMotive() == clientDTO.getMotive();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), profilePhoto, getEmail(), getMotive());
    }
}