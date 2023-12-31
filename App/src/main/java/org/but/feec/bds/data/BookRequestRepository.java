package org.but.feec.bds.data;

import org.but.feec.bds.api.BookRequestCreateView;
import org.but.feec.bds.api.BookRequestEditView;
import org.but.feec.bds.api.BookRequestSimpleView;
import org.but.feec.bds.config.DataSourceConfig;
import org.but.feec.bds.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRequestRepository {
    public BookRequestRepository() {}

    public void createBookRequest(BookRequestCreateView bookRequestCreateView) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO bds.book_request (title, isbn, user_id) " +
                          "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS
             );
        ) {
            preparedStatement.setString(1, bookRequestCreateView.getTitle());
            preparedStatement.setString(2, bookRequestCreateView.getIsbn());
            preparedStatement.setLong(3, bookRequestCreateView.getUserId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataAccessException("Creating book request has failed, no rows affected.");
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Creating book request failed.");
        }
    }

    public List<BookRequestSimpleView> getBookRequestsSimpleView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT book_request_id, title, isbn " +
                             "FROM bds.book_request;"
             );
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            List<BookRequestSimpleView> bookRequestSimpleViews = new ArrayList<>();
            while (resultSet.next()) {
                bookRequestSimpleViews.add(mapToBookRequestSimpleView(resultSet));
            }
            return bookRequestSimpleViews;
        }
        catch (SQLException e) {
            throw new DataAccessException("Book requests simple view could not be loaded.", e);
        }
    }

    public void editBookRequest(BookRequestEditView bookRequestEditView) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE bds.book_request " +
                             "SET title = ?, isbn = ? " +
                             "WHERE book_request_id = ?;"
             );
        ) {
            preparedStatement.setString(1, bookRequestEditView.getTitle());
            preparedStatement.setString(2, bookRequestEditView.getIsbn());
            preparedStatement.setLong(3, bookRequestEditView.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataAccessException("Editing book request has failed, no rows affected.");
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Editing book request has failed.", e);
        }
    }

    public void deleteBookRequest(Long id) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM bds.book_request " +
                             "WHERE book_request_id = ?;"
             );
        ){
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataAccessException("Deleting book request has failed, no rows affected");
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Deleting book request has failed.", e);
        }
    }

    private BookRequestSimpleView mapToBookRequestSimpleView (ResultSet rs) throws SQLException {
        BookRequestSimpleView bookRequestSimpleView = new BookRequestSimpleView();
        bookRequestSimpleView.setId(rs.getLong("book_request_id"));
        bookRequestSimpleView.setTitle(rs.getString("title"));
        bookRequestSimpleView.setIsbn(rs.getString("isbn"));
        return bookRequestSimpleView;
    }
}
