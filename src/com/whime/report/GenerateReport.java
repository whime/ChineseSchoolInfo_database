package com.whime.report;
//https://blog.csdn.net/acmman/article/details/51814243
//生成报表


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

 
public class GenerateReport {
    public GenerateReport()
    {

        Map<String,Object> parameters=new HashMap<String,Object>();
        ByteArrayOutputStream outPut=new ByteArrayOutputStream();
        
        File file=new File("D:\\report.pdf");
        String reportModelFile=".\\report\\report1.jasper";
        
        try {
        	/*关于setParameter弃用的问题
        	 * https://stackoverflow.com/questions/24117878/jasperreports-5-6-jrxlsexporter-setparameter-is-deprecated
        	 *
        	 */
            JasperPrint jasperPrint=JasperFillManager.fillReport(reportModelFile,
                    parameters,new ReportDataSource());
            JRPdfExporter exporter=new JRPdfExporter();
            
            //设置exporterInput
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            //设置exporterOutput
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            
            //设置ExportConfiguration
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            
            exporter.exportReport();
            
//            outputStream=new FileOutputStream(file);
//            outputStream.write(outPut.toByteArray());
            
        } catch (JRException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                outPut.flush();
                outPut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    }
}
