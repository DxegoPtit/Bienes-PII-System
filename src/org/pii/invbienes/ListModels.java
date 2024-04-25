/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pii.invbienes;

import javax.swing.DefaultComboBoxModel;
import org.pii.invbienes.controldb.SqlControllerClass;

/**
 *
 * @author PASANTIA
 */
public class ListModels {
    
    private DefaultComboBoxModel dcm;
    private SqlControllerClass scc = new SqlControllerClass();
    
    public DefaultComboBoxModel getFilterEntidades(){
        dcm = scc.model("entidades");
        return dcm;
    }
    
    public DefaultComboBoxModel getFilterSectores(){
        dcm = scc.model("sectores");
        return dcm;
    }
    
    public DefaultComboBoxModel getFilterUnidades(){
        dcm = scc.model("unidades");
        return dcm;
    }
    
    public DefaultComboBoxModel getFilterServicios(){
        dcm = scc.model("Servicios");
        return dcm;
    }
}
