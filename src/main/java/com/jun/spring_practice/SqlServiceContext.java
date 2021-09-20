package com.jun.spring_practice;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.jun.spring_practice.user.sqlservice.EmbeddedDbSqlRegistry;
import com.jun.spring_practice.user.sqlservice.OxmSqlService;
import com.jun.spring_practice.user.sqlservice.SqlMapConfig;
import com.jun.spring_practice.user.sqlservice.SqlRegistry;
import com.jun.spring_practice.user.sqlservice.SqlService;

@Configuration
public class SqlServiceContext {
	@Autowired SqlMapConfig sqlMapConfig;
	
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
		sqlService.setSqlmap(this.sqlMapConfig.getSqlMapResource());
		return sqlService;
	}

}
