/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pii.invbienes.controldb;

import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author PASANTIA
 */
public class SqlControllerClass {

    private Connection con;
    private DefaultTableModel dtm;
    private DefaultComboBoxModel dcm;

    private String ip, db, user, pwd;

    private PdfPTable table;

    private JasperViewer jvw;

    /*
    public PdfPTable fillInventoryReportByService(String IDSERV){
        table = new PdfPTable(6);
        
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * from bienes WHERE idServicio = " + IDSERV);
            
            table.addCell("CLASIFICACION");
            table.addCell("Nº DE BIEN");
            table.addCell("CANTIDAD");
            table.addCell("DESRIPCION");
            table.addCell("COSTO DE ADQISICION (BS.)");
            table.addCell("VALOR ESTIMADO (BS.)");
            
            if (rst.next()) {
                do {                    
                    table.addCell(rst.getString("clasificacion"));
                    table.addCell(rst.getString("nbien"));
                    table.addCell("1");
                    table.addCell(rst.getString("descripcion"));
                    table.addCell(rst.getString("monto_bs"));
                    table.addCell("");
                } while (rst.next());
            }
            
            return table;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally{
            closeCon();
        }
    }*/
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
                    + "INNER JOIN unidades ON xbienes.idUnidad = unidades.id "
                    + "INNER JOIN servicios ON xbienes.idServicio = servicios.id "
                    + "INNER JOIN sectores ON xbienes.idSector = sectores.id "
                    + "INNER JOIN trabajadores AS trabajadores ON xbienes.idtrabajador_asig = trabajadores.id "
                    + "WHERE "
                    + "sectores.id = " + ID;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nbien"));
                row.add(rs.getString("clasificacion"));
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

    public Vector dataInventarioByUnidades(String ID) {
        try {
            openCon();

            String sql = "SELECT xbienes.nbien AS nbien, "
                    + "xbienes.descripcion AS descripcion, "
                    + "xbienes.clasificacion AS clasificacion,"
                    + "xbienes.estado AS estado, "
                    + "xbienes.`status` AS `status`, "
                    + "trabajadores.nombre AS nombre,"
                    + "xbienes.ubicacion_asig AS ubicacion, "
                    + "servicios.nombre AS servicio, "
                    + "xbienes.fecha_inventariado AS fecha_inventariado "
                    + "FROM bienes AS xbienes "
                    + "INNER JOIN unidades ON xbienes.idUnidad = unidades.id "
                    + "INNER JOIN servicios ON xbienes.idServicio = servicios.id "
                    + "INNER JOIN trabajadores AS trabajadores ON xbienes.idtrabajador_asig = trabajadores.id "
                    + "WHERE "
                    + "unidades.id = " + ID;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nbien"));
                row.add(rs.getString("clasificacion"));
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

    public Vector dataInventarioByServicios(String ID) {
        try {
            openCon();

            String sql = "SELECT xbienes.nbien AS nbien, "
                    + "xbienes.descripcion AS descripcion, "
                    + "xbienes.clasificacion AS clasificacion,"
                    + "xbienes.estado AS estado, "
                    + "xbienes.`status` AS `status`, "
                    + "trabajadores.nombre AS nombre,"
                    + "xbienes.ubicacion_asig AS ubicacion, "
                    + "xbienes.fecha_inventariado AS fecha_inventariado, "
                    + "xbienes.idServicio AS servicios "
                    + "FROM bienes AS xbienes "
                    + "INNER JOIN servicios ON xbienes.idServicio = servicios.id "
                    + "INNER JOIN trabajadores AS trabajadores ON xbienes.idtrabajador_asig = trabajadores.id "
                    + "WHERE "
                    + "servicios.id = " + ID;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nbien"));
                row.add(rs.getString("clasificacion"));
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
                    + "unidades.nombre AS unidad, "
                    + "servicios.nombre AS servicio, "
                    + "entidades.nombre AS entidades, "
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

    //-------------------------------------------------------------------------//
    public Vector dataIncorporacionesByEntidad(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE identidad = " + ID + " AND concepto BETWEEN '01' AND '20'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE identidad = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataIncorporacionesBySector(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idSector = " + ID + " AND concepto BETWEEN '01' AND '20'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idsector = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataIncorporacionesByUnidades(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idunidad = " + ID + " AND concepto BETWEEN '01' AND '20'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idunidad = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataIncorporacionesByServicios(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idservicio = " + ID + " AND concepto BETWEEN '01' AND '20'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idservicio = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataIncorporacionesByAll() {
        try {
            openCon();

            String sql = "SELECT "
                    + "clasificacion AS cls,"
                    + "nbien AS nb,"
                    + "concepto AS conc,"
                    + "descripcion AS `desc`,"
                    + "monto_bs AS monto,"
                    + "nfactura AS nfac,"
                    + "fecha_mov AS fecha "
                    + "FROM movimientos WHERE concepto BETWEEN '01' AND '20'";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    //---------------------------------------------------------------------------//
    public Vector dataDesIncorporacionesByEntidad(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE identidad = " + ID + " AND concepto BETWEEN '51' AND '67'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE identidad = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataDesIncorporacionesBySector(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE identidad = " + ID + " AND concepto BETWEEN '51' AND '67'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idsector = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataDesIncorporacionesByUnidades(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE identidad = " + ID + " AND concepto BETWEEN '51' AND '67'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idunidad = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataDesIncorporacionesByServicios(String ID, String filter) {
        try {
            openCon();

            String sql = "";

            if (filter.isEmpty()) {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE identidad = " + ID + " AND concepto BETWEEN '51' AND '67'";
            } else {
                sql = "SELECT "
                        + "clasificacion AS cls,"
                        + "nbien AS nb,"
                        + "concepto AS conc,"
                        + "descripcion AS `desc`,"
                        + "monto_bs AS monto,"
                        + "nfactura AS nfac,"
                        + "fecha_mov AS fecha "
                        + "FROM movimientos WHERE idservicio = " + ID + " AND concepto = " + filter;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("nfac"));
                row.add(rs.getString("fecha"));
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

    public Vector dataDesIncorporacionesByAll() {
        try {
            openCon();

            String sql = "SELECT "
                    + "clasificacion AS cls,"
                    + "nbien AS nb,"
                    + "concepto AS conc,"
                    + "descripcion AS `desc`,"
                    + "monto_bs AS monto,"
                    + "actadesincorp AS des,"
                    + "fecha_mov AS fecha "
                    + "FROM movimientos WHERE concepto BETWEEN '51' AND '67'";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nb"));
                row.add(rs.getString("cls"));
                row.add(rs.getString("conc"));
                row.add(rs.getString("desc"));
                row.add(rs.getString("monto"));
                row.add(rs.getString("des"));
                row.add(rs.getString("fecha"));
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

    /*
    public String[] totalizeAllInventory(){
        try {
            openCon();
            String sql = "SELECT xbienes."
        } catch (Exception e) {
        }
    }
     */
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

    //------------------PARA GENERAR LOS REPORTES DEL INVENTARIO POR ENTE Y CONCEPTO------------------------//
    public void reportBienesByServicio(String FECHA, Integer TYPE, String idServicio, String concNum) {
        try {
            if (openCon() != null) {
                switch (TYPE) {
                    case 0:
                        // ESTO ES PARA BIENES!!!

                        // Load the JRXML file
                        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResource("/reportes/Bienesv2.jrxml").getFile());

                        // Create a new query
                        String newQuery = "SELECT "
                                + "a.clasificacion AS clasificacion,"
                                + "a.nbien AS nroBien,"
                                + "a.descripcion AS descBien,"
                                + "a.monto_bs AS mntBien,"
                                + "servicios.nombre AS nomServicio,"
                                + "unidades.nombre AS nomUnidad,"
                                + "sectores.nombre AS nomSector,"
                                + "entidades.nombre AS nomEntidad,"
                                + "servicios.ubicacion AS ubic,"
                                + "servicios.estado AS estado,"
                                + "servicios.municipio AS munip,"
                                + "servicios.parroquia AS parroq,"
                                + "a.idServicio AS idServ,"
                                + "( SELECT sum( monto_bs ) FROM bienes AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                + "FROM"
                                + " bienes AS a"
                                + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id "
                                + "LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id "
                                + "WHERE a.idServicio = " + idServicio
                                + " ORDER BY"
                                + " a.idServicio ASC";

                        // Set the new query in the JasperDesign
                        JRDesignQuery query = new JRDesignQuery();
                        query.setText(newQuery);
                        jasperDesign.setQuery(query);

                        //-----------//
                        JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                        // Crear parámetros para el informe
                        Map<String, Object> parameters = new HashMap<>();
                        parameters.put("fecha", FECHA);

                        JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con);

                        jvw = new JasperViewer(jprint, false);
                        jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        jvw.setVisible(true);

                        //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        break;
                    case 1:
                        if (concNum.isEmpty()) {
                            // ESTO ES PARA INCOPORACIONES (todo registro)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '01' AND '20' AND a.idServicio = " + idServicio
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            // ESTO ES PARA INCOPORACIONES (por concepto y ente)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto = '" + concNum + "' AND a.idServicio = " + idServicio
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }
                        break;
                    case 2:
                        if (concNum.isEmpty()) {
                            // ESTO ES PARA DESINCOPORACIONES (todo registro)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '51' AND '67' AND a.idServicio = " + idServicio
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            // ESTO ES PARA DESINCOPORACIONES (por concepto y ente)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto = '" + concNum + "' AND a.idServicio = " + idServicio
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "ERROR: ACCIÓN NO VÁLIDA", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL GENERAR REPORTE", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            closeCon();
        }
    }

    public void reportBienesByUnidad(String FECHA, Integer TYPE, String idUnidad, String concNum) {
        try {
            if (openCon() != null) {
                switch (TYPE) {
                    case 0:
                        // ESTO ES PARA BIENES!!!

                        // Load the JRXML file
                        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResource("/reportes/Bienesv2.jrxml").getFile());

                        // Create a new query
                        String newQuery = "SELECT "
                                + "a.clasificacion AS clasificacion,"
                                + "a.nbien AS nroBien,"
                                + "a.descripcion AS descBien,"
                                + "a.monto_bs AS mntBien,"
                                + "servicios.nombre AS nomServicio,"
                                + "unidades.nombre AS nomUnidad,"
                                + "sectores.nombre AS nomSector,"
                                + "entidades.nombre AS nomEntidad,"
                                + "servicios.ubicacion AS ubic,"
                                + "servicios.estado AS estado,"
                                + "servicios.municipio AS munip,"
                                + "servicios.parroquia AS parroq,"
                                + "a.idServicio AS idServ,"
                                + "( SELECT sum( monto_bs ) FROM bienes AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                + "FROM"
                                + " bienes AS a"
                                + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id "
                                + "LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id "
                                + "WHERE a.idUnidad = " + idUnidad
                                + " ORDER BY"
                                + " a.idServicio ASC";

                        // Set the new query in the JasperDesign
                        JRDesignQuery query = new JRDesignQuery();
                        query.setText(newQuery);
                        jasperDesign.setQuery(query);

                        //-----------//
                        JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                        // Crear parámetros para el informe
                        Map<String, Object> parameters = new HashMap<>();
                        parameters.put("fecha", FECHA);

                        JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con);

                        jvw = new JasperViewer(jprint, false);
                        jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        jvw.setVisible(true);

                        //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        break;
                    case 1:

                        if (concNum.isEmpty()) {
                            // ESTO ES PARA INCOPORACIONES (todo)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '01' AND '20' AND idUnidad = " + idUnidad + " "
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            // ESTO ES PARA INCOPORACIONES (por concepto)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto = '" + concNum + "' AND idUnidad = " + idUnidad + " "
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }

                        break;
                    case 2:
                        if (concNum.isEmpty()) {
                            // ESTO ES PARA DESINCOPORACIONES (todo registro)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '51' AND '67' AND a.idunidad = " + idUnidad
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            // ESTO ES PARA DESINCOPORACIONES (por concepto)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto = " + concNum + " AND a.idunidad = " + idUnidad
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "ERROR: ACCIÓN NO VÁLIDA", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL GENERAR REPORTE", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            closeCon();
        }
    }

    public void reportBienesBySector(String FECHA, Integer TYPE, String idSector, String concNum) {
        try {
            if (openCon() != null) {
                switch (TYPE) {
                    case 0:
                        // ESTO ES PARA BIENES!!!

                        // Load the JRXML file
                        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResource("/reportes/Bienesv2.jrxml").getFile());

                        // Create a new query
                        String newQuery = "SELECT "
                                + "a.clasificacion AS clasificacion,"
                                + "a.nbien AS nroBien,"
                                + "a.descripcion AS descBien,"
                                + "a.monto_bs AS mntBien,"
                                + "servicios.nombre AS nomServicio,"
                                + "unidades.nombre AS nomUnidad,"
                                + "sectores.nombre AS nomSector,"
                                + "entidades.nombre AS nomEntidad,"
                                + "servicios.ubicacion AS ubic,"
                                + "servicios.estado AS estado,"
                                + "servicios.municipio AS munip,"
                                + "servicios.parroquia AS parroq,"
                                + "a.idServicio AS idServ,"
                                + "( SELECT sum( monto_bs ) FROM bienes AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                + "FROM"
                                + " bienes AS a"
                                + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id "
                                + "LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id "
                                + "WHERE a.idSector = " + idSector
                                + " ORDER BY"
                                + " a.idServicio ASC";

                        // Set the new query in the JasperDesign
                        JRDesignQuery query = new JRDesignQuery();
                        query.setText(newQuery);
                        jasperDesign.setQuery(query);

                        //-----------//
                        JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                        // Crear parámetros para el informe
                        Map<String, Object> parameters = new HashMap<>();
                        parameters.put("fecha", FECHA);

                        JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con);

                        jvw = new JasperViewer(jprint, false);
                        jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        jvw.setVisible(true);

                        //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        break;
                    case 1:
                        if (concNum.isEmpty()) {
                            // ESTO ES PARA INCOPORACIONES (todo registro)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '01' AND '20' AND idSector = " + idSector + " "
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            // ESTO ES PARA INCOPORACIONES (por concepto y ente)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto = '" + concNum + "' AND idSector = " + idSector + " "
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }
                        break;
                    case 2:
                        if (concNum.isEmpty()) {
                            // ESTO ES PARA DESINCOPORACIONES (todo)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '51' AND '67' AND a.idSector = " + idSector
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            // ESTO ES PARA DESINCOPORACIONES (por concepto y ente)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto = '" + concNum + "' AND a.idSector = " + idSector
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "ERROR: ACCIÓN NO VÁLIDA", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL GENERAR REPORTE", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            closeCon();
        }
    }

    public void reportBienesByEntidad(String FECHA, Integer TYPE, String idEntidad, String concNum) {
        try {
            if (openCon() != null) {
                switch (TYPE) {
                    case 0:
                        System.out.println("I'M HERE (Bienes por entidad)");

                        // ESTO ES PARA BIENES!!!
                        // Load the JRXML file
                        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResource("/reportes/Bienesv2.jrxml").getFile());

                        // Create a new query
                        String newQuery = "SELECT "
                                + "a.clasificacion AS clasificacion,"
                                + "a.nbien AS nroBien,"
                                + "a.descripcion AS descBien,"
                                + "a.monto_bs AS mntBien,"
                                + "servicios.nombre AS nomServicio,"
                                + "unidades.nombre AS nomUnidad,"
                                + "sectores.nombre AS nomSector,"
                                + "entidades.nombre AS nomEntidad,"
                                + "servicios.ubicacion AS ubic,"
                                + "servicios.estado AS estado,"
                                + "servicios.municipio AS munip,"
                                + "servicios.parroquia AS parroq,"
                                + "a.idServicio AS idServ,"
                                + "( SELECT sum( monto_bs ) FROM bienes AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                + "FROM"
                                + " bienes AS a"
                                + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id "
                                + "LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id "
                                + "WHERE a.idEntidad = " + idEntidad
                                + " ORDER BY"
                                + " a.idServicio ASC";

                        // Set the new query in the JasperDesign
                        JRDesignQuery query = new JRDesignQuery();
                        query.setText(newQuery);
                        jasperDesign.setQuery(query);

                        //-----------//
                        JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                        // Crear parámetros para el informe
                        Map<String, Object> parameters = new HashMap<>();
                        parameters.put("fecha", FECHA);

                        JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con);

                        jvw = new JasperViewer(jprint, false);
                        jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        jvw.setVisible(true);

                        //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        break;
                    case 1:
                        if (concNum.isEmpty()) {
                            System.out.println("I'M HERE (Incorporaciones sin concepto)");
                            // ESTO ES PARA INCOPORACIONES (todo)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '01' AND '20' AND idEntidad = " + idEntidad
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            System.out.println("I'M HERE (Incorporaciones con concepto)");
                            // ESTO ES PARA INCOPORACIONES(por concepto y ente)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign2 = JRXmlLoader.load(getClass().getResource("/reportes/Incorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery2 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto = '" + concNum + "' AND idEntidad = " + idEntidad
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query2 = new JRDesignQuery();
                            query2.setText(newQuery2);
                            jasperDesign2.setQuery(query2);

                            //-----------//
                            JasperReport report2 = JasperCompileManager.compileReport(jasperDesign2);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters2 = new HashMap<>();
                            parameters2.put("fecha", FECHA);

                            JasperPrint jprint2 = JasperFillManager.fillReport(report2, parameters2, con);

                            jvw = new JasperViewer(jprint2, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }
                        break;
                    case 2:
                        if (concNum.isEmpty()) {
                            System.out.println("I'M HERE (desincorporaciones sin concepto)");
                            // ESTO ES PARA DESINCOPORACIONES (todo)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '51' AND '67' AND a.idEntidad = " + idEntidad
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        } else {
                            System.out.println("I'M HERE (desincorporaciones con concepto)");
                            // ESTO ES PARA DESINCOPORACIONES(por concepto y ente)!!!

                            // Load the JRXML file
                            JasperDesign jasperDesign3 = JRXmlLoader.load(getClass().getResource("/reportes/DesIncorporaciones.jrxml").getFile());

                            // Create a new query
                            String newQuery3 = "SELECT"
                                    + " a.clasificacion AS clasificacion,"
                                    + "a.nbien AS nroBien,"
                                    + "a.descripcion AS descBien,"
                                    + "a.monto_bs AS mntBien,"
                                    + "a.concepto AS idConcepto,"
                                    + "servicios.nombre AS nomServicio,"
                                    + "unidades.nombre AS nomUnidad,"
                                    + "sectores.nombre AS nomSector,"
                                    + "entidades.nombre AS nomEntidad,"
                                    + "servicios.ubicacion as ubic,"
                                    + "servicios.estado as estado,"
                                    + "servicios.municipio as munip,"
                                    + "servicios.parroquia as parroq,"
                                    + "a.idServicio as idServ,"
                                    + "( SELECT sum( monto_bs ) FROM movimientos AS b WHERE b.idServicio = a.idServicio ) AS costo_aq "
                                    + " FROM "
                                    + "movimientos AS a"
                                    + " INNER JOIN servicios ON a.idServicio = servicios.id"
                                    + " LEFT JOIN unidades ON servicios.idUnidadAs = unidades.id"
                                    + " LEFT JOIN sectores ON unidades.idSectorAs = sectores.id"
                                    + " LEFT JOIN entidades ON sectores.idEntidadAs = entidades.id"
                                    + " WHERE"
                                    + " a.concepto BETWEEN '" + concNum + "' AND a.idEntidad = " + idEntidad
                                    + " ORDER BY"
                                    + " a.idServicio";

                            // Set the new query in the JasperDesign
                            JRDesignQuery query3 = new JRDesignQuery();
                            query3.setText(newQuery3);
                            jasperDesign3.setQuery(query3);

                            //-----------//
                            JasperReport report3 = JasperCompileManager.compileReport(jasperDesign3);

                            // Crear parámetros para el informe
                            Map<String, Object> parameters3 = new HashMap<>();
                            parameters3.put("fecha", FECHA);

                            JasperPrint jprint3 = JasperFillManager.fillReport(report3, parameters3, con);

                            jvw = new JasperViewer(jprint3, false);
                            jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jvw.setVisible(true);

                            //JasperExportManager.exportReportToPdfFile(jprint, "path/to/your/report.pdf");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "ERROR: ACCIÓN NO VÁLIDA", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL GENERAR REPORTE", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            closeCon();
        }
    }
    //-----------------------------------------------------------------------------------------------------//

    //-----------------PARA CARGAR LOS REPORTES CON TODOS LOS REGISTROS-------------------------//
    public void reportIncorp(String FECHA) {
        try {
            if (openCon() != null) {

                JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Incorporaciones.jasper"));

                // Crear parámetros para el informe
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("fecha", FECHA);

                JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con);

                jvw = new JasperViewer(jprint, false);
                jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jvw.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL GENERAR REPORTE", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            closeCon();
        }
    }

    public void reportDesIncorp(String FECHA) {
        try {
            if (openCon() != null) {

                JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/DesIncorporaciones.jasper"));

                // Crear parámetros para el informe
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("fecha", FECHA);

                JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con);

                jvw = new JasperViewer(jprint, false);
                jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jvw.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL GENERAR REPORTE", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            closeCon();
        }
    }

    public void reportInventario(String FECHA) {
        try {
            if (openCon() != null) {
                JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Bienesv2.jasper"));

                // Crear parámetros para el informe
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("fecha", FECHA);

                JasperPrint jprint = JasperFillManager.fillReport(report, parameters, con);

                jvw = new JasperViewer(jprint, false);
                jvw.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jvw.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL GENERAR REPORTE", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            closeCon();
        }
    }

    //---------------------------------------------------------------------------------------//
    public String[] bienData(String BID) {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT "
                    + "bienes.clasificacion AS clasif, "
                    + "bienes.nbien AS nb, "
                    + "bienes.descripcion AS `desc`, "
                    + "bienes.estado AS estado, "
                    + "bienes.`status` AS `status`, "
                    + "bienes.ubicacion_asig AS ubic, "
                    + "bienes.idtrabajador_asig AS idtrab, "
                    + "bienes.monto_bs AS montobs, "
                    + "bienes.idEntidad AS identidad, "
                    + "bienes.idSector AS idsector, "
                    + "bienes.idUnidad AS idunidad, "
                    + "bienes.idServicio AS idservicio, "
                    + "bienes.fecha_inventariado AS fecha "
                    + "FROM bienes "
                    + "WHERE"
                    + " bienes.nbien = '" + BID + "'");

            String[] data;

            if (rst.next()) {
                data = new String[]{
                    rst.getString("clasif"),
                    rst.getString("nb"),
                    rst.getString("desc"),
                    rst.getString("estado"),
                    rst.getString("status"),
                    rst.getString("idtrab"),
                    rst.getString("ubic"),
                    rst.getString("montobs"),
                    rst.getString("identidad"),
                    rst.getString("idsector"),
                    rst.getString("idunidad"),
                    rst.getString("idservicio"),
                    rst.getString("fecha")
                };
                return data;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeCon();
        }
    }
    
    public String[] IncorpData(String BID) {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT "
                    + "movimientos.clasificacion AS clasif, "
                    + "movimientos.nbien AS nb, "
                    + "movimientos.descripcion AS `desc`,"
                    + "movimientos.monto_bs AS montobs,"
                    + "movimientos.nfactura AS nfacto,"
                    + "movimientos.ordencompra AS ordcompra, "
                    + "movimientos.idEntidad AS identidad, "
                    + "movimientos.idSector AS idsector, "
                    + "movimientos.idUnidad AS idunidad, "
                    + "movimientos.idServicio AS idservicio, "
                    + "movimientos.fecha_mov AS fecha,"
                    + "movimientos.concepto AS conc "
                    + "FROM movimientos "
                    + "WHERE"
                    + " movimientos.nbien = '" + BID + "'");

            String[] data;

            if (rst.next()) {
                data = new String[]{
                    rst.getString("clasif"),
                    rst.getString("nb"),
                    rst.getString("desc"),
                    rst.getString("nfacto"),
                    rst.getString("ordcompra"),
                    rst.getString("montobs"),
                    rst.getString("identidad"),
                    rst.getString("idsector"),
                    rst.getString("idunidad"),
                    rst.getString("idservicio"),
                    rst.getString("fecha"),
                    rst.getString("conc")
                };
                return data;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeCon();
        }
    }
    
    public String[] DesIncorpData(String BID) {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT "
                    + "movimientos.clasificacion AS clasif, "
                    + "movimientos.nbien AS nb, "
                    + "movimientos.descripcion AS `desc`, "
                    + "movimientos.monto_bs AS montobs,"
                    + "movimientos.actadesincorp AS desincActa,"
                    + "movimientos.idEntidad AS identidad, "
                    + "movimientos.idSector AS idsector, "
                    + "movimientos.idUnidad AS idunidad, "
                    + "movimientos.idServicio AS idservicio, "
                    + "movimientos.fecha_mov AS fecha,"
                    + "movimientos.concepto AS conc "
                    + "FROM movimientos "
                    + "WHERE"
                    + " movimientos.nbien = '" + BID + "'");

            String[] data;

            if (rst.next()) {
                data = new String[]{
                    rst.getString("clasif"),
                    rst.getString("nb"),
                    rst.getString("desc"),
                    rst.getString("desincActa"),
                    rst.getString("montobs"),
                    rst.getString("identidad"),
                    rst.getString("idsector"),
                    rst.getString("idunidad"),
                    rst.getString("idservicio"),
                    rst.getString("fecha"),
                    rst.getString("conc")
                };
                return data;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeCon();
        }
    }

    public String getWorker(String ID) {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM trabajadores WHERE ID = " + ID);
            if (rst.next()) {
                return rst.getString("nombre");
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        } finally {
            closeCon();
        }
    }

    public DefaultComboBoxModel getWorkers() {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM trabajadores");

            DefaultComboBoxModel dcm = new DefaultComboBoxModel();
            while (rst.next()) {
                dcm.addElement(rst.getString("id"));
            }

            return dcm;
        } catch (SQLException e) {
            return null;
        } finally {
            closeCon();
        }
    }

    public String[] getRutaEntes(String ID) {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT "
                    + "entidades.nombre AS Entidad,"
                    + "sectores.nombre AS Sector,"
                    + "unidades.nombre AS Unidad,"
                    + "servicios.nombre AS Servicio "
                    + "FROM unidades "
                    + "INNER JOIN servicios ON unidades.id = servicios.idUnidadAs "
                    + "INNER JOIN sectores ON sectores.id = unidades.idSectorAs "
                    + "INNER JOIN entidades ON entidades.id = sectores.idEntidadAs "
                    + "WHERE servicios.id = " + ID);

            String[] data;
            if (rst.next()) {
                data = new String[]{
                    rst.getString("Entidad"),
                    rst.getString("Sector"),
                    rst.getString("Unidad"),
                    rst.getString("Servicio"),};
                return data;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        } finally {
            closeCon();
        }
    }

    public DefaultComboBoxModel getServicios() {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM servicios");

            DefaultComboBoxModel dcm = new DefaultComboBoxModel();
            while (rst.next()) {
                dcm.addElement(rst.getString("id"));
            }

            return dcm;
        } catch (SQLException e) {
            return null;
        } finally {
            closeCon();
        }
    }

    public String getEstado(String ID) {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT estado FROM bienes WHERE nbien = '" + ID + "'");
            if (rst.next()) {
                return rst.getString("estado");
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        } finally {
            closeCon();
        }
    }

    public String getStatus(String ID) {
        try {
            openCon();
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery("SELECT status FROM bienes WHERE nbien = '" + ID + "'");
            if (rst.next()) {
                return rst.getString("status");
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        } finally {
            closeCon();
        }
    }

    public Boolean updateBien(String byNBien, String[] data) {
        try {
            String[] dataBien = data;
            String clasif = dataBien[0] + "-" + dataBien[1] + "-" + dataBien[2];

            String sql = "UPDATE bienes SET "
                    + "clasificacion = '" + clasif + "',"
                    + "descripcion = '" + dataBien[5] + "',"
                    + "estado = '" + dataBien[4] + "',"
                    + "status = '" + dataBien[3] + "',"
                    + "idtrabajador_asig = " + dataBien[6] + ","
                    + "ubicacion_asig = '" + dataBien[7] + "',"
                    + "monto_bs = '" + dataBien[8] + "',"
                    + "idServicio = " + dataBien[9] + ","
                    + "idUnidad = (SELECT idUnidadAs FROM servicios WHERE id = " + dataBien[9] + "),"
                    + "idSector = (SELECT idSectorAs FROM unidades WHERE id = (SELECT idUnidadAs FROM servicios WHERE id = " + dataBien[9] + ")), "
                    + "idEntidad = (SELECT idEntidadAs FROM sectores WHERE id = (SELECT idSectorAs FROM unidades WHERE id = (SELECT idUnidadAs FROM servicios WHERE id = " + dataBien[9] + "))),"
                    + "fecha_inventariado = '" + dataBien[10] + "' "
                    + "WHERE bienes.nbien = '" + dataBien[11] + "'";

            System.out.println("SQL " + sql);

            openCon();
            Statement stm = con.createStatement();
            Integer stt = stm.executeUpdate(sql);

            if (stt != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        } finally {
            closeCon();
        }
    }

    public Boolean updateBienSTATUS(String byNBien, String STATUS) {
        try {
            String sql = "UPDATE bienes SET status = '" + STATUS + "' WHERE nbien = '" + byNBien + "'";
            System.out.println("SQL " + sql);

            openCon();
            Statement stm = con.createStatement();
            Integer stt = stm.executeUpdate(sql);

            if (stt != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        } finally {
            closeCon();
        }
    }

    //------------------------------------PARA AÑADIR UN BIEN----------------------------//
    private String[] getentsIDs(String idServ) {
        try {
            if (openCon() != null) {
                Statement stm = con.createStatement();
                ResultSet rst = stm.executeQuery("SELECT "
                        + "entidades.id AS entidad,"
                        + "sectores.id AS sector,"
                        + "unidades.id AS unidad"
                        + " FROM "
                        + "servicios "
                        + "INNER JOIN unidades ON servicios.idUnidadAs = unidades.id "
                        + "INNER JOIN sectores ON sectores.id = unidades.idSectorAs "
                        + "INNER JOIN entidades ON entidades.id = sectores.idEntidadAs "
                        + "WHERE "
                        + "servicios.id = " + idServ);

                if (rst.next()) {

                    String[] data = new String[]{
                        rst.getString("entidad"),
                        rst.getString("sector"),
                        rst.getString("unidad")
                    };

                    return data;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR\n ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            closeCon();
        }
    }

    public Boolean addBien(String nroBien, String clasif, String descripcion, String estado, String status, String idTrab, String ubic, String monto_bs, String idServicio, String fecha) {
        try {
            if (openCon() != null) {
                if (getentsIDs(idServicio) != null) {
                    String[] data = getentsIDs(idServicio);
                    if (verifyExistencia(nroBien, 0)) {
                        return false;
                    } else {
                        Statement stm = con.createStatement();
                        Integer i = 0;
                        i = stm.executeUpdate("INSERT INTO bienes VALUES(null , '" + clasif + "', '" + nroBien + "', '" + descripcion + "', "
                                + "'" + estado + "', '" + status + "', " + idTrab + ", '" + ubic + "', '" + monto_bs + "', " + data[0] + ", " + data[1] + ", " + data[2] + ", " + idServicio + ", '" + fecha + "')");
                        if (i != 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR\n ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            closeCon();
        }
    }

    public Boolean verifyExistencia(String nroBien, Integer TYPE) {
        try {
            switch (TYPE) {
                case 0:
                    //Verifica en bienes
                    if (openCon() != null) {
                        Statement stm = con.createStatement();
                        ResultSet rst = stm.executeQuery("SELECT * FROM bienes WHERE nbien = '" + nroBien + "'");
                        if (rst.next()) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case 1:
                    //Verifica en incorporaciones
                    if (openCon() != null) {
                        if (openCon() != null) {
                            Statement stm = con.createStatement();
                            ResultSet rst = stm.executeQuery("SELECT * FROM movimientos WHERE nbien = '" + nroBien + "'");
                            if (rst.next()) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case 2:
                    //Verifica en desincorporaciones
                    if (openCon() != null) {
                        if (openCon() != null) {
                            Statement stm = con.createStatement();
                            ResultSet rst = stm.executeQuery("SELECT * FROM movimientos WHERE nbien = '" + nroBien + "'");
                            if (rst.next()) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                default:
                    JOptionPane.showMessageDialog(null, "Tipo de consulta no válida", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                    return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR\n ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //-----------------------------------PARA AÑADIR UNA INCORPORACIÓN-------------------//
    public Boolean addIncorp(String nroBien, String clasif, String descripcion, String estado, String status, String concepto, String ordCompra, String nFactura, String monto_bs, String idServicio, String fecha, String iduser) {
        try {
            if (openCon() != null) {
                if (getentsIDs(idServicio) != null) {
                    String[] data = getentsIDs(idServicio);
                    if (verifyExistencia(nroBien, 1)) {
                        return false;
                    } else {
                        Statement stm = con.createStatement();
                        Integer i = 0;
                        i = stm.executeUpdate("INSERT INTO piibienes.movimientos (clasificacion, nbien, concepto, descripcion, monto_bs, nfactura, ordencompra, actadesincorp, idusuario, identidad, idsector, "
                                + "idunidad, idservicio, fecha_mov) VALUES ('" + clasif + "', '" + nroBien + "', '" + concepto + "', '" + descripcion + "', '" + monto_bs + "', '" + nFactura + "', "
                                + "'" + ordCompra + "', '', " + iduser + ", " + data[0] + ", " + data[1] + ", " + data[2] + ", " + idServicio + ", '" + fecha + "')");
                        if (i != 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR\n ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            closeCon();
        }
    }

    //-----------------------------------PARA AÑADIR UNA DESINCORPORACIÓN-------------------//
    public Boolean addDesIncorp(String nroBien, String clasif, String descripcion, String estado, String status, String concepto, String ordCompra, String nFactura, String monto_bs, String idServicio, String fecha, String iduser, Boolean isWithNumeroActa) {
        try {
            if (openCon() != null) {
                if (isWithNumeroActa) {
                    if (getentsIDs(idServicio) != null) {
                        String[] data = getentsIDs(idServicio);
                        if (verifyExistencia(nroBien, 1)) {
                            return false;
                        } else {
                            Statement stm = con.createStatement();
                            Integer i = 0;
                            i = stm.executeUpdate("INSERT INTO piibienes.movimientos (clasificacion, nbien, concepto, descripcion, monto_bs, nfactura, ordencompra, actadesincorp, idusuario, identidad, idsector, "
                                    + "idunidad, idservicio, fecha_mov) VALUES ('" + clasif + "', '" + nroBien + "', '" + concepto + "', '" + descripcion + "', '" + monto_bs + "', '', "
                                    + "'', '" + nFactura + "', " + iduser + ", " + data[0] + ", " + data[1] + ", " + data[2] + ", " + idServicio + ", '" + fecha + "')");
                            if (i != 0) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return null;
                    }
                } else {
                    if (getentsIDs(idServicio) != null) {
                        String[] data = getentsIDs(idServicio);
                        if (verifyExistencia(nroBien, 1)) {
                            return false;
                        } else {
                            Statement stm = con.createStatement();
                            Integer i = 0;
                            i = stm.executeUpdate("INSERT INTO piibienes.movimientos (clasificacion, nbien, concepto, descripcion, monto_bs, nfactura, ordencompra, actadesincorp, idusuario, identidad, idsector, "
                                    + "idunidad, idservicio, fecha_mov) VALUES (" + clasif + ", '" + nroBien + "', '" + concepto + "', '" + descripcion + "', '" + monto_bs + "', '', "
                                    + "'', '" + nFactura + "', " + iduser + ", " + data[0] + ", " + data[1] + ", " + data[2] + ", " + idServicio + ", '" + fecha + "')");
                            if (i != 0) {
                                if (setAsDesincorporar(nroBien)) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return null;
                    }
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR\n ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            closeCon();
        }
    }

    private Boolean setAsDesincorporar(String nroBien) {
        try {
            if (openCon() != null) {
                if (verifyExistencia(nroBien, 1)) {
                    return false;
                } else {
                    Statement stm = con.createStatement();
                    Integer i = 0;
                    i = stm.executeUpdate("UPDATE piibienes.bienes SET `status` = 'A DESINCORPORAR' WHERE nbien = '" + nroBien + "'");
                    if (i != 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR\n ERROR: " + e.getLocalizedMessage(), ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            this.closeCon();
        }
    }

}
