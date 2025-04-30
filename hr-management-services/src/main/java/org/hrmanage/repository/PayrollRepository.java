package org.hrmanage.repository;

import org.hrmanage.entity.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<PayrollEntity, Integer> {
}
