package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContentFilesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentFiles.class);
        ContentFiles contentFiles1 = new ContentFiles();
        contentFiles1.setId(1L);
        ContentFiles contentFiles2 = new ContentFiles();
        contentFiles2.setId(contentFiles1.getId());
        assertThat(contentFiles1).isEqualTo(contentFiles2);
        contentFiles2.setId(2L);
        assertThat(contentFiles1).isNotEqualTo(contentFiles2);
        contentFiles1.setId(null);
        assertThat(contentFiles1).isNotEqualTo(contentFiles2);
    }
}
