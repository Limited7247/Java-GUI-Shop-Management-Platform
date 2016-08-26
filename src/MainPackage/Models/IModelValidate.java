/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models;

import java.util.List;

/**
 *
 * @author Limited
 */
public interface IModelValidate {
    public boolean IsValidate();

    public String MessageValidate();

    public String ToLogString();

    ///public List<Object> ToListActionModel();
}
