package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminControls} entity.
 */
public class AdminControlsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String action;

    @Size(max = 50)
    private String controlClass;

    @NotNull
    @Size(max = 255)
    private String argument;

    @NotNull
    private Integer order;

    @NotNull
    private YesNo isStatic;

    private AdminPagesDTO pageId;

    private AdminTabsDTO tabId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getControlClass() {
        return controlClass;
    }

    public void setControlClass(String controlClass) {
        this.controlClass = controlClass;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public YesNo getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(YesNo isStatic) {
        this.isStatic = isStatic;
    }

    public AdminPagesDTO getPageId() {
        return pageId;
    }

    public void setPageId(AdminPagesDTO pageId) {
        this.pageId = pageId;
    }

    public AdminTabsDTO getTabId() {
        return tabId;
    }

    public void setTabId(AdminTabsDTO tabId) {
        this.tabId = tabId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminControlsDTO)) {
            return false;
        }

        AdminControlsDTO adminControlsDTO = (AdminControlsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminControlsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminControlsDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", controlClass='" + getControlClass() + "'" +
            ", argument='" + getArgument() + "'" +
            ", order=" + getOrder() +
            ", isStatic='" + getIsStatic() + "'" +
            ", pageId=" + getPageId() +
            ", tabId=" + getTabId() +
            "}";
    }
}
