package edu.handong.analysis.datamodel;
import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String,Integer> semestersByYearAndSemester; 
	
	public Student(String studentId) {
		this.studentId = studentId;
	} // constructor
	public void addCourse(Course newRecord) {
		
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		
	}
	public int getNumCourseInNthSementer(int semester) {
		
	}

}