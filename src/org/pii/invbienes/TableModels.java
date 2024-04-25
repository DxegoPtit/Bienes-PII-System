/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pii.invbienes;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.pii.invbienes.controldb.SqlControllerClass;

/**
 *
 * @author PASANTIA
 */
public class TableModels {
    private DefaultTableModel dtm;
    private SqlControllerClass scc = new SqlControllerClass();
    
    public DefaultTableModel modeloInventarioByEntidad(String ID){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("DESCRIPCION");
            columnas.add("ESTADO");
            columnas.add("STATUS");
            columnas.add("ASIGNADO A");
            columnas.add("UBICACION");
            columnas.add("SECTOR");
            columnas.add("UNIDAD");
            columnas.add("SERVICIO");
            columnas.add("FECHA INVENTARIADO");
        Vector<Vector<Object>> data = scc.dataInventarioByEntidad(ID);
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
    
    public DefaultTableModel modeloInventarioBySector(String ID){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("DESCRIPCION");
            columnas.add("ESTADO");
            columnas.add("STATUS");
            columnas.add("ASIGNADO A");
            columnas.add("UBICACION");
            columnas.add("UNIDAD");
            columnas.add("SERVICIO");
            columnas.add("FECHA INVENTARIADO");
        Vector<Vector<Object>> data = scc.dataInventarioBySector(ID);
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
    
    public DefaultTableModel modeloInventarioByUnidades(String ID){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("DESCRIPCION");
            columnas.add("ESTADO");
            columnas.add("STATUS");
            columnas.add("ASIGNADO A");
            columnas.add("UBICACION");
            columnas.add("SERVICIO");
            columnas.add("FECHA INVENTARIADO");
        Vector<Vector<Object>> data = scc.dataInventarioByUnidades(ID);
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
    
    public DefaultTableModel modeloInventarioByServicios(String ID){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("DESCRIPCION");
            columnas.add("ESTADO");
            columnas.add("STATUS");
            columnas.add("ASIGNADO A");
            columnas.add("UBICACION");
            columnas.add("FECHA INVENTARIADO");
        Vector<Vector<Object>> data = scc.dataInventarioByServicios(ID);
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
    
    public DefaultTableModel modeloInventarioByAll(){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("DESCRIPCION");
            columnas.add("ESTADO");
            columnas.add("STATUS");
            columnas.add("ASIGNADO A");
            columnas.add("UBICACION");
            columnas.add("ENTIDAD");
            columnas.add("SECTOR");
            columnas.add("UNIDAD");
            columnas.add("SERVICIO");
            columnas.add("FECHA INVENTARIADO");
        Vector<Vector<Object>> data = scc.dataInventarioByAll();
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
}
