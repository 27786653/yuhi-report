package com.yuhi.datasource;

public class Student
{
	private String stuid;
	
	private String name;
	
	private String course;
	
	private String grade;
	
	public Student()
	{
		
	}
	
	public Student(String id, String name, String course, String grade)
	{
		this.stuid = id;
		this.name = name;
		this.course = course;
		this.grade = grade;
	}

	public String getStuid()
	{
		return stuid;
	}

	public void setStuid(String stuid)
	{
		this.stuid = stuid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCourse()
	{
		return course;
	}

	public void setCourse(String course)
	{
		this.course = course;
	}

	public String getGrade()
	{
		return grade;
	}

	public void setGrade(String grade)
	{
		this.grade = grade;
	}
}
