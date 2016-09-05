/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Orders;

import LibData.Models.OrderLine;
import LibData.Models.Orders;
import static LimitedSolution.Utilities.CurrencyHelper.VNDToInt;
import LimitedSolution.Utilities.ModelHelper.IModelValidate;
import java.util.List;

/**
 *
 * @author Limited
 */
public class NewOrderViewModel implements IModelValidate {

    public List<OrderLine> OrderLines;

    public String TotalPrice;
    public String VATPrice;
    public String DiscountPrice;
    public String PaidPrice;

    public String GuestName;
    public String GuestAddress;
    public String GuestPhone;
    public String GuestEmail;

    public String Details;

    public NewOrderViewModel(List<OrderLine> OrderLines, String TotalPrice, String VATPrice, String DiscountPrice, String PaidPrice, String GuestName, String GuestAddress, String GuestPhone, String GuestEmail, String Details) {
        this.OrderLines = OrderLines;
        this.TotalPrice = TotalPrice;
        this.VATPrice = VATPrice;
        this.DiscountPrice = DiscountPrice;
        this.PaidPrice = PaidPrice;
        this.GuestName = GuestName;
        this.GuestAddress = GuestAddress;
        this.GuestPhone = GuestPhone;
        this.GuestEmail = GuestEmail;
        this.Details = Details;
    }

    public Orders getOrders() {
        if (this.IsValidate()) {
            Orders order = new Orders();

            order.setOrderLineCollection(OrderLines);
            
            order.setTotalPrice(VNDToInt(this.TotalPrice));
            order.setVATPrice(VNDToInt(this.VATPrice));
            order.setDiscount(VNDToInt(this.DiscountPrice));
            order.setPaidPrice(VNDToInt(this.PaidPrice));
            
            order.setGuestName(GuestName);
            order.setGuestAddress(GuestAddress);
            order.setGuestPhone(GuestPhone);
            order.setGuestEmail(GuestEmail);
            order.setDetails(Details);

            return order;
        }

        return null;
    }

    @Override
    public boolean IsValidate() {
        if (this.OrderLines == null || this.OrderLines.size() <= 0) {
            return false;
        }

        if (VNDToInt(this.TotalPrice) == null) {
            return false;
        }

        if (VNDToInt(this.TotalPrice) <= 0) {
            return false;
        }

        if (VNDToInt(this.VATPrice) == null) {
            return false;
        }

        if (VNDToInt(this.VATPrice) <= 0) {
            return false;
        }

        if (this.DiscountPrice != null && !this.DiscountPrice.isEmpty() && VNDToInt(this.DiscountPrice) == null) {
            return false;
        }
        if (this.DiscountPrice != null && !this.DiscountPrice.isEmpty() && VNDToInt(this.DiscountPrice) < 0) {
            return false;
        }

        if (VNDToInt(this.PaidPrice) == null || VNDToInt(this.PaidPrice) <= 0) {
            return false;
        }

        if (this.GuestName == null || this.GuestName.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public String MessageValidate() {
        String warning = "";

        if (this.OrderLines == null || this.OrderLines.size() <= 0) {
            warning += "Hóa đơn trống;" + '\n';
        }

        if (VNDToInt(this.TotalPrice) == null || VNDToInt(this.TotalPrice) <= 0) {
            warning += "Tổng tiền không hợp lệ;" + '\n';
        }
        if (VNDToInt(this.VATPrice) == null || VNDToInt(this.VATPrice) <= 0) {
            warning += "Thuế VAT không hợp lệ;" + '\n';
        }

        if (this.DiscountPrice != null && !this.DiscountPrice.isEmpty() && VNDToInt(this.DiscountPrice) == null) {
            warning += "Khuyến mãi không hợp lệ;" + '\n';
        }
        if (this.DiscountPrice != null && !this.DiscountPrice.isEmpty() && VNDToInt(this.DiscountPrice) != null && VNDToInt(this.DiscountPrice) < 0) {
            warning += "Khuyến mãi không hợp lệ;" + '\n';
        }

        if (VNDToInt(this.PaidPrice) == null || VNDToInt(this.PaidPrice) <= 0) {
            warning += "Thanh toán không hợp lệ;" + '\n';
        }

        if (this.GuestName == null || this.GuestName.isEmpty()) {
            warning += "Vui lòng nhập tên khách." + '\n';
        }

        return warning;
    }

    @Override
    public String ToLogString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
