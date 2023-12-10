
$(document).ready(function () {
    $("#loginRequest").html(JSON.stringify(loginCallObject));
});

var username = $("#userName").val();
var password = $("#password").val();
var id = 1;

function login() {
    username = $("#userName").val();
    password = $("#password").val();
    apiCall(loginCallObject);
}

function dropDownChange(event) {
    var apiObject = apiCallArray[parseInt(event.value)]
    $("#requestMetaData").html(JSON.stringify(apiObject));
    apiCall(apiObject);
}

function apiCall(apiObject) {
    $.ajax(apiObject);
}

var loginCallObject = {
    type: "GET",
    beforeSend: function (request) {
        request.setRequestHeader('Authorization', 'Basic ' + window.btoa(username + ':' + password));
    },
    url: "http://localhost:8080/user",
    data: "",
    dataType: "json",
    contentType: "application/json; charset=utf-8",
    xhrFields: {
        withCredentials: true,
        observe: 'response'
    },
    success: function (resultData) {
        $("#loginResponse").html(JSON.stringify(resultData));
        $("#user").html(resultData.name);
        $("#role").html(resultData.role);
        id = resultData.id;
    },
    error: function (err) {
        alert("Error " + err);
    }
};

var apiCallArray = [
    {
        type: "GET",
        url: "http://localhost:8080/myAccount?id=" + id,
        data: "",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        xhrFields: {
            withCredentials: true,
            observe: 'response'
        },
        success: function (resultData) {
            $("#apiResponse").html(JSON.stringify(resultData));
        },
        error: function (err) {
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        url: "http://localhost:8080/myBalance?id=" + id,
        data: "",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        xhrFields: {
            withCredentials: true,
            observe: 'response'
        },
        success: function (resultData) {
            $("#apiResponse").html(JSON.stringify(resultData));
        },
        error: function (err) {
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        url: "http://localhost:8080/myCards?id=" + id,
        data: "",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        xhrFields: {
            withCredentials: true,
            observe: 'response'
        },
        success: function (resultData) {
            $("#apiResponse").html(JSON.stringify(resultData));
        },
        error: function (err) {
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        url: "http://localhost:8080/contact?id=" + id,
        data: "",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        xhrFields: {
            withCredentials: true,
            observe: 'response'
        },
        success: function (resultData) {
            $("#apiResponse").html(JSON.stringify(resultData));
        },
        error: function (err) {
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        url: "http://localhost:8080/myLoans?id=" + id,
        data: "",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        xhrFields: {
            withCredentials: true,
            observe: 'response'
        },
        success: function (resultData) {
            $("#apiResponse").html(JSON.stringify(resultData));
        },
        error: function (err) {
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        url: "http://localhost:8080/notices?id=" + id,
        data: "",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (resultData) {
            $("#apiResponse").html(JSON.stringify(resultData));
        },
        error: function (err) {
            alert("Error " + err);
        }
    }

];