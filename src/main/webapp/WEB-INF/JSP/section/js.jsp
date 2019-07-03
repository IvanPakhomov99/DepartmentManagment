<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>

<script src="<c:url value="/static/js/jquery.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.js"/>"></script>
<script src="<c:url value="/static/js/app.js"/>"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/webshim/1.16.0/dev/polyfiller.js"></script>

<script>
    webshims.setOptions('forms', {
        iVal: {
            sel: '.ws-validate'
        }
    });
    webshims.polyfill('forms forms-ext');


    $(function () {
        $('input[data-relmax]').each(function () {
            var oldVal = $(this).prop('value');
            var relmax = $(this).data('relmax');
            var max = new Date();
            max.setFullYear(max.getFullYear() + relmax);
            $.prop(this, 'max', $(this).prop('valueAsDate', max).val());
            $.prop(this, 'value', oldVal);
        });
    });
</script>

<script>
    function employeeAddButton() {
        var id = $("#id").val();
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();
        var email = $("#email").val();
        var birthday = $("#birthday").val();
        var salary = $("#salary").val();
        var departmentName = "${depName}";
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
</script>
