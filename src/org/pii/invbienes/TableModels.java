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
    
    //--------------------------------------------------------------------------------//
    
    public DefaultTableModel modeloIncorporacionesByEntidad(String[] dataFiltro){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("CONCEPTO");
            columnas.add("DESCRIPCION");
            columnas.add("COSTO ADQ.");
            columnas.add("Nº FACTURA");
            columnas.add("FECHA INVENTARIADO");
            /*
            
            row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
            
            */
        Vector<Vector<Object>> data = scc.dataIncorporacionesByEntidad(dataFiltro[0], dataFiltro[1]);
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
    
    public DefaultTableModel modeloIncorporacionesBySector(String ID){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
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
    
    public DefaultTableModel modeloIncorporacionesByUnidades(String ID){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
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
    
    public DefaultTableModel modeloIncorporacionesByServicios(String ID){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("DESCRIPCION");
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
    
    public DefaultTableModel modeloIncorporacionesByAll(){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("CONCEPTO");
            columnas.add("DESCRIPCION");
            columnas.add("COSTO ADQUSICION");
            columnas.add("Nº FACTURA");
            columnas.add("FECHA INVENTARIADO");
        Vector<Vector<Object>> data = scc.dataIncorporacionesByAll();
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
    /*
    public DefaultTableModel filterIncorporacionesByAll(String CONC){
        Vector<String> columnas = new Vector<>();
            columnas.add("NUMERO DE BIEN");
            columnas.add("CLASIFICACION");
            columnas.add("CONCEPTO");
            columnas.add("DESCRIPCION");
            columnas.add("COSTO ADQUSICION");
            columnas.add("Nº FACTURA");
            columnas.add("FECHA INVENTARIADO");
        Vector<Vector<Object>> data = scc.filterIncorporacionesByAll(CONC);
        
        dtm = new DefaultTableModel(data, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        
        return dtm;
    }
*/
}
