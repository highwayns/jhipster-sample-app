package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InnodbLockMonitor.
 */
@Entity
@Table(name = "innodb_lock_monitor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InnodbLockMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "a")
    private Integer a;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InnodbLockMonitor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getA() {
        return this.a;
    }

    public InnodbLockMonitor a(Integer a) {
        this.setA(a);
        return this;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InnodbLockMonitor)) {
            return false;
        }
        return id != null && id.equals(((InnodbLockMonitor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InnodbLockMonitor{" +
            "id=" + getId() +
            ", a=" + getA() +
            "}";
    }
}
