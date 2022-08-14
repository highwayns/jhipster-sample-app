package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.BankAccounts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankAccounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountsRepository extends JpaRepository<BankAccounts, Long> {}
