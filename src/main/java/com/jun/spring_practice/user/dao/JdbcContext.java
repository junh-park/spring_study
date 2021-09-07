package com.jun.spring_practice.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.jun.spring_practice.user.dao.strategy.StatementStrategy;

public class JdbcContext {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void executeSql(final String query) throws SQLException {
		workWithStatementStrategy(c -> c.prepareStatement(query));
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if(ps != null) { try {ps.close(); } catch (SQLException e){} }
			if(c != null) { try {c.close(); } catch (SQLException e){} }
		}
	}

}
