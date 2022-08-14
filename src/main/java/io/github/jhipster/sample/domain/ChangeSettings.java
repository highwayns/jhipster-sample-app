package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChangeSettings.
 */
@Entity
@Table(name = "change_settings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChangeSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "request", nullable = false)
    private byte[] request;

    @NotNull
    @Column(name = "request_content_type", nullable = false)
    private String requestContentType;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChangeSettings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getRequest() {
        return this.request;
    }

    public ChangeSettings request(byte[] request) {
        this.setRequest(request);
        return this;
    }

    public void setRequest(byte[] request) {
        this.request = request;
    }

    public String getRequestContentType() {
        return this.requestContentType;
    }

    public ChangeSettings requestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
        return this;
    }

    public void setRequestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
    }

    public Instant getDate() {
        return this.date;
    }

    public ChangeSettings date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public ChangeSettings siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChangeSettings)) {
            return false;
        }
        return id != null && id.equals(((ChangeSettings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChangeSettings{" +
            "id=" + getId() +
            ", request='" + getRequest() + "'" +
            ", requestContentType='" + getRequestContentType() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
