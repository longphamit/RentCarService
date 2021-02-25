<%-- 
    Document   : home
    Created on : Jan 6, 2021, 12:56:43 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Bootstrap Simple Login Form</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <style>
            .login-form {
                width: 340px;
                margin: 50px auto;
                font-size: 15px;
            }
            .login-form form {
                margin-bottom: 15px;
                background: #f7f7f7;
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                padding: 30px;
            }
            .login-form h2 {
                margin: 0 0 15px;
            }
            .form-control, .btn {
                min-height: 38px;
                border-radius: 2px;
            }
            .btn {        
                font-size: 15px;
                font-weight: bold;
            }
        </style>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <div class="login-form">
            <form action="Login" method="post" onsubmit="return checkCaptCha();">
                <h2 class="text-center">Log in</h2> 
                <div class="form-group">
                   Email <input name="${initParam['FORM_USER_ID']}" type="text" class="form-control" placeholder="Email" required="required">
                </div>
                <div class="form-group">
                   Mật khẩu <input name="${initParam['FORM_USER_PASSWORD']}" type="password" class="form-control" placeholder="Password" required="required">
                </div>
                <div class="g-recaptcha" data-sitekey="6Ld52EMaAAAAAP-rR8NykL93kX2-XgNVNA0cEmFP"></div>
                <br/>
                <div class="form-group">
                    <button name="action" value="login" type="submit" class="btn btn-primary btn-block">Log in</button>
                </div>
                
                <div class="clearfix">
                    <a href="<%=request.getContextPath()%>/" class="float-left">Quay về trang chủ</a>
                </div>        
            </form>
            <c:if test="${sessionScope.message!=null}">
                <c:choose>
                    <c:when test="${sessionScope.message.status==false}">
                        <p style="color: red">${sessionScope.message.content}</p>
                    </c:when>
                </c:choose>
                <c:remove var="message" scope="session" /> 
            </c:if>
            <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#exampleModal">Tạo tài khoản</button>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Đăng ký tài khoản</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form onsubmit="return checkFormRegist()" action="Register" method="POST">
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Email</label>
                                <input name="${initParam['FORM_USER_ID']}" type="text" class="form-control" id="regisName"/>
                                <p style="color: red" id="checkName"></p>
                            </div>
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Full Name</label>
                                <input name="${initParam['FORM_USER_NAME']}" type="text" class="form-control" id="regisFullName"/>
                                <p style="color: red" id="checkFullName"></p>
                            </div>
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Phone number</label>
                                <input name="${initParam['FORM_USER_PHONE']}" type="text" class="form-control" id="regisPhone"/>
                                <p style="color: red" id="checkPhone"></p>
                            </div>
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Address</label>
                                <input id="regisAddress" name="${initParam['FORM_USER_ADDRESS']}" type="text" class="form-control" >
                                <p style="color: red" id="checkAddress"></p>
                            </div>
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Mật khẩu</label>
                                <input name="${initParam['FORM_USER_PASSWORD']}" type="password" class="form-control" id="regisPass">
                                <p style="color: red" id="checkPass"></p>
                            </div>
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Nhập lại mật khẩu</label>
                                <input type="password" class="form-control" id="regisCheckPass">
                                <p style="color: red" id="checkConfirm"></p>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Đăng ký tài khoản</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script type="text/javascript">
                            const PATTERN_TEXT = "[A-Za-z0-9]{1,100}";
                            const PATTERN_EMAIL = "[A-Za-z0-9]{1,}@[A-Za-z0-9]{1,}";
                            const PATTERN_PHONE = "[0-9]{10}";
                            const checkFormRegist = () => {
                                document.getElementById("checkName").innerHTML = "";
                                document.getElementById("checkPass").innerHTML = "";
                                document.getElementById("checkConfirm").innerHTML = "";
                                document.getElementById("checkPhone").innerHTML = "";
                                document.getElementById("checkFullName").innerHTML = "";
                                document.getElementById("checkAddress").innerHTML="";
                                var regisName = document.getElementById("regisName").value.trim();
                                var regisPass = document.getElementById("regisPass").value.trim();
                                var regisCheckPass = document.getElementById("regisCheckPass").value.trim();
                                var regisPhone = document.getElementById("regisPhone").value.trim();
                                var regisFullName = document.getElementById("regisFullName").value.trim();
                                var regisAddress=document.getElementById("regisAddress").value.trim();
                                var check = true;
                                
                                if (!regisName.match(PATTERN_EMAIL)) {
                                    document.getElementById("checkName").innerHTML = "invalid email"
                                    check = false;
                                }
                                if (!regisPhone.match(PATTERN_PHONE)) {
                                    document.getElementById("checkPhone").innerHTML = "invalid phone"
                                    check = false;
                                }
                                if (!regisPass.match(PATTERN_TEXT)) {
                                    document.getElementById("checkPass").innerHTML = "invalid password"
                                    check = false;
                                }
                                if (!regisAddress.match(PATTERN_TEXT)) {
                                    document.getElementById("checkAddress").innerHTML = "invalid address"
                                    check = false;
                                }
                                if (!regisFullName.match(PATTERN_TEXT)) {
                                    document.getElementById("checkFullName").innerHTML = "invalid full name"
                                    check = false;
                                }
                                if (!regisCheckPass.match(PATTERN_TEXT)) {
                                    document.getElementById("checkConfirm").innerHTML = "invalid confirm"
                                    check = false;
                                }
                                if (check) {
                                    if (regisPass !== regisCheckPass) {
                                        document.getElementById("checkConfirm").innerHTML = "invalid confirm"
                                        check = false;
                                    }
                                }
                                return check;

                            }
                            const checkCaptCha = () => {
                                var response = grecaptcha.getResponse();
                                if (response.length == 0) {
                                    alert("Please verify you are humann!");
                                    return false;
                                } else {
                                    return true;
                                }
                                return false;
                            }

        </script>
    </body>
</html>