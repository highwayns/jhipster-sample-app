package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteUsersAccess.
 */
@Entity
@Table(name = "site_users_access")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteUsersAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start", nullable = false)
    private Long start;

    @NotNull
    @Column(name = "last", nullable = false)
    private Long last;

    @NotNull
    @Column(name = "attempts", nullable = false)
    private Integer attempts;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SiteUsersAccess id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStart() {
        return this.start;
    }

    public SiteUsersAccess start(Long start) {
        this.setStart(start);
        return this;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getLast() {
        return this.last;
    }

    public SiteUsersAccess last(Long last) {
        this.setLast(last);
        return this;
    }

    public void setLast(Long last) {
        this.last = last;
    }

    public Integer getAttempts() {
        return this.attempts;
    }

    public SiteUsersAccess attempts(Integer attempts) {
        this.setAttempts(attempts);
        return this;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public SiteUsersAccess siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteUsersAccess)) {
            return false;
        }
        return id != null && id.equals(((SiteUsersAccess) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUsersAccess{" +
            "id=" + getId() +
            ", start=" + getStart() +
            ", last=" + getLast() +
            ", attempts=" + getAttempts() +
            "}";
    }
}
