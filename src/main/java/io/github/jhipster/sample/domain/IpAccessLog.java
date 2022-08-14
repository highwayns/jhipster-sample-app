package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IpAccessLog.
 */
@Entity
@Table(name = "ip_access_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IpAccessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ip", nullable = false)
    private Long ip;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "login", nullable = false)
    private YesNo login;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IpAccessLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIp() {
        return this.ip;
    }

    public IpAccessLog ip(Long ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public IpAccessLog timestamp(Instant timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public YesNo getLogin() {
        return this.login;
    }

    public IpAccessLog login(YesNo login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(YesNo login) {
        this.login = login;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IpAccessLog)) {
            return false;
        }
        return id != null && id.equals(((IpAccessLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IpAccessLog{" +
            "id=" + getId() +
            ", ip=" + getIp() +
            ", timestamp='" + getTimestamp() + "'" +
            ", login='" + getLogin() + "'" +
            "}";
    }
}
