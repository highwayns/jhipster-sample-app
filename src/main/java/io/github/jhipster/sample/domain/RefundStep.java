package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.RefundStatus;
import io.github.jhipster.sample.domain.enumeration.RefundStepAction;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RefundStep.
 */
@Entity
@Table(name = "refund_step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RefundStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "reference")
    private Long reference;

    @Column(name = "create_date_time_utc")
    private Instant createDateTimeUtc;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private RefundStepAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RefundStatus status;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private ResultAttributes resultAttributes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RefundStep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return this.reference;
    }

    public RefundStep reference(Long reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Instant getCreateDateTimeUtc() {
        return this.createDateTimeUtc;
    }

    public RefundStep createDateTimeUtc(Instant createDateTimeUtc) {
        this.setCreateDateTimeUtc(createDateTimeUtc);
        return this;
    }

    public void setCreateDateTimeUtc(Instant createDateTimeUtc) {
        this.createDateTimeUtc = createDateTimeUtc;
    }

    public RefundStepAction getAction() {
        return this.action;
    }

    public RefundStep action(RefundStepAction action) {
        this.setAction(action);
        return this;
    }

    public void setAction(RefundStepAction action) {
        this.action = action;
    }

    public RefundStatus getStatus() {
        return this.status;
    }

    public RefundStep status(RefundStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RefundStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public RefundStep description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResultAttributes getResultAttributes() {
        return this.resultAttributes;
    }

    public void setResultAttributes(ResultAttributes resultAttributes) {
        this.resultAttributes = resultAttributes;
    }

    public RefundStep resultAttributes(ResultAttributes resultAttributes) {
        this.setResultAttributes(resultAttributes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefundStep)) {
            return false;
        }
        return id != null && id.equals(((RefundStep) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefundStep{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            ", createDateTimeUtc='" + getCreateDateTimeUtc() + "'" +
            ", action='" + getAction() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
