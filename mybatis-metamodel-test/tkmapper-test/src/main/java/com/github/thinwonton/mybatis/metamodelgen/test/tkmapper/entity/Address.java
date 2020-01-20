package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {
    private String street;
    private String zipCode;

    public static class AddressHandler implements TypeHandler<Address> {

        @Override
        public void setParameter(PreparedStatement ps, int i, Address parameter, JdbcType jdbcType) throws SQLException {
            ps.setString(i, parameter.getStreet());
        }

        @Override
        public Address getResult(ResultSet rs, String columnName) throws SQLException {
            final String value = rs.getString(columnName);
            final Address address = new Address();
            address.setStreet(value);
            return address;
        }

        @Override
        public Address getResult(ResultSet rs, int columnIndex) throws SQLException {
            final String value = rs.getString(columnIndex);
            final Address address = new Address();
            address.setStreet(value);
            return address;
        }

        @Override
        public Address getResult(CallableStatement cs, int columnIndex) throws SQLException {
            return null;
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}