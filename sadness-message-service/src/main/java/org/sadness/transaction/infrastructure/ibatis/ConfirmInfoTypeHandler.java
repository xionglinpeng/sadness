package org.sadness.transaction.infrastructure.ibatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.sadness.transaction.entity.ConfirmInfo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/5 21:33
 */
public class ConfirmInfoTypeHandler extends BaseTypeHandler<ConfirmInfo> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ConfirmInfo parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toJson());
    }

    @Override
    public ConfirmInfo getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonConfirmInfo = rs.getString(columnName);
        return ConfirmInfo.toInstance(jsonConfirmInfo);
    }

    @Override
    public ConfirmInfo getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonConfirmInfo = rs.getString(columnIndex);
        return ConfirmInfo.toInstance(jsonConfirmInfo);
    }

    @Override
    public ConfirmInfo getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonConfirmInfo = cs.getString(columnIndex);
        return ConfirmInfo.toInstance(jsonConfirmInfo);
    }
}
