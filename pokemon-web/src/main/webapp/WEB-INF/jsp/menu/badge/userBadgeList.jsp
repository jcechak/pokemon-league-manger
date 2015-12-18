<%-- 
    Document   : badgeList
    Created on : 11.12.2015, 13:33:17
    Author     : Milos Bartak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Badges list</title>
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/TableCSSCode.css" />
        <link rel="shortcut icon" href="/pa165/resources/images/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/pa165/resources/css/site.css" />
    </head>
    <body>
        <jsp:include page="../../navigator.jsp"></jsp:include>
        <br>
        <h1>Badges</h1>
        <c:set var="stadiumsMap" value="${stadiumsMap}"></c:set>
        
    <table class="CSSTableGenerator">
        <thead>
            <tr>
                <th colspan="4">Obtain badge at</th>
            </tr>
            <tr>
                <th>Stadium name</th>
                <th>Stadium type</th>
                <th>Leader name</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${stadiumsAndTrainers}" var="pair">
                <tr>
                    <td><c:out value="${pair.key.city}"/></td>
                    <td><c:out value="${pair.key.type}"/></td>
                    <td><c:out value="${pair.value.name} ${pair.value.surname}"/></td>
                    <td>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
