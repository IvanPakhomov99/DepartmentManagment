<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee Management</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Employee Management</h1>
    <h2>
        <c:choose>
            <c:when test="${depName != null}">
                <a href="../employee/list?depName=<c:out value='${depName}' />">List Employee</a>
            </c:when>
            <c:otherwise>
                <a href="../employee/list">List Employee</a>
            </c:otherwise>
        </c:choose>
        <a href="../department">Department List</a>
    </h2>
</div>
<div align="center">
    <c:if test="${employee != null}">
    <form action="update<c:if test="${depName != null}">?depName=<c:out value='${depName}' /></c:if>" method="post">
        </c:if>
        <c:if test="${employee == null}">
        <form  action="insert<c:if test="${depName != null}">?depName=<c:out value='${depName}' /></c:if>" method="post">
            </c:if>
            <table border="1" cellpadding="4">
                <caption>
                    <h2>
                        <c:if test="${employee != null}">
                            Edit
                        </c:if>
                        <c:if test="${employee == null}">
                            Add new Employee in the ${depName} Department
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${employee != null}">
                    <input type="hidden" name="id" value="<c:out value='${employee.id}' />"/>
                </c:if>
                <tr>
                    <th>First Name:</th>
                    <td>
                        <input type="text" id="firstName" size="45"
                               value="<c:out value='${employee.firstName}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Last Name:</th>
                    <td>
                        <input type="text" id="lastName" size="45"
                               value="<c:out value='${employee.lastName}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input type="text" id="email" size="45"
                               value="<c:out value='${employee.email}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Birthday:</th>
                    <td>
                        <input type="text" id="birthday" size="45"
                               value="<c:out value='${employee.birthday}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Salary:</th>
                    <td>
                        <input type="text" id="salary" size="45"
                               value="<c:out value='${employee.salary}' />"
                        />
                    </td>
                </tr>
                <c:if test="${employee != null && depName != null}">
                <tr>
                    <th>Department:</th>
                    <td>
                        <input type="text" id="departmentName" size="45"
                               value="<c:out value='${employee.depName}' />"
                        />
                    </td>
                </tr>
                </c:if>
                <%--<c:if test="${depName == null}">
                    <tr>
                        <th>Department:</th>
                        <td>
                            <input type="text" id="departmentName" size="45"
                                   value="<c:out value='${employee.depName}' />"
                            />
                        </td>
                    </tr>
                </c:if>--%>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
    </form>
</div>
</body>
</html>
