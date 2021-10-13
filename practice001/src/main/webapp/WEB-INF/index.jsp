<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring Boot JSP</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script language="JavaScript">
        $(document).ready(function (){
            alert("hi");
        });

        function kCl(){
            alert("good man");
        }
    </script>
</head>
    <body>
        <input type="button" value="hiroo" size="24" onclick="kCl()">
    <div>
        <%!
        int k = 5;

        %>
        <%for(int i=0; i<k; i++)
        out.println("Tomcat Server Start "+i + "<br>");
        out.println("깃허브에 연결했다.");
        %>
        <%= k %>

    </div>
    </body>
</html>