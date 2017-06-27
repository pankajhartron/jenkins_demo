package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Entity_2.
 */
@Table(name = "entity_2")
public class Entity_2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    @NotNull
    private String username;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Entity_2 username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity_2 entity_2 = (Entity_2) o;
        if (entity_2.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entity_2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entity_2{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            "}";
    }
}
