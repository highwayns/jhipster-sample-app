package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Links.
 */
@Entity
@Table(name = "links")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Link data;

    @OneToOne
    @JoinColumn(unique = true)
    private Link action;

    @OneToOne
    @JoinColumn(unique = true)
    private Link documentation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Links id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Link getData() {
        return this.data;
    }

    public void setData(Link link) {
        this.data = link;
    }

    public Links data(Link link) {
        this.setData(link);
        return this;
    }

    public Link getAction() {
        return this.action;
    }

    public void setAction(Link link) {
        this.action = link;
    }

    public Links action(Link link) {
        this.setAction(link);
        return this;
    }

    public Link getDocumentation() {
        return this.documentation;
    }

    public void setDocumentation(Link link) {
        this.documentation = link;
    }

    public Links documentation(Link link) {
        this.setDocumentation(link);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Links)) {
            return false;
        }
        return id != null && id.equals(((Links) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Links{" +
            "id=" + getId() +
            "}";
    }
}
