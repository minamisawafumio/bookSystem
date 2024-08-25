package com.itkoza.data;

public class UserData {
	  /** 氏名 */
	  private String name;
	  /** 年齢 */
	  private int age;
	  
	  public UserData(String name, int age){
		  this.name = name;
		  this.age = age;
	  }

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}
