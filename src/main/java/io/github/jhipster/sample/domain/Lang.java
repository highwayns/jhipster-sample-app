package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Lang.
 */
@Entity
@Table(name = "lang")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "key", length = 255, nullable = false)
    private String key;

    @NotNull
    @Size(max = 255)
    @Column(name = "esp", length = 255, nullable = false)
    private String esp;

    @NotNull
    @Size(max = 255)
    @Column(name = "eng", length = 255, nullable = false)
    private String eng;

    @NotNull
    @Size(max = 255)
    @Column(name = "jhi_order", length = 255, nullable = false)
    private String order;

    @NotNull
    @Column(name = "p_id", nullable = false)
    private Integer pId;

    @NotNull
    @Size(max = 255)
    @Column(name = "zh", length = 255, nullable = false)
    private String zh;

    @NotNull
    @Size(max = 255)
    @Column(name = "ru", length = 255, nullable = false)
    private String ru;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lang id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public Lang key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEsp() {
        return this.esp;
    }

    public Lang esp(String esp) {
        this.setEsp(esp);
        return this;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }

    public String getEng() {
        return this.eng;
    }

    public Lang eng(String eng) {
        this.setEng(eng);
        return this;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getOrder() {
        return this.order;
    }

    public Lang order(String order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getpId() {
        return this.pId;
    }

    public Lang pId(Integer pId) {
        this.setpId(pId);
        return this;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getZh() {
        return this.zh;
    }

    public Lang zh(String zh) {
        this.setZh(zh);
        return this;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getRu() {
        return this.ru;
    }

    public Lang ru(String ru) {
        this.setRu(ru);
        return this;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lang)) {
            return false;
        }
        return id != null && id.equals(((Lang) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lang{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", esp='" + getEsp() + "'" +
            ", eng='" + getEng() + "'" +
            ", order='" + getOrder() + "'" +
            ", pId=" + getpId() +
            ", zh='" + getZh() + "'" +
            ", ru='" + getRu() + "'" +
            "}";
    }
}
