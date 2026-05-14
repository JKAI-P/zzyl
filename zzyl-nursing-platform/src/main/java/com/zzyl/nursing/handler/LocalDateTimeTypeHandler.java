package com.zzyl.nursing.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MyBatis TypeHandler: LocalDateTime -> String
 */
@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes({JdbcType.DATE, JdbcType.TIMESTAMP})
public class LocalDateTimeTypeHandler extends BaseTypeHandler<String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        LocalDateTime dateTime = rs.getObject(columnName, LocalDateTime.class);
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        LocalDateTime dateTime = rs.getObject(columnIndex, LocalDateTime.class);
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        LocalDateTime dateTime = cs.getObject(columnIndex, LocalDateTime.class);
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }
}
