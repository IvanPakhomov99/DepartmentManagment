<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Department Management</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Department Management</h1>
    <h2>
        <a href="../department/new">Add New Department</a>
    </h2>
</div>
<div align="center">
    <c:if test="${department != null}">
    <form action="update" method="post">
        </c:if>
        <c:if test="${department == null}">
        <form action="insert" method="post">
            </c:if>
            <table border="1" cellpadding="4">
                <caption>
                    <h2>
                        <c:if test="${department != null}">
                            Edit
                        </c:if>
                        <c:if test="${department == null}">
                            Add
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${department != null}">
                    <input type="hidden" name="id" value="<c:out value='${department.id}' />"/>
                </c:if>
                <tr>
                    <th>Department name:</th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${department.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Location:</th>
                    <td>
                        <input type="text" name="city" size="45"
                               value="<c:out value='${department.city}' />"
                        />
                    </td>
                </tr>
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
