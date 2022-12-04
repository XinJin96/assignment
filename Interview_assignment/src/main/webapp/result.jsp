<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search Result</title>
</head>
<body>
<center>
  <h1>Search Result</h1>
  <%
  String result=(String)session.getAttribute("result");
  out.println(result);
  %>

</center>
</body>
</html>