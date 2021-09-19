package com.jun.spring_practice;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.mail.MailSender;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;

import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.dao.UserDaoJDBC;
import com.jun.spring_practice.user.service.DummyMailSender;
import com.jun.spring_practice.user.service.TestUserServiceImpl;
import com.jun.spring_practice.user.service.UserService;
import com.jun.spring_practice.user.service.UserServiceImpl;
import com.jun.spring_practice.user.sqlservice.EmbeddedDbSqlRegistry;
import com.jun.spring_practice.user.sqlservice.OxmSqlService;
import com.jun.spring_practice.user.sqlservice.SqlRegistry;
import com.jun.spring_practice.user.sqlservice.SqlService;
import com.mysql.cj.jdbc.Driver;

@Configuration
@EnableTransactionManagement
public class TestApplicationContext {
	@Autowired
	SqlService sqlService;
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/spring_study?user=root");
		dataSource.setUsername("root");
		dataSource.setPassword("qkrwnsghd");
		return dataSource;
//		<bean id="dataSource"
//			class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
//			<property name="driverClass" value="com.mysql.cj.jdbc.Driver" />
//			<property name="url" value="jdbc:mysql://localhost:3306/spring_study?user=root" />
//			<property name="username" value="root" />
//			<property name="password" value="qkrwnsghd" />
//		</bean>
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
//	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
//		<property name="dataSource" ref="dataSource" />
//	</bean>
	}

	@Bean
	public UserDao userDao() {
		UserDaoJDBC dao = new UserDaoJDBC();
		dao.setDataSource(dataSource());
		dao.setSqlService(this.sqlService);
		return dao;
	}

	@Bean
	public UserService userService() {
		UserServiceImpl service = new UserServiceImpl();
		service.setUserDao(userDao());
		service.setMailSender(mailSender());
		return service;
	}

	@Bean
	public UserService testUserService() {
		TestUserServiceImpl testService = new TestUserServiceImpl();
		testService.setUserDao(userDao());
		testService.setMailSender(mailSender());
		return testService;
	}

	@Bean
	public MailSender mailSender() {
		return new DummyMailSender();
	}


	@Bean
	public DataSource embeddedDatabase() {
		return new EmbeddedDatabaseBuilder().setName("embeddedDatabase").setType(HSQL)
				.addScript("classpath:/schema.sql").build();
	}

	@Bean
	public SqlRegistry sqlRegistry() {
		EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
		sqlRegistry.setDataSource(embeddedDatabase());
		return sqlRegistry;
	}

	@Bean
	public Unmarshaller unmarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.jun.spring_practice.user.sqlservice.jaxb");
		return marshaller;
	}
	
	@Bean
	public SqlService sqlService() {
		OxmSqlService sqlService = new OxmSqlService();
		sqlService.setSqlRegistry(sqlRegistry());
		sqlService.setUnmarshaller(unmarshaller());
		return sqlService;
	}


}
