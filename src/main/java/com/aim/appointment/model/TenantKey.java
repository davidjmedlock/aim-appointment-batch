package com.aim.appointment.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A TenantKey.
 */
@Entity
@Table(name = "tenant_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TenantKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tenant_key_name", nullable = false, unique = true, updatable = false, insertable = true)
    // Think that we need a JsonIgnore here
    private String tenantKeyName;

    @NotNull
    @Column(name = "tenant_hash_key", nullable = false, unique = true, updatable = false, insertable = true)
    // Think that we need a JsonIgnore here
    private String tenantHashKey;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "batch_code")
    private String batchCode;

    @Column(name = "health_plan_id")
    private Long healthPlanId;

    @Column(name = "provider_group_id")
    private Long providerGroupId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantKeyName() {
        return tenantKeyName;
    }

    public TenantKey tenantKeyName(String tenantKeyName) {
        this.tenantKeyName = tenantKeyName;
        return this;
    }

    public void setTenantKeyName(String tenantKeyName) {
        this.tenantKeyName = tenantKeyName;
    }

    public String getTenantHashKey() {
        return tenantHashKey;
    }

    public TenantKey tenantHashKey(String tenantHashKey) {
        this.tenantHashKey = tenantHashKey;
        return this;
    }

    public void setTenantHashKey(String tenantHashKey) {
        this.tenantHashKey = tenantHashKey;
    }

    public Boolean isActive() {
        return active;
    }

    public TenantKey active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public TenantKey batchCode(String batchCode) {
        this.batchCode = batchCode;
        return this;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Long getHealthPlanId() {
        return healthPlanId;
    }

    public TenantKey healthPlanId(Long healthPlanId) {
        this.healthPlanId = healthPlanId;
        return this;
    }

    public void setHealthPlanId(Long healthPlanId) {
        this.healthPlanId = healthPlanId;
    }

    public Long getProviderGroupId() {
        return providerGroupId;
    }

    public TenantKey providerGroupId(Long providerGroupId) {
        this.providerGroupId = providerGroupId;
        return this;
    }

    public void setProviderGroupId(Long providerGroupId) {
        this.providerGroupId = providerGroupId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantKey)) {
            return false;
        }
        return id != null && id.equals(((TenantKey) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TenantKey{" +
                "id=" + getId() +
                ", tenantKeyName='" + getTenantKeyName() + "'" +
                ", tenantHashKey='" + getTenantHashKey() + "'" +
                ", active='" + isActive() + "'" +
                ", healthPlanId=" + getHealthPlanId() +
                ", providerGroupId=" + getProviderGroupId() +
                "}";
    }
}
