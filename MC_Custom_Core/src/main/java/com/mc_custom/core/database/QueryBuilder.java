package com.mc_custom.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A basic Query Builder for Java SQL.<br>
 * This is not a complete builder, and should be used primarily as a template.
 */
public class QueryBuilder {

	private Connection connection;
	private PreparedStatement statement;
	private ResultSet result;
	private int row = 1;
	private int index = 1;

	/**
	 * Starts the QueryBuilder.
	 *
	 * @param connection The Connection to the database.
	 * @throws SQLException
	 */
	public QueryBuilder(Connection connection) throws SQLException {
		if (connection != null) {
			this.connection = connection;
		}
		else {
			throw new SQLException();
		}
	}

	/**
	 * Starts the QueryBuilder.
	 *
	 * @param connection The Connection to the database.
	 * @param raw_query  The query String.
	 * @throws SQLException
	 */
	public QueryBuilder(Connection connection, String raw_query) throws SQLException {
		if (connection != null) {
			this.connection = connection;
			statement = connection.prepareStatement(raw_query);
		}
		else {
			throw new SQLException();
		}
	}

	/**
	 * Creates a new PreparedStatement from the given query String.
	 *
	 * @param raw_query The query String.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setQuery(String raw_query) throws SQLException {
		if (raw_query != null) {
			statement = connection.prepareStatement(raw_query);
		}
		else {
			closeConnection();
			throw new SQLException();
		}
		return this;
	}

	/**
	 * Sets the value of the current column to an Integer
	 *
	 * @param value The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setInt(Integer value) throws SQLException {
		try {
			return setInt(index++, value);
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the specified column to an Integer
	 *
	 * @param column_index The column to set.
	 * @param value        The value to set
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setInt(int column_index, Integer value) throws SQLException {
		try {
			statement.setInt(column_index, value);
			return this;
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the current column to a Double
	 *
	 * @param value The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setDouble(Double value) throws SQLException {
		try{
			return setDouble(index++, value);
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the specified column to a Double
	 *
	 * @param column_index The column to set.
	 * @param value        The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setDouble(int column_index, Double value) throws SQLException {
		try{
			statement.setDouble(column_index, value);
			return this;
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the current column to a Long
	 *
	 * @param value The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setLong(Long value) throws SQLException {
		try{
			return setLong(index++, value);
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the specified column to a Long
	 *
	 * @param column_index The column to set.
	 * @param value        The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setLong(int column_index, Long value) throws SQLException {
		try{
			statement.setDouble(column_index, value);
			return this;
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the current column to a String
	 *
	 * @param value The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setString(String value) throws SQLException {
		try{
			return setString(index++, value);
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the specified column to a String
	 *
	 * @param column_index The column to set.
	 * @param value        The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setString(int column_index, String value) throws SQLException {
		try{
			statement.setString(column_index, value);
			return this;
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the current column to a Boolean
	 *
	 * @param value The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setBoolean(Boolean value) throws SQLException {
		try{
			return setBoolean(index++, value);
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Sets the value of the specified column to a Boolean
	 *
	 * @param column_index The column to set.
	 * @param value        The value to set.
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder setBoolean(int column_index, Boolean value) throws SQLException {
		try {
			statement.setBoolean(column_index, value);
			return this;
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Executes the previously set PreparedStatement.<br>
	 *
	 * @return an updated QueryBuilder
	 * @throws SQLException
	 */
	public QueryBuilder executeQuery() throws SQLException {
		try {
			result = statement.executeQuery();
			return this;
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Executes the previously set PreparedStatement.<br>
	 * This should be used when performing queries that do not require data to be returned<br>
	 * such as <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code>;
	 *
	 * @return an updated QueryBuilder
	 * @return -1 if error occurs
	 */
	public int update(){
		try {
			int result = statement.executeUpdate();
			return result;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return -1;
	}

	/**
	 * Moves the cursor to the next row.
	 *
	 * @return an updated QueryBuilder
	 */
	public boolean nextRow() throws SQLException {
		try {
			//Move to next row
			row++;
			return result.next();
		}
		catch (Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Moves the cursor to the specified row.
	 *
	 * @param row The index of the row<br>
	 *            Remember that SQL is 1-based.
	 * @return an updated QueryBuilder
	 */
	public QueryBuilder setRow(int row) throws SQLException {
		try {
			//Update current row
			this.row = row;
			result.absolute(row);
			return this;
		}
		catch(Exception ex){
			ex.printStackTrace();
			closeConnection();
			throw new SQLException(ex);
		}
	}

	/**
	 * Checks to see if the specified row exists.
	 *
	 * @param row The index of the row<br>
	 *            Remember that SQL is 1-based.
	 * @return an updated QueryBuilder
	 */
	public boolean rowExists(int row) {
		try {
			boolean exists = result.absolute(row);
			// resets the cursor the the previous row
			result.absolute(this.row);
			return exists;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			closeConnection();
		}
	}

	/**
	 * Fetches a specific column value.
	 *
	 * @param column_index The column index to fetch<br>
	 *                     Remember that SQL is 1-based.
	 * @param clazz        The Class of the Object to fetch
	 * @return An Object casted to the specified Class.
	 */
	public <T> T fetch(int column_index, Class<T> clazz) {
		try {
			if (result.first()) {
				cast(result.getObject(column_index), clazz);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return null;
	}

	/**
	 * Fetches a single row with a single return value.
	 *
	 * @param clazz The Class of the Object to fetch
	 * @return An Object casted to the specified Class.
	 */
	public <T> T fetchOne(Class<T> clazz) {
		try {
			if (result.first()) {
				return cast(result.getObject(1), clazz);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return null;
	}

	/**
	 * Fetches a single row of N {@link Column}s<br>
	 * Since the {@link Column}s are passed in by reference, there is no return value
	 *
	 * @param columns The {@link Column}s to fetch.
	 */
	public void fetchOne(Column<?>... columns) {
		try {
			if (result.first()) {
				for (int i = 0; i < columns.length; i++) {
					columns[i].value = result.getObject(i + 1);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}

	/**
	 * Fetches all rows for N {@link Column}s<br>
	 * Since the {@link Column}s are passed in by reference, there is no return value
	 *
	 * @param columns The {@link Column}s to fetch
	 */
	public void fetchAll(Column<?>... columns) {
		try {
			for (int i = 0; i < columns.length; i++) {
				result.beforeFirst();
				while (nextRow()) {
					columns[i].add(result.getObject(i + 1));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}

	/**
	 * Fetches all rows for a single Column
	 *
	 * @param clazz The Class of the Object to fetch
	 * @return An Object casted to the specified Class.
	 */
	public <T> List<T> fetchAll(Class<T> clazz) {
		List<T> fetch_all = new ArrayList<>();
		try {
			while (result.next()) {
				Object o = result.getObject(1);
				fetch_all.add(cast(o, clazz));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return fetch_all;
	}

	/**
	 * Closes the connection.<br>
	 * Notes: The connection will be closed automatically if you use <code>fetch*</code> or <code>update</code>
	 */
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {
			try {
				result.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Casts an Object to a generic type.
	 *
	 * @param o     The Object to cast
	 * @param clazz The Class to cast to
	 * @return A generic type
	 */
	private <T> T cast(Object o, Class<T> clazz) {
		if (clazz == null || o == null) {
			return null;
		}
		// We want to use Integers, SQL tries to give us Longs
		if (o instanceof Long && !clazz.equals(Long.class)) {
			if (clazz.equals(Integer.class)) {
				return clazz.cast(((Long) o).intValue());
			}
			if (clazz.equals(Short.class)) {
				return clazz.cast(((Long) o).shortValue());
			}
		}
		return clazz.cast(o);
	}
}