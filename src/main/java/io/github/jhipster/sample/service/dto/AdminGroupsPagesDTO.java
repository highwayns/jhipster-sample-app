package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminGroupsPages} entity.
 */
public class AdminGroupsPagesDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean permission;

    private AdminPagesDTO pageId;

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

    public AdminPagesDTO getPageId() {
        return pageId;
    }

    public void setPageId(AdminPagesDTO pageId) {
        this.pageId = pageId;
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
        if (!(o instanceof AdminGroupsPagesDTO)) {
            return false;
        }

        AdminGroupsPagesDTO adminGroupsPagesDTO = (AdminGroupsPagesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminGroupsPagesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminGroupsPagesDTO{" +
            "id=" + getId() +
            ", permission='" + getPermission() + "'" +
            ", pageId=" + getPageId() +
            ", groupId=" + getGroupId() +
            "}";
    }
}
