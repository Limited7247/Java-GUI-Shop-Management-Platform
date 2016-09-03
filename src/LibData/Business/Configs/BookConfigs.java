/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Business.Configs;

import LibData.Business.GlobalConfigs;
import LimitedSolution.Utilities.LibDataUtilities.BusinessConfigs.AbstractGlobalConfigs;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author Limited
 */
public class BookConfigs {

    public static final int BOOK_STATUS_ACTIVE = 100;
    public static final int BOOK_STATUS_INACTIVE = 0;

    public static final String BOOK_STATUS_ACTIVE_VALUE = "Sẵn sàng";
    public static final String BOOK_STATUS_INACTIVE_VALUE = "Tạm khóa";

    public static Dictionary<Integer, String> getBookStatusDictionary()
    {
        Dictionary<Integer, String> dictionary = new Hashtable<Integer, String>();
                
        dictionary.put(GlobalConfigs.CONST_UNDEFINED_KEY, GlobalConfigs.CONST_UNDEFINED_VALUE);
        dictionary.put(BOOK_STATUS_ACTIVE, BOOK_STATUS_ACTIVE_VALUE);
        dictionary.put(BOOK_STATUS_INACTIVE, BOOK_STATUS_INACTIVE_VALUE);
        
        return dictionary;
    }
    
    public static String getBookStatus(int key)
    {
        return GlobalConfigs.getValueFromDictionary(key, getBookStatusDictionary(), GlobalConfigs.CONST_UNDEFINED_KEY);
    }
    
}
