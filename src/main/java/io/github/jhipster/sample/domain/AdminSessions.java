package io.github.jhipster.sample.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminSessions.
 */
@Entity
@Table(name = "admin_sessions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminSessions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 32)
    @Column(name = "session_id", length = 32, nullable = false)
    private String sessionId;

    @NotNull
    @Column(name = "session_time", nullable = false)
    private Instant sessionTime;

    @NotNull
    @Column(name = "session_start", nullable = false)
    private Instant sessionStart;

    @NotNull
    @Size(max = 255)
    @Column(name = "session_value", length = 255, nullable = false)
    private String sessionValue;

    @NotNull
    @Size(max = 16)
    @Column(name = "ip_address", length = 16, nullable = false)
    private String ipAddress;

    @NotNull
    @Size(max = 255)
    @Column(name = "user_agent", length = 255, nullable = false)
    private String userAgent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminSessions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public AdminSessions sessionId(String sessionId) {
        this.setSessionId(sessionId);
        return this;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getSessionTime() {
        return this.sessionTime;
    }

    public AdminSessions sessionTime(Instant sessionTime) {
        this.setSessionTime(sessionTime);
        return this;
    }

    public void setSessionTime(Instant sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Instant getSessionStart() {
        return this.sessionStart;
    }

    public AdminSessions sessionStart(Instant sessionStart) {
        this.setSessionStart(sessionStart);
        return this;
    }

    public void setSessionStart(Instant sessionStart) {
        this.sessionStart = sessionStart;
    }

    public String getSessionValue() {
        return this.sessionValue;
    }

    public AdminSessions sessionValue(String sessionValue) {
        this.setSessionValue(sessionValue);
        return this;
    }

    public void setSessionValue(String sessionValue) {
        this.sessionValue = sessionValue;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public AdminSessions ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public AdminSessions userAgent(String userAgent) {
        this.setUserAgent(userAgent);
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminSessions)) {
            return false;
        }
        return id != null && id.equals(((AdminSessions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminSessions{" +
            "id=" + getId() +
            ", sessionId='" + getSessionId() + "'" +
            ", sessionTime='" + getSessionTime() + "'" +
            ", sessionStart='" + getSessionStart() + "'" +
            ", sessionValue='" + getSessionValue() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            "}";
    }
}
