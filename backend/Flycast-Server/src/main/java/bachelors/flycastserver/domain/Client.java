package bachelors.flycastserver.domain;

import bachelors.flycastserver.utils.enums.AppUseMotives;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Client extends Identifiable implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer profilePhoto;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AppUseMotives motive;

    public Client() {
    }

    public Client(UUID id, String username, String password, Integer profilePhoto, String email, AppUseMotives motive) {
        super(id);
        this.username = username;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.email = email;
        this.motive = motive;
    }

    public Client(String username, String password, Integer profilePhoto, String email, AppUseMotives motive) {
        this.username = username;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.email = email;
        this.motive = motive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getMotive().name()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "Client{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profilePhoto=" + profilePhoto +
                ", email='" + email + '\'' +
                ", motive=" + motive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(getUsername(), client.getUsername()) && Objects.equals(getPassword(), client.getPassword()) && Objects.equals(getProfilePhoto(), client.getProfilePhoto()) && Objects.equals(getEmail(), client.getEmail()) && getMotive() == client.getMotive();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getProfilePhoto(), getEmail(), getMotive());
    }
}
