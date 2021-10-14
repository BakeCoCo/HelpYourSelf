<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring Boot JSP</title>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script lang="JavaScript">
        // window.onload = function(){
        //     alert("good start");
        // }
        $(document).ready(function() {
            alert("Ready to Start \nStart is Half");
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
        out.println("깃허브에 연결했다.<br>");
        out.println("Shift 2번 = 모두찾기<br>");
        out.println("Ctrl + Shift + A = Action 찾기");
        out.println("<br><a href='https://www.jetbrains.com/help/'>JETBRAIN help사이트</a><br>");
        out.println("<br><a href=");
        %>
        https://www.jetbrains.com/help/idea/discover-intellij-idea.html#IntelliJ-IDEA-supported-languages

        <%
        out.println("Inteli J 튜토리얼 사이트 </a>");
        %>
        <%= k %>
        <br><a href="https://stackoverflow.com/questions/37038850/why-intellij-idea-doesnt-recognize-the-dollar-operator-of-jquery">inteli j jquery 사용하기</a>
        <!-- 대충 CTRL + ALT + S 눌러서 Languages & Frameworksf  D  SDAF  -->
    </div>
    </body>
</html>