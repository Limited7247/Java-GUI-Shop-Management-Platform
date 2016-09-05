/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LibData.JPAControllers.OrdersJpaController;
import LibData.Models.OrderLine;
import LibData.Models.Orders;
import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jinq.jpa.JPAJinqStream;

/**
 *
 * @author Limited
 */
public class OrdersProvider implements IProvider {

    private OrdersJpaController jpaOrders = new OrdersJpaController(ProviderHelper.getEntityManagerFactory());

    private OrdersJpaController getJPAOrders() {
        ProviderHelper.RefreshDatabase(jpaOrders.getEntityManager());
        return jpaOrders;
    }

    private JPAJinqStream<Orders> getJinqOrders() {
        return ProviderHelper.getJinqStream().streamAll(ProviderHelper.getEntityManager(), Orders.class);
    }

    private void showLog(Exception ex) {
        Logger.getLogger(BookProvider.class.getName()).log(Level.SEVERE, null, ex);
    }

    public String getNewIdCode() {
        return new ConfigsProvider().getNewIdCode("O");
    }

    public List<Orders> getAll() {
        try {
            return getJinqOrders().sortedDescendingBy(m -> m.getCreateTime()).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean Insert(Object object) {
        try {
            Orders order = (Orders) object;
            order.setId(UUID.randomUUID().toString());
            order.setIdCode(getNewIdCode());
            order.setCreateTime(new Date());

            List<OrderLine> orderLines = (List<OrderLine>) order.getOrderLineCollection();
            order.setOrderLineCollection(new ArrayList<OrderLine>());
            
            getJPAOrders().create(order);
            new OrderLineProvider().Insert(orderLines, order);
            
            return true;
        } catch (Exception e) {
            showLog(e);
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

    public Orders getById(String id) {
        try {
            return getJPAOrders().findOrders(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Orders> Find(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
