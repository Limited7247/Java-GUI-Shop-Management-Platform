/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities;

/**
 *
 * @author Limited
 */
public interface IProvider {
    public boolean Insert(Object object);
    public boolean Delete(String id);
    public boolean Update(Object object);
}
