/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pii.invbienes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.pii.invbienes.controldb.SqlControllerClass;
/**
 *
 * @author PASANTIA
 */
public class ReportCreator {

    private SqlControllerClass sqc;
    private JasperViewer jvw;
    
    public ReportCreator() {
        this.sqc = new SqlControllerClass();
    }
    
}
