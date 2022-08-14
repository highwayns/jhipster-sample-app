package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.IpAccessLog} entity.
 */
public class IpAccessLogDTO implements Serializable {

    private Long id;

    @NotNull
    private Long ip;

    @NotNull
    private Instant timestamp;

    @NotNull
    private YesNo login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public YesNo getLogin() {
        return login;
    }

    public void setLogin(YesNo login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IpAccessLogDTO)) {
            return false;
        }

        IpAccessLogDTO ipAccessLogDTO = (IpAccessLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ipAccessLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IpAccessLogDTO{" +
            "id=" + getId() +
            ", ip=" + getIp() +
            ", timestamp='" + getTimestamp() + "'" +
            ", login='" + getLogin() + "'" +
            "}";
    }
}
