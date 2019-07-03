<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:include page="section/css.jsp"/>
<jsp:include page="section/js.jsp"/>
<html>
<head>
    <title>Employee Web Application</title>
</head>
<body onload="hideTable()">
<div style="text-align: center;">
    <h1>EMPLOYEE</h1>
    <h2>
        <p><a href="../department/list">List All Departments</a></p>
        <p><input type='button' onClick='showAddTable();' value='Add'></p>
    </h2>
</div>
<div class="col-md-4 col-md-offset-4">
    <table align="center" class="table table-striped" id="employeeTable" border="1" cellpadding="5">
        <caption><h2>List of Employee</h2></caption>
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">FirstName</th>
            <th scope="col">LastName</th>
            <th scope="col">Email</th>
            <th scope="col">Birthday</th>
            <th scope="col">Salary</th>
            <th scope="col">Department</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="employee" items="${employeeList}">
            <tr>
                <td scope="row"><c:out value="${employee.id}"/></td>
                <td><c:out value="${employee.firstName}"/></td>
                <td><c:out value="${employee.lastName}"/></td>
                <td><c:out value="${employee.email}"/></td>
                <td><c:out value="${employee.birthday}"/></td>
                <td><c:out value="${employee.salary}"/></td>
                <td><c:out value="${employee.depName}"/></td>
                <td>
                    <c:if test="${depName != null}">
                        <input type='button' data-employeeId='${employee.id}'
                               onClick='showEditTable(${employee.id});' value='Edit'>
                        <input type='button' onClick='deleteEmployee(${(employee.id)})' value='Delete'>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<div class="col-md-4 col-md-offset-4">
    <h2 style="display: none" id="addHeader">
        Add new employee in the ${depName} department
    </h2>
    <h2 style="display: none" id="editHeader">
        Edit selected employee
    </h2>
    <form class="ws-validate" id="actionForm" method="post">
        <div hidden class="form-group">
            <label for="id">Id</label>
            <input type="text" class="form-control" id="id">
        </div>
        <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" class="form-control" id="firstName" pattern="[A-z]{2,20}" placeholder="Vania" size="20" required/>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" class="form-control" id="lastName" pattern="[A-z]{2,20}" placeholder="Pakhomov" size="20" required/>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" size="45" placeholder="example@example.com" multiple required/>
        </div>
        <div class="form-group">
            <label for="birthday">Birthday</label>
            <input type="date" class="form-control" id="birthday" data-relmax="-18"/>
        </div>
        <div class="form-group">
            <label for="salary">Salary</label>
            <input type="number" class="form-control" id="salary" size="10"/>
        </div>
        <div id="departmentField" class="form-group">
            <label for="departmentName">Department</label>
            <select class="form-control" id="departmentName">
                <c:forEach var="department" items="${departmentList}">
                    <option>${department.name}</option>
                </c:forEach>
            </select>
        </div>
        <input class="btn btn-primary" type="submit" id="confirmAddingButton"
               onclick="employeeAddButton()">

        <input class="btn btn-primary" type="submit" id="confirmUpdateButton"
               onclick="employeeUpdateButton()">

    </form>
</div>
</body>
</html>
