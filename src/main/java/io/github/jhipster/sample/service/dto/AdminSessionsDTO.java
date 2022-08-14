package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminSessions} entity.
 */
public class AdminSessionsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 32)
    private String sessionId;

    @NotNull
    private Instant sessionTime;

    @NotNull
    private Instant sessionStart;

    @NotNull
    @Size(max = 255)
    private String sessionValue;

    @NotNull
    @Size(max = 16)
    private String ipAddress;

    @NotNull
    @Size(max = 255)
    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Instant sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Instant getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Instant sessionStart) {
        this.sessionStart = sessionStart;
    }

    public String getSessionValue() {
        return sessionValue;
    }

    public void setSessionValue(String sessionValue) {
        this.sessionValue = sessionValue;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminSessionsDTO)) {
            return false;
        }

        AdminSessionsDTO adminSessionsDTO = (AdminSessionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminSessionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminSessionsDTO{" +
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
