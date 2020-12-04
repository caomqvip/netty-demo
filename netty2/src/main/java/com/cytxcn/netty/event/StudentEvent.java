package com.cytxcn.netty.event;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;

public class StudentEvent{

	
	
	



	private String name;
	private Integer age;
	private Integer sex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	
	
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
	public StudentEvent(String name, Integer age, Integer sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
	
	public StudentEvent() {
		super();
	}

	
	
	

}
