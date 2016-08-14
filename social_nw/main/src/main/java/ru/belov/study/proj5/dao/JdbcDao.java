package ru.belov.study.proj5.dao;

import ru.belov.study.proj5.dao.functions.ExceptionalConsumer;
import ru.belov.study.proj5.dao.functions.ExceptionalFunction;
import ru.belov.study.proj5.dao.functions.ExceptionalRunnable;
import ru.belov.study.proj5.dao.functions.ExceptionalSupplier;

import java.sql.*;
import java.util.Optional;
import java.util.function.Consumer;

public interface JdbcDao {

    Connection getConnection();

    default <T> ExceptionalSupplier<T, SQLException> mapConnection(
            ExceptionalFunction<Connection, T, SQLException> connectionMapper) {
        return () -> {
            try (final Connection connection = getConnection()) {
                return connectionMapper.get(connection);
            }
        };
    }

    default <T> ExceptionalSupplier<T, SQLException> mapStatement(
            ExceptionalFunction<Statement, T, SQLException> statementMapper) {
        return mapConnection(connection -> {
            try (final Statement statement = connection.createStatement()) {
                return statementMapper.get(statement);
            }
        });
    }

    default <T> ExceptionalSupplier<T, SQLException> mapPreparedStatement(
            String sql,
            ExceptionalFunction<PreparedStatement, T, SQLException> statementMapper) {
        return mapConnection(connection -> {
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                return statementMapper.get(statement);
            }
        });
    }

    default <T> ExceptionalSupplier<T, SQLException> mapResultSet(
            String sql,
            ExceptionalFunction<ResultSet, T, SQLException> resultSetMapper) {
        return mapStatement(statement -> {
            try (final ResultSet rs = statement.executeQuery(sql)) {
                return resultSetMapper.get(rs);
            }
        });
    }

    default <T> ExceptionalSupplier<Optional<T>, SQLException> mapRow(
            String sql,
            ExceptionalFunction<ResultSet, T, SQLException> rowMapper) {
        return mapResultSet(sql,
                resultSet -> resultSet.next() ? Optional.of(rowMapper.get(resultSet)) : Optional.empty());
    }

    default ExceptionalRunnable<SQLException> mapRows(
            String sql,
            ExceptionalConsumer<ResultSet, SQLException> rowMapper) {
        return mapResultSet(sql, resultSet -> {
            while (resultSet.next())
                rowMapper.call(resultSet);
            return 0;
        })::executeOrThrowUnchecked;
    }

    default <T> ExceptionalRunnable<SQLException> mapAndReduceRows(
            String sql,
            ExceptionalFunction<ResultSet, T, SQLException> rowMapper,
            Consumer<T> reducer) {
        return mapRows(sql, resultSet -> reducer.accept(rowMapper.get(resultSet)));
    }
}