package com.jun.spring_practice.user.sqlservice;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.sqlservice.jaxb.SqlType;
import com.jun.spring_practice.user.sqlservice.jaxb.Sqlmap;

public class JaxbXmlSqlReader implements SqlReader{
	private static final String DEFAULT_SQLMAP_FILE="sqlmap.xml";
	private String sqlmapFile = DEFAULT_SQLMAP_FILE;
	private SqlRegistry sqlRegistry;
	
	public void setSqlmapFile(String sqlmapFile) {
		this.sqlmapFile = sqlmapFile;
	}

	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	public void read(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
		String contextPath = Sqlmap.class.getPackage().getName();
		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is= UserDao.class.getResourceAsStream(this.sqlmapFile);
			Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
			
			for (SqlType sql : sqlmap.getSql()) {
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
