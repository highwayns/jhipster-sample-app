package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.PaymentMethodInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentMethodInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentMethodInfoRepository extends JpaRepository<PaymentMethodInfo, Long> {}
