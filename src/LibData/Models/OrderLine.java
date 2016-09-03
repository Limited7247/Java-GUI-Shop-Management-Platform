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
@Table(name = "OrderLine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderLine.findAll", query = "SELECT o FROM OrderLine o"),
    @NamedQuery(name = "OrderLine.findById", query = "SELECT o FROM OrderLine o WHERE o.id = :id"),
    @NamedQuery(name = "OrderLine.findByQuantity", query = "SELECT o FROM OrderLine o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "OrderLine.findByUnitPrice", query = "SELECT o FROM OrderLine o WHERE o.unitPrice = :unitPrice"),
    @NamedQuery(name = "OrderLine.findByTotalPrice", query = "SELECT o FROM OrderLine o WHERE o.totalPrice = :totalPrice"),
    @NamedQuery(name = "OrderLine.findByDiscountPrice", query = "SELECT o FROM OrderLine o WHERE o.discountPrice = :discountPrice"),
    @NamedQuery(name = "OrderLine.findByVATPrice", query = "SELECT o FROM OrderLine o WHERE o.vATPrice = :vATPrice"),
    @NamedQuery(name = "OrderLine.findByPaidPrice", query = "SELECT o FROM OrderLine o WHERE o.paidPrice = :paidPrice"),
    @NamedQuery(name = "OrderLine.findByCreateTime", query = "SELECT o FROM OrderLine o WHERE o.createTime = :createTime")})
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Basic(optional = false)
    @Column(name = "Quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "UnitPrice")
    private long unitPrice;
    @Basic(optional = false)
    @Column(name = "TotalPrice")
    private long totalPrice;
    @Basic(optional = false)
    @Column(name = "DiscountPrice")
    private long discountPrice;
    @Basic(optional = false)
    @Column(name = "VATPrice")
    private long vATPrice;
    @Basic(optional = false)
    @Column(name = "PaidPrice")
    private long paidPrice;
    @Basic(optional = false)
    @Column(name = "CreateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToMany(mappedBy = "orderLineCollection")
    private Collection<Inventory> inventoryCollection;
    @JoinColumn(name = "CreateBy", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Account createBy;
    @JoinColumn(name = "OrderId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Orders orderId;
    @JoinColumn(name = "ProductId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Product productId;

    public OrderLine() {
    }

    public OrderLine(String id) {
        this.id = id;
    }

    public OrderLine(String id, int quantity, long unitPrice, long totalPrice, long discountPrice, long vATPrice, long paidPrice, Date createTime) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.vATPrice = vATPrice;
        this.paidPrice = paidPrice;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public long getVATPrice() {
        return vATPrice;
    }

    public void setVATPrice(long vATPrice) {
        this.vATPrice = vATPrice;
    }

    public long getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(long paidPrice) {
        this.paidPrice = paidPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @XmlTransient
    public Collection<Inventory> getInventoryCollection() {
        return inventoryCollection;
    }

    public void setInventoryCollection(Collection<Inventory> inventoryCollection) {
        this.inventoryCollection = inventoryCollection;
    }

    public Account getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Account createBy) {
        this.createBy = createBy;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
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
        if (!(object instanceof OrderLine)) {
            return false;
        }
        OrderLine other = (OrderLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LibData.Models.OrderLine[ id=" + id + " ]";
    }
    
}
