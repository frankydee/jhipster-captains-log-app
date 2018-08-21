package uk.co.networkrail.trackgeometry.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CaptainsLog.
 */
@Entity
@Table(name = "captains_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CaptainsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "run_date", nullable = false)
    private Instant runDate;

    @Column(name = "rst")
    private String rst;

    @Column(name = "load_date")
    private Instant loadDate;

    @OneToMany(mappedBy = "captainsLog")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LogEntry> logEntries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRunDate() {
        return runDate;
    }

    public CaptainsLog runDate(Instant runDate) {
        this.runDate = runDate;
        return this;
    }

    public void setRunDate(Instant runDate) {
        this.runDate = runDate;
    }

    public String getRst() {
        return rst;
    }

    public CaptainsLog rst(String rst) {
        this.rst = rst;
        return this;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Instant getLoadDate() {
        return loadDate;
    }

    public CaptainsLog loadDate(Instant loadDate) {
        this.loadDate = loadDate;
        return this;
    }

    public void setLoadDate(Instant loadDate) {
        this.loadDate = loadDate;
    }

    public Set<LogEntry> getLogEntries() {
        return logEntries;
    }

    public CaptainsLog logEntries(Set<LogEntry> logEntries) {
        this.logEntries = logEntries;
        return this;
    }

    public CaptainsLog addLogEntry(LogEntry logEntry) {
        this.logEntries.add(logEntry);
        logEntry.setCaptainsLog(this);
        return this;
    }

    public CaptainsLog removeLogEntry(LogEntry logEntry) {
        this.logEntries.remove(logEntry);
        logEntry.setCaptainsLog(null);
        return this;
    }

    public void setLogEntries(Set<LogEntry> logEntries) {
        this.logEntries = logEntries;
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
        CaptainsLog captainsLog = (CaptainsLog) o;
        if (captainsLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), captainsLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaptainsLog{" +
            "id=" + getId() +
            ", runDate='" + getRunDate() + "'" +
            ", rst='" + getRst() + "'" +
            ", loadDate='" + getLoadDate() + "'" +
            "}";
    }
}
