package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.ChangeSettings} entity.
 */
public class ChangeSettingsDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] request;

    private String requestContentType;

    @NotNull
    private Instant date;

    private SiteUsersDTO siteUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getRequest() {
        return request;
    }

    public void setRequest(byte[] request) {
        this.request = request;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public SiteUsersDTO getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUsersDTO siteUser) {
        this.siteUser = siteUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChangeSettingsDTO)) {
            return false;
        }

        ChangeSettingsDTO changeSettingsDTO = (ChangeSettingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, changeSettingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChangeSettingsDTO{" +
            "id=" + getId() +
            ", request='" + getRequest() + "'" +
            ", date='" + getDate() + "'" +
            ", siteUser=" + getSiteUser() +
            "}";
    }
}
