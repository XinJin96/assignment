package com.example.interview_assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class write2DB {
    public void writeOnlineSourceToLocalDB() throws IOException, SQLException, ClassNotFoundException {
        URL url = new URL("https://raw.githubusercontent.com/jinchen003/Nearabl.Sample.Data/main/us-500.csv");
        Class.forName("com.mysql.jdbc.Driver");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        //create database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataset", "root", "admin");
        if (httpURLConnection.getResponseCode() == 200) {
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dataset.users" +
                    "(firstname, lastname, companyname, address, city, county, state, zip, phone1, phone2, email, web)" +
                    "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            connection.setAutoCommit(false);
            String content = null;
            int count = 0;
            bufferedReader.readLine();
            while((content = bufferedReader.readLine()) != null){
                String[] data = content.split(",");
                String firstName = "";
                String lastname = "";
                String companyname = "";
                String address = "";
                String city = "";
                String county = "";
                String state = "";
                String zip = "";
                String phone1 = "";
                String phone2 = "";
                String email = "";
                String web = "";
                if(data.length == 13){
                    firstName = data[0];
                    lastname = data[1];
                    companyname = data[2] + ", " + data[3];
                    address = data[4];
                    city = data[5];
                    county = data[6];
                    state = data[7];
                    zip = data[8];
                    phone1 = data[9];
                    phone2 = data[10];
                    email = data[11];
                    web = data[12];
                }else{
                    firstName = data[0];
                    lastname = data[1];
                    companyname = data[2];
                    address = data[3];
                    city = data[4];
                    county = data[5];
                    state = data[6];
                    zip = data[7];
                    phone1 = data[8];
                    phone2 = data[9];
                    email = data[10];
                    web = data[11];
                }
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastname);
                preparedStatement.setString(3, companyname);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, city);
                preparedStatement.setString(6, county);
                preparedStatement.setString(7, state);
                preparedStatement.setString(8, zip);
                preparedStatement.setString(9, phone1);
                preparedStatement.setString(10, phone2);
                preparedStatement.setString(11, email);
                preparedStatement.setString(12, web);
                if (count % 500 == 0) {
                    preparedStatement.executeBatch();
                }
                preparedStatement.addBatch();
            }
            bufferedReader.close();
            preparedStatement.executeBatch();
            connection.commit();
            connection.close();
            System.out.println("Data saved");
        }
    }
}
