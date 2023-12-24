
$(document).ready(function () {
    accessCode = getParameterByName('code');
    $("#accessCode").html("Access code" + accessCode);
    $("#code").val(accessCode);

    $('#submitButton').click(function (e) {
        e.preventDefault();
        $.ajax({
            url: 'http://localhost:8080/realms/eazybankdev/protocol/openid-connect/token',
            type: 'post',
            dataType: 'json',
            data: $('#myForm').serialize(),
            success: function (data) {
                console.log(data);
                accessToken = data.access_token;
            },
            error: function (err) {
                alert("Error -->" + JSON.stringify(err.responseJSON));
            }
        });
    });
});

function getParameterByName(name, url = window.location.href) {
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

var accessCode = "";
var accessToken = "";
var id = "abc@xyx.com";




function dropDownChange(event) {
    var apiObject = apiCallArray[parseInt(event.value)]
    $("#requestMetaData").html(JSON.stringify(apiObject));
    apiCall(apiObject);
}

function apiCall(apiObject) {
    $.ajax(apiObject);
}


var apiCallArray = [
    {
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader('Authorization', "Bearer " + accessToken);
        },
        url: "http://localhost:8081/myAccount?email=" + id,
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
            $("#apiResponse").html(JSON.stringify(err.responseJSON));
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader('Authorization', "Bearer " + accessToken);
        },
        url: "http://localhost:8081/myBalance?email=" + id,
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
            $("#apiResponse").html(JSON.stringify(err.responseJSON));
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader('Authorization', "Bearer " + accessToken);
        },
        url: "http://localhost:8081/myCards?email=" + id,
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
            $("#apiResponse").html(JSON.stringify(err.responseJSON));
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader('Authorization', "Bearer " + accessToken);
        },
        url: "http://localhost:8081/contact?email=" + id,
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
            $("#apiResponse").html(JSON.stringify(err.responseJSON));
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader('Authorization', "Bearer " + accessToken);
        },
        url: "http://localhost:8081/myLoans?email=" + id,
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
            $("#apiResponse").html(JSON.stringify(err.responseJSON));
            alert("Error " + err);
        }
    },
    {
        type: "GET",
        url: "http://localhost:8081/notices?email=" + id,
        data: "",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (resultData) {
            $("#apiResponse").html(JSON.stringify(resultData));
        },
        error: function (err) {
            $("#apiResponse").html(JSON.stringify(err.responseJSON));
            alert("Error " + err);
        }
    }

];