package com.qirsam.jdbc.starter;

import com.qirsam.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        long flightId = 8;
        var deleteFlightSql = "DELETE FROM flight WHERE id = " + flightId;
        var deleteTicketsSql = "DELETE FROM ticket WHERE flight_id = " + flightId;
        Connection connection = null;
        Statement statement = null;
//        PreparedStatement deleteFlightStatement = null;
//        PreparedStatement deleteTicketStatement = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.addBatch(deleteTicketsSql);
            statement.addBatch(deleteFlightSql);

            var executeBatch = statement.executeBatch();
//            deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
//            deleteTicketStatement = connection.prepareStatement(deleteTicketsSql);

//            deleteTicketStatement.setLong(1, flightId);
//            deleteTicketStatement.executeUpdate();

//            if (true) {
//                throw new RuntimeException("Ooops");
//            }

//            deleteFlightStatement.setLong(1, flightId);
//            deleteFlightStatement.executeUpdate();

            connection.commit();
        } catch (Exception e) {
            if (connection != null)
                connection.rollback();
            throw e;
        } finally {
            if (connection != null)
                connection.close();
            if (statement != null)
                statement.close();
//            if (deleteFlightStatement != null)
//                deleteFlightStatement.close();
//            if (deleteTicketStatement != null)
//                deleteTicketStatement.close();
        }
    }
}
