package com.qirsam.jdbc.starter;

//import com.qirsam.jdbc.starter.util.ConnectionManager;

import com.qirsam.jdbc.starter.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
//        Long flightId = 2L;
//        var result = getTicketsByFlightId(flightId);
//        System.out.println(result);

//        var result = getFlightsBetween(LocalDate.of(2020, 10, 1).atStartOfDay(), LocalDate.now().atStartOfDay());
//        System.out.println(result);
        try {
            checkMetaData();
        } finally {
            ConnectionManager.closePool();
        }
    }


    private static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();

            var catalogs = metaData.getSchemas();
            while (catalogs.next()){
                System.out.println(catalogs.getString(1));
            }

            var tables = metaData.getTables(null,null,"%", null);
            while (tables.next()){
                System.out.println(tables.getString("TABLE_NAME"));
            }
        }

    }
    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        List<Long> result = new ArrayList<>();

        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sql);) {
            prepareStatement.setFetchSize(50); // количество строк, которое драйвер забирает за один раз
            prepareStatement.setQueryTimeout(10); //установка времени ожидания
            prepareStatement.setMaxRows(100); //максимальное количество строк
            System.out.println(prepareStatement);
            prepareStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(prepareStatement);
            prepareStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(prepareStatement);

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getObject("id", Long.class));
            }

        }
        return result;
    }

    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?;
                """;

        List<Long> result = new ArrayList<>();

        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sql);) {
            prepareStatement.setLong(1, flightId);
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
//                result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // Для работы с NULL
            }
        }

        return result;

    }
}
