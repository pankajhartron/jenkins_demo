package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Entity_3.
 */
@Table(name = "entity_3")
public class Entity_3 implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    @NotNull
    @Size(min = 3)
    private String username;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String password;

    private Integer otp;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Entity_3 username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Entity_3 password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getOtp() {
        return otp;
    }

    public Entity_3 otp(Integer otp) {
        this.otp = otp;
        return this;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity_3 entity_3 = (Entity_3) o;
        if (entity_3.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entity_3.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entity_3{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", otp='" + getOtp() + "'" +
            "}";
    }
}
