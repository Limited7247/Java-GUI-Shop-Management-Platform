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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "Orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByIdCode", query = "SELECT o FROM Orders o WHERE o.idCode = :idCode"),
    @NamedQuery(name = "Orders.findByCreateTime", query = "SELECT o FROM Orders o WHERE o.createTime = :createTime"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByGuestName", query = "SELECT o FROM Orders o WHERE o.guestName = :guestName"),
    @NamedQuery(name = "Orders.findByGuestAddress", query = "SELECT o FROM Orders o WHERE o.guestAddress = :guestAddress"),
    @NamedQuery(name = "Orders.findByGuestPhone", query = "SELECT o FROM Orders o WHERE o.guestPhone = :guestPhone"),
    @NamedQuery(name = "Orders.findByGuestEmail", query = "SELECT o FROM Orders o WHERE o.guestEmail = :guestEmail"),
    @NamedQuery(name = "Orders.findByTotalPrice", query = "SELECT o FROM Orders o WHERE o.totalPrice = :totalPrice"),
    @NamedQuery(name = "Orders.findByDiscount", query = "SELECT o FROM Orders o WHERE o.discount = :discount"),
    @NamedQuery(name = "Orders.findByVATPrice", query = "SELECT o FROM Orders o WHERE o.vATPrice = :vATPrice"),
    @NamedQuery(name = "Orders.findByPaidPrice", query = "SELECT o FROM Orders o WHERE o.paidPrice = :paidPrice"),
    @NamedQuery(name = "Orders.findByDetails", query = "SELECT o FROM Orders o WHERE o.details = :details")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Basic(optional = false)
    @Column(name = "IdCode")
    private String idCode;
    @Basic(optional = false)
    @Column(name = "CreateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic(optional = false)
    @Column(name = "Status")
    private int status;
    @Basic(optional = false)
    @Column(name = "GuestName")
    private String guestName;
    @Column(name = "GuestAddress")
    private String guestAddress;
    @Column(name = "GuestPhone")
    private String guestPhone;
    @Column(name = "GuestEmail")
    private String guestEmail;
    @Basic(optional = false)
    @Column(name = "TotalPrice")
    private long totalPrice;
    @Basic(optional = false)
    @Column(name = "Discount")
    private long discount;
    @Basic(optional = false)
    @Column(name = "VATPrice")
    private long vATPrice;
    @Basic(optional = false)
    @Column(name = "PaidPrice")
    private long paidPrice;
    @Column(name = "Details")
    private String details;
    @JoinColumn(name = "CreateBy", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Account createBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<OrderLine> orderLineCollection;

    public Orders() {
    }

    public Orders(String id) {
        this.id = id;
    }

    public Orders(String id, String idCode, Date createTime, int status, String guestName, long totalPrice, long discount, long vATPrice, long paidPrice) {
        this.id = id;
        this.idCode = idCode;
        this.createTime = createTime;
        this.status = status;
        this.guestName = guestName;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.vATPrice = vATPrice;
        this.paidPrice = paidPrice;
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

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestAddress() {
        return guestAddress;
    }

    public void setGuestAddress(String guestAddress) {
        this.guestAddress = guestAddress;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Account getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Account createBy) {
        this.createBy = createBy;
    }

    @XmlTransient
    public Collection<OrderLine> getOrderLineCollection() {
        return orderLineCollection;
    }

    public void setOrderLineCollection(Collection<OrderLine> orderLineCollection) {
        this.orderLineCollection = orderLineCollection;
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LibData.Models.Orders[ id=" + id + " ]";
    }
    
}
