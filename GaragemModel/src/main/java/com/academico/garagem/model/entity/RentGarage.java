/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.entity;

import com.academico.garagem.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mathe
 */
@Entity
@Table(name = "rent_garage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RentGarage.findAll", query = "SELECT r FROM RentGarage r"),
    @NamedQuery(name = "RentGarage.findById", query = "SELECT r FROM RentGarage r WHERE r.id = :id"),
    @NamedQuery(name = "RentGarage.findByDateTime", query = "SELECT r FROM RentGarage r WHERE r.dateTime = :dateTime"),
    @NamedQuery(name = "RentGarage.findByIsAuth", query = "SELECT r FROM RentGarage r WHERE r.isAuth = :isAuth"),
    @NamedQuery(name = "RentGarage.findByInitialDateTime", query = "SELECT r FROM RentGarage r WHERE r.initialDateTime = :initialDateTime"),
    @NamedQuery(name = "RentGarage.findByFinalDateTime", query = "SELECT r FROM RentGarage r WHERE r.finalDateTime = :finalDateTime")})
public class RentGarage extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateTime;
    @Column(name = "is_auth")
    private Boolean isAuth;
    @Column(name = "initial_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date initialDateTime;
    @Column(name = "final_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date finalDateTime;
    @JsonBackReference
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Advertisement advertisementId;
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehicle vehicleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rentGarage")
    private List<Rating> ratingList;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rentGarageId")
    private List<Message> messageList;

    public RentGarage() {
    }

    public RentGarage(Integer id) {
        this.id = id;
    }

    public RentGarage(Integer id, Date dateTime) {
        this.id = id;
        this.dateTime = dateTime;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }

    public Date getInitialDateTime() {
        return initialDateTime;
    }

    public void setInitialDateTime(Date initialDateTime) {
        this.initialDateTime = initialDateTime;
    }

    public Date getFinalDateTime() {
        return finalDateTime;
    }

    public void setFinalDateTime(Date finalDateTime) {
        this.finalDateTime = finalDateTime;
    }

    public Advertisement getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Advertisement advertisementId) {
        this.advertisementId = advertisementId;
    }

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
    }

    @XmlTransient
    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    @XmlTransient
    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
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
        if (!(object instanceof RentGarage)) {
            return false;
        }
        RentGarage other = (RentGarage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.academico.garagem.model.entity.RentGarage[ id=" + id + " ]";
    }

}
