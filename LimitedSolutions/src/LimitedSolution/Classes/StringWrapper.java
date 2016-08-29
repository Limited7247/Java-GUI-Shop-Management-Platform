/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LimitedSolution.Classes;

/**
 *
 * @author Limited
 */
public class StringWrapper {

    public String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void appendString(String string) {
        this.string += string;
    }
    
    public StringWrapper() {
        this.string = "";
    }

    public StringWrapper(String string) {
        this.string = string;
    }
    
    public StringWrapper(StringWrapper stringWrapper)
    {
        this.string = stringWrapper.getString();
    }

}
