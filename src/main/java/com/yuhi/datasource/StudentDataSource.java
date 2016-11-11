package com.yuhi.datasource;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class StudentDataSource extends JRAbstractBeanDataSourceProvider {

	private List<Student> listStudent;

	public StudentDataSource() {
		super(Student.class);
	}

	@Override
	public JRDataSource create(JasperReport jrReport) throws JRException {

		listStudent = new ArrayList<Student>();

		listStudent.add(new Student("S001", "Abhijeet", "MCA", "B"));
		listStudent.add(new Student("S002", "Mukesh", "PGDCA", "A"));
		listStudent.add(new Student("S003", "Roop Singh Rathor", "BCA", "B"));
		listStudent.add(new Student("S004", "Kishor Kumar", "MCA", "A"));
		listStudent.add(new Student("S005", "Moh. Rafi", "MBA", "B"));
		listStudent.add(new Student("S006", "Rakhi Sawant", "DCA", "C"));
		listStudent.add(new Student("S007", "Kvita Krishnamurthy", "BCA", "B"));
		listStudent.add(new Student("S008", "Kumar Sanu", "MCA", "B"));
		listStudent.add(new Student("S009", "Sonu Nigam", "PGDCA", "A"));
		listStudent.add(new Student("S0010", "Sunidhi Chauhan", "BBA", "B"));
		listStudent.add(new Student("S0011", "Lata Manjeshkar", "MCA", "A"));
		listStudent.add(new Student("S001", "Abhijeet", "MCA", "B"));
		listStudent.add(new Student("S002", "Mukesh", "PGDCA", "A"));
		listStudent.add(new Student("S003", "Roop Singh Rathor", "BCA", "B"));
		listStudent.add(new Student("S004", "Kishor Kumar", "MCA", "A"));
		listStudent.add(new Student("S005", "Moh. Rafi", "MBA", "B"));
		listStudent.add(new Student("S006", "Rakhi Sawant", "DCA", "C"));
		listStudent.add(new Student("S007", "Kvita Krishnamurthy", "BCA", "B"));
		listStudent.add(new Student("S008", "Kumar Sanu", "MCA", "B"));
		listStudent.add(new Student("S009", "Sonu Nigam", "PGDCA", "A"));
		listStudent.add(new Student("S0010", "Sunidhi Chauhan", "BBA", "B"));
		listStudent.add(new Student("S0011", "Lata Manjeshkar", "MCA", "A"));
		listStudent.add(new Student("S001", "Abhijeet", "MCA", "B"));
		listStudent.add(new Student("S002", "Mukesh", "PGDCA", "A"));
		listStudent.add(new Student("S003", "Roop Singh Rathor", "BCA", "B"));
		listStudent.add(new Student("S004", "Kishor Kumar", "MCA", "A"));
		listStudent.add(new Student("S005", "Moh. Rafi", "MBA", "B"));
		listStudent.add(new Student("S006", "Rakhi Sawant", "DCA", "C"));
		listStudent.add(new Student("S007", "Kvita Krishnamurthy", "BCA", "B"));
		listStudent.add(new Student("S008", "Kumar Sanu", "MCA", "B"));
		listStudent.add(new Student("S009", "Sonu Nigam", "PGDCA", "A"));
		listStudent.add(new Student("S0010", "Sunidhi Chauhan", "BBA", "B"));
		listStudent.add(new Student("S0011", "Lata Manjeshkar", "MCA", "A"));
		return new JRBeanCollectionDataSource(listStudent);
	}


	@Override
	public void dispose(JRDataSource jrds) throws JRException {
		listStudent.clear();
		listStudent= null;
	}

}
