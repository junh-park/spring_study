package com.jun.spring_practice;

import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTestMain {

//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
// 		GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		User user = new User("whiteship", "jun", "married");
//			
//		dao.add(user);
//		
//		System.out.println(user.getId()+" added successfully");
//		
//		User user2 = dao.get("whiteship");
//		
//		System.out.println(user2.getName());
//		System.out.println(user2.getPassword());
//		context.close();
//		
//		}
	@Test
	public void addAndGet() {
		new GenericXmlApplicationContext("applicationiContext.xml");
	}
}
