<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Department Web Application</title>
</head>
<body>
<div style="text-align: center;">
    <h1>DEPARTMENTS</h1>
    <h2>
        <a href="../department/new">Add New Department</a>
        <a href="../department/list">Departments List</a>
        <a href="../employee">Employee List</a>
    </h2>
</div>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Departments</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Location</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="department" items="${departmentList}">
                <tr>
                    <td><c:out value="${department.id}" /></td>
                    <td><c:out value="${department.name}" /></td>
                    <td><c:out value="${department.city}" /></td>
                    <td>
                    	<a href="edit?id=<c:out value='${department.id}' />">Edit</a>
                    	<a href="delete?id=<c:out value='${department.id}' />">Delete</a>
                        <a href="../employee?depName=<c:out value='${department.name}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
