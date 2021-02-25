<%-- 
    Document   : order_detail
    Created on : Feb 18, 2021, 1:28:20 PM
    Author     : ASUS
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            @import url(//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css);
            /*reset css*/
            div,label{margin:0;padding:0;}
            body{margin:20px;}
            h1{font-size:1.5em;margin:10px;}
            /****** Style Star Rating Widget *****/
            #rating{border:none;float:left;}
            #rating>input{display:none;}/*ẩn input radio - vì chúng ta đã có label là GUI*/
            #rating>label:before{margin:5px;font-size:1.25em;font-family:FontAwesome;display:inline-block;content:"\f005";}/*1 ngôi sao*/
            #rating>.half:before{content:"\f089";position:absolute;}/*0.5 ngôi sao*/
            #rating>label{color:#ddd;float:right;}/*float:right để lật ngược các ngôi sao lại đúng theo thứ tự trong thực tế*/
            /*thêm màu cho sao đã chọn và các ngôi sao phía trước*/
            #rating>input:checked~label,
            #rating:not(:checked)>label:hover, 
            #rating:not(:checked)>label:hover~label{color:#FFD700;}
            /* Hover vào các sao phía trước ngôi sao đã chọn*/
            #rating>input:checked+label:hover,
            #rating>input:checked~label:hover,
            #rating>label:hover~input:checked~label,
            #rating>input:checked~label:hover~label{color:#FFED85;}
        </style>
    </head>
    <%@include file="header_history.jsp" %>

    <body>
        <div class="container">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Hình ảnh</th>
                        <th scope="col">Tên xe</th>
                        <th scope="col">Biển số xe</th>
                        <th scope="col">Năm sản xuất</th>
                        <th scope="col">Loại xe</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Ngày thuê</th>
                        <th scope="col">Ngày trả</th>
                        <th>Đánh giá</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.listProducts}" var="item">
                        <tr>
                            <td>
                                <img style="width: 500px;height: 300px"  src="${item.value.imageAddress}" class="card-img-top" alt="...">
                            </td>
                            <td>${item.value.name}</td>
                            <td>${item.value.carNumber}</td>
                            <td>${item.value.yearProduct}</td>
                            <td>${item.value.categoryDTO.name}</td>
                            <td>${item.value.price} USD/day</td>
                            <td><fmt:formatDate value="${item.value.fromDate}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                            <td><fmt:formatDate value="${item.value.toDate}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                            <td>
                                <form action="SendRating" method="POST">
                                    <input type="hidden" name="id" value="${item.key}"/>
                                    <input type="hidden" name="idOrder" value="${param.id}"/>
                                    <div>
                                        <div id="rating">
                                            <input type="radio" id="star5" name="rating" value="5" />
                                            <label class = "full" for="star5" title="Awesome - 5 stars"></label>
                                            <input type="radio" id="star4" name="rating" value="4" />
                                            <label class = "full" for="star4" title="Pretty good - 4 stars"></label>
                                            <input type="radio" id="star3" name="rating" value="3" />
                                            <label class = "full" for="star3" title="Meh - 3 stars"></label>
                                            <input type="radio" id="star2" name="rating" value="2" />
                                            <label class = "full" for="star2" title="Kinda bad - 2 stars"></label>
                                            <input type="radio" id="star1" name="rating" value="1" />
                                            <label class = "full" for="star1" title="Sucks big time - 1 star"></label>
                                        </div>
                                    </div>
                                    <div>
                                        <textarea name="txtFeedback" placeholder="Nhận xét"></textarea>
                                    </div>
                                    <div>
                                        <button type="submit" class="btn btn-success">Gửi</button>
                                    </div>
                                </form>
                                <c:if test="${sessionScope.message!=null}">
                                    <div>
                                        <c:choose>
                                            <c:when test="${sessionScope.message.status==false}">
                                                <p style="color: red;">${sessionScope.message.content}</p>
                                            </c:when>
                                            <c:when test="${sessionScope.message.status==true}">
                                                <p style="color: green">${sessionScope.message.content}</p>
                                            </c:when>
                                        </c:choose>
                                        <c:remove var="message" scope="session" /> 
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
    <%@include file="footer.jsp" %>
</html>
