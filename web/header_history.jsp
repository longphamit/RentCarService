<%-- 
    Document   : header
    Created on : Jan 22, 2021, 11:15:05 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    </head>
</head>
<body>
    <div class="jumbotron bg-success">
        <h1 class="d-flex justify-content-center" style="color: #ffffff">Rent Car System</h1>

        <nav class="navbar navbar-light bg-light" style="margin-top: 100px">
            <div class="container">
                <ul class="navbar-nav">
                    <c:if test="${sessionScope.USER!=null}">
                        <li class="nav-item">
                            <a href="History" style="margin: 20px;color: #ffffff;width: 200px;height: 50px;margin-right: 30px" type="button" class="btn btn-info">
                                Lịch sử đặt xe
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.USER.status eq 'NEW'}">
                        <li class="nav-item" id="btnVerifyEmailModal">
                            <button style="margin: 20px;color: #ffffff;width: 200px;height: 50px;margin-right: 30px" class="btn btn-warning"  data-toggle="modal" data-target="#verifyEmailModal">
                                Verify your email
                            </button>
                        </li>
                        <div id="verifyEmailModal" class="modal fade" role="dialog">
                            <div class="modal-dialog">
                                <!-- Modal content-->
                                <div class="modal-content" >
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Your email: </p>
                                        <p id="verifyEmail">${sessionScope.USER.userId}</p>
                                    </div>
                                    <div id="modalContent" style="margin: 0 auto">
                                        <div id="loading" style="visibility: hidden;margin:0 auto">
                                            <div style="align-self: center" class="spinner-border text-danger" role="status">
                                                <span class="sr-only">Loading...</span>
                                            </div>
                                            <p style="align-self: center">Sending...</p>
                                        </div>
                                        <div id="inputCodeDiv">
                                        </div>
                                        <br/>
                                        <div>
                                            <button id='btnSendEmail' onclick="verifyEmail(this)"  class="btn btn-primary">Send Mail</button>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <li class="nav-item">
                        <a style="margin: 20px;color: #ffffff;width: 200px;height: 50px;margin-right: 30px;" href="cart.jsp" class="btn btn-info" >
                            Xem giỏ hàng
                        </a>
                    </li>
                    <c:if test="${sessionScope.USER==null}">
                        <li class="nav-item">
                            <a style="margin: 20px;color: #ffffff;width: 200px;height: 50px;margin-right: 30px" class="btn btn-danger" href="login.jsp">
                                Login
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.USER!=null}">
                        <li class="nav-item">
                            <Form action="Logout" method="GET">
                                <button style="margin: 20px;color: #ffffff;width: 200px;height: 50px;margin-right: 30px" class="btn btn-danger">
                                    Logout
                                </button>
                            </Form>
                        </li>
                    </c:if>
                </ul>
                <div>
                    <form onsubmit="return checkSearch()" action="History" method="GET" class="d-flex">
                        <div style="margin-left: 10px;margin-top: 10px">
                            <a href="Main" class="btn btn-success" style="color: #ffffff;width: 300px;height: 50px;margin-right: 20px;margin-bottom: 30px">Trang chủ</a>
                        </div>
                        <div style="width: 200px;margin-left: 10px">
                           Tên <input id="searchNameInForm" name="${initParam['SEARCH_NAME']}" class="form-control me-2" value="${param.searchName}"  type="search" placeholder="Name" aria-label="Search">
                        </div>
                        <div style="width: 200px;margin-left: 10px">
                           Ngày tạo <input id="searchCreateDateInForm" name="searchCreateDate" class="form-control me-2" value="${param.searchCreateDate}"  type="date" onkeydown="return false"  placeholder="Search" aria-label="Search">
                        </div>
                        
                        <div style="margin-left: 10px;margin-top: 10px">
                            <button class="btn btn-info" style="color: #ffffff;width: 200px;height: 50px;margin-right: 30px;margin-left: 20px" type="submit">Search</button>
                        </div>
                        
                    </form>
                    <a href="History" class="btn btn-warning" style="color: #ffffff;width: 200px;height: 50px">Tất cả</a>
                    <a href="GetOrderRemoved" class="btn btn-warning" style="color: #ffffff;width: 200px;height: 50px">Đơn hàng đã xóa</a>
                </div>

            </div>
        </nav>
    </div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script>
                            const verifyEmail = async() => {
                                document.getElementById("btnSendEmail").disabled = true;
                                var email = document.getElementById("verifyEmail").innerHTML;
                                $("#inputCodeDiv").html("");
                                var inputDiv = "<p style='color:blue'>Enter verify code you recieved in your email</p></br>\n\
                        <input type='number' id='verifyCode'>\n\
                        <button class='btn btn-success' onclick='verifyCode()'>Verify</button>";
                                document.getElementById("loading").style.visibility = "visible";
                                await $.ajax({
                                    url: "http://localhost:8080/RentCarService/VerifyEmail?email=" + email,
                                    method: "GET",
                                    success: function (data, textStatus, jqXHR) {
                                        console.log(data)
                                        $("#inputCodeDiv").html(inputDiv);
                                        document.getElementById("loading").style.visibility = 'hidden';
                                    }
                                })
                                document.getElementById("btnSendEmail").disabled = false;
                            }
                            const verifyCode = () => {
                                var email = document.getElementById("verifyEmail").innerHTML;
                                var code = document.getElementById("verifyCode").value;
                                $.ajax({
                                    url: "http://localhost:8080/RentCarService/VerifyCode?code=" + code,
                                    method: "GET",
                                    success: function (data, textStatus, jqXHR) {
                                        console.log(data);
                                        if (data.status == 1) {
                                            document.getElementById("modalContent").innerHTML = "Your account is actived";
                                            $("#btnVerifyEmailModal").remove();
                                        } else {
                                            alert("invalid code");
                                        }
                                    }
                                })
                            }
                            const checkSearch = () => {
                                var searchName = document.getElementById("searchNameInForm").value.trim();
                                console.log(searchName)
                                var searchCreateDate = document.getElementById("searchCreateDateInForm").value.trim();

                                if (searchName == "" && searchCreateDate == "") {
                                    alert("Xin hãy nhập giá trị để tìm kiếm")
                                    return false;
                                }
                                return true;
                            }
                            $(function () {
                                $.ajax({
                                    url: "http://localhost:8080/RentCarService/AutoComplete",
                                    method: "GET",
                                    success: function (data, textStatus, jqXHR) {
                                        console.log(data)
                                        $("#searchName").autocomplete({
                                            source: data,
                                            minLength: 1,
                                        });
                                    }
                                })
                            });
</script>
</html>
