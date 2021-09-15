package com.example.application.data.dto;

import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route
public class CustomerDTO {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String addressStreet;
    private String addressNumber;
    private String addressPostCode;
    private String addressCity;
    private String peselNumber;
    private String nipNumber;
    private String idType;
    private String idNumber;
    private String mailAddress;
    private boolean isActive;
    private LocalDate registrationDate;
    private LocalDate closedDate;
    private List<Long> loansId = new ArrayList<>();
    private List<Long> loansApplicationId = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressPostCode() {
        return addressPostCode;
    }

    public void setAddressPostCode(String addressPostCode) {
        this.addressPostCode = addressPostCode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getPeselNumber() {
        return peselNumber;
    }

    public void setPeselNumber(String peselNumber) {
        this.peselNumber = peselNumber;
    }

    public String getNipNumber() {
        return nipNumber;
    }

    public void setNipNumber(String nipNumber) {
        this.nipNumber = nipNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDate closedDate) {
        this.closedDate = closedDate;
    }

    public List<Long> getLoansId() {
        return loansId;
    }

    public void setLoansId(List<Long> loansId) {
        this.loansId = loansId;
    }

    public List<Long> getLoansApplicationId() {
        return loansApplicationId;
    }

    public void setLoansApplicationId(List<Long> loansApplicationId) {
        this.loansApplicationId = loansApplicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDTO)) return false;

        CustomerDTO that = (CustomerDTO) o;

        if (isActive != that.isActive) return false;
        if (!name.equals(that.name)) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (addressStreet != null ? !addressStreet.equals(that.addressStreet) : that.addressStreet != null)
            return false;
        if (addressNumber != null ? !addressNumber.equals(that.addressNumber) : that.addressNumber != null)
            return false;
        if (addressPostCode != null ? !addressPostCode.equals(that.addressPostCode) : that.addressPostCode != null)
            return false;
        if (addressCity != null ? !addressCity.equals(that.addressCity) : that.addressCity != null) return false;
        if (!peselNumber.equals(that.peselNumber)) return false;
        if (idType != null ? !idType.equals(that.idType) : that.idType != null) return false;
        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null) return false;
        if (mailAddress != null ? !mailAddress.equals(that.mailAddress) : that.mailAddress != null) return false;
        return registrationDate != null ? registrationDate.equals(that.registrationDate) : that.registrationDate == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (addressStreet != null ? addressStreet.hashCode() : 0);
        result = 31 * result + (addressNumber != null ? addressNumber.hashCode() : 0);
        result = 31 * result + (addressPostCode != null ? addressPostCode.hashCode() : 0);
        result = 31 * result + (addressCity != null ? addressCity.hashCode() : 0);
        result = 31 * result + peselNumber.hashCode();
        result = 31 * result + (idType != null ? idType.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (mailAddress != null ? mailAddress.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        return result;
    }
}
