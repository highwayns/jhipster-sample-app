package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminGroupsTabs} entity.
 */
public class AdminGroupsTabsDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean permission;

    private AdminTabsDTO tabId;

    private AdminGroupsDTO groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public AdminTabsDTO getTabId() {
        return tabId;
    }

    public void setTabId(AdminTabsDTO tabId) {
        this.tabId = tabId;
    }

    public AdminGroupsDTO getGroupId() {
        return groupId;
    }

    public void setGroupId(AdminGroupsDTO groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminGroupsTabsDTO)) {
            return false;
        }

        AdminGroupsTabsDTO adminGroupsTabsDTO = (AdminGroupsTabsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminGroupsTabsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminGroupsTabsDTO{" +
            "id=" + getId() +
            ", permission='" + getPermission() + "'" +
            ", tabId=" + getTabId() +
            ", groupId=" + getGroupId() +
            "}";
    }
}
