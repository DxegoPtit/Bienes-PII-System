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
import org.pii.invbienes.controldb.SqlControllerClass;
/**
 *
 * @author PASANTIA
 */
public class ReportCreator {

    private SqlControllerClass sqc;
    
    public ReportCreator() {
        this.sqc = new SqlControllerClass();
    }
    
    public Boolean generarInventarioByService(String ID){
        /*
        Document doc = new Document();
        PdfPTable table = sqc.fillInventoryReportByService(ID);
        
        try {
            String USERPATH = System.getProperty("user.home");
            PdfWriter.getInstance(doc, new FileOutputStream(USERPATH + "/Desktop/testpdf.pdf"));
            
            Paragraph cabecera = new Paragraph();
            cabecera.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            cabecera.add("GOBERNACION DEL ESTADO BARINAS\n"
                    + "OFICINA DE BIENES Y SERVICIOS\n"
                    + "CONTROL DE INVENTARIO");
            cabecera.setFont(FontFactory.getFont("Arial", 25, Font.BOLD, BaseColor.BLACK));
            
            doc.open();
            doc.add(cabecera);
            
            if (table != null) {
                doc.add(table);
                JOptionPane.showMessageDialog(null, "REPORTE CREADO!!!", ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL CREAR EL REORTE", ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (DocumentException | FileNotFoundException e ) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getLocalizedMessage(), ".:: Sistema de Inventario de Bienes del Programa de Informática Integral ::.", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally{
            doc.close();
        }
*/
        
        return null;
    }
}
