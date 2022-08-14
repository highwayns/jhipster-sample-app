package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.SiteUsersCatch} entity.
 */
public class SiteUsersCatchDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer attempts;

    private SiteUsersDTO siteUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
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
        if (!(o instanceof SiteUsersCatchDTO)) {
            return false;
        }

        SiteUsersCatchDTO siteUsersCatchDTO = (SiteUsersCatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, siteUsersCatchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUsersCatchDTO{" +
            "id=" + getId() +
            ", attempts=" + getAttempts() +
            ", siteUser=" + getSiteUser() +
            "}";
    }
}
