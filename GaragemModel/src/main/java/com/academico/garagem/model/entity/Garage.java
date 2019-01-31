/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.entity;

import com.academico.garagem.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "garage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Garage.findAll", query = "SELECT g FROM Garage g"),
    @NamedQuery(name = "Garage.findById", query = "SELECT g FROM Garage g WHERE g.id = :id"),
    @NamedQuery(name = "Garage.findByHeight", query = "SELECT g FROM Garage g WHERE g.height = :height"),
    @NamedQuery(name = "Garage.findByWidth", query = "SELECT g FROM Garage g WHERE g.width = :width"),
    @NamedQuery(name = "Garage.findByLength", query = "SELECT g FROM Garage g WHERE g.length = :length"),
    @NamedQuery(name = "Garage.findByAccess", query = "SELECT g FROM Garage g WHERE g.access = :access"),
    @NamedQuery(name = "Garage.findByHasRoof", query = "SELECT g FROM Garage g WHERE g.hasRoof = :hasRoof"),
    @NamedQuery(name = "Garage.findByHasCam", query = "SELECT g FROM Garage g WHERE g.hasCam = :hasCam"),
    @NamedQuery(name = "Garage.findByHasIndemnity", query = "SELECT g FROM Garage g WHERE g.hasIndemnity = :hasIndemnity"),
    @NamedQuery(name = "Garage.findByHasElectronicGate", query = "SELECT g FROM Garage g WHERE g.hasElectronicGate = :hasElectronicGate")})
public class Garage extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "height")
    private double height;
    @Basic(optional = false)
    @NotNull
    @Column(name = "width")
    private double width;
    @Basic(optional = false)
    @NotNull
    @Column(name = "length")
    private double length;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "access")
    private String access;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_roof")
    private boolean hasRoof;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_cam")
    private boolean hasCam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_indemnity")
    private boolean hasIndemnity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_electronic_gate")
    private boolean hasElectronicGate;
    @OneToMany(mappedBy = "garageId")
    private List<Image> imageList;
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address addressId;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "garageId")
    private List<Advertisement> advertisementList;

    public Garage() {
    }

    public Garage(Integer id) {
        this.id = id;
    }

    public Garage(Integer id, double height, double width, double length, String access, boolean hasRoof, boolean hasCam, boolean hasIndemnity, boolean hasElectronicGate) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.length = length;
        this.access = access;
        this.hasRoof = hasRoof;
        this.hasCam = hasCam;
        this.hasIndemnity = hasIndemnity;
        this.hasElectronicGate = hasElectronicGate;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public boolean getHasRoof() {
        return hasRoof;
    }

    public void setHasRoof(boolean hasRoof) {
        this.hasRoof = hasRoof;
    }

    public boolean getHasCam() {
        return hasCam;
    }

    public void setHasCam(boolean hasCam) {
        this.hasCam = hasCam;
    }

    public boolean getHasIndemnity() {
        return hasIndemnity;
    }

    public void setHasIndemnity(boolean hasIndemnity) {
        this.hasIndemnity = hasIndemnity;
    }

    public boolean getHasElectronicGate() {
        return hasElectronicGate;
    }

    public void setHasElectronicGate(boolean hasElectronicGate) {
        this.hasElectronicGate = hasElectronicGate;
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

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    @XmlTransient
    public List<Advertisement> getAdvertisementList() {
        return advertisementList;
    }

    public void setAdvertisementList(List<Advertisement> advertisementList) {
        this.advertisementList = advertisementList;
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
        if (!(object instanceof Garage)) {
            return false;
        }
        Garage other = (Garage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.academico.garagem.model.entity.Garage[ id=" + id + " ]";
    }

}
