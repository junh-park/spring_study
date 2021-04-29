package com.jun.spring_practice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jun.spring_practice.entity.User;

public class UserDao {
	private DataSource dataSource;
	private JdbcContext jdbcContext;
	private JdbcTemplate jdbcTemplate;

	public UserDao() {}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		this.dataSource = dataSource;
	}

	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	public void add(final User user) throws SQLException {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
	}

	public void deleteAll() throws SQLException {
		this.jdbcTemplate.update("delete from users");
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, 
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return null;
					}
				});
	}
	
	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}

//	public int getCount() throws SQLException {
//		return this.jdbcTemplate.query(new PreparedStatementCreator() {
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				return con.prepareStatement("select count(*) from users");
//			}
//		}, new ResultSetExtractor<Integer>() {
//			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//				rs.next();
//				return rs.getInt(1);
//			}
//		});
//	}

//	public int getCount() throws SQLException {
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			con = dataSource.getConnection();
//			ps = con.prepareStatement("select count(*) from users");
//
//			rs = ps.executeQuery();
//
//			rs.next();
//			return rs.getInt(1);
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if (rs != null) { try { rs.close(); } catch (SQLException e) { } }
//			if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
//			if (con != null) { try { con.close(); } catch (SQLException e) { } }
//		}
//	}
}
