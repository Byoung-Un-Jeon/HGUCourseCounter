package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String, Student> students;
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		
		ArrayList<String> lines = Utils.getLines(dataPath, true);

		students = loadStudentCourseRecords(lines);
		
		//To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 

		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines){
		
		// TODO: Implement this method
		HashMap<String,Student> temp = new HashMap<String,Student>();
	
		for (String line : lines) {
			//라인정보를 course로 바꿔줌
			Course tempCourse = new Course(line);
			Student tempStudent = new Student(tempCourse.getStudentId());
			tempStudent.addCourse(tempCourse);
			//만약 처음으로 들어오는 값이면
			if(temp.isEmpty()) {
				//스튜던트에 course와 id를 셋해준뒤 그냥 넣어준다.
				temp.put(tempStudent.getStudentId(), tempStudent);
			} else {
				//만약 스튜던트 아이디가 이미 있다면
				if(temp.containsKey(tempStudent.getStudentId())) {
					//그 스튜던트에 코스를 추가해줍니다.
					temp.get(tempStudent.getStudentId()).addCourse(tempCourse);
				}else {
					temp.put(tempStudent.getStudentId(), tempStudent);
				}
			}
        }
		return temp; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method
		//학생의 번호에 맞게 sorting되어 있는 sortedStudent
		//학번 - 총학기수 - N학기 - N학기 course count
		ArrayList<String> result = new ArrayList<String>();
		result.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		
		//스튜던트가 없을때까지 돌아갈 루프 -> 
		 for( String key : sortedStudents.keySet() ){
	         // key는 스튜던트 아이디 , 밸류튼 스튜던트   
			 String tempKey = key;
			 HashMap<String,Integer> tempHash = sortedStudents.get(key).loadStudentCourseRecords();
			 //총학기수는 어떻게 찾는가 젠장 -> loadStudentCourseRecords().size()이다!!!
			 String tempTotal = Integer.toString(tempHash.size());
			 //N학기..... -> loadStudentCourseRecords의 value값
			 //루프가 또 돌아가야한다.
			 //각 N번째 학기 -> loadStudentCourseRecords()의 사이즈만큼 돌아간다.
			 //loadStudentCourseRecords()의 밸류를 getNumCourseInNthSementer(int semester)에 넣는다!!!!!
			 //1 2 3 4 5 6 7 8 이렇게 흘러간다. secondkey = 스트링     value = 1234
			 
			 for(int i = 1; i <= tempHash.size(); i++) {
				 
				 for(String secondKey : tempHash.keySet()) {
					 if(i == tempHash.get(secondKey)) {
						 String tempSemester = Integer.toString(tempHash.get(secondKey));
						 String tempNumcourses = Integer.toString(sortedStudents.get(key).getNumCourseInNthSementer(i));
						 result.add(tempKey + "," + tempTotal + "," + tempSemester + "," + tempNumcourses);
					 }
				 } 
			 }
		 }
				
		return result; // do not forget to return a proper variable.
	}
}
