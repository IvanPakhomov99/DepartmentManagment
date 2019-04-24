<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script>

    function showAddTable() {
        document.getElementById('actionForm').reset();
        document.getElementById('actionForm').action = "employee/insert";
        document.getElementById('confirmAddingButton').style.visibility = "visible";
        document.getElementById('confirmUpdateButton').style.visibility = "hidden";
        document.getElementById('actionForm').style.visibility = "visible";
        document.getElementById('departmentField').style.visibility = "hidden";
    }

    function showEditTable(id) {
        document.getElementById('actionForm').action = "employee/update";
        document.getElementById('confirmAddingButton').style.visibility = "hidden";
        document.getElementById('confirmUpdateButton').style.visibility = "visible";
        document.getElementById('departmentField').style.visibility = "visible";
        document.getElementById('actionForm').style.visibility = "visible";
        populateEditForm(id);
    }

    function hideTable() {
        /*document.getElementById('insertForm').style.visibility = "hidden";*/
        document.getElementById('actionForm').style.visibility = "hidden";
    }
    function employeeAddButton() {
        var id = $("#id").val();
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();
        var email = $("#email").val();
        var birthday = $("#birthday").val();
        console.log(birthday)
        var salary = $("#salary").val();
        var departmentName = "${depName}";
        console.log(departmentName);
        $.ajax({
            url: "/employee/insert",
            type: "post",
            data: {
                id: id,
                firstName: firstName,
                lastName: lastName,
                email: email,
                birthday: birthday,
                salary: salary,
                departmentName: departmentName
            },
            cache: false,
            success: function (data) {
                location.reload()
            }
        });
    }
    function employeeUpdateButton() {
        var id = $("#id").val();
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();
        var email = $("#email").val();
        var birthday = $("#birthday").val();
        var salary = $("#salary").val();
        var departmentName = $("#departmentName").val();
        $.ajax({
            url: "/employee/update",
            type: "post",
            data: {
                id: id,
                firstName: firstName,
                lastName: lastName,
                email: email,
                birthday: birthday,
                salary: salary,
                departmentName: departmentName
            },
            cache: false,
            success: function (data) {
                location.reload()
            }
        });
    }

    function deleteEmployee(id) {
        var reqURL = "employee/delete?id=" + id;
        $.get(reqURL);
        location.reload();
    }

    function populateEditForm(id) {
        var reqURL = "employee/edit?id=" + id;
        $.get(reqURL, function (data) {
            var dateString = data.birthday;
            var d = new Date(dateString);
            var result = d.getFullYear() + '-' + d.getMonth() + '-' + d.getDate();
            console.log(result);
            document.getElementById('id').value = data.id;
            document.getElementById('firstName').value = data.firstName;
            document.getElementById('lastName').value = data.lastName;
            document.getElementById('email').value = data.email;
            document.getElementById('birthday').value = data.birthday;
            document.getElementById('salary').value = data.salary;
            document.getElementById('departmentName').value = data.depName;
        });
    }
</script>

<html>
<head>
    <title>Employee Web Application</title>
</head>
<body onload="hideTable()">
<div style="text-align: center;">
    <h1>EMPLOYEE</h1>
    <h2>
        <input type='button' onClick='showAddTable();' value='Add'>
        <%--<a href="../department">List All Departments</a>--%>
    </h2>
</div>
<div align="center">
    <table id="employeeTable" border="1" cellpadding="5">
        <caption><h2>List of Employee</h2></caption>
        <tr>
            <th>ID</th>
            <th>FirstName</th>
            <th>LastName</th>
            <th>Email</th>
            <th>Birthday</th>
            <th>Salary</th>
            <th>Department</th>
        </tr>
        <c:forEach var="employee" items="${employeeList}">
            <tr>
                <td><c:out value="${employee.id}"/></td>
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
    </table>
</div>


<div>
    <div align="center">
        <form id="actionForm" method="post">
            <table border="1" cellpadding="4">
                <caption>
                    <h2 id="editHeader">
                        Edit
                    </h2>
                    <h2 id=addHeader>
                        Add new Employee in the ${depName} Department
                    </h2>
                </caption>
                <%--<c:if test="${employee != null}">
                    <input type="hidden" name="id" value="<c:out value='${employee.id}' />"/>
                </c:if>--%>
                <tr hidden>
                    <td>
                        <input type="text" id="id" name="id" size="45">
                    </td>
                </tr>
                <tr>
                    <th>First Name:</th>
                    <td>
                        <input type="text" id="firstName" name="firstName" size="45"/>
                    </td>
                </tr>
                <tr>
                    <th>Last Name:</th>
                    <td>
                        <input type="text" id="lastName" name="lastName" size="45"/>
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input type="text" id="email" name="email" size="45"/>
                    </td>
                </tr>
                <tr>
                    <th>Birthday:</th>
                    <td>
                        <input type="text" id="birthday" name="birthday" size="45"/>
                    </td>
                </tr>
                <tr>
                    <th>Salary:</th>
                    <td>
                        <input type="text" id="salary" name="salary" size="45"/>
                    </td>
                </tr>
                <tr id="departmentField">
                    <th>Department:</th>
                    <td>
                        <input type="text" id="departmentName" name="departmentName" size="45"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" name="confirmAddingButton" onclick="employeeAddButton()" id="confirmAddingButton" value="Add"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" id="confirmUpdateButton" name="confirmUpdateButton" onclick="employeeUpdateButton()" value="Edit"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
