/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.entity;

import com.academico.garagem.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mathe
 */
@Entity
@Table(name = "vehicle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v"),
    @NamedQuery(name = "Vehicle.findById", query = "SELECT v FROM Vehicle v WHERE v.id = :id"),
    @NamedQuery(name = "Vehicle.findByPlate", query = "SELECT v FROM Vehicle v WHERE v.plate = :plate"),
    @NamedQuery(name = "Vehicle.findByType", query = "SELECT v FROM Vehicle v WHERE v.type = :type"),
    @NamedQuery(name = "Vehicle.findByManufacturer", query = "SELECT v FROM Vehicle v WHERE v.manufacturer = :manufacturer"),
    @NamedQuery(name = "Vehicle.findByModel", query = "SELECT v FROM Vehicle v WHERE v.model = :model"),
    @NamedQuery(name = "Vehicle.findByYear", query = "SELECT v FROM Vehicle v WHERE v.year = :year"),
    @NamedQuery(name = "Vehicle.findByColor", query = "SELECT v FROM Vehicle v WHERE v.color = :color"),
    @NamedQuery(name = "Vehicle.findByChassis", query = "SELECT v FROM Vehicle v WHERE v.chassis = :chassis"),
    @NamedQuery(name = "Vehicle.findByIsAuth", query = "SELECT v FROM Vehicle v WHERE v.isAuth = :isAuth")})
public class Vehicle extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "plate")
    private String plate;
    @Size(max = 2147483647)
    @Column(name = "type")
    private String type;
    @Size(max = 2147483647)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Size(max = 2147483647)
    @Column(name = "model")
    private String model;
    @Column(name = "year")
    private String year;
    @Size(max = 2147483647)
    @Column(name = "color")
    private String color;
    @Size(max = 2147483647)
    @Column(name = "chassis")
    private String chassis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_auth")
    private boolean isAuth;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleId")
    private List<RentGarage> rentGarageList;
    @OneToMany(mappedBy = "vehicleId")
    private List<Image> imageList;
    @JsonManagedReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public Vehicle() {
    }

    public Vehicle(Integer id) {
        this.id = id;
    }

    public Vehicle(Integer id, String plate, boolean isAuth) {
        this.id = id;
        this.plate = plate;
        this.isAuth = isAuth;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    @XmlTransient
    public List<RentGarage> getRentGarageList() {
        return rentGarageList;
    }

    public void setRentGarageList(List<RentGarage> rentGarageList) {
        this.rentGarageList = rentGarageList;
    }

    @XmlTransient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.academico.garagem.model.entity.Vehicle[ id=" + id + " ]";
    }

}
