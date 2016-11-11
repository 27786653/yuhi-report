package com.yuhi.datasource;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class StudentBase implements JRDataSource
{
	private List<Student> listStudent;
	private int index = -1;
	
	public StudentBase()
	{
		super();
		prepareDataSource();
	}
	
	public void prepareDataSource()
	{
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
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException
	{
		Student student = listStudent.get(index);
		
		if(field.getName().equals("stuId"))
		{
			return student.getStuid();
		}
		else if(field.getName().equals("name"))
		{
			return student.getName();
		}
		else if(field.getName().equals("course"))
		{
			return student.getCourse();
		}
		else if(field.getName().equals("grade"))
		{
			return student.getGrade();
		}
		return null;
	}

	@Override
	public boolean next() throws JRException
	{
		if(index < listStudent.size()-1)
		{
			index++;
			return true;
		}
		return false;
	}
}
