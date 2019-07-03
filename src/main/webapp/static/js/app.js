function showAddTable() {
    document.getElementById('actionForm').reset();
    document.getElementById('addHeader').style.display = "block";
    document.getElementById('editHeader').style.display = "none";
    document.getElementById('actionForm').action = "employee/insert";
    document.getElementById('confirmAddingButton').style.visibility = "visible";
    document.getElementById('confirmUpdateButton').style.visibility = "hidden";
    document.getElementById('actionForm').style.visibility = "visible";
    document.getElementById('departmentField').style.visibility = "hidden";
}

function showEditTable(id) {
    document.getElementById('actionForm').action = "employee/update";
    document.getElementById('addHeader').style.display = "none";
    document.getElementById('editHeader').style.display = "block";
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

/*
function employeeAddButton(depName) {
    var id = $("#id").val();
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var email = $("#email").val();
    var birthday = $("#birthday").val();
    console.log(birthday)
    var salary = $("#salary").val();
    var departmentName = depName;
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
*/
function validateData() {
    var reqURL = "department/insert" + id;
    $.post(reqURL, function (data) {
        var dateString = data.birthday;
        var d = new Date(dateString);
        var result = d.getFullYear() + '-' + d.getMonth() + '-' + d.getDate();
        console.log(result);
        document.getElementById('id').value = data.id;
        document.getElementById('firstName').value = data.firstName;
    })
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
