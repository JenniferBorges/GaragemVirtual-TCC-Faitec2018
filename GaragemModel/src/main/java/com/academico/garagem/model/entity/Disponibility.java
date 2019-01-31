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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mathe
 */
@Entity
@Table(name = "disponibility")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disponibility.findAll", query = "SELECT d FROM Disponibility d"),
    @NamedQuery(name = "Disponibility.findById", query = "SELECT d FROM Disponibility d WHERE d.id = :id"),
    @NamedQuery(name = "Disponibility.findByDay", query = "SELECT d FROM Disponibility d WHERE d.day = :day"),
    @NamedQuery(name = "Disponibility.findByStartsAt", query = "SELECT d FROM Disponibility d WHERE d.startsAt = :startsAt"),
    @NamedQuery(name = "Disponibility.findByEndsAt", query = "SELECT d FROM Disponibility d WHERE d.endsAt = :endsAt"),
    @NamedQuery(name = "Disponibility.deleteByAdvertisementId", query = "DELETE FROM Disponibility d WHERE d.advertisementId.id = :advertisementId")})
public class Disponibility extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    private int day;
    @Basic(optional = false)
    @NotNull
    @Column(name = "starts_at")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date startsAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ends_at")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date endsAt;
    @JsonBackReference
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Advertisement advertisementId;

    public Disponibility() {
    }

    public Disponibility(Integer id) {
        this.id = id;
    }

    public Disponibility(Integer id, int day, Date startsAt, Date endsAt) {
        this.id = id;
        this.day = day;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Date startsAt) {
        this.startsAt = startsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public Advertisement getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Advertisement advertisementId) {
        this.advertisementId = advertisementId;
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
        if (!(object instanceof Disponibility)) {
            return false;
        }
        Disponibility other = (Disponibility) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.academico.garagem.model.entity.Disponibility[ id=" + id + " ]";
    }

}
