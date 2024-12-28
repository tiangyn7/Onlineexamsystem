<%@ page isErrorPage="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>

            <html>

            <head>
                <title>服务器内部错误</title>
            </head>

            <body>
                <h2>服务器发生内部错误</h2>
                <p>${exception}</p>
                <pre>
        <c:out value="${pageContext.exception}" />
    </pre>
            </body>

            </html>