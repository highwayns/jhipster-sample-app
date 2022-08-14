package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminOrder.class);
        AdminOrder adminOrder1 = new AdminOrder();
        adminOrder1.setId(1L);
        AdminOrder adminOrder2 = new AdminOrder();
        adminOrder2.setId(adminOrder1.getId());
        assertThat(adminOrder1).isEqualTo(adminOrder2);
        adminOrder2.setId(2L);
        assertThat(adminOrder1).isNotEqualTo(adminOrder2);
        adminOrder1.setId(null);
        assertThat(adminOrder1).isNotEqualTo(adminOrder2);
    }
}
