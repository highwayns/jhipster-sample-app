package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.SiteUsersAccess} entity.
 */
public class SiteUsersAccessDTO implements Serializable {

    private Long id;

    @NotNull
    private Long start;

    @NotNull
    private Long last;

    @NotNull
    private Integer attempts;

    private SiteUsersDTO siteUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getLast() {
        return last;
    }

    public void setLast(Long last) {
        this.last = last;
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
        if (!(o instanceof SiteUsersAccessDTO)) {
            return false;
        }

        SiteUsersAccessDTO siteUsersAccessDTO = (SiteUsersAccessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, siteUsersAccessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUsersAccessDTO{" +
            "id=" + getId() +
            ", start=" + getStart() +
            ", last=" + getLast() +
            ", attempts=" + getAttempts() +
            ", siteUser=" + getSiteUser() +
            "}";
    }
}
