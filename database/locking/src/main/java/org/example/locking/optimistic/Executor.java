package org.example.locking.optimistic;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * Transfer 100 from one account to another using optimistic locking.
 * <p>
 * Isolation levels:
 * 1) READ UNCOMMITTED - Postgres doesn't support it, READ COMMITTED is used instead.
 * 2) READ COMMITTED (default) - there is no exception if another transaction has already modified row,
 * just extractCount==0 and/or addCount==0. Check these two values and go to retry money transfer.
 * 3) REPEATABLE READ - there is 'could not serialize access due to concurrent update' exception if another transaction
 * has already modified row. Just retry money transfer.
 * 4) SERIALIZABLE - there is 'could not serialize access due to concurrent update' exception if another transaction
 * has already modified row. Just retry money transfer.
 */
@Slf4j
public class Executor {

    private static final int TRANSFER_AMOUNT = 100;

    // docker pull postgres
    // docker run --name my-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=db -p 5432:5432 -d postgres
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://0.0.0.0:5432/db", "user", "password")) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            // connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            // connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            boolean success = false;
            int retries = 3;

            while (!success && retries > 0) {
                retries--;
                try {
                    int extractCount = updateBalance(connection, 1, -TRANSFER_AMOUNT);
                    int addCount = updateBalance(connection, 2, TRANSFER_AMOUNT);

                    if (extractCount > 0 && addCount > 0) {
                        connection.commit();
                        success = true;
                    } else {
                        connection.rollback();
                    }
                } catch (SQLException exception) {
                    log.warn("Transaction failed, retrying... Attempts left: {}", retries, exception);
                    connection.rollback();
                }
            }

            if (!success) {
                log.error("Transaction failed after max retries");
            }

        } catch (Exception exception) {
            log.error("An error occurred during the transaction", exception);
        }
    }

    private static int updateBalance(Connection connection, int accountId, int amount) throws SQLException {
        String selectQuery = "SELECT version FROM money WHERE id = ?";

        String updateQuery = """
                UPDATE money
                SET balance = balance + ?, version = version + 1
                WHERE id = ? AND version = ?
                """;

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            selectStatement.setInt(1, accountId);
            ResultSet selectResultSet = selectStatement.executeQuery();

            if (selectResultSet.next()) {
                int version = selectResultSet.getInt("version");

                updateStatement.setInt(1, amount);
                updateStatement.setInt(2, accountId);
                updateStatement.setInt(3, version);

                return updateStatement.executeUpdate();
            } else {
                return 0;
            }
        }
    }

}
