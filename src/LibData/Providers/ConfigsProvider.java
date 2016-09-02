/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import LibData.JPAControllers.ConfigsJpaController;
import LibData.Models.Configs;
import static LibData.Models.Factories.ConfigsFactory.createConfigs;
import java.util.Date;
import java.util.List;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

/**
 *
 * @author Limited
 */
public class ConfigsProvider implements IProvider {
    private ConfigsJpaController jpaConfigs = new ConfigsJpaController(ProviderHelper.getEntityManagerFactory());
    
    private ConfigsJpaController getJPAConfigs()
    {
        return jpaConfigs;
    }
    
    private JPAJinqStream<Configs> getJinqConfigs()
    {
        return ProviderHelper.getJinqStream().streamAll(ProviderHelper.getEntityManager(), Configs.class);
    }
    
    public List<Configs> getAll()
    {
        try {
            return getJinqConfigs().sortedDescendingBy(m -> m.getCreateTime()).toList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public int count()
    {
        try {
            return getAll().size();
        } catch (Exception e) {
            return -1;
        }
    }
    
    public Configs getByKey(String Key)
    {
        try {
            return jpaConfigs.findConfigs(Key);
        } catch (Exception e) {
            return null;
        }
    }
    
    public String getValueByKey(String Key)
    {
        try {
            return getByKey(Key).getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean IncreaseValueByKey(String key)
    {
        try {
            Configs config = getByKey(key);
            if (config == null)
            {
                Insert(createConfigs(key, "0"));
            }
            
            long value = Long.parseLong(config.getValue()) + 1;
            config.setValue(value + "");
            Update(config);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     *
     * @param A Configs object was set key and value
     * @return
     */
    @Override
    public boolean Insert(Object object) {
        try {
            Configs config = (Configs) object;
            config.setCreateTime(new Date());
            getJPAConfigs().create(config);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param A Configs object was set key
     * @return
     */
    @Override
    public boolean Delete(String id) {
        try {
            getJPAConfigs().destroy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param A Configs object was set key and new value
     * @return
     */
    @Override
    public boolean Update(Object object) {
        try {
            Configs config = getJPAConfigs().findConfigs(((Configs) object).getIdKey());
            config.setValue(((Configs) object).getValue());
            getJPAConfigs().edit(config);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
