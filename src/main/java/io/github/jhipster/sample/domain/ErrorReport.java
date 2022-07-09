package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.Locale;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ErrorReport.
 */
@Entity
@Table(name = "error_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ErrorReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Locale language;

    @Column(name = "is_fatal_error")
    private Boolean isFatalError;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ErrorReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLanguage() {
        return this.language;
    }

    public ErrorReport language(Locale language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Boolean getIsFatalError() {
        return this.isFatalError;
    }

    public ErrorReport isFatalError(Boolean isFatalError) {
        this.setIsFatalError(isFatalError);
        return this;
    }

    public void setIsFatalError(Boolean isFatalError) {
        this.isFatalError = isFatalError;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorReport)) {
            return false;
        }
        return id != null && id.equals(((ErrorReport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ErrorReport{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", isFatalError='" + getIsFatalError() + "'" +
            "}";
    }
}
