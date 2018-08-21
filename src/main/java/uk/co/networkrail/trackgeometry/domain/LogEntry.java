package uk.co.networkrail.trackgeometry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import uk.co.networkrail.trackgeometry.domain.enumeration.InvalidationCode;

/**
 * A LogEntry.
 */
@Entity
@Table(name = "log_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_mileage")
    private Integer startMileage;

    @Column(name = "end_mileage")
    private Integer endMileage;

    @Enumerated(EnumType.STRING)
    @Column(name = "invalidation_code")
    private InvalidationCode invalidationCode;

    @OneToOne
    @JoinColumn(unique = true)
    private ELR elr;

    @OneToOne
    @JoinColumn(unique = true)
    private Track trackId;

    @ManyToOne
    @JsonIgnoreProperties("logEntries")
    private CaptainsLog captainsLog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartMileage() {
        return startMileage;
    }

    public LogEntry startMileage(Integer startMileage) {
        this.startMileage = startMileage;
        return this;
    }

    public void setStartMileage(Integer startMileage) {
        this.startMileage = startMileage;
    }

    public Integer getEndMileage() {
        return endMileage;
    }

    public LogEntry endMileage(Integer endMileage) {
        this.endMileage = endMileage;
        return this;
    }

    public void setEndMileage(Integer endMileage) {
        this.endMileage = endMileage;
    }

    public InvalidationCode getInvalidationCode() {
        return invalidationCode;
    }

    public LogEntry invalidationCode(InvalidationCode invalidationCode) {
        this.invalidationCode = invalidationCode;
        return this;
    }

    public void setInvalidationCode(InvalidationCode invalidationCode) {
        this.invalidationCode = invalidationCode;
    }

    public ELR getElr() {
        return elr;
    }

    public LogEntry elr(ELR eLR) {
        this.elr = eLR;
        return this;
    }

    public void setElr(ELR eLR) {
        this.elr = eLR;
    }

    public Track getTrackId() {
        return trackId;
    }

    public LogEntry trackId(Track track) {
        this.trackId = track;
        return this;
    }

    public void setTrackId(Track track) {
        this.trackId = track;
    }

    public CaptainsLog getCaptainsLog() {
        return captainsLog;
    }

    public LogEntry captainsLog(CaptainsLog captainsLog) {
        this.captainsLog = captainsLog;
        return this;
    }

    public void setCaptainsLog(CaptainsLog captainsLog) {
        this.captainsLog = captainsLog;
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
        LogEntry logEntry = (LogEntry) o;
        if (logEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogEntry{" +
            "id=" + getId() +
            ", startMileage=" + getStartMileage() +
            ", endMileage=" + getEndMileage() +
            ", invalidationCode='" + getInvalidationCode() + "'" +
            "}";
    }
}
