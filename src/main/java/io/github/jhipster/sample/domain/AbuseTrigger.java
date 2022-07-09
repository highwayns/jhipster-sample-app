package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AbuseTrigger.
 */
@Entity
@Table(name = "abuse_trigger")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AbuseTrigger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Double score;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "triggers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "triggers" }, allowSetters = true)
    private Set<AbuseReport> abuseReports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "abuseTriggers", "entries" }, allowSetters = true)
    private Parameters parameters;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AbuseTrigger id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return this.score;
    }

    public AbuseTrigger score(Double score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCode() {
        return this.code;
    }

    public AbuseTrigger code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public AbuseTrigger description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AbuseReport> getAbuseReports() {
        return this.abuseReports;
    }

    public void setAbuseReports(Set<AbuseReport> abuseReports) {
        if (this.abuseReports != null) {
            this.abuseReports.forEach(i -> i.setTriggers(null));
        }
        if (abuseReports != null) {
            abuseReports.forEach(i -> i.setTriggers(this));
        }
        this.abuseReports = abuseReports;
    }

    public AbuseTrigger abuseReports(Set<AbuseReport> abuseReports) {
        this.setAbuseReports(abuseReports);
        return this;
    }

    public AbuseTrigger addAbuseReport(AbuseReport abuseReport) {
        this.abuseReports.add(abuseReport);
        abuseReport.setTriggers(this);
        return this;
    }

    public AbuseTrigger removeAbuseReport(AbuseReport abuseReport) {
        this.abuseReports.remove(abuseReport);
        abuseReport.setTriggers(null);
        return this;
    }

    public Parameters getParameters() {
        return this.parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public AbuseTrigger parameters(Parameters parameters) {
        this.setParameters(parameters);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbuseTrigger)) {
            return false;
        }
        return id != null && id.equals(((AbuseTrigger) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbuseTrigger{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
