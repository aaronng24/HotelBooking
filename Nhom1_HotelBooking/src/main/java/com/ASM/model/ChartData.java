package com.ASM.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ChartData {
	private static JdbcTemplate jdbcTemplate;
	static Map<Object, Object> map = null;
	static List<List<DataPointModel>> list = new ArrayList<List<DataPointModel>>();
	static List<DataPointModel> dataPoints1 = new ArrayList<DataPointModel>();

	static {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=J5ASM_VooTreeVeeVuu");
		dataSource.setUsername("sa");
		dataSource.setPassword("Scavenger2497");
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public static class DataPointModel {
		int x;
		float y;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float f) {
			this.y = f;
		}
	}

	public static class DatabaseConnectionException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public DatabaseConnectionException(String message) {
			super(message);
		}
	}

	private static void getDataPoints() {
		String sql = "select MONTH(checkoutdate) as month, SUM(total_price) as total from booking GROUP BY MONTH(checkoutdate)";

		try {
			dataPoints1 = jdbcTemplate.query(sql, new RowMapper<DataPointModel>() {

				@Override
				public DataPointModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					DataPointModel dataPoint = new DataPointModel();

					dataPoint.setX(rs.getInt("month"));
					dataPoint.setY(rs.getFloat("total"));

					return dataPoint;
				}
			});
		} catch (Exception e) {
			System.out.println(e);
			dataPoints1 = null;
			throw new DatabaseConnectionException("Error while getting data");
		}
		list.add(dataPoints1);
	}

	public static List<List<DataPointModel>> getCanvasjsDataList() {
		getDataPoints();
		return list;
	}
}
