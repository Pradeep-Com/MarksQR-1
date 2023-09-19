package com.MarksQR;

public class Student {
	    private String name;
	    private String rollNumber;
	    private String branch;

	    public Student(String name, String rollNumber, String branch) {
	        this.name = name;
	        this.rollNumber = rollNumber;
	        this.branch = branch;
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRollNumber() {
			return rollNumber;
		}

		public void setRollNumber(String rollNumber) {
			this.rollNumber = rollNumber;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
		}
    
}
