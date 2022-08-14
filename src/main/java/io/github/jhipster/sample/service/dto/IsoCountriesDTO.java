package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.IsoCountries} entity.
 */
public class IsoCountriesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String locale;

    @NotNull
    @Size(max = 2)
    private String code;

    @Size(max = 200)
    private String name;

    @Size(max = 50)
    private String prefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsoCountriesDTO)) {
            return false;
        }

        IsoCountriesDTO isoCountriesDTO = (IsoCountriesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, isoCountriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsoCountriesDTO{" +
            "id=" + getId() +
            ", locale='" + getLocale() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", prefix='" + getPrefix() + "'" +
            "}";
    }
}
