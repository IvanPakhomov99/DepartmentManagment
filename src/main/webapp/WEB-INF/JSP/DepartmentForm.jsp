<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="section/css.jsp"/>
<jsp:include page="section/js.jsp"/>
<html>
<head>
    <title>Department Management</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Department Management</h1>
    <h2>
        <a href="../department/list">Departments List</a>
    </h2>
</div>
<div class="col-md-4 col-md-offset-4">
    <c:if test="${department != null}">
    <form action="update" method="post">
        </c:if>
        <c:if test="${department == null}">
        <form action="insert" method="post">
            </c:if>
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
                <div class="form-group">
                    <input type="hidden" name="id" value="<c:out value='${department.id}' />"/>
                </div>
            </c:if>
            <div class="form-group">
                <label for="name">Department name</label>
                <input type="text" class="form-control" id="name" name="name" required value="${department.name}"/>
            </div>
            <div class="form-group">
                <label for="city">Location</label>
                <input type="text" class="form-control" id="city" name="city" pattern="[A-z]{1,40}" required value="${department.city}"/>
            </div>
            <input type="submit" class="btn btn-primary" value="Save"/>
        </form>
    </form>
</div>
</body>
</html>
