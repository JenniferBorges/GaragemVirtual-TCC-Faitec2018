/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.entity;

import com.academico.garagem.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mathe
 */
@Entity
@Table(name = "user_phone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPhone.findAll", query = "SELECT u FROM UserPhone u"),
    @NamedQuery(name = "UserPhone.findById", query = "SELECT u FROM UserPhone u WHERE u.id = :id"),
    @NamedQuery(name = "UserPhone.findByNumber", query = "SELECT u FROM UserPhone u WHERE u.number = :number"),
    @NamedQuery(name = "UserPhone.deleteByUserId", query = "DELETE FROM UserPhone u WHERE u.userId.id = :userId")})
public class UserPhone extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Formato de telefone/fax inválido, deve ser xxx-xxx-xxxx")//if the field contains number or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "number")
    private String number;
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public UserPhone() {
    }

    public UserPhone(Integer id) {
        this.id = id;
    }

    public UserPhone(Integer id, String number) {
        this.id = id;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        if (!(object instanceof UserPhone)) {
            return false;
        }
        UserPhone other = (UserPhone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.academico.garagem.model.entity.UserPhone[ id=" + id + " ]";
    }

}
