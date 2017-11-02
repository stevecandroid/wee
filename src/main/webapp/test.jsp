<%--
  Created by IntelliJ IDEA.
  User: steve
  Date: 17-10-30
  Time: 下午7:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session</title>
</head>
<body>
    <font size="64" color="purple"> My Session is :  <%= request.getSession().getId() %> </font>
</body>
</html>
