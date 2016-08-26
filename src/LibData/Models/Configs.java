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
    @NamedQuery(name = "Configs.findByKey", query = "SELECT c FROM Configs c WHERE c.key = :key"),
    @NamedQuery(name = "Configs.findByValue", query = "SELECT c FROM Configs c WHERE c.value = :value"),
    @NamedQuery(name = "Configs.findByCreateTime", query = "SELECT c FROM Configs c WHERE c.createTime = :createTime")})
public class Configs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Key")
    private String key;
    @Basic(optional = false)
    @Column(name = "Value")
    private String value;
    @Basic(optional = false)
    @Column(name = "CreateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Configs() {
    }

    public Configs(String key) {
        this.key = key;
    }

    public Configs(String key, String value, Date createTime) {
        this.key = key;
        this.value = value;
        this.createTime = createTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        hash += (key != null ? key.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configs)) {
            return false;
        }
        Configs other = (Configs) object;
        if ((this.key == null && other.key != null) || (this.key != null && !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LibData.Models.Configs[ key=" + key + " ]";
    }
    
}
