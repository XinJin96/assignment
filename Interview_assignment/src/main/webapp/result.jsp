<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="en">
<form action="result" method="post">
<head>
    <meta charset="UTF-8">
    <title>Search Result</title>
</head>
<body>
<center>
  <h1>Search Result</h1>
  <%
  String result="";

  ResultSet resultSet= (ResultSet) session.getAttribute("result");
  while(resultSet.next()){
      result+=resultSet.getString("firstname")+resultSet.getString("lastname")+resultSet.getString("city");
      result+="\n";
  }


  out.println(result);
  %>

    <br>
    <center>
        <button type="back" value = "back" name="back" >back</button>
    </center>


</center>
</body>
</html>