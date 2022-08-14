package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.ContentFiles} entity.
 */
public class ContentFilesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer fId;

    @NotNull
    @Size(max = 4)
    private String ext;

    @NotNull
    @Size(max = 255)
    private String dir;

    @NotNull
    @Size(max = 255)
    private String url;

    @NotNull
    @Size(max = 255)
    private String oldName;

    @NotNull
    @Size(max = 50)
    private String fieldName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentFilesDTO)) {
            return false;
        }

        ContentFilesDTO contentFilesDTO = (ContentFilesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contentFilesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentFilesDTO{" +
            "id=" + getId() +
            ", fId=" + getfId() +
            ", ext='" + getExt() + "'" +
            ", dir='" + getDir() + "'" +
            ", url='" + getUrl() + "'" +
            ", oldName='" + getOldName() + "'" +
            ", fieldName='" + getFieldName() + "'" +
            "}";
    }
}
