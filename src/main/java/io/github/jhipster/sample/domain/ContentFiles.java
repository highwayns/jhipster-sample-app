package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContentFiles.
 */
@Entity
@Table(name = "content_files")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContentFiles implements Serializable {

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
    @Size(max = 4)
    @Column(name = "ext", length = 4, nullable = false)
    private String ext;

    @NotNull
    @Size(max = 255)
    @Column(name = "dir", length = 255, nullable = false)
    private String dir;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @NotNull
    @Size(max = 255)
    @Column(name = "old_name", length = 255, nullable = false)
    private String oldName;

    @NotNull
    @Size(max = 50)
    @Column(name = "field_name", length = 50, nullable = false)
    private String fieldName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContentFiles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getfId() {
        return this.fId;
    }

    public ContentFiles fId(Integer fId) {
        this.setfId(fId);
        return this;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getExt() {
        return this.ext;
    }

    public ContentFiles ext(String ext) {
        this.setExt(ext);
        return this;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getDir() {
        return this.dir;
    }

    public ContentFiles dir(String dir) {
        this.setDir(dir);
        return this;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getUrl() {
        return this.url;
    }

    public ContentFiles url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOldName() {
        return this.oldName;
    }

    public ContentFiles oldName(String oldName) {
        this.setOldName(oldName);
        return this;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public ContentFiles fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentFiles)) {
            return false;
        }
        return id != null && id.equals(((ContentFiles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentFiles{" +
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
