package com.restassuredapitemplate.utils;


import com.restassuredapitemplate.GlobalParameters;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseUtils {

    private static Connection connection = null;

    private static void startConnection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //exemplo para Microsoft SQL Server
            connection = DriverManager.getConnection("jdbc:sqlserver://" + GlobalParameters.DB_URL +"\\sqlexpress;", GlobalParameters.DB_USER, GlobalParameters.DB_PASSWORD);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static ArrayList<String> getQueryResult(String query){
        ArrayList<String> arrayList = null;
        startConnection();

        try {
            Statement stmt = null;
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                ExtentReportsUtils.addTestDataBaseStepInfoError(3, query);
                throw new RuntimeException("A query executada não retornou resultado");
            }

            else{
                int numberOfColumns;
                ResultSetMetaData metadata=null;

                arrayList = new ArrayList<String>();
                metadata = rs.getMetaData();
                numberOfColumns = metadata.getColumnCount();

                while (rs.next()) {
                    int i = 1;
                    while(i <= numberOfColumns) {
                        arrayList.add(rs.getString(i++));
                    }
                }
            }

        } catch (Exception e) {
            ExtentReportsUtils.addTestDataBaseStepInfoError(3, query);
            throw new RuntimeException(e.getMessage());
        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        ExtentReportsUtils.addTestDataBaseStepInfoPass(3, query);
        return arrayList;
    }

    public static void executeQuery(String query){
        startConnection();

        try {
            Statement stmt = null;
            stmt = connection.createStatement();
            stmt.execute(query);
        } catch (Exception e) {
            ExtentReportsUtils.addTestDataBaseStepInfoError(3, query);
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        ExtentReportsUtils.addTestDataBaseStepInfoPass(3, query);
    }

    public static ArrayList<String> getQueryResultWithoutLog(String query){
        ArrayList<String> arrayList = null;
        startConnection();

        try {
            Statement stmt = null;
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                throw new RuntimeException("A query executada não retornou resultado");
            }

            else{
                int numberOfColumns;
                ResultSetMetaData metadata=null;

                arrayList = new ArrayList<String>();
                metadata = rs.getMetaData();
                numberOfColumns = metadata.getColumnCount();

                while (rs.next()) {
                    int i = 1;
                    while(i <= numberOfColumns) {
                        arrayList.add(rs.getString(i++));
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return arrayList;
    }

    public static void executeQueryWithoutLog(String query){
        startConnection();

        try {
            Statement stmt = null;
            stmt = connection.createStatement();
            stmt.execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}