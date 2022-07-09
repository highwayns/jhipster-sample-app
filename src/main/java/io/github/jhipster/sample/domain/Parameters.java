package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Parameters.
 */
@Entity
@Table(name = "parameters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parameters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "parameter")
    private String parameter;

    @OneToMany(mappedBy = "parameters")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "abuseReports", "parameters" }, allowSetters = true)
    private Set<AbuseTrigger> abuseTriggers = new HashSet<>();

    @OneToMany(mappedBy = "parameters")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parameters" }, allowSetters = true)
    private Set<Entry> entries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parameters id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter() {
        return this.parameter;
    }

    public Parameters parameter(String parameter) {
        this.setParameter(parameter);
        return this;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Set<AbuseTrigger> getAbuseTriggers() {
        return this.abuseTriggers;
    }

    public void setAbuseTriggers(Set<AbuseTrigger> abuseTriggers) {
        if (this.abuseTriggers != null) {
            this.abuseTriggers.forEach(i -> i.setParameters(null));
        }
        if (abuseTriggers != null) {
            abuseTriggers.forEach(i -> i.setParameters(this));
        }
        this.abuseTriggers = abuseTriggers;
    }

    public Parameters abuseTriggers(Set<AbuseTrigger> abuseTriggers) {
        this.setAbuseTriggers(abuseTriggers);
        return this;
    }

    public Parameters addAbuseTrigger(AbuseTrigger abuseTrigger) {
        this.abuseTriggers.add(abuseTrigger);
        abuseTrigger.setParameters(this);
        return this;
    }

    public Parameters removeAbuseTrigger(AbuseTrigger abuseTrigger) {
        this.abuseTriggers.remove(abuseTrigger);
        abuseTrigger.setParameters(null);
        return this;
    }

    public Set<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(Set<Entry> entries) {
        if (this.entries != null) {
            this.entries.forEach(i -> i.setParameters(null));
        }
        if (entries != null) {
            entries.forEach(i -> i.setParameters(this));
        }
        this.entries = entries;
    }

    public Parameters entries(Set<Entry> entries) {
        this.setEntries(entries);
        return this;
    }

    public Parameters addEntry(Entry entry) {
        this.entries.add(entry);
        entry.setParameters(this);
        return this;
    }

    public Parameters removeEntry(Entry entry) {
        this.entries.remove(entry);
        entry.setParameters(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parameters)) {
            return false;
        }
        return id != null && id.equals(((Parameters) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parameters{" +
            "id=" + getId() +
            ", parameter='" + getParameter() + "'" +
            "}";
    }
}
