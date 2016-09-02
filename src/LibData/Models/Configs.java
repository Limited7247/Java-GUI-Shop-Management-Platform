/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Limited
 */
@Entity
@Table(name = "Configs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configs.findAll", query = "SELECT c FROM Configs c"),
    @NamedQuery(name = "Configs.findByIdKey", query = "SELECT c FROM Configs c WHERE c.idKey = :idKey"),
    @NamedQuery(name = "Configs.findByValue", query = "SELECT c FROM Configs c WHERE c.value = :value"),
    @NamedQuery(name = "Configs.findByCreateTime", query = "SELECT c FROM Configs c WHERE c.createTime = :createTime")})
public class Configs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdKey")
    private String idKey;
    @Basic(optional = false)
    @Column(name = "Value")
    private String value;
    @Basic(optional = false)
    @Column(name = "CreateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Configs() {
    }

    public Configs(String idKey) {
        this.idKey = idKey;
    }

    public Configs(String idKey, String value, Date createTime) {
        this.idKey = idKey;
        this.value = value;
        this.createTime = createTime;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKey != null ? idKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configs)) {
            return false;
        }
        Configs other = (Configs) object;
        if ((this.idKey == null && other.idKey != null) || (this.idKey != null && !this.idKey.equals(other.idKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LibData.Models.Configs[ idKey=" + idKey + " ]";
    }
    
}
