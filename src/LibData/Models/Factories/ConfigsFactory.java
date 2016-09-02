/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Models.Factories;

import LibData.Models.Configs;

/**
 *
 * @author Limited
 */
public class ConfigsFactory {
    public static Configs createConfigs(String key, String value)
    {
        Configs config = new Configs();
        
        config.setIdKey(key);
        config.setValue(value);
        
        return config;
    }
}
