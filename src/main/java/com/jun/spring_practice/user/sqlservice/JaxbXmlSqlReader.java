package com.jun.spring_practice.user.sqlservice;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.*;

import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.sqlservice.jaxb.SqlType;
import com.jun.spring_practice.user.sqlservice.jaxb.Sqlmap;

public class JaxbXmlSqlReader implements SqlReader{
	private static final Resource DEFAULT_SQLMAP_FILE= new ClassPathResource("sqlmap.xml", UserDao.class);
	private Resource sqlmapFile = DEFAULT_SQLMAP_FILE;
	private SqlRegistry sqlRegistry;
	
	public void setSqlmapFile(Resource sqlmapFile) {
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
			StreamSource is = new StreamSource(this.sqlmapFile.getInputStream());
			Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
			
			for (SqlType sql : sqlmap.getSql()) {
				sqlRegistry.registerSql(sql.getKey(), sql.getValue());
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to get the file " + this.sqlmapFile.getFilename(), e);
		}
	}
}
