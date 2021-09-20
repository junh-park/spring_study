package com.jun.spring_practice.user.sqlservice;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import com.jun.spring_practice.exception.SqlRetrievalFailureException;
import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.sqlservice.jaxb.SqlType;
import com.jun.spring_practice.user.sqlservice.jaxb.Sqlmap;

public class OxmSqlService implements SqlService {
	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
	private final BaseSqlService baseSqlService = new BaseSqlService();
	
	private SqlRegistry sqlRegistry;
	
	public void setSqlmap(Resource sqlmap) {
		this.oxmSqlReader.setSqlmap(sqlmap);
	}
	
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	@PostConstruct
	public void loadSql() {
		this.baseSqlService.setSqlReader(this.oxmSqlReader);
		this.baseSqlService.setSqlRegistry(this.sqlRegistry);

		this.baseSqlService.loadSql();
	}
	
	public String getSql(String key) throws SqlRetrievalFailureException {
		return this.baseSqlService.getSql(key);
	}
	
	private class OxmSqlReader implements SqlReader {
		private Resource sqlmap = new ClassPathResource("sqlmap.xml");
		private Resource sqlmapFile = sqlmap;
		private Unmarshaller unmarshaller;
		
		public void setSqlmap(Resource sqlmap) {
			this.sqlmap = sqlmap;
		}
		
		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}
		
		public void read(SqlRegistry sqlRegistry) {
			try { 
				Source source = new StreamSource(sqlmap.getInputStream());
				Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);

				for (SqlType sql : sqlmap.getSql()) {
					sqlRegistry.registerSql(sql.getKey(), sql.getValue());
				}
			} catch (IOException e) {
				throw new IllegalArgumentException("Cannot retrieve " + this.sqlmap.getFilename());
			}
		}
	}
}
