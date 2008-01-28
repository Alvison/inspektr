/**
 *  Copyright 2007 Rutgers, the State University of New Jersey
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inspektr.webapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.inspektr.statistics.annotation.Statistic.Precision;
import org.inspektr.webapp.domain.Statistic;
import org.inspektr.webapp.domain.StatisticImpl;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Implementation of {@link StatisticDao} that loads the required statistics from the database.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class JdbcStatisticDao extends SimpleJdbcDaoSupport implements StatisticDao {
	
	private final ParameterizedRowMapper<Statistic> statisticParameterizedRowMapper = new ParameterizedRowMapper<Statistic>() {

		public Statistic mapRow(final ResultSet rs, int rownum) throws SQLException {
			final StatisticImpl statistic = new StatisticImpl();
			statistic.setApplicationCode(rs.getString("APPLIC_CD"));
			statistic.setCount(rs.getInt("STAT_COUNT"));
			statistic.setPrecision(Precision.valueOf(rs.getString("STAT_PRECISION")));
			statistic.setWhat(rs.getString("STAT_NAME"));
			statistic.setWhen(rs.getTimestamp("STAT_DATE"));
			
			return statistic;
		}
	};
	
	private final ParameterizedRowMapper<String> applicationCodeAsStringParameterizedRowMapper = new ParameterizedRowMapper<String>() {

		public String mapRow(final ResultSet rs, final int rownum) throws SQLException {
			return rs.getString("APPLIC_CD");
		}};
	
	private final String SQL_SELECT_PREFIX = "Select APPLIC_CD, STAT_COUNT, STAT_PRECISION, STAT_NAME, STAT_DATE From COM_STATISTICS Where APPLIC_CD = ? AND ";
	
	private final String SQL_SELECT_SUFFIX = " ORDER BY STAT_DATE, STAT_PRECISION";
	
	public List<String> getApplicationCodes() {
		return getSimpleJdbcTemplate().query("Select Distinct APPLIC_CD From COM_STATISTICS Order By APPLIC_CD", this.applicationCodeAsStringParameterizedRowMapper);
	}

	public List<Statistic> findComparisonStatistics(final Date firstDate,
			final Date secondDate, final String applicationCode,
			final Precision[] requiredPrecision) {
		final Date[] firstDateLowHigh = constructLowAndHighDates(firstDate);
		final Date[] secondDateLowHigh = constructLowAndHighDates(secondDate);
		return getSimpleJdbcTemplate().query(SQL_SELECT_PREFIX + "((STAT_DATE >= ? AND STAT_DATE <= ?) OR (STAT_DATE >= ? AND STAT_DATE <=?)) AND " + constructPrecisionSuffix(requiredPrecision)  + SQL_SELECT_SUFFIX, this.statisticParameterizedRowMapper, applicationCode, firstDateLowHigh[0], firstDateLowHigh[1], secondDateLowHigh[0], secondDateLowHigh[1]);
	}

	public List<Statistic> findStatisticsForDateRange(final Date startDate,
			final Date endDate, final String applicationCode, final Precision[] requiredPrecision) {
		return getSimpleJdbcTemplate().query(SQL_SELECT_PREFIX + "STAT_DATE >= ? AND STAT_DATE <= ? AND " + constructPrecisionSuffix(requiredPrecision) + SQL_SELECT_SUFFIX, this.statisticParameterizedRowMapper, applicationCode, startDate, endDate);
	}
	
	private String constructPrecisionSuffix(final Precision[] requiredPrecision) {
		final StringBuilder builder = new StringBuilder();
		builder.append("(");
		for (final Precision precision : requiredPrecision) {
			builder.append("STAT_PRECISION = '" + precision.name() + "' OR ");
		}
		
		return builder.substring(0, builder.length()-4) + ")";
	}
	
	private Date[] constructLowAndHighDates(final Date date) {
		final Calendar calendar1 = Calendar.getInstance();
		final Calendar calendar2 = Calendar.getInstance();
		
		calendar1.setTime(date);
		calendar2.setTime(date);
		
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND, 0);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		
		calendar2.set(Calendar.MINUTE, 59);
		calendar2.set(Calendar.SECOND, 59);
		calendar2.set(Calendar.MILLISECOND, 999);
		calendar2.set(Calendar.HOUR_OF_DAY, 23);
		
		final Date[] array = new Date[] {calendar1.getTime(), calendar2.getTime()};

		return array;
	}
}
