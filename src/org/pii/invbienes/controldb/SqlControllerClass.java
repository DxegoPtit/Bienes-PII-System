/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pii.invbienes.controldb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PASANTIA
 */
public class SqlControllerClass {

    private Connection con;
    private DefaultTableModel dtm;
    private DefaultComboBoxModel dcm;

    private String ip, db, user, pwd;

    private void getProperties() {
        try {
            String rutaIni = new File(getClass().getResource("/org/pii/invbienes/settings.ini").getFile()).getAbsolutePath();
            Properties prop = new Properties();
            prop.load(new FileInputStream(rutaIni));

            ip = prop.getProperty("IP");
            db = prop.getProperty("DATABASE");
            user = prop.getProperty("USER");
            pwd = prop.getProperty("PWD");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setSettings(String ip, String db, String user, String pwd) {
        this.ip = ip;
        this.db = db;
        this.user = user;
        this.pwd = pwd;
    }

    public SqlControllerClass() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            getProperties();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection openCon() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db, user, pwd);
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private Connection closeCon() {
        try {
            con.close();
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String[] getSessionState(String usr, String pwd) {
        try {
            if (openCon() != null) {
                Statement stm = con.createStatement();
                ResultSet rst = stm.executeQuery("SELECT * from usuarios WHERE user = '" + usr + "' AND pwd = '" + pwd + "'");
                String datos[] = new String[2];

                if (rst.next()) {
                    datos[0] = rst.getString("nombres");
                    datos[1] = rst.getString("id");
                    return datos;
                } else {
                    return null;
                }
            } else {
                //fallo al conectar
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            closeCon();
        }
    }

    public Boolean setNewAccount(String name, String usr, String pwd) {
        try {
            if (openCon() != null) {
                Statement stm = con.createStatement();
                int a = stm.executeUpdate("INSERT INTO usuarios VALUES(0,'" + usr + "','" + pwd + "','" + name + "')");
                if (a != 0) {
                    return true; //retorna true si logro insertar
                } else {
                    return true; //retorna false si no logro insertar
                }
            } else {
                //fallo al conectar
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            closeCon();
        }
    }

    public Boolean setNewPasswd(String iduser, String cPwd, String nPwd, String nPwd2) {
        try {
            String pwd_v;

            if (cPwd.isEmpty() || nPwd.isEmpty() || nPwd2.isEmpty()) {
                return false; //si todos estan vacios entonces retorna falso
            } else {
                if (!nPwd.equals(nPwd2)) {
                    return false; //si no se parecen las dos contraseñas entonces retorna falso
                } else {
                    //proceso para cambiar contraseña
                    if (openCon() != null) {
                        Statement stm = con.createStatement();
                        ResultSet rst = stm.executeQuery("SELECT pwd FROM usuarios WHERE id = " + iduser);

                        if (rst.next()) {
                            pwd_v = rst.getString("pwd");

                            if (cPwd.equals(pwd_v)) {
                                int a = stm.executeUpdate("UPDATE usuarios SET pwd = " + nPwd + " WHERE id = " + iduser);

                                if (a != 0) {
                                    closeCon();
                                    return true; //retorna true si logro modificar
                                } else {
                                    closeCon();
                                    return true; //retorna false si no logro modificar
                                }
                            } else {
                                return false; //retorna false si no es igual
                            }

                        } else {
                            return false; //si no consiguió la contraseña segun id
                        }

                    } else {
                        System.err.println("ERROR WHILE SETTNG A CONECTION WITH THE DB");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public Vector dataInventarioByEntidad(String ID) {
        try {
            openCon();

            String sql = "SELECT xbienes.nbien AS nbien, xbienes.descripcion AS descripcion, "
                    + "xbienes.clasificacion AS clasificacion, xbienes.estado AS estado,"
                    + "xbienes.`status` AS `status`,trabajadores.nombre AS nombre, "
                    + "xbienes.ubicacion_asig AS ubicacion,  sectores.nombre AS sector, "
                    + "unidades.nombre AS unidad, servicios.nombre AS servicio, "
                    + "xbienes.fecha_inventariado AS fecha_inventariado "
                    + "FROM bienes AS xbienes INNER JOIN sectores ON xbienes.idSector = sectores.id "
                    + "INNER JOIN unidades ON xbienes.idUnidad = unidades.id "
                    + "INNER JOIN servicios ON xbienes.idServicio = servicios.id "
                    + "INNER JOIN entidades ON xbienes.idEntidad = entidades.id "
                    + "INNER JOIN trabajadores AS trabajadores ON xbienes.idtrabajador_asig = trabajadores.id "
                    + "WHERE entidades.id = " + ID;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nbien"));
                row.add(rs.getString("clasificacion"));
                row.add(rs.getString("descripcion"));
                row.add(rs.getString("estado"));
                row.add(rs.getString("status"));
                row.add(rs.getString("nombre"));
                row.add(rs.getString("ubicacion"));
                row.add(rs.getString("sector"));
                row.add(rs.getString("unidad"));
                row.add(rs.getString("servicio"));
                row.add(rs.getString("fecha_inventariado"));
                data.add(row);
            }
            
            return data;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            closeCon();
        }
    }
    
    public Vector dataInventarioBySector(String ID) {
        try {
            openCon();

            String sql = "SELECT xbienes.nbien AS nbien, "
                    + "xbienes.descripcion AS descripcion, "
                    + "xbienes.clasificacion AS clasificacion,"
                    + "xbienes.estado AS estado, "
                    + "xbienes.`status` AS `status`, "
                    + "trabajadores.nombre AS nombre,"
                    + "xbienes.ubicacion_asig AS ubicacion, "
                    + "unidades.nombre AS unidad, "
                    + "servicios.nombre AS servicio, "
                    + "xbienes.fecha_inventariado AS fecha_inventariado "
                    + "FROM bienes AS xbienes "
                    + "INNER JOIN unidades ON xbienes.idUnidad = unidades.id"
                    + "INNER JOIN servicios ON xbienes.idServicio = servicios.id"
                    + "INNER JOIN sectores ON xbienes.idSector = sectores.id"
                    + "INNER JOIN trabajadores AS trabajadores ON xbienes.idtrabajador_asig = trabajadores.id "
                    + "WHERE "
                    + "sectores.id = 1;" + ID;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nbien"));
                row.add(rs.getString("clasificacion"));
                row.add(rs.getString("descripcion"));
                row.add(rs.getString("estado"));
                row.add(rs.getString("status"));
                row.add(rs.getString("nombre"));
                row.add(rs.getString("ubicacion"));
                row.add(rs.getString("unidad"));
                row.add(rs.getString("servicio"));
                row.add(rs.getString("fecha_inventariado"));
                data.add(row);
            }
            
            return data;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            closeCon();
        }
    }

    public Vector dataInventarioByAll() {
        try {
            openCon();

            String sql = "SELECT xbienes.nbien AS nbien, xbienes.descripcion AS descripcion, "
                    + "xbienes.clasificacion AS clasificacion, xbienes.estado AS estado,"
                    + "xbienes.`status` AS `status`,trabajadores.nombre AS nombre, "
                    + "xbienes.ubicacion_asig AS ubicacion,  sectores.nombre AS sector, "
                    + "unidades.nombre AS unidad, servicios.nombre AS servicio, "
                    + "xbienes.fecha_inventariado AS fecha_inventariado "
                    + "FROM bienes AS xbienes INNER JOIN sectores ON xbienes.idSector = sectores.id "
                    + "INNER JOIN unidades ON xbienes.idUnidad = unidades.id "
                    + "INNER JOIN servicios ON xbienes.idServicio = servicios.id "
                    + "INNER JOIN entidades ON xbienes.idEntidad = entidades.id "
                    + "INNER JOIN trabajadores AS trabajadores ON xbienes.idtrabajador_asig = trabajadores.id";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nbien"));
                row.add(rs.getString("clasificacion"));
                row.add(rs.getString("descripcion"));
                row.add(rs.getString("estado"));
                row.add(rs.getString("status"));
                row.add(rs.getString("nombre"));
                row.add(rs.getString("ubicacion"));
                row.add(rs.getString("sector"));
                row.add(rs.getString("unidad"));
                row.add(rs.getString("servicio"));
                row.add(rs.getString("fecha_inventariado"));
                data.add(row);
            }
            
            return data;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            closeCon();
        }
    }
    
    public DefaultComboBoxModel model(String filterTable) {
        try {
            dcm = new DefaultComboBoxModel();

            if (openCon() != null) {
                Statement stm = con.createStatement();
                ResultSet rst = stm.executeQuery("SELECT * FROM " + filterTable);

                while (rst.next()) {
                    dcm.addElement(rst.getString("id") + "-. " + rst.getString("nombre"));
                }

                return dcm;
            } else {
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            closeCon();
        }
    }

}