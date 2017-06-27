package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Foo.
 */
@Table(name = "foo")
public class Foo implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Foo foo = (Foo) o;
        if (foo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Foo{" +
            "id=" + getId() +
            "}";
    }
}
