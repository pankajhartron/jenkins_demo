package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Entity_1.
 */
@Table(name = "entity_1")
public class Entity_1 implements Serializable {

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

    public Entity_1 username(String username) {
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
        Entity_1 entity_1 = (Entity_1) o;
        if (entity_1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entity_1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entity_1{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            "}";
    }
}
