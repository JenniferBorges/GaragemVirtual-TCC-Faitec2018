/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author mathe
 */
@Embeddable
public class RatingPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "rent_garage_id")
    private int rentGarageId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;

    public RatingPK() {
    }

    public RatingPK(int rentGarageId, int userId) {
        this.rentGarageId = rentGarageId;
        this.userId = userId;
    }

    public int getRentGarageId() {
        return rentGarageId;
    }

    public void setRentGarageId(int rentGarageId) {
        this.rentGarageId = rentGarageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rentGarageId;
        hash += (int) userId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RatingPK)) {
            return false;
        }
        RatingPK other = (RatingPK) object;
        if (this.rentGarageId != other.rentGarageId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.academico.garagem.model.entity.RatingPK[ rentGarageId=" + rentGarageId + ", userId=" + userId + " ]";
    }

}
