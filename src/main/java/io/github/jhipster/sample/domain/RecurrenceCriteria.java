package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.RecurrenceType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RecurrenceCriteria.
 */
@Entity
@Table(name = "recurrence_criteria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RecurrenceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_type")
    private RecurrenceType recurrenceType;

    @Column(name = "recurring_expiry")
    private Instant recurringExpiry;

    @Column(name = "recurring_frequency")
    private Integer recurringFrequency;

    @Column(name = "instalments")
    private Integer instalments;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RecurrenceCriteria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecurrenceType getRecurrenceType() {
        return this.recurrenceType;
    }

    public RecurrenceCriteria recurrenceType(RecurrenceType recurrenceType) {
        this.setRecurrenceType(recurrenceType);
        return this;
    }

    public void setRecurrenceType(RecurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public Instant getRecurringExpiry() {
        return this.recurringExpiry;
    }

    public RecurrenceCriteria recurringExpiry(Instant recurringExpiry) {
        this.setRecurringExpiry(recurringExpiry);
        return this;
    }

    public void setRecurringExpiry(Instant recurringExpiry) {
        this.recurringExpiry = recurringExpiry;
    }

    public Integer getRecurringFrequency() {
        return this.recurringFrequency;
    }

    public RecurrenceCriteria recurringFrequency(Integer recurringFrequency) {
        this.setRecurringFrequency(recurringFrequency);
        return this;
    }

    public void setRecurringFrequency(Integer recurringFrequency) {
        this.recurringFrequency = recurringFrequency;
    }

    public Integer getInstalments() {
        return this.instalments;
    }

    public RecurrenceCriteria instalments(Integer instalments) {
        this.setInstalments(instalments);
        return this;
    }

    public void setInstalments(Integer instalments) {
        this.instalments = instalments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecurrenceCriteria)) {
            return false;
        }
        return id != null && id.equals(((RecurrenceCriteria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecurrenceCriteria{" +
            "id=" + getId() +
            ", recurrenceType='" + getRecurrenceType() + "'" +
            ", recurringExpiry='" + getRecurringExpiry() + "'" +
            ", recurringFrequency=" + getRecurringFrequency() +
            ", instalments=" + getInstalments() +
            "}";
    }
}
