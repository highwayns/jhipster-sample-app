package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminGroups.
 */
@Entity
@Table(name = "admin_groups")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @OneToMany(mappedBy = "groupId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pageId", "groupId" }, allowSetters = true)
    private Set<AdminGroupsPages> adminGroupsPages = new HashSet<>();

    @OneToMany(mappedBy = "groupId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tabId", "groupId" }, allowSetters = true)
    private Set<AdminGroupsTabs> adminGroupsTabs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminGroups id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AdminGroups name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return this.order;
    }

    public AdminGroups order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Set<AdminGroupsPages> getAdminGroupsPages() {
        return this.adminGroupsPages;
    }

    public void setAdminGroupsPages(Set<AdminGroupsPages> adminGroupsPages) {
        if (this.adminGroupsPages != null) {
            this.adminGroupsPages.forEach(i -> i.setGroupId(null));
        }
        if (adminGroupsPages != null) {
            adminGroupsPages.forEach(i -> i.setGroupId(this));
        }
        this.adminGroupsPages = adminGroupsPages;
    }

    public AdminGroups adminGroupsPages(Set<AdminGroupsPages> adminGroupsPages) {
        this.setAdminGroupsPages(adminGroupsPages);
        return this;
    }

    public AdminGroups addAdminGroupsPages(AdminGroupsPages adminGroupsPages) {
        this.adminGroupsPages.add(adminGroupsPages);
        adminGroupsPages.setGroupId(this);
        return this;
    }

    public AdminGroups removeAdminGroupsPages(AdminGroupsPages adminGroupsPages) {
        this.adminGroupsPages.remove(adminGroupsPages);
        adminGroupsPages.setGroupId(null);
        return this;
    }

    public Set<AdminGroupsTabs> getAdminGroupsTabs() {
        return this.adminGroupsTabs;
    }

    public void setAdminGroupsTabs(Set<AdminGroupsTabs> adminGroupsTabs) {
        if (this.adminGroupsTabs != null) {
            this.adminGroupsTabs.forEach(i -> i.setGroupId(null));
        }
        if (adminGroupsTabs != null) {
            adminGroupsTabs.forEach(i -> i.setGroupId(this));
        }
        this.adminGroupsTabs = adminGroupsTabs;
    }

    public AdminGroups adminGroupsTabs(Set<AdminGroupsTabs> adminGroupsTabs) {
        this.setAdminGroupsTabs(adminGroupsTabs);
        return this;
    }

    public AdminGroups addAdminGroupsTabs(AdminGroupsTabs adminGroupsTabs) {
        this.adminGroupsTabs.add(adminGroupsTabs);
        adminGroupsTabs.setGroupId(this);
        return this;
    }

    public AdminGroups removeAdminGroupsTabs(AdminGroupsTabs adminGroupsTabs) {
        this.adminGroupsTabs.remove(adminGroupsTabs);
        adminGroupsTabs.setGroupId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminGroups)) {
            return false;
        }
        return id != null && id.equals(((AdminGroups) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminGroups{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
