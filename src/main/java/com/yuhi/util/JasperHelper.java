package com.yuhi.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;

public class JasperHelper {
    private static Logger logger = Logger.getLogger(JasperHelper.class);
    public static final String PRINT_TYPE = "print";
    public static final String PDF_TYPE = "pdf";
    public static final String EXCEL_TYPE = "excel";
    public static final String HTML_TYPE = "html";
    public static final String WORD_TYPE = "word";

    public static void prepareReport(JasperReport jasperReport, String type) {
        logger.debug("The method======= prepareReport() start.......................");
        /*
         * 如果导出的是excel，则需要去掉周围的margin
         */
        if ("excel".equals(type))
            try {
                Field margin = JRBaseReport.class
                        .getDeclaredField("leftMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("topMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("bottomMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                Field pageHeight = JRBaseReport.class
                        .getDeclaredField("pageHeight");
                pageHeight.setAccessible(true);
                pageHeight.setInt(jasperReport, 2147483647);
            } catch (Exception exception) {
            }
    }

    /**
     * 导出excel
     */
    public static void exportExcel(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response) throws IOException, JRException {
        /*
         * 设置头信息
         */
        response.setContentType("application/vnd.ms-excel");
        String defaultname = null;
        if (defaultFilename.trim() != null && defaultFilename != null) {
            defaultname = defaultFilename + ".xls";
        } else {
            defaultname = "export.xls";
        }

        response.setHeader("Content-Disposition", "attachment; filename=\""
                + URLEncoder.encode(defaultname, "UTF-8") + "\"");


        ServletOutputStream ouputStream = response.getOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);

        exporter.setParameter(
                JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.TRUE); // 删除记录最下面的空行

        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                Boolean.FALSE);// 删除多余的ColumnHeader
        //
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                Boolean.FALSE);// 显示边框
        exporter.exportReport();
        ouputStream.flush();
        ouputStream.close();
    }

    public static enum DocType {
        PDF, HTML, XLS, XML, RTF
    }
    public static JRAbstractExporter getJRExporter(DocType docType) {
        JRAbstractExporter exporter = null;
        switch (docType) {
        case PDF:
            exporter = new JRPdfExporter();
            break;
        case HTML:
            exporter = new JRHtmlExporter();
            break;
        case XLS:
            exporter = new JExcelApiExporter();
            break;
        case XML:
            exporter = new JRXmlExporter();
            break;
        case RTF:
            exporter = new JRRtfExporter();
            break;
        }
        return exporter;
    }
    /**
     * 导出pdf，注意此处中文问题，
     * 
     * 这里应该详细说：主要在ireport里变下就行了。看图
     * 
     * 1）在ireport的classpath中加入iTextAsian.jar 2）在ireport画jrxml时，看ireport最左边有个属性栏。
     * 
     * 下边的设置就在点字段的属性后出现。 pdf font name ：STSong-Light ，pdf encoding ：UniGB-UCS2-H
     */
    private static void exportPdf(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response) throws IOException, JRException {
        response.setContentType("application/pdf");
        String defaultname = null;
        if (defaultFilename.trim() != null && defaultFilename != null) {
            defaultname = defaultFilename + ".pdf";
        } else {
            defaultname = "export.pdf";
        }
        String fileName = new String(defaultname.getBytes("GBK"), "ISO8859_1");
        response.setHeader("Content-disposition", "attachment; filename="
                + fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    /**
     * 导出html
     */
    private static void exportHtml(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response) throws IOException, JRException {
        response.setContentType("text/html");
        ServletOutputStream ouputStream = response.getOutputStream();
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                Boolean.FALSE);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);

        exporter.exportReport();

        ouputStream.flush();
        ouputStream.close();
    }

    /**
     * 导出word
     */
    private static void exportWord(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response) throws JRException, IOException {
        response.setContentType("application/msword;charset=utf-8");
        String defaultname = null;
        if (defaultFilename.trim() != null && defaultFilename != null) {
            defaultname = defaultFilename + ".doc";
        } else {
            defaultname = "export.doc";
        }
        String fileName = new String(defaultname.getBytes("GBK"), "utf-8");
        response.setHeader("Content-disposition", "attachment; filename="
                + fileName);
        JRExporter exporter = new JRRtfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                response.getOutputStream());

        exporter.exportReport();
    }

    /**
     * 按照类型导出不同格式文件
     * 
     * @param datas
     *            数据
     * @param type
     *            文件类型
     * @param is
     *            jasper文件的来源
     * @param request
     * @param response
     * @param defaultFilename默认的导出文件的名称
     */
    public static void export(String type, String defaultFilename, File is,
            HttpServletRequest request, HttpServletResponse response,
            Map parameters, Connection conn) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
            prepareReport(jasperReport, type);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, conn);

            if (EXCEL_TYPE.equals(type)) {
                exportExcel(jasperPrint, defaultFilename, request, response);
            } else if (PDF_TYPE.equals(type)) {
                exportPdf(jasperPrint, defaultFilename, request, response);
            } else if (HTML_TYPE.equals(type)) {
                exportHtml(jasperPrint, defaultFilename, request, response);
            } else if (WORD_TYPE.equals(type)) {
                exportWord(jasperPrint, defaultFilename, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void export(String type, String defaultFilename, File is,
            HttpServletRequest request, HttpServletResponse response,
            Map parameters, JRDataSource conn) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
            prepareReport(jasperReport, type);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, conn);

            if (EXCEL_TYPE.equals(type)) {
                exportExcel(jasperPrint, defaultFilename, request, response);
            } else if (PDF_TYPE.equals(type)) {
                exportPdf(jasperPrint, defaultFilename, request, response);
            } else if (HTML_TYPE.equals(type)) {
                exportHtml(jasperPrint, defaultFilename, request, response);
            } else if (WORD_TYPE.equals(type)) {
                exportWord(jasperPrint, defaultFilename, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 输出html静态页面，必须注入request和response
     * 
     * @param jasperPath
     * @param params
     * @param sourceList
     * @param imageUrl
     *            报表文件使用的图片路径，比如 ../servlets/image?image=
     * @throws JRException
     * @throws IOException
     * @throws ServletException
     */
    public static void showHtml(String defaultFilename, String reportfile,
            HttpServletRequest request, HttpServletResponse response, Map parameters,
            JRDataSource conn) throws JRException, IOException {


        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");

        JRAbstractExporter exporter = getJRExporter(DocType.HTML);

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportfile,
                parameters, conn);
        request.getSession().setAttribute(
                ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
                jasperPrint);

        PrintWriter out = response.getWriter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporter.setParameter(
        JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
        Boolean.FALSE);
        exporter.exportReport();
        out.flush();

    }
    public static void showHtml(String defaultFilename, String reportfile,
            HttpServletRequest request, HttpServletResponse response, Map parameters,
            Connection conn) throws JRException, IOException {


        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");

        JRAbstractExporter exporter = getJRExporter(DocType.HTML);

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportfile,
                parameters, conn);
        request.getSession().setAttribute(
                ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
                jasperPrint);

        PrintWriter out = response.getWriter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporter.setParameter(
        JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
        Boolean.FALSE);
        exporter.exportReport();
        out.flush();

    }
   
    public static void showPdf(String defaultFilename, String reportfile,
            HttpServletRequest request, HttpServletResponse response, Map parameters,
            Connection conn) throws JRException, IOException {


        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setContentType("application/pdf");

        JRAbstractExporter exporter = getJRExporter(DocType.PDF);

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportfile,
                parameters, conn);
        request.getSession().setAttribute(
                ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
                jasperPrint);

        PrintWriter out = response.getWriter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporter.exportReport();
        out.flush();

    }


}