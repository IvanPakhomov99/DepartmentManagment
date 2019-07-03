<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="section/css.jsp"/>
<jsp:include page="section/js.jsp"/>
<html>
<head>
    <title>Department Web Application</title>
</head>
<body>
<div style="text-align: center;">
    <h1>DEPARTMENTS</h1>
    <h2>
        <a href="../department/new">Add New Department</a>
    </h2>
</div>
<div class="col-md-4 col-md-offset-4">
    <table  align="center" class="table table-striped" border="1" cellpadding="5">
        <caption><h2>List of Departments</h2></caption>
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Location</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="department" items="${departmentList}">
            <tr>
                <td scope="row"><c:out value="${department.id}"/></td>
                <td><c:out value="${department.name}"/></td>
                <td><c:out value="${department.city}"/></td>
                <td>
                    <a href="../department/edit?id=<c:out value='${department.id}' />">Edit</a>
                    <a href="delete?id=<c:out value='${department.id}' />">Delete</a>
                    <a href="../employee?depName=<c:out value='${department.name}' />">Add</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
