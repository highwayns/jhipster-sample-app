package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminPages.
 */
@Entity
@Table(name = "admin_pages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminPages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "f_id", nullable = false)
    private Integer fId;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @NotNull
    @Size(max = 255)
    @Column(name = "icon", length = 255, nullable = false)
    private String icon;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Column(name = "page_map_reorders", nullable = false)
    private Boolean pageMapReorders;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "one_record", nullable = false)
    private YesNo oneRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminPages id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getfId() {
        return this.fId;
    }

    public AdminPages fId(Integer fId) {
        this.setfId(fId);
        return this;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getName() {
        return this.name;
    }

    public AdminPages name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public AdminPages url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return this.icon;
    }

    public AdminPages icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return this.order;
    }

    public AdminPages order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getPageMapReorders() {
        return this.pageMapReorders;
    }

    public AdminPages pageMapReorders(Boolean pageMapReorders) {
        this.setPageMapReorders(pageMapReorders);
        return this;
    }

    public void setPageMapReorders(Boolean pageMapReorders) {
        this.pageMapReorders = pageMapReorders;
    }

    public YesNo getOneRecord() {
        return this.oneRecord;
    }

    public AdminPages oneRecord(YesNo oneRecord) {
        this.setOneRecord(oneRecord);
        return this;
    }

    public void setOneRecord(YesNo oneRecord) {
        this.oneRecord = oneRecord;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminPages)) {
            return false;
        }
        return id != null && id.equals(((AdminPages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminPages{" +
            "id=" + getId() +
            ", fId=" + getfId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            ", icon='" + getIcon() + "'" +
            ", order=" + getOrder() +
            ", pageMapReorders='" + getPageMapReorders() + "'" +
            ", oneRecord='" + getOneRecord() + "'" +
            "}";
    }
}
