package com.aim.appointment.repository;

import com.aim.appointment.model.TenantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantKeyRepository extends JpaRepository<TenantKey, Long> {
    @Query("SELECT tenantKey FROM TenantKey tenantKey WHERE tenantKey.providerGroupId = ?1")
    public Optional<TenantKey> findByProviderGroupId(Long providerGroupId);

    @Query("SELECT tenantKey FROM TenantKey tenantKey WHERE tenantKey.batchCode = ?1")
    public Optional<TenantKey> findByBatchCode(String batchCode);
}
