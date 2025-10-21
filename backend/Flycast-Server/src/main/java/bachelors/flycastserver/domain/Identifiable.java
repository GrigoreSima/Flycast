package bachelors.flycastserver.domain;


import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public class Identifiable {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public Identifiable(UUID id) {
        this.id = id;
    }

    public Identifiable() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Identifiable{" +
                "id=" + id +
                '}';
    }
}
