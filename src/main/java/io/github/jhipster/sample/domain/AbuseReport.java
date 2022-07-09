package io.github.jhipster.sample.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AbuseReport.
 */
@Entity
@Table(name = "abuse_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AbuseReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Double score;

    @Column(name = "created_date_time_utc")
    private Instant createdDateTimeUtc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AbuseReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return this.score;
    }

    public AbuseReport score(Double score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Instant getCreatedDateTimeUtc() {
        return this.createdDateTimeUtc;
    }

    public AbuseReport createdDateTimeUtc(Instant createdDateTimeUtc) {
        this.setCreatedDateTimeUtc(createdDateTimeUtc);
        return this;
    }

    public void setCreatedDateTimeUtc(Instant createdDateTimeUtc) {
        this.createdDateTimeUtc = createdDateTimeUtc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbuseReport)) {
            return false;
        }
        return id != null && id.equals(((AbuseReport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbuseReport{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", createdDateTimeUtc='" + getCreatedDateTimeUtc() + "'" +
            "}";
    }
}
