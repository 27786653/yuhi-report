package com.yuhi.report;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuhi.datasource.StudentDataSource;

@Controller
public class JasperBean {

	private JRDataSource jrDatasource;

	public JasperBean() throws JRException {
		StudentDataSource dsStudent =  new StudentDataSource();
		jrDatasource = dsStudent.create(null);
	}

	@RequestMapping(value = "/jrreport", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
//		model.addAttribute("url", "/WEB-INF/jasper/JRStudent.jasper");
//		model.addAttribute("jrMainDataSource", jrDatasource);
//		model.addAttribute("format", "pdf");
//		return "iReportView";
		StudentDataSource dsStudent =  new StudentDataSource();
		try {
			jrDatasource = dsStudent.create(null);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("url", "/WEB-INF/jasper/report2.jasper");
		model.addAttribute("jrMainDataSource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "iReportView";
	}
	@RequestMapping(value = "/jrreporthtml", method = RequestMethod.GET)
	public String print(ModelMap model) {
		StudentDataSource dsStudent =  new StudentDataSource();
		try {
			jrDatasource = dsStudent.create(null);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("url", "/WEB-INF/jasper/JRStudent.jasper");
		model.addAttribute("jrMainDataSource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "iReportView";
	}
	@Autowired
	@Qualifier(value="mdataSource")
	private DataSource ds;
	
	@RequestMapping(value = "/queryIncomeSumServlet", method = RequestMethod.GET)
    public void queryIncomeSumHtml(HttpServletRequest request,
            HttpServletResponse response) throws IOException, JRException, SQLException {
		//STEP 1 : 查询数据 
		//STEP 2 : 指定数据源
		//STEP 3 : 指定模板文件
		ServletContext context = request.getSession().getServletContext();
		File reportFile = null;
		reportFile = new File(context.getRealPath("WEB-INF/classes/JRStudent.jasper"));
		String exportFilePath = "tempPath";
		
		/* STEP 4 : 输出报表
		*不同输出方式，具体实现在后边注释*/
		/*使用HTML输出，表现为页面直接显示报表*/
//		JasperHelper.showHtml(exportFilePath , reportFile.getPath(), request,
//		                        response, new HashMap(), jrDatasource);
//		JasperHelper.showPdf(exportFilePath , reportFile.getPath(), request,
//                response, new HashMap(), ds.getConnection());
		//		/*使用PDF输出，表现为出现PDF文件的下载对话框*/
//		JasperHelper.export("pdf", "pdf", reportFile, request,
//		                        response, new HashMap(), jrDatasource);
//		/*使用EXCEL输出，表现为出现EXCEL文件的下载对话框*/
//		JasperHelper.export("excel", "excel", reportFile, request,
//		                        response, new HashMap(), jrDatasource);
		}
}
