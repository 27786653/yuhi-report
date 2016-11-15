package com.yuhi.report;


import java.util.Properties;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

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
		StudentDataSource dsStudent =  new StudentDataSource();
		try {
			jrDatasource = dsStudent.create(null);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties attributes=new Properties();
		attributes.setProperty("age", "20");
		model.addAttribute("url", "/WEB-INF/jasper/report2.jasper");
		model.addAttribute("jrMainDataSource", jrDatasource);
		model.addAttribute("model", attributes);
		model.addAttribute("format", "pdf");
		return "iReportView";
	}
	
}
