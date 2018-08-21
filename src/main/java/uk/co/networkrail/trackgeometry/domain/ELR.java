package uk.co.networkrail.trackgeometry.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ELR.
 */
@Entity
@Table(name = "elr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ELR implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "elr_code", nullable = false)
    private String elrCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElrCode() {
        return elrCode;
    }

    public ELR elrCode(String elrCode) {
        this.elrCode = elrCode;
        return this;
    }

    public void setElrCode(String elrCode) {
        this.elrCode = elrCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ELR eLR = (ELR) o;
        if (eLR.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eLR.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ELR{" +
            "id=" + getId() +
            ", elrCode='" + getElrCode() + "'" +
            "}";
    }
}
