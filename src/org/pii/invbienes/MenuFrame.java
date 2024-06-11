/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.pii.invbienes;

import static com.formdev.flatlaf.FlatLaf.updateUI;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.pii.invbienes.controldb.SqlControllerClass;

/**
 *
 * @author Admin
 */
public class MenuFrame extends javax.swing.JFrame {

    /**
     * Creates new form menuPrincipal
     */
    private Boolean mode = true; //True -> claro, False -> oscuro.
    private SqlControllerClass scc;
    private TableModels tmls;

    private String[] user_data;

    private char apwd[];
    private char npwd[];
    private char npwd2[];

    private JTextField uiText;
    private JPasswordField uiPwd;

    private Integer BIENES = 0, INCORPORACIONES = 1, DESINCORPORACIONES = 2;

    private String ENTIDAD, SECTOR, UNIDAD, SERVICIO;

    private ListModels lmodels;

    public MenuFrame() {
        IconFontSwing.register(FontAwesome.getIconFont());

        initComponents();
        this.setLocationRelativeTo(null);
        // IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 24, new Color(0, 0, 0))
        scc = new SqlControllerClass();
        setFiltersModels();
        loadDefaultTables();
    }

    private void callReport(String FECHA, Integer TYPE) {

        switch (TYPE) {
            case 0:
                scc.reportInventario(FECHA);
                break;
            case 1:
                scc.reportIncorp(FECHA);
                break;
            case 2:
                scc.reportDesIncorp(FECHA);
                break;
            default:
                JOptionPane.showMessageDialog(null, "VALOR PARA GENERAR REPORTE NO VÁLIDO", ".::ERROR - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                ;
                break;
        }
    }

    //----------------------------------------------------------------//
    private void callReportBienesByServicio(String FECHA, Integer TYPE, String idServicio, String conceptoNro) {

        switch (TYPE) {
            case 0:
                //Para bienes
                scc.reportBienesByServicio(FECHA, TYPE, idServicio, conceptoNro);
                break;
            case 1:
                //Para incorporaciones
                scc.reportBienesByServicio(FECHA, TYPE, idServicio, conceptoNro);
                break;
            case 2:
                //Para desincorporaciones
                scc.reportBienesByServicio(FECHA, TYPE, idServicio, conceptoNro);
                break;
            default:
                JOptionPane.showMessageDialog(null, "VALOR PARA GENERAR REPORTE NO VÁLIDO", ".::ERROR - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                ;
                break;
        }

    }

    private void callReportBienesByUnidad(String FECHA, Integer TYPE, String idUnidad, String conceptoNro) {

        switch (TYPE) {
            case 0:
                //Para bienes
                scc.reportBienesByUnidad(FECHA, TYPE, idUnidad, conceptoNro);
                break;
            case 1:
                //Para incorporaciones
                scc.reportBienesByUnidad(FECHA, TYPE, idUnidad, conceptoNro);
                break;
            case 2:
                //Para desincorporaciones
                scc.reportBienesByUnidad(FECHA, TYPE, idUnidad, conceptoNro);
                break;
            default:
                JOptionPane.showMessageDialog(null, "VALOR PARA GENERAR REPORTE NO VÁLIDO", ".::ERROR - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                ;
                break;
        }

    }

    private void callReportBienesBySector(String FECHA, Integer TYPE, String idSector, String conceptoNro) {

        switch (TYPE) {
            case 0:
                //Para bienes
                scc.reportBienesBySector(FECHA, TYPE, idSector, conceptoNro);
                break;
            case 1:
                //Para incorporaciones
                scc.reportBienesBySector(FECHA, TYPE, idSector, conceptoNro);
                break;
            case 2:
                //Para desincorporaciones
                scc.reportBienesBySector(FECHA, TYPE, idSector, conceptoNro);
                break;
            default:
                JOptionPane.showMessageDialog(null, "VALOR PARA GENERAR REPORTE NO VÁLIDO", ".::ERROR - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                ;
                break;
        }

    }

    private void callReportBienesByEntidades(String FECHA, Integer TYPE, String idEntidad, String conceptoNro) {

        switch (TYPE) {
            case 0:
                //Para bienes
                scc.reportBienesByEntidad(FECHA, TYPE, idEntidad, conceptoNro);
                break;
            case 1:
                //Para incorporaciones
                scc.reportBienesByEntidad(FECHA, TYPE, idEntidad, conceptoNro);
                break;
            case 2:
                //Para desincorporaciones
                scc.reportBienesByEntidad(FECHA, TYPE, idEntidad, conceptoNro);
                break;
            default:
                JOptionPane.showMessageDialog(null, "VALOR PARA GENERAR REPORTE NO VÁLIDO", ".::ERROR - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
                ;
                break;
        }

    }

    //----------------------------------------------------------------//
    private void loadDefaultTables() {
        tmls = new TableModels();
        invTable.setModel(tmls.modeloInventarioByAll());
        inctable.setModel(tmls.modeloIncorporacionesByAll());
        desinctable.setModel(tmls.modeloDesIncorporacionesByAll());
        faltable.setModel(tmls.modeloFaltantesByAll());
    }

    private void setFiltersModels() {
        lmodels = new ListModels();
        //--- DE BIENES:
        entidadesList.setModel(lmodels.getFilterEntidades());
        sectoresList.setModel(lmodels.getFilterSectores());
        unidadesList.setModel(lmodels.getFilterUnidades());
        serviciosList.setModel(lmodels.getFilterServicios());

        //--- DE INCORPORACIONES:
        entidadesList2.setModel(lmodels.getFilterEntidades());
        sectoresList2.setModel(lmodels.getFilterSectores());
        unidadesList2.setModel(lmodels.getFilterUnidades());
        serviciosList2.setModel(lmodels.getFilterServicios());
        
        //--- DE DESINCORPORACIONES:
        entidadesList3.setModel(lmodels.getFilterEntidades());
        sectoresList3.setModel(lmodels.getFilterSectores());
        unidadesList3.setModel(lmodels.getFilterUnidades());
        serviciosList3.setModel(lmodels.getFilterServicios());
        
        //--- DE FALTANTES:
        entidadesList1.setModel(lmodels.getFilterEntidades());
        sectoresList1.setModel(lmodels.getFilterSectores());
        unidadesList1.setModel(lmodels.getFilterUnidades());
        serviciosList1.setModel(lmodels.getFilterServicios());
    }

    public void setUserData(String[] data) {
        if (data != null) {
            this.user_data = data;

            setWelcomeMessage(user_data[0]);
        } else {
            JOptionPane.showMessageDialog(null, "ERROR INESPERADO: DATOS DE USUARIO NO ENCONTRADOS.", ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            authFrame athfr = new authFrame();
            athfr.setVisible(true);
        }
    }

    private void setWelcomeMessage(String name) {
        pplname.setText(name);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AccountMgr = new javax.swing.JFrame();
        btnOK = new javax.swing.JButton();
        btnBACK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        pwdactual = new javax.swing.JPasswordField();
        seebtn1 = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        pwdnueva = new javax.swing.JPasswordField();
        seebtn2 = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        seebtn3 = new javax.swing.JToggleButton();
        pwdnueva2 = new javax.swing.JPasswordField();
        DetailsBienes = new javax.swing.JFrame();
        titlebar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        scene = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        grupo = new javax.swing.JTextField();
        sgrp = new javax.swing.JTextField();
        seccion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        desc = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        nbien = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        comboEstado = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        comboStatus = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        comboIdAsig = new javax.swing.JComboBox<>();
        jButton11 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        ubic = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        costo = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        servCombo = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        fechatxt = new javax.swing.JTextField();
        txtAsig = new javax.swing.JTextField();
        entidadTxt = new javax.swing.JTextField();
        unidadTxt = new javax.swing.JTextField();
        sectorTxt = new javax.swing.JTextField();
        servTxt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jButton16 = new javax.swing.JButton();
        DetailsIncorporaciones = new javax.swing.JFrame();
        titlebar1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        scene1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        grupo1 = new javax.swing.JTextField();
        sgrp1 = new javax.swing.JTextField();
        seccion1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        desc1 = new javax.swing.JTextArea();
        jLabel45 = new javax.swing.JLabel();
        nbien1 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        costo1 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        servCombo1 = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        fechatxt1 = new javax.swing.JTextField();
        entidadTxt1 = new javax.swing.JTextField();
        unidadTxt1 = new javax.swing.JTextField();
        sectorTxt1 = new javax.swing.JTextField();
        servTxt1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jToggleButton3 = new javax.swing.JToggleButton();
        jButton47 = new javax.swing.JButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jLabel56 = new javax.swing.JLabel();
        nf_txt = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        ordc_txt = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        concLista = new javax.swing.JComboBox<>();
        DetailsDesIncorporaciones = new javax.swing.JFrame();
        titlebar2 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        scene2 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        grupo4 = new javax.swing.JTextField();
        sgrp2 = new javax.swing.JTextField();
        seccion2 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        desc2 = new javax.swing.JTextArea();
        jLabel62 = new javax.swing.JLabel();
        nbien2 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        costo2 = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        servCombo2 = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        fechatxt2 = new javax.swing.JTextField();
        entidadTxt2 = new javax.swing.JTextField();
        unidadTxt2 = new javax.swing.JTextField();
        sectorTxt2 = new javax.swing.JTextField();
        servTxt2 = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        jToggleButton5 = new javax.swing.JToggleButton();
        jButton48 = new javax.swing.JButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jLabel71 = new javax.swing.JLabel();
        nActa = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        concLista2 = new javax.swing.JComboBox<>();
        scenary1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        pplname = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnMODE = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        entidadesList = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        sectoresList = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        unidadesList = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        serviciosList = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        selectedLabel = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        invTable = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelVisualizando = new javax.swing.JLabel();
        jButton41 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel37 = new javax.swing.JPanel();
        entidadesList2 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        concList = new javax.swing.JComboBox<>();
        jButton22 = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        sectoresList2 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        concList1 = new javax.swing.JComboBox<>();
        jButton13 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        unidadesList2 = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        concList2 = new javax.swing.JComboBox<>();
        jButton17 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        serviciosList2 = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        concList3 = new javax.swing.JComboBox<>();
        jButton18 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        selectedLabel2 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inctable = new javax.swing.JTable();
        jPanel43 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        labelVisualizando2 = new javax.swing.JLabel();
        jButton42 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel46 = new javax.swing.JPanel();
        entidadesList3 = new javax.swing.JComboBox<>();
        jButton12 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        concList4 = new javax.swing.JComboBox<>();
        jButton36 = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        sectoresList3 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        concList5 = new javax.swing.JComboBox<>();
        jButton19 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        unidadesList3 = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        concList6 = new javax.swing.JComboBox<>();
        jButton20 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jPanel49 = new javax.swing.JPanel();
        serviciosList3 = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        concList7 = new javax.swing.JComboBox<>();
        jButton21 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jPanel50 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        selectedLabel3 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        desinctable = new javax.swing.JTable();
        jPanel52 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        labelVisualizando3 = new javax.swing.JLabel();
        jButton43 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel31 = new javax.swing.JPanel();
        entidadesList1 = new javax.swing.JComboBox<>();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        sectoresList1 = new javax.swing.JComboBox<>();
        jButton60 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        unidadesList1 = new javax.swing.JComboBox<>();
        jButton62 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        serviciosList1 = new javax.swing.JComboBox<>();
        jButton64 = new javax.swing.JButton();
        jButton65 = new javax.swing.JButton();
        jPanel59 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        selectedLabel4 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        faltable = new javax.swing.JTable();
        jPanel61 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        labelVisualizando4 = new javax.swing.JLabel();
        jButton57 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();

        AccountMgr.setTitle("Cambiar contraseña de usuario .:: Sistema de Inventario de Bienes de PII ::.");
        AccountMgr.setLocationByPlatform(true);
        AccountMgr.setMinimumSize(new java.awt.Dimension(500, 300));
        AccountMgr.setResizable(false);

        btnOK.setBackground(new java.awt.Color(0, 204, 153));
        btnOK.setIcon(IconFontSwing.buildIcon(FontAwesome.CHECK, 24, new Color(0, 0, 0)));
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnBACK.setBackground(new java.awt.Color(0, 204, 153));
        btnBACK.setIcon(IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 24, new Color(0, 0, 0)));
        btnBACK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBACKActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Ingrese su contraseña actual:");

        seebtn1.setBackground(new java.awt.Color(0, 204, 153));
        seebtn1.setIcon(IconFontSwing.buildIcon(FontAwesome.EYE, 20, new Color(0, 0, 0)));
        seebtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seebtn1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ingrese su nueva contraseña:");

        seebtn2.setBackground(new java.awt.Color(0, 204, 153));
        seebtn2.setIcon(IconFontSwing.buildIcon(FontAwesome.EYE, 20, new Color(0, 0, 0)));
        seebtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seebtn2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Ingrese su nueva contraseña:");

        seebtn3.setBackground(new java.awt.Color(0, 204, 153));
        seebtn3.setIcon(IconFontSwing.buildIcon(FontAwesome.EYE, 20, new Color(0, 0, 0)));
        seebtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seebtn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AccountMgrLayout = new javax.swing.GroupLayout(AccountMgr.getContentPane());
        AccountMgr.getContentPane().setLayout(AccountMgrLayout);
        AccountMgrLayout.setHorizontalGroup(
            AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountMgrLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountMgrLayout.createSequentialGroup()
                        .addGroup(AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(AccountMgrLayout.createSequentialGroup()
                                .addComponent(pwdnueva2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(seebtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addGroup(AccountMgrLayout.createSequentialGroup()
                                .addComponent(pwdnueva, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(seebtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addGroup(AccountMgrLayout.createSequentialGroup()
                                .addComponent(pwdactual, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(seebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(148, 148, 148))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountMgrLayout.createSequentialGroup()
                        .addComponent(btnBACK, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        AccountMgrLayout.setVerticalGroup(
            AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountMgrLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(seebtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pwdactual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(seebtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pwdnueva, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(seebtn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pwdnueva2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AccountMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBACK, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        DetailsBienes.setMinimumSize(new java.awt.Dimension(781, 690));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Detalles del bien");

        javax.swing.GroupLayout titlebarLayout = new javax.swing.GroupLayout(titlebar);
        titlebar.setLayout(titlebarLayout);
        titlebarLayout.setHorizontalGroup(
            titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlebarLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel8)
                .addContainerGap(591, Short.MAX_VALUE))
        );
        titlebarLayout.setVerticalGroup(
            titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlebarLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel8)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        DetailsBienes.getContentPane().add(titlebar, java.awt.BorderLayout.PAGE_START);

        scene.setPreferredSize(new java.awt.Dimension(781, 653));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Clasificación:");

        grupo.setEnabled(false);

        sgrp.setEnabled(false);

        seccion.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("-");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("-");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Descripción:");

        desc.setColumns(20);
        desc.setRows(5);
        desc.setWrapStyleWord(true);
        desc.setEnabled(false);
        jScrollPane4.setViewportView(desc);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Nº de Bien:");

        nbien.setEnabled(false);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Estado:");

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "EXCELENTE", "BUENO", "REGULAR", "MALO", "INSERVIBLE" }));
        comboEstado.setEnabled(false);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Status:");

        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "A DESINCORPORAR", "UBICADO", "FALTANTE" }));
        comboStatus.setEnabled(false);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Asignado a:");

        comboIdAsig.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboIdAsig.setEnabled(false);
        comboIdAsig.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboIdAsigItemStateChanged(evt);
            }
        });

        jButton11.setText("Registrar un nuevo trabajador");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Ubicado en:");

        ubic.setEnabled(false);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Costo (Bs.):");

        costo.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Entidad:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Sector:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Unidad:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Servicio:");

        servCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        servCombo.setEnabled(false);
        servCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                servComboItemStateChanged(evt);
            }
        });
        servCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servComboActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Fecha inventariado:");

        fechatxt.setEnabled(false);

        txtAsig.setEditable(false);

        entidadTxt.setEditable(false);

        unidadTxt.setEditable(false);

        sectorTxt.setEditable(false);

        servTxt.setEditable(false);

        jButton14.setText("Marcar como \"A desincorporar\"");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("Registrar o ver entes");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("MODIFICAR");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jButton16.setText("CERRAR");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout sceneLayout = new javax.swing.GroupLayout(scene);
        scene.setLayout(sceneLayout);
        sceneLayout.setHorizontalGroup(
            sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sceneLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sceneLayout.createSequentialGroup()
                        .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(sceneLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sceneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(sceneLayout.createSequentialGroup()
                                        .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nbien)
                                            .addGroup(sceneLayout.createSequentialGroup()
                                                .addComponent(grupo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(sgrp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(seccion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(comboEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(sceneLayout.createSequentialGroup()
                                        .addComponent(comboIdAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAsig)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(ubic, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(sceneLayout.createSequentialGroup()
                                        .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(sceneLayout.createSequentialGroup()
                                        .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(entidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fechatxt, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(sceneLayout.createSequentialGroup()
                                                .addComponent(unidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(servCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(servTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sceneLayout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sectorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sceneLayout.setVerticalGroup(
            sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sceneLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(sceneLayout.createSequentialGroup()
                            .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(grupo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(sgrp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(seccion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nbien, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(sceneLayout.createSequentialGroup()
                            .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(sceneLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboIdAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(sceneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAsig)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ubic, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(entidadTxt, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sectorTxt))
                .addGap(10, 10, 10)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(servTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechatxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        DetailsBienes.getContentPane().add(scene, java.awt.BorderLayout.CENTER);

        DetailsIncorporaciones.setMinimumSize(new java.awt.Dimension(781, 690));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Detalles de la incorporación");

        javax.swing.GroupLayout titlebar1Layout = new javax.swing.GroupLayout(titlebar1);
        titlebar1.setLayout(titlebar1Layout);
        titlebar1Layout.setHorizontalGroup(
            titlebar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlebar1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel14)
                .addContainerGap(502, Short.MAX_VALUE))
        );
        titlebar1Layout.setVerticalGroup(
            titlebar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlebar1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel14)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        DetailsIncorporaciones.getContentPane().add(titlebar1, java.awt.BorderLayout.PAGE_START);

        scene1.setPreferredSize(new java.awt.Dimension(781, 653));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Clasificación:");

        grupo1.setEnabled(false);

        sgrp1.setEnabled(false);

        seccion1.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("-");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("-");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setText("Descripción:");

        desc1.setColumns(20);
        desc1.setRows(5);
        desc1.setWrapStyleWord(true);
        desc1.setEnabled(false);
        jScrollPane6.setViewportView(desc1);

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel45.setText("Nº de Bien:");

        nbien1.setEnabled(false);

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel50.setText("Costo (Bs.):");

        costo1.setEnabled(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel51.setText("Entidad:");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel52.setText("Sector:");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel53.setText("Unidad:");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setText("Servicio:");

        servCombo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        servCombo1.setEnabled(false);
        servCombo1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                servCombo1ItemStateChanged(evt);
            }
        });
        servCombo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servCombo1ActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel55.setText("Fecha inventariado:");

        fechatxt1.setEnabled(false);

        entidadTxt1.setEditable(false);

        unidadTxt1.setEditable(false);

        sectorTxt1.setEditable(false);

        servTxt1.setEditable(false);

        jToggleButton3.setText("MODIFICAR");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jButton47.setText("CERRAR");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        jToggleButton4.setText("Eliminar Registro");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel56.setText("Nº Factura:");

        nf_txt.setEnabled(false);

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setText("Orden de Compra:");

        ordc_txt.setEnabled(false);

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel58.setText("Concepto:");

        concLista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01-. Inventario inicial.", "02-. Incorporaciones por traspaso. ", "03-. Compras. ", "04-. Construcción de Inmuebles. ", "05-. Adiciones mejoras. ", "06-. Producción de elementos (muebles). ", "07-. Suministro de Bienes de otras entidades. ", "09-. Incorporación de semovientes. ", "10-. Reconstrucción de equipos. ", "11-. Incorporación por donación. ", "12-. Incorporación por permuta. ", "13-. Adscripción de Bienes Inmuebles.", "14-. Omisión en inventario. ", "16-. Incorporación por cambio de subgrupo. ", "17-. Corrección de desincorporación.", "18-. Incorporación por otros conceptos.", "19-. Incorporación de muebles procedentes de los almacenes.", "20-. Herencia vacantes." }));
        concLista.setEnabled(false);

        javax.swing.GroupLayout scene1Layout = new javax.swing.GroupLayout(scene1);
        scene1.setLayout(scene1Layout);
        scene1Layout.setHorizontalGroup(
            scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scene1Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scene1Layout.createSequentialGroup()
                        .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, scene1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(scene1Layout.createSequentialGroup()
                                        .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nbien1)
                                            .addGroup(scene1Layout.createSequentialGroup()
                                                .addComponent(grupo1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(sgrp1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(seccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(223, 223, 223))
                                    .addGroup(scene1Layout.createSequentialGroup()
                                        .addComponent(costo1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(scene1Layout.createSequentialGroup()
                                        .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(entidadTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fechatxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(scene1Layout.createSequentialGroup()
                                                .addComponent(unidadTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(servCombo1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(servTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(scene1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(scene1Layout.createSequentialGroup()
                                        .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(scene1Layout.createSequentialGroup()
                                                .addComponent(nf_txt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(concLista, 0, 337, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ordc_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scene1Layout.createSequentialGroup()
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sectorTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        scene1Layout.setVerticalGroup(
            scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scene1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scene1Layout.createSequentialGroup()
                        .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(grupo1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sgrp1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(seccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nbien1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nf_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ordc_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(concLista, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(costo1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(entidadTxt1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sectorTxt1))
                .addGap(10, 10, 10)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(servTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unidadTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(scene1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechatxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        DetailsIncorporaciones.getContentPane().add(scene1, java.awt.BorderLayout.CENTER);

        DetailsDesIncorporaciones.setMinimumSize(new java.awt.Dimension(781, 690));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel48.setText("Detalles de la desincorporación");

        javax.swing.GroupLayout titlebar2Layout = new javax.swing.GroupLayout(titlebar2);
        titlebar2.setLayout(titlebar2Layout);
        titlebar2Layout.setHorizontalGroup(
            titlebar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlebar2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel48)
                .addContainerGap(474, Short.MAX_VALUE))
        );
        titlebar2Layout.setVerticalGroup(
            titlebar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlebar2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel48)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        DetailsDesIncorporaciones.getContentPane().add(titlebar2, java.awt.BorderLayout.PAGE_START);

        scene2.setPreferredSize(new java.awt.Dimension(781, 653));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel49.setText("Clasificación:");

        grupo4.setEnabled(false);

        sgrp2.setEnabled(false);

        seccion2.setEnabled(false);

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("-");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("-");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel61.setText("Descripción:");

        desc2.setColumns(20);
        desc2.setRows(5);
        desc2.setWrapStyleWord(true);
        desc2.setEnabled(false);
        jScrollPane7.setViewportView(desc2);

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel62.setText("Nº de Bien:");

        nbien2.setEnabled(false);

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel65.setText("Costo (Bs.):");

        costo2.setEnabled(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel66.setText("Entidad:");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel67.setText("Sector:");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel68.setText("Unidad:");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel69.setText("Servicio:");

        servCombo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        servCombo2.setEnabled(false);
        servCombo2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                servCombo2ItemStateChanged(evt);
            }
        });
        servCombo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servCombo2ActionPerformed(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setText("Fecha inventariado:");

        fechatxt2.setEnabled(false);

        entidadTxt2.setEditable(false);

        unidadTxt2.setEditable(false);

        sectorTxt2.setEditable(false);

        servTxt2.setEditable(false);

        jToggleButton5.setText("MODIFICAR");
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        jButton48.setText("CERRAR");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        jToggleButton6.setText("Eliminar Registro");
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel71.setText("Nº Acta:");

        nActa.setEnabled(false);

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel73.setText("Concepto:");

        concLista2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01-. Inventario inicial.", "02-. Incorporaciones por traspaso. ", "03-. Compras. ", "04-. Construcción de Inmuebles. ", "05-. Adiciones mejoras. ", "06-. Producción de elementos (muebles). ", "07-. Suministro de Bienes de otras entidades. ", "09-. Incorporación de semovientes. ", "10-. Reconstrucción de equipos. ", "11-. Incorporación por donación. ", "12-. Incorporación por permuta. ", "13-. Adscripción de Bienes Inmuebles.", "14-. Omisión en inventario. ", "16-. Incorporación por cambio de subgrupo. ", "17-. Corrección de desincorporación.", "18-. Incorporación por otros conceptos.", "19-. Incorporación de muebles procedentes de los almacenes.", "20-. Herencia vacantes." }));
        concLista2.setEnabled(false);

        javax.swing.GroupLayout scene2Layout = new javax.swing.GroupLayout(scene2);
        scene2.setLayout(scene2Layout);
        scene2Layout.setHorizontalGroup(
            scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scene2Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scene2Layout.createSequentialGroup()
                        .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, scene2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(scene2Layout.createSequentialGroup()
                                        .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nbien2)
                                            .addGroup(scene2Layout.createSequentialGroup()
                                                .addComponent(grupo4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(sgrp2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(seccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(223, 223, 223))
                                    .addGroup(scene2Layout.createSequentialGroup()
                                        .addComponent(costo2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(scene2Layout.createSequentialGroup()
                                        .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(entidadTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fechatxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(scene2Layout.createSequentialGroup()
                                                .addComponent(unidadTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(servCombo2, 0, 79, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(servTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(scene2Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                                    .addComponent(concLista2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nActa)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scene2Layout.createSequentialGroup()
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sectorTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        scene2Layout.setVerticalGroup(
            scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scene2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scene2Layout.createSequentialGroup()
                        .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(grupo4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sgrp2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(seccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nbien2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7)
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nActa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(concLista2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(costo2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(entidadTxt2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sectorTxt2))
                .addGap(10, 10, 10)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(servTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unidadTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(scene2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechatxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        DetailsDesIncorporaciones.getContentPane().add(scene2, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(".:: Sistema de Bienes ::.");
        setMinimumSize(new java.awt.Dimension(1136, 775));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        scenary1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        scenary1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        scenary1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scenary1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Menú Principal");

        jLabel18.setText("¡Bienvenido, ");

        pplname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pplname.setText("...");

        jLabel20.setText("!");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Cambiar la contraseña");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton9.setText("Cambiar Información de la Cuenta");
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);

        jButton10.setText("Eliminar cuenta");
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pplname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)))
                .addContainerGap(607, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(pplname)
                    .addComponent(jLabel20)
                    .addComponent(jButton1))
                .addGap(32, 32, 32)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(454, Short.MAX_VALUE))
        );

        btnMODE.setBackground(new java.awt.Color(0, 204, 153));
        btnMODE.setIcon(IconFontSwing.buildIcon(FontAwesome.ADJUST, 24, new Color(0, 0, 0)));
        btnMODE.setToolTipText("MODO CLARO/NOCTURNO");
        btnMODE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMODEActionPerformed(evt);
            }
        });

        logoutBtn.setBackground(new java.awt.Color(0, 204, 153));
        logoutBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 24, new Color(0, 0, 0)));
        logoutBtn.setToolTipText("CERRAR SESIÓN");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnMODE, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnMODE, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        scenary1.addTab("CUENTA DE USUARIO", jPanel1);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros de Inventario:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        entidadesList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton2.setText("VISUALIZAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton33.setText("Generar Reporte");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(entidadesList, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entidadesList, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("POR ENTIDAD", jPanel14);

        sectoresList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton3.setText("VISUALIZAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton32.setText("Generar Reporte");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(sectoresList, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sectoresList, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("POR SECTOR", jPanel18);

        unidadesList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton4.setText("VISUALIZAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton31.setText("Generar Reporte");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(unidadesList, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unidadesList, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("POR UNIDAD DE TRABAJO", jPanel19);

        serviciosList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton5.setText("VISUALIZAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton30.setText("Generar Reporte");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(serviciosList, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serviciosList, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("POR SERVICIO", jPanel20);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jButton6.setText("Ver todo el inventario");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setText("Generar Reporte");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton23.setText("Añadir un bien");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("       Seleccionando:");

        selectedLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectedLabel.setText("...");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(selectedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        invTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        invTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(invTable);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("    Inventario de Bienes");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("       Viendo inventario para:");

        labelVisualizando.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelVisualizando.setText("...");

        jButton41.setText("ACTUALIZAR TABLA");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelVisualizando)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton41)
                .addGap(82, 82, 82))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelVisualizando, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        scenary1.addTab("INVENTARIO", jPanel2);

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros de Inventario:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        entidadesList2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton7.setText("VISUALIZAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel32.setText("Filtrar por concepto:");

        concList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "01-. Inventario inicial.", "02-. Incorporaciones por traspaso. ", "03-. Compras. ", "04-. Construcción de Inmuebles. ", "05-. Adiciones mejoras. ", "06-. Producción de elementos (muebles). ", "07-. Suministro de Bienes de otras entidades. ", "09-. Incorporación de semovientes. ", "10-. Reconstrucción de equipos. ", "11-. Incorporación por donación. ", "12-. Incorporación por permuta. ", "13-. Adscripción de Bienes Inmuebles.", "14-. Omisión en inventario. ", "16-. Incorporación por cambio de subgrupo. ", "17-. Corrección de desincorporación.", "18-. Incorporación por otros conceptos.", "19-. Incorporación de muebles procedentes de los almacenes.", "20-. Herencia vacantes." }));

        jButton22.setText("Generar Reporte");
        jButton22.setToolTipText("");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32)
                    .addComponent(entidadesList2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entidadesList2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton22)
                .addGap(9, 9, 9))
        );

        jTabbedPane3.addTab("POR ENTIDAD", jPanel37);

        sectoresList2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel33.setText("Filtrar por concepto:");

        concList1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "01-. Inventario inicial.", "02-. Incorporaciones por traspaso. ", "03-. Compras. ", "04-. Construcción de Inmuebles. ", "05-. Adiciones mejoras. ", "06-. Producción de elementos (muebles). ", "07-. Suministro de Bienes de otras entidades. ", "09-. Incorporación de semovientes. ", "10-. Reconstrucción de equipos. ", "11-. Incorporación por donación. ", "12-. Incorporación por permuta. ", "13-. Adscripción de Bienes Inmuebles.", "14-. Omisión en inventario. ", "16-. Incorporación por cambio de subgrupo. ", "17-. Corrección de desincorporación.", "18-. Incorporación por otros conceptos.", "19-. Incorporación de muebles procedentes de los almacenes.", "20-. Herencia vacantes." }));

        jButton13.setText("VISUALIZAR");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton28.setText("Generar Reporte");
        jButton28.setToolTipText("");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33)
                    .addComponent(sectoresList2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sectoresList2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton28)
                .addGap(11, 11, 11))
        );

        jTabbedPane3.addTab("POR SECTOR", jPanel38);

        unidadesList2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel34.setText("Filtrar por concepto:");

        concList2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "01-. Inventario inicial.", "02-. Incorporaciones por traspaso. ", "03-. Compras. ", "04-. Construcción de Inmuebles. ", "05-. Adiciones mejoras. ", "06-. Producción de elementos (muebles). ", "07-. Suministro de Bienes de otras entidades. ", "09-. Incorporación de semovientes. ", "10-. Reconstrucción de equipos. ", "11-. Incorporación por donación. ", "12-. Incorporación por permuta. ", "13-. Adscripción de Bienes Inmuebles.", "14-. Omisión en inventario. ", "16-. Incorporación por cambio de subgrupo. ", "17-. Corrección de desincorporación.", "18-. Incorporación por otros conceptos.", "19-. Incorporación de muebles procedentes de los almacenes.", "20-. Herencia vacantes." }));

        jButton17.setText("VISUALIZAR");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton29.setText("Generar Reporte");
        jButton29.setToolTipText("");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34)
                    .addComponent(unidadesList2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(unidadesList2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jButton29)
                .addGap(7, 7, 7))
        );

        jTabbedPane3.addTab("POR UNIDAD DE TRABAJO", jPanel39);

        serviciosList2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel35.setText("Filtrar por concepto:");

        concList3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "01-. Inventario inicial.", "02-. Incorporaciones por traspaso. ", "03-. Compras. ", "04-. Construcción de Inmuebles. ", "05-. Adiciones mejoras. ", "06-. Producción de elementos (muebles). ", "07-. Suministro de Bienes de otras entidades. ", "09-. Incorporación de semovientes. ", "10-. Reconstrucción de equipos. ", "11-. Incorporación por donación. ", "12-. Incorporación por permuta. ", "13-. Adscripción de Bienes Inmuebles.", "14-. Omisión en inventario. ", "16-. Incorporación por cambio de subgrupo. ", "17-. Corrección de desincorporación.", "18-. Incorporación por otros conceptos.", "19-. Incorporación de muebles procedentes de los almacenes.", "20-. Herencia vacantes." }));

        jButton18.setText("VISUALIZAR");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton34.setText("Generar Reporte");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35)
                    .addComponent(serviciosList2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serviciosList2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton18)
                .addGap(1, 1, 1)
                .addComponent(jButton34)
                .addGap(11, 11, 11))
        );

        jTabbedPane3.addTab("POR SERVICIO", jPanel40);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jButton24.setText("Ver todo el inventario");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton25.setText("Generar Reporte");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton35.setText("Registrar Incorporación");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton24, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("       Seleccionando:");

        selectedLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectedLabel2.setText("...");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectedLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(selectedLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        inctable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        inctable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inctableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(inctable);

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setText("    Inventario de Incorporaciones");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("       Viendo inventario para:");

        labelVisualizando2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelVisualizando2.setText("...");

        jButton42.setText("ACTUALIZAR TABLA");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelVisualizando2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton42)
                .addGap(82, 82, 82))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelVisualizando2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        scenary1.addTab("INCORPORAR", jPanel11);

        jPanel45.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros de Inventario:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        entidadesList3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton12.setText("VISUALIZAR");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel36.setText("Filtrar por concepto:");

        concList4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "51-. Desincorporación por traspaso ", "52-. Venta ", "54-. Suministros de Bienes a otras entidades ", "55-. Desarme ", "56-. Inservibilidad ", "57-. Deterioro ", "58-. Demolición ", "59-. Desincorporación de semovientes ", "60-. Faltantes por investigar ", "61-. Desincorporación por permuta ", "62-. Desincorporación por donación ", "63-. Desincorporación por adscripción de Bienes Inmuebles ", "65-. Desincorporación por cambio de subgrupo ", "66-. Corrección de incorporaciones ", "67-. Desincorporación por otros conceptos" }));

        jButton36.setText("Generar Reporte");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36)
                    .addComponent(entidadesList3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entidadesList3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addGap(1, 1, 1)
                .addComponent(jButton36)
                .addGap(13, 13, 13))
        );

        jTabbedPane4.addTab("POR ENTIDAD", jPanel46);

        sectoresList3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel37.setText("Filtrar por concepto:");

        concList5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "51-. Desincorporación por traspaso ", "52-. Venta ", "54-. Suministros de Bienes a otras entidades ", "55-. Desarme ", "56-. Inservibilidad ", "57-. Deterioro ", "58-. Demolición ", "59-. Desincorporación de semovientes ", "60-. Faltantes por investigar ", "61-. Desincorporación por permuta ", "62-. Desincorporación por donación ", "63-. Desincorporación por adscripción de Bienes Inmuebles ", "65-. Desincorporación por cambio de subgrupo ", "66-. Corrección de incorporaciones ", "67-. Desincorporación por otros conceptos" }));

        jButton19.setText("VISUALIZAR");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton37.setText("Generar Reporte");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37)
                    .addComponent(sectoresList3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sectoresList3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton19)
                .addGap(1, 1, 1)
                .addComponent(jButton37)
                .addGap(13, 13, 13))
        );

        jTabbedPane4.addTab("POR SECTOR", jPanel47);

        unidadesList3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel38.setText("Filtrar por concepto:");

        concList6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "51-. Desincorporación por traspaso ", "52-. Venta ", "54-. Suministros de Bienes a otras entidades ", "55-. Desarme ", "56-. Inservibilidad ", "57-. Deterioro ", "58-. Demolición ", "59-. Desincorporación de semovientes ", "60-. Faltantes por investigar ", "61-. Desincorporación por permuta ", "62-. Desincorporación por donación ", "63-. Desincorporación por adscripción de Bienes Inmuebles ", "65-. Desincorporación por cambio de subgrupo ", "66-. Corrección de incorporaciones ", "67-. Desincorporación por otros conceptos" }));

        jButton20.setText("VISUALIZAR");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton38.setText("Generar Reporte");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel38)
                    .addComponent(unidadesList3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(unidadesList3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton20)
                .addGap(1, 1, 1)
                .addComponent(jButton38)
                .addGap(13, 13, 13))
        );

        jTabbedPane4.addTab("POR UNIDAD DE TRABAJO", jPanel48);

        serviciosList3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel39.setText("Filtrar por concepto:");

        concList7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "51-. Desincorporación por traspaso ", "52-. Venta ", "54-. Suministros de Bienes a otras entidades ", "55-. Desarme ", "56-. Inservibilidad ", "57-. Deterioro ", "58-. Demolición ", "59-. Desincorporación de semovientes ", "60-. Faltantes por investigar ", "61-. Desincorporación por permuta ", "62-. Desincorporación por donación ", "63-. Desincorporación por adscripción de Bienes Inmuebles ", "65-. Desincorporación por cambio de subgrupo ", "66-. Corrección de incorporaciones ", "67-. Desincorporación por otros conceptos" }));

        jButton21.setText("VISUALIZAR");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton39.setText("Generar Reporte");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concList7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39)
                    .addComponent(serviciosList3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serviciosList3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concList7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton21)
                .addGap(1, 1, 1)
                .addComponent(jButton39)
                .addGap(13, 13, 13))
        );

        jTabbedPane4.addTab("POR SERVICIO", jPanel49);

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jButton26.setText("Ver todo el inventario");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton27.setText("Generar Reporte");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton40.setText("Registrar Desincorporación");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton26, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(jButton40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel40.setText("       Seleccionando:");

        selectedLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectedLabel3.setText("...");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectedLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(selectedLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        desinctable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        desinctable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                desinctableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(desinctable);

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel41.setText("    Inventario de Desincorporaciones");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel42.setText("       Viendo inventario para:");

        labelVisualizando3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelVisualizando3.setText("...");

        jButton43.setText("ACTUALIZAR TABLA");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelVisualizando3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton43)
                .addGap(82, 82, 82))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelVisualizando3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        scenary1.addTab("DESINCORPORAR", jPanel15);

        jButton54.setText("Ver todo el inventario");
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        jButton55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton55.setText("Generar Reporte");
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        jButton56.setText("Registrar Desincorporación");
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros de Inventario:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        entidadesList1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton58.setText("VISUALIZAR");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });

        jButton59.setText("Generar Reporte");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(entidadesList1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton58)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entidadesList1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("POR ENTIDAD", jPanel31);

        sectoresList1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton60.setText("VISUALIZAR");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        jButton61.setText("Generar Reporte");
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(sectoresList1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton60)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sectoresList1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("POR SECTOR", jPanel32);

        unidadesList1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton62.setText("VISUALIZAR");
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });

        jButton63.setText("Generar Reporte");
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(unidadesList1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton62)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unidadesList1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton63, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("POR UNIDAD DE TRABAJO", jPanel33);

        serviciosList1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton64.setText("VISUALIZAR");
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });

        jButton65.setText("Generar Reporte");
        jButton65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton65ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(serviciosList1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton64)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serviciosList1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton64, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton65, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("POR SERVICIO", jPanel34);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton54, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(jButton56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel72.setText("       Seleccionando:");

        selectedLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectedLabel4.setText("...");

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectedLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(selectedLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        faltable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        faltable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                faltableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(faltable);

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel74.setText("    Reportes de Faltantes");

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel75.setText("       Viendo inventario para:");

        labelVisualizando4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelVisualizando4.setText("...");

        jButton57.setText("ACTUALIZAR TABLA");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addComponent(jLabel75)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelVisualizando4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jButton57)
                .addGap(82, 82, 82))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelVisualizando4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        scenary1.addTab("FALTANTES", jPanel29);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 943, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 766, Short.MAX_VALUE)
        );

        scenary1.addTab("REGISTRAR / VER ENTES", jPanel16);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 943, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 766, Short.MAX_VALUE)
        );

        scenary1.addTab("VER / REGISTRAR TRABAJADORES", jPanel17);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scenary1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scenary1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        // TODO add your handling code here:
        String apwd_s, npwd_s, npwd_s2;
        apwd = pwdactual.getPassword();
        npwd = pwdnueva.getPassword();
        npwd2 = pwdnueva2.getPassword();
        apwd_s = String.valueOf(apwd);
        npwd_s = String.valueOf(npwd);
        npwd_s2 = String.valueOf(npwd2);
        if (scc.setNewPasswd(user_data[1], apwd_s, npwd_s, npwd_s2)) {
            JOptionPane.showMessageDialog(null, "¡Cambio realizado con éxito!", ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.INFORMATION_MESSAGE);
            AccountMgr.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error mientras se modificaba la contraseña.\nAsegúrese que escribió los datos correctamente o de que tiene conexión a la base de datos.", ".::ERROR CRÍTICO - Sistema de Inventario de Bienes del Programa de Informática Integral::.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnBACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBACKActionPerformed
        // TODO add your handling code here:
        AccountMgr.setVisible(false);
    }//GEN-LAST:event_btnBACKActionPerformed

    private void seebtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seebtn1ActionPerformed
        // TODO add your handling code here:
        uiText = new JTextField();
        uiPwd = new JPasswordField();
        if (seebtn1.isSelected()) {
            //v
            pwdactual.setUI(uiText.getUI());
        } else {
            //f
            pwdactual.setUI(uiPwd.getUI());
        }
    }//GEN-LAST:event_seebtn1ActionPerformed

    private void seebtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seebtn2ActionPerformed
        // TODO add your handling code here:
        uiText = new JTextField();
        uiPwd = new JPasswordField();
        if (seebtn2.isSelected()) {
            //v
            pwdnueva.setUI(uiText.getUI());
        } else {
            //f
            pwdnueva.setUI(uiPwd.getUI());
        }
    }//GEN-LAST:event_seebtn2ActionPerformed

    private void seebtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seebtn3ActionPerformed
        // TODO add your handling code here:
        uiText = new JTextField();
        uiPwd = new JPasswordField();
        if (seebtn3.isSelected()) {
            //v
            pwdnueva2.setUI(uiText.getUI());
        } else {
            //f
            pwdnueva2.setUI(uiPwd.getUI());
        }
    }//GEN-LAST:event_seebtn3ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_formMouseClicked

    private void scenary1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scenary1MouseClicked
        // TODO add your handling code here:
        System.out.println("BOUNDS: " + this.getSize());
    }//GEN-LAST:event_scenary1MouseClicked

    private void invTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invTableMouseClicked
        // TODO add your handling code here:
        scc = new SqlControllerClass();

        System.out.println("Clicks: " + evt.getClickCount());

        if (evt.getClickCount() <= 1) {
            String data = invTable.getValueAt(invTable.getSelectedRow(), invTable.getSelectedColumn()).toString();
            selectedLabel.setText(data);
        } else {

            String BID = invTable.getValueAt(invTable.getSelectedRow(), 0).toString();
            System.out.println(BID);
            String[] data = scc.bienData(BID);
//            String cls = data[2];
//            System.out.println("cls " + cls);
            seeDetails(data, 0);

        }
    }//GEN-LAST:event_invTableMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Crear un formato de fecha personalizado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

        // Formatear la fecha actual como una cadena
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Imprimir la fecha formateada por consola
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        callReport(fechaFormateada, BIENES);


    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        invTable.setModel(tmls.modeloInventarioByAll());
        labelVisualizando.setText("...");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = serviciosList.getSelectedItem().toString();

        labelVisualizando.setText("SERVICIO " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        invTable.setModel(tmls.modeloInventarioByServicios(idExtraido));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = unidadesList.getSelectedItem().toString();

        labelVisualizando.setText("UNIDAD: " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        invTable.setModel(tmls.modeloInventarioByUnidades(idExtraido));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = sectoresList.getSelectedItem().toString();

        labelVisualizando.setText("SECTOR " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        invTable.setModel(tmls.modeloInventarioBySector(idExtraido));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = entidadesList.getSelectedItem().toString();

        labelVisualizando.setText("ENTIDAD " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        invTable.setModel(tmls.modeloInventarioByEntidad(idExtraido));

    }//GEN-LAST:event_jButton2ActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);

        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            JFrame.setDefaultLookAndFeelDecorated(true);
            SwingUtilities.invokeLater(() -> {
                // Actualizar la apariencia de la ventana principal
                updateUI();
            });
            authFrame athFrm = new authFrame();
            athFrm.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "ERROR CRÍTICO: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes - ERROR ::.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void btnMODEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMODEActionPerformed

        try {
            if (mode) {
                //si es claro, pasa a oscuro
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
                JFrame.setDefaultLookAndFeelDecorated(true);
                SwingUtilities.invokeLater(() -> {
                    // Actualizar la apariencia de la ventana principal
                    updateUI();
                });
                mode = false;
            } else {
                //si es oscuro, lo mismo
                UIManager.setLookAndFeel(new FlatMacLightLaf());
                JFrame.setDefaultLookAndFeelDecorated(true);
                SwingUtilities.invokeLater(() -> {
                    // Actualizar la apariencia de la ventana principal
                    updateUI();
                });
                mode = true;
            }
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("ERROR: " + e.getLocalizedMessage());
        }
    }//GEN-LAST:event_btnMODEActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        AccountMgr.setVisible(true);
        AccountMgr.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        inctable.setModel(tmls.modeloIncorporacionesByAll());
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        Date fechaActual = new Date();

        // Crear un formato de fecha personalizado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

        // Formatear la fecha actual como una cadena
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Imprimir la fecha formateada por consola
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        callReport(fechaFormateada, INCORPORACIONES);

    }//GEN-LAST:event_jButton25ActionPerformed

    private void inctableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inctableMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        scc = new SqlControllerClass();

        System.out.println("Clicks: " + evt.getClickCount());

        if (evt.getClickCount() <= 1) {
            String data = inctable.getValueAt(inctable.getSelectedRow(), inctable.getSelectedColumn()).toString();
            selectedLabel2.setText(data);
        } else {

            String BID = inctable.getValueAt(inctable.getSelectedRow(), 0).toString();
            System.out.println(BID);
            String[] data = scc.IncorpData(BID);
//            String cls = data[2];
//            System.out.println("cls " + cls);
            seeDetails(data, 1);

            JOptionPane.showMessageDialog(null, "¡Esta función aún no está disponible!", ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_inctableMouseClicked

    private void comboIdAsigItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboIdAsigItemStateChanged
        // TODO add your handling code here:
        scc = new SqlControllerClass();
        txtAsig.setText(scc.getWorker(comboIdAsig.getSelectedItem().toString()));
    }//GEN-LAST:event_comboIdAsigItemStateChanged

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        DetailsBienes.setVisible(false);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        scc = new SqlControllerClass();

        if (jToggleButton2.isSelected()) {
            grupo.setEnabled(true);
            sgrp.setEnabled(true);
            seccion.setEnabled(true);
            comboEstado.setEnabled(true);
            comboStatus.setEnabled(true);
            desc.setEnabled(true);
            comboIdAsig.setEnabled(true);
            ubic.setEnabled(true);
            costo.setEnabled(true);
            servCombo.setEnabled(true);
            fechatxt.setEnabled(true);
        } else {
            grupo.setEnabled(false);
            sgrp.setEnabled(false);
            seccion.setEnabled(false);
            comboEstado.setEnabled(false);
            comboStatus.setEnabled(false);
            desc.setEnabled(false);
            comboIdAsig.setEnabled(false);
            ubic.setEnabled(false);
            costo.setEnabled(false);
            servCombo.setEnabled(false);
            fechatxt.setEnabled(false);

            //para el grupo, subgrupo y seccion:
            String grupost = this.grupo.getText(),
                    subgrp = this.sgrp.getText(),
                    sec = this.seccion.getText();
            //para el numero de bien, status y estado:
            String stat = this.comboStatus.getSelectedItem().toString(),
                    est = this.comboEstado.getSelectedItem().toString(),
                    nb = this.nbien.getText();
            //para la descripcion:
            String dsc = this.desc.getText();
            //para el usuario asignado:
            String idUser = this.comboIdAsig.getSelectedItem().toString(),
                    ubicacion = this.ubic.getText();
            //para el costo:
            String costo_adq = this.costo.getText();
            //para el servicio seleccionado:
            String IDserv = this.servCombo.getSelectedItem().toString();
            //para la fecha:
            String fecha = this.fechatxt.getText();

            String[] data = new String[]{
                grupost, //0
                subgrp, //1
                sec, //2
                stat, //3
                est, //4
                dsc, //5
                idUser, //6
                ubicacion, //7
                costo_adq, //8
                IDserv, //9
                fecha, //10
                nb, //11
            }; //son 12 (o sea, el vector termina en 11)

            if (JOptionPane.showConfirmDialog(null, "¿Está seguro de proceder con los cambios?", "Confirme modificaciones", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                if (scc.updateBien(nb, data)) {
                    JOptionPane.showMessageDialog(null, "Se han realizado cambios con exito", ".:: INFORMACION ::.", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar", ".:: ERROR ::.", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                //Selecciono no
            }
        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void servComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_servComboItemStateChanged
        // TODO add your handling code here:
        String ID = servCombo.getSelectedItem().toString();
        setEntesDetails(ID);
    }//GEN-LAST:event_servComboItemStateChanged

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        scc = new SqlControllerClass();
        String nb = nbien.getText();
        if (scc.updateBienSTATUS(nb, "A DESINCORPORAR")) {
            JOptionPane.showMessageDialog(null, "Se han realizado cambios con exito", ".:: INFORMACION ::.", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar", ".:: ERROR ::.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        DetailsBienes.setVisible(false);
        scenary1.setSelectedIndex(5);
    }//GEN-LAST:event_jButton15ActionPerformed

    //---------------INCORPORACIONES----------------//
    
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:

        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = entidadesList2.getSelectedItem().toString();

        labelVisualizando2.setText("ENTIDAD " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        inctable.setModel(tmls.modeloIncorporacionesByEntidad(vectorEnte));
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = sectoresList2.getSelectedItem().toString();

        labelVisualizando2.setText("SECTOR " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList1.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        inctable.setModel(tmls.modeloIncorporacionesBySector(vectorEnte));
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = unidadesList2.getSelectedItem().toString();

        labelVisualizando2.setText("UNIDAD " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList2.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        inctable.setModel(tmls.modeloIncorporacionesByUnidades(vectorEnte));
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = serviciosList2.getSelectedItem().toString();

        labelVisualizando2.setText("SERVICIO " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList3.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        inctable.setModel(tmls.modeloIncorporacionesByServicios(vectorEnte));
    }//GEN-LAST:event_jButton18ActionPerformed

    //---------------DESINCORPORACIONES----------------//
    
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:

        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = entidadesList3.getSelectedItem().toString();

        labelVisualizando3.setText("ENTIDAD " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList4.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        desinctable.setModel(tmls.modeloDesIncorporacionesByEntidad(vectorEnte));
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = sectoresList3.getSelectedItem().toString();

        labelVisualizando3.setText("SECTOR " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList5.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        desinctable.setModel(tmls.modeloDesIncorporacionesBySector(vectorEnte));
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = unidadesList3.getSelectedItem().toString();

        labelVisualizando3.setText("UNIDAD " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList6.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        desinctable.setModel(tmls.modeloDesIncorporacionesByUnidades(vectorEnte));
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        //Extrae el id del ente.
        String item, idEntidadExtraido = "";

        item = serviciosList3.getSelectedItem().toString();

        labelVisualizando3.setText("SERVICIO " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idEntidadExtraido += matcher.group();
        }

        //extrae el numero de concepto
        String item2, idConceptoExtraido2 = "";

        item2 = concList7.getSelectedItem().toString();

        patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        matcher = patron.matcher(item2);

        while (matcher.find()) {
            idConceptoExtraido2 += matcher.group();
        }

        String[] vectorEnte = new String[]{idEntidadExtraido, idConceptoExtraido2};

        desinctable.setModel(tmls.modeloDesIncorporacionesByServicios(vectorEnte));
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        desinctable.setModel(tmls.modeloDesIncorporacionesByAll());
        labelVisualizando3.setText("...");
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        Date fechaActual = new Date();

        // Crear un formato de fecha personalizado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

        // Formatear la fecha actual como una cadena
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Imprimir la fecha formateada por consola
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        callReport(fechaFormateada, DESINCORPORACIONES);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void desinctableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desinctableMouseClicked

        scc = new SqlControllerClass();

        System.out.println("Clicks: " + evt.getClickCount());

        if (evt.getClickCount() <= 1) {
            String data = desinctable.getValueAt(desinctable.getSelectedRow(), desinctable.getSelectedColumn()).toString();
            selectedLabel3.setText(data);
        } else {
            
            String BID = desinctable.getValueAt(desinctable.getSelectedRow(), 0).toString();
            System.out.println(BID);
            String[] data = scc.DesIncorpData(BID);
//            String cls = data[2];
//            System.out.println("cls " + cls);
            seeDetails(data, 2);
            
            
        }
    }//GEN-LAST:event_desinctableMouseClicked

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = serviciosList.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        Date fechaActual = new Date();

        // Crear un formato de fecha personalizado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

        // Formatear la fecha actual como una cadena
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Imprimir la fecha formateada por consola
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        callReportBienesByServicio(fechaFormateada, BIENES, idExtraido, "");
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = unidadesList.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        Date fechaActual = new Date();

        // Crear un formato de fecha personalizado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

        // Formatear la fecha actual como una cadena
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Imprimir la fecha formateada por consola
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        callReportBienesByUnidad(fechaFormateada, BIENES, idExtraido, "");
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = sectoresList.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        Date fechaActual = new Date();

        // Crear un formato de fecha personalizado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

        // Formatear la fecha actual como una cadena
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Imprimir la fecha formateada por consola
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        callReportBienesBySector(fechaFormateada, BIENES, idExtraido, "");
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = entidadesList.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        Date fechaActual = new Date();

        // Crear un formato de fecha personalizado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");

        // Formatear la fecha actual como una cadena
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Imprimir la fecha formateada por consola
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        callReportBienesByEntidades(fechaFormateada, BIENES, idExtraido, "");
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // Obtiene el id de la entidad
        String item;
        String idExtraido = "";

        item = entidadesList2.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", INCORPORACIONES (Entidad), CONCEPTO: " + concNumExtraido);

        callReportBienesByEntidades(fechaFormateada, INCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        String item;
        String idExtraido = "";

        item = sectoresList2.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList1.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", INCORPORACIONES (sector), CONCEPTO: " + concNumExtraido);

        callReportBienesBySector(fechaFormateada, INCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = unidadesList2.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList2.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", INCORPORACIONES (unidad), CONCEPTO: " + concNumExtraido);

        callReportBienesByUnidad(fechaFormateada, INCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = serviciosList2.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList3.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", INCORPORACIONES (servicio), CONCEPTO: " + concNumExtraido);

        callReportBienesByServicio(fechaFormateada, INCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        // Obtiene el id de la entidad
        String item;
        String idExtraido = "";

        item = entidadesList3.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList4.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", DESINCORPORACIONES (Entidad), CONCEPTO: " + concNumExtraido);

        callReportBienesByEntidades(fechaFormateada, DESINCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = sectoresList3.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList5.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", DESINCORPORACIONES (sector), CONCEPTO: " + concNumExtraido);

        callReportBienesBySector(fechaFormateada, DESINCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = unidadesList3.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList6.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", DESINCORPORACIONES (unidad), CONCEPTO: " + concNumExtraido);

        callReportBienesByUnidad(fechaFormateada, DESINCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        String item;
        String idExtraido = "";

        item = serviciosList3.getSelectedItem().toString();

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }
        //--------------------------------------//

        //Obtiene el concepto y lo formatea--//
        String conc = concList7.getSelectedItem().toString();
        String concNum, concNumExtraido = "";

        if (conc.equals("-")) {
            concNum = "";
        } else {
            concNum = conc;
        }

        Pattern patron2 = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher2 = patron2.matcher(concNum);

        while (matcher2.find()) {
            concNumExtraido += matcher2.group();
        }
        //------------------------------------//

        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        System.out.println("Fecha actual formateada: " + fechaFormateada);

        System.out.println("ID: " + idExtraido + ", DESINCORPORACIONES (servicio), CONCEPTO: " + concNumExtraido);

        callReportBienesByServicio(fechaFormateada, DESINCORPORACIONES, idExtraido, concNumExtraido);
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        AddingBien adg = new AddingBien();
        adg.setVisible(true);
    }//GEN-LAST:event_jButton23ActionPerformed


    private void servComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_servComboActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        invTable.setModel(tmls.modeloInventarioByAll());
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        AddingIncorporacion adi = new AddingIncorporacion();
        adi.setiduser(user_data[1]);
        adi.setVisible(true);
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        inctable.setModel(tmls.modeloIncorporacionesByAll());
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        AddingDesincorporacion ads = new AddingDesincorporacion();
        ads.setVisible(true);
        ads.setiduser(user_data[1]);
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        desinctable.setModel(tmls.modeloDesIncorporacionesByAll());
        labelVisualizando3.setText("...");
    }//GEN-LAST:event_jButton43ActionPerformed

    private void servCombo1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_servCombo1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_servCombo1ItemStateChanged

    private void servCombo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servCombo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_servCombo1ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void servCombo2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_servCombo2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_servCombo2ItemStateChanged

    private void servCombo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servCombo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_servCombo2ActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton6ActionPerformed

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        invTable.setModel(tmls.modeloFaltantesByAll());
        labelVisualizando.setText("...");
    }//GEN-LAST:event_jButton54ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton56ActionPerformed

    private void faltableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faltableMouseClicked
        // TODO add your handling code here:
        scc = new SqlControllerClass();

        System.out.println("Clicks: " + evt.getClickCount());

        if (evt.getClickCount() <= 1) {
            String data = faltable.getValueAt(faltable.getSelectedRow(), faltable.getSelectedColumn()).toString();
            selectedLabel4.setText(data);
        } else {
            JOptionPane.showMessageDialog(null, "FUNCION NO DESARROLLADA", "FUNCION NO DESARROLLADA", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_faltableMouseClicked

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();
        faltable.setModel(tmls.modeloFaltantesByAll());
        labelVisualizando.setText("...");
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = entidadesList1.getSelectedItem().toString();

        labelVisualizando4.setText("ENTIDAD " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        faltable.setModel(tmls.modeloFaltantesByEntidad(idExtraido));
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = sectoresList1.getSelectedItem().toString();

        labelVisualizando4.setText("SECTOR " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        faltable.setModel(tmls.modeloFaltantesBySector(idExtraido));
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = unidadesList1.getSelectedItem().toString();

        labelVisualizando4.setText("UNIDAD: " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        faltable.setModel(tmls.modeloFaltantesByUnidades(idExtraido));
    }//GEN-LAST:event_jButton62ActionPerformed

    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
        // TODO add your handling code here:
        tmls = new TableModels();

        String item;
        String idExtraido = "";

        item = serviciosList1.getSelectedItem().toString();

        labelVisualizando4.setText("SERVICIO " + item);

        Pattern patron = Pattern.compile("\\d+"); // Busca uno o mas digitos de tipo entero
        Matcher matcher = patron.matcher(item);

        while (matcher.find()) {
            idExtraido += matcher.group();
        }

        faltable.setModel(tmls.modeloFaltantesByServicios(idExtraido));
    }//GEN-LAST:event_jButton64ActionPerformed

    private void jButton65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton65ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton65ActionPerformed

    private void setEntesDetails(String byID) {
        scc = new SqlControllerClass();
        String[] route;
        route = scc.getRutaEntes(byID);

        servTxt.setText(route[0]);
        sectorTxt.setText(route[1]);
        unidadTxt.setText(route[2]);
        entidadTxt.setText(route[3]);
    }

    private void seeDetails(String[] data, Integer destino) {

        switch (destino) {
            case 0:
                verBien(data);
                break;
            case 1:
                verIncorp(data);
                break;
            case 2:
                verDesIncorp(data);
                break;
        }

    }

    private void verBien(String[] data) {
        scc = new SqlControllerClass();

        //Establece los campos de GRUPO, SUBGRUPO Y SECCION
        grupo.setText(clasSplit(data[0])[0]);
        sgrp.setText(clasSplit(data[0])[1]);
        seccion.setText(clasSplit(data[0])[2]);

        //...
        nbien.setText(data[1]);
        desc.setText(data[2]);

        setEstado(data[1]);
        setStatus(data[1]);

        comboIdAsig.setModel(scc.getWorkers());
        txtAsig.setText(scc.getWorker(comboIdAsig.getSelectedItem().toString()));
        ubic.setText(data[6]);

        costo.setText(data[7]);
        fechatxt.setText(data[12]);

        servCombo.setModel(scc.getServicios());
        String[] ente = scc.getRutaEntes(servCombo.getSelectedItem().toString());
        entidadTxt.setText(ente[0]);
        sectorTxt.setText(ente[1]);
        unidadTxt.setText(ente[2]);
        servTxt.setText(ente[3]);

        //Salta la ventana de detalles
        DetailsBienes.setVisible(true);
        DetailsBienes.setLocationRelativeTo(null);
    }

    private void verIncorp(String[] data) {
        /*
        
        data = new String[]{
                    rst.getString("clasif"), 0
                    rst.getString("nb"), 1
                    rst.getString("desc"), 2
                    rst.getString("nfacto"), 3
                    rst.getString("ordcompra") 4,
                    rst.getString("montobs"), 5
                    rst.getString("identidad"), 6
                    rst.getString("idsector"), 7
                    rst.getString("idunidad"), 8
                    rst.getString("idservicio"), 9
                    rst.getString("fecha"), 10
                    rst.getString("conc") 11
                };
        
         */

        scc = new SqlControllerClass();

        //Establece los campos de GRUPO, SUBGRUPO Y SECCION
        grupo1.setText(clasSplit(data[0])[0]);
        sgrp1.setText(clasSplit(data[0])[1]);
        seccion1.setText(clasSplit(data[0])[2]);

        //...
        nbien1.setText(data[1]);
        desc1.setText(data[2]);

        String CONCEPTO = data[11];//falta por acomodar!!!!!!!!

        costo1.setText(data[5]);
        fechatxt1.setText(data[10]);

        String nFacto = data[3], ordCompra = data[4];
        nf_txt.setText(nFacto);
        ordc_txt.setText(ordCompra);

        servCombo1.setModel(scc.getServicios());
        String[] ente = scc.getRutaEntes(servCombo1.getSelectedItem().toString());
        entidadTxt1.setText(ente[0]);
        sectorTxt1.setText(ente[1]);
        unidadTxt1.setText(ente[2]);
        servTxt1.setText(ente[3]);

        //Salta la ventana de detalles
        DetailsIncorporaciones.setVisible(true);
        DetailsIncorporaciones.setLocationRelativeTo(null);
    }

    private void verDesIncorp(String[] data) {
        /*
        
        data = new String[]{
                    rst.getString("clasif"),0
                    rst.getString("nb"),1
                    rst.getString("desc"),2
                    rst.getString("desincActa"),3
                    rst.getString("montobs"),4
                    rst.getString("identidad"),5
                    rst.getString("idsector"),6
                    rst.getString("idunidad"),7
                    rst.getString("idservicio"),8
                    rst.getString("fecha"),9
                    rst.getString("conc")10
                };
        
         */

        scc = new SqlControllerClass();

        //Establece los campos de GRUPO, SUBGRUPO Y SECCION
        grupo4.setText(clasSplit(data[0])[0]);
        sgrp2.setText(clasSplit(data[0])[1]);
        seccion2.setText(clasSplit(data[0])[2]);

        //...
        nbien2.setText(data[1]);
        desc2.setText(data[2]);


        String actaDes = data[3];
        nActa.setText(actaDes);

        String CONCEPTO = data[10];
        Integer conc = Integer.getInteger(CONCEPTO);
        System.out.println("CONC: " + CONCEPTO);

        costo2.setText(data[4]);
        fechatxt2.setText(data[9]);

        servCombo2.setModel(scc.getServicios());
        String[] ente = scc.getRutaEntes(servCombo2.getSelectedItem().toString());
        entidadTxt2.setText(ente[0]);
        sectorTxt2.setText(ente[1]);
        unidadTxt2.setText(ente[2]);
        servTxt2.setText(ente[3]);

        //Salta la ventana de detalles
        DetailsDesIncorporaciones.setVisible(true);
        DetailsDesIncorporaciones.setLocationRelativeTo(null);
    }

    private String[] clasSplit(String TEXT) {
        //para separar clasificacion en 3 partes;
        return TEXT.split("-");
    }

    private void setEstado(String NBIEN) {
        scc = new SqlControllerClass();
        String ESTADO = scc.getEstado(NBIEN);
        switch (ESTADO) {
            case "EXCELENTE":
                comboEstado.setSelectedIndex(1);
                break;
            case "BUENO":
                comboEstado.setSelectedIndex(2);
                break;
            case "REGULAR":
                comboEstado.setSelectedIndex(3);
                break;
            case "MALO":
                comboEstado.setSelectedIndex(4);
                break;
            case "INSERVIBLE":
                comboEstado.setSelectedIndex(5);
                break;
            default:
                comboEstado.setSelectedIndex(0);
                break;
        }
    }

    private void setStatus(String NBIEN) {
        scc = new SqlControllerClass();
        String STATUS = scc.getStatus(NBIEN);
        switch (STATUS) {
            case "A DESINCORPORAR":
                comboStatus.setSelectedIndex(1);
                break;
            case "UBICADO":
                comboStatus.setSelectedIndex(2);
                break;
            case "FALTANTE POR INVESTIGAR":
                comboStatus.setSelectedIndex(3);
                break;
            default:
                comboStatus.setSelectedIndex(0);
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            JFrame.setDefaultLookAndFeelDecorated(true);

        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame AccountMgr;
    private javax.swing.JFrame DetailsBienes;
    private javax.swing.JFrame DetailsDesIncorporaciones;
    private javax.swing.JFrame DetailsIncorporaciones;
    private javax.swing.JButton btnBACK;
    private javax.swing.JButton btnMODE;
    private javax.swing.JButton btnOK;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JComboBox<String> comboIdAsig;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JComboBox<String> concList;
    private javax.swing.JComboBox<String> concList1;
    private javax.swing.JComboBox<String> concList2;
    private javax.swing.JComboBox<String> concList3;
    private javax.swing.JComboBox<String> concList4;
    private javax.swing.JComboBox<String> concList5;
    private javax.swing.JComboBox<String> concList6;
    private javax.swing.JComboBox<String> concList7;
    private javax.swing.JComboBox<String> concLista;
    private javax.swing.JComboBox<String> concLista2;
    private javax.swing.JTextField costo;
    private javax.swing.JTextField costo1;
    private javax.swing.JTextField costo2;
    private javax.swing.JTextArea desc;
    private javax.swing.JTextArea desc1;
    private javax.swing.JTextArea desc2;
    private javax.swing.JTable desinctable;
    private javax.swing.JTextField entidadTxt;
    private javax.swing.JTextField entidadTxt1;
    private javax.swing.JTextField entidadTxt2;
    private javax.swing.JComboBox<String> entidadesList;
    private javax.swing.JComboBox<String> entidadesList1;
    private javax.swing.JComboBox<String> entidadesList2;
    private javax.swing.JComboBox<String> entidadesList3;
    private javax.swing.JTable faltable;
    private javax.swing.JTextField fechatxt;
    private javax.swing.JTextField fechatxt1;
    private javax.swing.JTextField fechatxt2;
    private javax.swing.JTextField grupo;
    private javax.swing.JTextField grupo1;
    private javax.swing.JTextField grupo4;
    private javax.swing.JTable inctable;
    private javax.swing.JTable invTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JLabel labelVisualizando;
    private javax.swing.JLabel labelVisualizando2;
    private javax.swing.JLabel labelVisualizando3;
    private javax.swing.JLabel labelVisualizando4;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JTextField nActa;
    private javax.swing.JTextField nbien;
    private javax.swing.JTextField nbien1;
    private javax.swing.JTextField nbien2;
    private javax.swing.JTextField nf_txt;
    private javax.swing.JTextField ordc_txt;
    private javax.swing.JLabel pplname;
    private javax.swing.JPasswordField pwdactual;
    private javax.swing.JPasswordField pwdnueva;
    private javax.swing.JPasswordField pwdnueva2;
    private javax.swing.JTabbedPane scenary1;
    private javax.swing.JPanel scene;
    private javax.swing.JPanel scene1;
    private javax.swing.JPanel scene2;
    private javax.swing.JTextField seccion;
    private javax.swing.JTextField seccion1;
    private javax.swing.JTextField seccion2;
    private javax.swing.JTextField sectorTxt;
    private javax.swing.JTextField sectorTxt1;
    private javax.swing.JTextField sectorTxt2;
    private javax.swing.JComboBox<String> sectoresList;
    private javax.swing.JComboBox<String> sectoresList1;
    private javax.swing.JComboBox<String> sectoresList2;
    private javax.swing.JComboBox<String> sectoresList3;
    private javax.swing.JToggleButton seebtn1;
    private javax.swing.JToggleButton seebtn2;
    private javax.swing.JToggleButton seebtn3;
    private javax.swing.JLabel selectedLabel;
    private javax.swing.JLabel selectedLabel2;
    private javax.swing.JLabel selectedLabel3;
    private javax.swing.JLabel selectedLabel4;
    private javax.swing.JComboBox<String> servCombo;
    private javax.swing.JComboBox<String> servCombo1;
    private javax.swing.JComboBox<String> servCombo2;
    private javax.swing.JTextField servTxt;
    private javax.swing.JTextField servTxt1;
    private javax.swing.JTextField servTxt2;
    private javax.swing.JComboBox<String> serviciosList;
    private javax.swing.JComboBox<String> serviciosList1;
    private javax.swing.JComboBox<String> serviciosList2;
    private javax.swing.JComboBox<String> serviciosList3;
    private javax.swing.JTextField sgrp;
    private javax.swing.JTextField sgrp1;
    private javax.swing.JTextField sgrp2;
    private javax.swing.JPanel titlebar;
    private javax.swing.JPanel titlebar1;
    private javax.swing.JPanel titlebar2;
    private javax.swing.JTextField txtAsig;
    private javax.swing.JTextField ubic;
    private javax.swing.JTextField unidadTxt;
    private javax.swing.JTextField unidadTxt1;
    private javax.swing.JTextField unidadTxt2;
    private javax.swing.JComboBox<String> unidadesList;
    private javax.swing.JComboBox<String> unidadesList1;
    private javax.swing.JComboBox<String> unidadesList2;
    private javax.swing.JComboBox<String> unidadesList3;
    // End of variables declaration//GEN-END:variables
//Originalmente solo Dios y yo sabiamos como este codigo funcionaba. Ahora solo lo sabe Dios. (el pepe)
}
