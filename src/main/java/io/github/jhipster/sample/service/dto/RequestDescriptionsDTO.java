package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.RequestDescriptions} entity.
 */
public class RequestDescriptionsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nameEn;

    @NotNull
    @Size(max = 255)
    private String nameEs;

    @NotNull
    @Size(max = 255)
    private String nameRu;

    @NotNull
    @Size(max = 255)
    private String nameZh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDescriptionsDTO)) {
            return false;
        }

        RequestDescriptionsDTO requestDescriptionsDTO = (RequestDescriptionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDescriptionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDescriptionsDTO{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameEs='" + getNameEs() + "'" +
            ", nameRu='" + getNameRu() + "'" +
            ", nameZh='" + getNameZh() + "'" +
            "}";
    }
}
