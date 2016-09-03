/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Business.Configs;

import LibData.Business.GlobalConfigs;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author Limited
 */
public class InventoryConfigs {

    public static final int INVENTORY_STATUS_UPTODATE = 100;
    public static final int INVENTORY_STATUS_SAVED = 200;

    public static final String INVENTORY_STATUS_UPTODATE_VALUE = "Mới nhất";
    public static final String INVENTORY_STATUS_SAVED_VALUE = "Bản lưu";

    public static final int INVENTORY_TYPE_IN = 100;
    public static final int INVENTORY_TYPE_OUT = 101;
    public static final int INVENTORY_TYPE_UPDATE = 102;
    public static final int INVENTORY_TYPE_INIT = 200;

    public static final String INVENTORY_TYPE_IN_VALUE = "Nhập kho";
    public static final String INVENTORY_TYPE_OUT_VALUE = "Xuất kho";
    public static final String INVENTORY_TYPE_UPDATE_VALUE = "Kiểm kê";
    public static final String INVENTORY_TYPE_INIT_VALUE = "Khởi tạo";
    
    public static final String INVENTORY_UNIT_TYPE_UNDEFINED = "Không rõ";
    public static final String INVENTORY_UNIT_TYPE_BOOK = "Quyển";

    public static Dictionary<Integer, String> getInventoryStatusDictionary() {
        Dictionary<Integer, String> dictionary = new Hashtable<Integer, String>();

        dictionary.put(GlobalConfigs.CONST_UNDEFINED_KEY, GlobalConfigs.CONST_UNDEFINED_VALUE);
        dictionary.put(INVENTORY_STATUS_UPTODATE, INVENTORY_STATUS_UPTODATE_VALUE);
        dictionary.put(INVENTORY_STATUS_SAVED, INVENTORY_STATUS_SAVED_VALUE);

        return dictionary;
    }

    public static String getInventoryStatus(int key) {
        return GlobalConfigs.getValueFromDictionary(key, getInventoryStatusDictionary(), GlobalConfigs.CONST_UNDEFINED_KEY);
    }

    public static Dictionary<Integer, String> getInventoryTypeDictionary() {
        Dictionary<Integer, String> dictionary = new Hashtable<Integer, String>();

        dictionary.put(GlobalConfigs.CONST_UNDEFINED_KEY, GlobalConfigs.CONST_UNDEFINED_VALUE);
        dictionary.put(INVENTORY_TYPE_IN, INVENTORY_TYPE_IN_VALUE);
        dictionary.put(INVENTORY_TYPE_OUT, INVENTORY_TYPE_OUT_VALUE);
        dictionary.put(INVENTORY_TYPE_UPDATE, INVENTORY_TYPE_UPDATE_VALUE);
        dictionary.put(INVENTORY_TYPE_INIT, INVENTORY_TYPE_INIT_VALUE);

        return dictionary;
    }

    public static String getInventoryType(int key) {
        return GlobalConfigs.getValueFromDictionary(key, getInventoryTypeDictionary(), GlobalConfigs.CONST_UNDEFINED_KEY);
    }

}
