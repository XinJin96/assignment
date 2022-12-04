package com.example.interview_assignment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name = "searchServlet", urlPatterns = {"/search"})
public class searchServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("call init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            write2DB write2DB=new write2DB();
        try {
            write2DB.writeOnlineSourceToLocalDB();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        out.println("data loaded");

        String field=request.getParameter("Field");
        String input= request.getParameter("input");


        Connection conn = null;
        java.sql.Statement statement = null;
        try {
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/dataset", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement=conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String queryStr="SELECT * FROM DATASET.USERS WHERE "+field+" LIKE '%"+input+"%'";

        ResultSet resultSet=null;

        try {
            resultSet =statement.executeQuery(queryStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HttpSession session = request.getSession();
        session.setAttribute("result", resultSet);

        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}
