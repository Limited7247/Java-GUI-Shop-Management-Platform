/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Limited
 */
@Entity
@Table(name = "Inventory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i"),
    @NamedQuery(name = "Inventory.findById", query = "SELECT i FROM Inventory i WHERE i.id = :id"),
    @NamedQuery(name = "Inventory.findByIdCode", query = "SELECT i FROM Inventory i WHERE i.idCode = :idCode"),
    @NamedQuery(name = "Inventory.findByUnit", query = "SELECT i FROM Inventory i WHERE i.unit = :unit"),
    @NamedQuery(name = "Inventory.findByQuantity", query = "SELECT i FROM Inventory i WHERE i.quantity = :quantity"),
    @NamedQuery(name = "Inventory.findByCreateTime", query = "SELECT i FROM Inventory i WHERE i.createTime = :createTime"),
    @NamedQuery(name = "Inventory.findByStatus", query = "SELECT i FROM Inventory i WHERE i.status = :status"),
    @NamedQuery(name = "Inventory.findByType", query = "SELECT i FROM Inventory i WHERE i.type = :type")})
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Basic(optional = false)
    @Column(name = "IdCode")
    private String idCode;
    @Basic(optional = false)
    @Column(name = "Unit")
    private String unit;
    @Basic(optional = false)
    @Column(name = "Quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "CreateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic(optional = false)
    @Column(name = "Status")
    private int status;
    @Basic(optional = false)
    @Column(name = "Type")
    private int type;
    @JoinTable(name = "OrderLineInventoryLine", joinColumns = {
        @JoinColumn(name = "InventoryId", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "OrderLineId", referencedColumnName = "Id")})
    @ManyToMany
    private Collection<OrderLine> orderLineCollection;
    @JoinColumn(name = "CreateBy", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Account createBy;
    @JoinColumn(name = "ProductId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Product productId;

    public Inventory() {
    }

    public Inventory(String id) {
        this.id = id;
    }

    public Inventory(String id, String idCode, String unit, int quantity, Date createTime, int status, int type) {
        this.id = id;
        this.idCode = idCode;
        this.unit = unit;
        this.quantity = quantity;
        this.createTime = createTime;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<OrderLine> getOrderLineCollection() {
        return orderLineCollection;
    }

    public void setOrderLineCollection(Collection<OrderLine> orderLineCollection) {
        this.orderLineCollection = orderLineCollection;
    }

    public Account getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Account createBy) {
        this.createBy = createBy;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LibData.Models.Inventory[ id=" + id + " ]";
    }
    
}
