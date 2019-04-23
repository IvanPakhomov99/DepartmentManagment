<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#updateButton').click(function (e) {
            e.preventDefault();
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
                    $("#refresh").html(data);
                }
            });
        });
    });
</script>
<script>
    function insertForm() {

    }

    function updateForm(id) {
        $.get("/employee/update?id=" + id,
            {
                firstName: 'firstName',
                lastName: 'lastName',
                email: 'email',
                birthday: 'birthday',
                departmentName: 'departmentName',
                salary: 'salary',

            }
        );
    }

    /*  function showAddTable() {
          document.getElementById('insertForm').style.visibility = "visible";
      }*/

    function showEditTable(id) {
        console.log(id);
        document.getElementById('updateForm').style.visibility = "visible";
        populateEditForm(id);
    }

    function hideTable() {
        /*document.getElementById('insertForm').style.visibility = "hidden";*/
        document.getElementById('updateForm').style.visibility = "hidden";
    }

    function populateEditForm(id) {
        var reqURL = "employee/edit?id=" + id;
        $.get(reqURL, function (data) {
            document.getElementById('id').value = data.id;
            document.getElementById('firstName').value = data.firstName;
            document.getElementById('lastName').value = data.lastName;
            document.getElementById('email').value = data.email;
            document.getElementById('birthday').value = data.birthday;
            document.getElementById('salary').value = data.salary;
            document.getElementById('departmentName').value = data.depName;
        });
    }

    function deleteEmployee(id) {
        var reqURL = "employee/delete?id=" + id;
        $.get(reqURL);
        location.reload();
    }
</script>

<html>
<head>
    <title>Employee Web Application</title>
</head>
<body onload="javascript:hideTable()">
<div style="text-align: center;">
    <h1>EMPLOYEE</h1>
    <%-- <h2>
         <input type='button' onClick='javascript:showAddTable();' value='Add'>
         <a href="../department">List All Departments</a>
     </h2>--%>
</div>
<div align="center">
    <table id="employeeTable" name="employeeTable" border="1" cellpadding="5">
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
                               onClick='javascript:showEditTable(${employee.id});' value='Edit'>
                        <input type='button' onClick='javascript:deleteEmployee(${(employee.id)})' value='Delete'>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    <div align="center">
        <form id="updateForm"
              name="updateForm"
              action="employee/update">
            <%-- <form id="insertForm"
                   onSubmit="return insertForm()"
                   method="post">--%>
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
                        <input type="text" id="id" name="id" size="45"/>
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
                <tr id="editAttribute">
                    <th>Department:</th>
                    <td>
                        <input type="text" id="departmentName" name="departmentName" size="45"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" id="updateButton" value="Save"/>
                    </td>
                </tr>
            </table>
            <%--</form>--%>
        </form>
    </div>
</div>
</body>
</html>
