package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HistoryActions.
 */
@Entity
@Table(name = "history_actions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HistoryActions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_en", length = 255, nullable = false)
    private String nameEn;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_es", length = 255, nullable = false)
    private String nameEs;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_ru", length = 255, nullable = false)
    private String nameRu;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_zh", length = 255, nullable = false)
    private String nameZh;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HistoryActions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public HistoryActions nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEs() {
        return this.nameEs;
    }

    public HistoryActions nameEs(String nameEs) {
        this.setNameEs(nameEs);
        return this;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    public String getNameRu() {
        return this.nameRu;
    }

    public HistoryActions nameRu(String nameRu) {
        this.setNameRu(nameRu);
        return this;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameZh() {
        return this.nameZh;
    }

    public HistoryActions nameZh(String nameZh) {
        this.setNameZh(nameZh);
        return this;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoryActions)) {
            return false;
        }
        return id != null && id.equals(((HistoryActions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoryActions{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameEs='" + getNameEs() + "'" +
            ", nameRu='" + getNameRu() + "'" +
            ", nameZh='" + getNameZh() + "'" +
            "}";
    }
}
