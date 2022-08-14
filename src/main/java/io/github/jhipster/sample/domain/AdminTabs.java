package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminTabs.
 */
@Entity
@Table(name = "admin_tabs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminTabs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Size(max = 255)
    @Column(name = "icon", length = 255, nullable = false)
    private String icon;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hidden", nullable = false)
    private YesNo hidden;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_ctrl_panel", nullable = false)
    private YesNo isCtrlPanel;

    @NotNull
    @Column(name = "for_group", nullable = false)
    private Integer forGroup;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "one_record", nullable = false)
    private YesNo oneRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminTabs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AdminTabs name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return this.order;
    }

    public AdminTabs order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getIcon() {
        return this.icon;
    }

    public AdminTabs icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return this.url;
    }

    public AdminTabs url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public YesNo getHidden() {
        return this.hidden;
    }

    public AdminTabs hidden(YesNo hidden) {
        this.setHidden(hidden);
        return this;
    }

    public void setHidden(YesNo hidden) {
        this.hidden = hidden;
    }

    public YesNo getIsCtrlPanel() {
        return this.isCtrlPanel;
    }

    public AdminTabs isCtrlPanel(YesNo isCtrlPanel) {
        this.setIsCtrlPanel(isCtrlPanel);
        return this;
    }

    public void setIsCtrlPanel(YesNo isCtrlPanel) {
        this.isCtrlPanel = isCtrlPanel;
    }

    public Integer getForGroup() {
        return this.forGroup;
    }

    public AdminTabs forGroup(Integer forGroup) {
        this.setForGroup(forGroup);
        return this;
    }

    public void setForGroup(Integer forGroup) {
        this.forGroup = forGroup;
    }

    public YesNo getOneRecord() {
        return this.oneRecord;
    }

    public AdminTabs oneRecord(YesNo oneRecord) {
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
        if (!(o instanceof AdminTabs)) {
            return false;
        }
        return id != null && id.equals(((AdminTabs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminTabs{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", icon='" + getIcon() + "'" +
            ", url='" + getUrl() + "'" +
            ", hidden='" + getHidden() + "'" +
            ", isCtrlPanel='" + getIsCtrlPanel() + "'" +
            ", forGroup=" + getForGroup() +
            ", oneRecord='" + getOneRecord() + "'" +
            "}";
    }
}
