/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LibData.JPAControllers.OrderLineJpaController;
import LibData.Models.Inventory;
import LibData.Models.OrderLine;
import LibData.Models.Orders;
import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.jinq.jpa.JPAJinqStream;

/**
 *
 * @author Limited
 */
public class OrderLineProvider implements IProvider {

    private OrderLineJpaController _jpaOrderLine = new OrderLineJpaController(ProviderHelper.getEntityManagerFactory());

    private OrderLineJpaController getJPAOrderLine() {
        ProviderHelper.RefreshDatabase(_jpaOrderLine.getEntityManager());
        return _jpaOrderLine;
    }

    private JPAJinqStream<OrderLine> getJinqOrderLine() {
        return ProviderHelper.getJinqStream().streamAll(ProviderHelper.getEntityManager(), OrderLine.class);
    }

    private InventoryProvider _inventoryProvider = new InventoryProvider();

    public boolean Insert(List<OrderLine> orderLines, Orders order) {
        try {
            for (OrderLine orderLine : orderLines) {
                orderLine.setOrderId(order);
                orderLine.setCreateBy(order.getCreateBy());

                if (!Insert(orderLine)) {
                    return false;
                }

                if (!_inventoryProvider.InventoryOut(
                        _inventoryProvider.getInitInventoryByProductId(orderLine.getProductId().getId()).getId(),
                        orderLine.getQuantity(),
                        order.getCreateBy()
                )) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean Insert(Object object) {
        try {
            OrderLine line = (OrderLine) object;
            line.setId(UUID.randomUUID().toString());
            line.setCreateTime(new Date());

            getJPAOrderLine().create(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean Delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean Update(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
