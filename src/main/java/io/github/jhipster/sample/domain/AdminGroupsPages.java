package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminGroupsPages.
 */
@Entity
@Table(name = "admin_groups_pages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminGroupsPages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "permission", nullable = false)
    private Boolean permission;

    @OneToOne
    @JoinColumn(unique = true)
    private AdminPages pageId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "adminGroupsPages", "adminGroupsTabs" }, allowSetters = true)
    private AdminGroups groupId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminGroupsPages id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPermission() {
        return this.permission;
    }

    public AdminGroupsPages permission(Boolean permission) {
        this.setPermission(permission);
        return this;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public AdminPages getPageId() {
        return this.pageId;
    }

    public void setPageId(AdminPages adminPages) {
        this.pageId = adminPages;
    }

    public AdminGroupsPages pageId(AdminPages adminPages) {
        this.setPageId(adminPages);
        return this;
    }

    public AdminGroups getGroupId() {
        return this.groupId;
    }

    public void setGroupId(AdminGroups adminGroups) {
        this.groupId = adminGroups;
    }

    public AdminGroupsPages groupId(AdminGroups adminGroups) {
        this.setGroupId(adminGroups);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminGroupsPages)) {
            return false;
        }
        return id != null && id.equals(((AdminGroupsPages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminGroupsPages{" +
            "id=" + getId() +
            ", permission='" + getPermission() + "'" +
            "}";
    }
}
