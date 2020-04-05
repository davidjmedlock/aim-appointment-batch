package com.aim.appointment.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A TmpAppointmentLoad.
 */
@Entity
@Table(name = "tmp_appointment_load")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TmpAppointmentLoad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "npi")
    private String npi;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "appointment_type")
    private String appointmentType;

    @Column(name = "extraction_date_time")
    private String extractionDateTime;

    @Column(name = "next_available_appt")
    private String nextAvailableAppt;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "processed_at")
    private ZonedDateTime processedAt;

    @Column(name = "record_status")
    private String recordStatus;

    @Column(name = "load_file_name")
    private String loadFileName;

    @Column(name = "tenant_hash_key")
    private String tenantHashKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public TmpAppointmentLoad firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public TmpAppointmentLoad lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNpi() {
        return npi;
    }

    public TmpAppointmentLoad npi(String npi) {
        this.npi = npi;
        return this;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }

    public String getAddress1() {
        return address1;
    }

    public TmpAppointmentLoad address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public TmpAppointmentLoad address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public TmpAppointmentLoad address3(String address3) {
        this.address3 = address3;
        return this;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public TmpAppointmentLoad city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public TmpAppointmentLoad state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public TmpAppointmentLoad zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public TmpAppointmentLoad phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public TmpAppointmentLoad appointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
        return this;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getExtractionDateTime() {
        return extractionDateTime;
    }

    public TmpAppointmentLoad extractionDateTime(String extractionDateTime) {
        this.extractionDateTime = extractionDateTime;
        return this;
    }

    public void setExtractionDateTime(String extractionDateTime) {
        this.extractionDateTime = extractionDateTime;
    }

    public String getNextAvailableAppt() {
        return nextAvailableAppt;
    }

    public TmpAppointmentLoad nextAvailableAppt(String nextAvailableAppt) {
        this.nextAvailableAppt = nextAvailableAppt;
        return this;
    }

    public void setNextAvailableAppt(String nextAvailableAppt) {
        this.nextAvailableAppt = nextAvailableAppt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public TmpAppointmentLoad createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getProcessedAt() {
        return processedAt;
    }

    public TmpAppointmentLoad processedAt(ZonedDateTime processedAt) {
        this.processedAt = processedAt;
        return this;
    }

    public void setProcessedAt(ZonedDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public TmpAppointmentLoad recordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
        return this;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getLoadFileName() {
        return loadFileName;
    }

    public TmpAppointmentLoad loadFileName(String loadFileName) {
        this.loadFileName = loadFileName;
        return this;
    }

    public void setLoadFileName(String loadFileName) {
        this.loadFileName = loadFileName;
    }

    public String getTenantHashKey() {
        return tenantHashKey;
    }

    public TmpAppointmentLoad tenantHashKey(String tenantHashKey) {
        this.tenantHashKey = tenantHashKey;
        return this;
    }

    public void setTenantHashKey(String tenantHashKey) {
        this.tenantHashKey = tenantHashKey;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TmpAppointmentLoad)) {
            return false;
        }
        return id != null && id.equals(((TmpAppointmentLoad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TmpAppointmentLoad{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", npi='" + getNpi() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", address3='" + getAddress3() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zip='" + getZip() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", appointmentType='" + getAppointmentType() + "'" +
            ", extractionDateTime='" + getExtractionDateTime() + "'" +
            ", nextAvailableAppt='" + getNextAvailableAppt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", processedAt='" + getProcessedAt() + "'" +
            ", recordStatus='" + getRecordStatus() + "'" +
            ", loadFileName='" + getLoadFileName() + "'" +
            ", tenantHashKey='" + getTenantHashKey() + "'" +
            "}";
    }
}
