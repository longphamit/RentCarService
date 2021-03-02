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
                                <img style="width: 300px;height: 200px"  src="${item.imageAddress}" class="card-img-top" alt="...">
                            </td>
                            <td>${item.name}</td>
                            <td>${item.carNumber}</td>
                            <td>${item.yearProduct}</td>
                            <td>${item.categoryDTO.name}</td>
                            <td>${item.price} USD/day</td>
                            <td><fmt:formatDate value="${item.fromDate}" pattern="dd-MM-yyyy HH:mm"></fmt:formatDate></td>
                            <td><fmt:formatDate value="${item.toDate}" pattern="dd-MM-yyyy HH:mm"></fmt:formatDate></td>
                            <td>
                                <form action="SendRating" method="POST">
                                    <input type="hidden" name="id" value="${item.id}"/>
                                    <input type="hidden" name="idOrder" value="${param.id}"/>
                                    <div>
                                        <div id="rating">
                                            1<input type="radio" id="star5" name="rating" value="1" />
                                            2<input type="radio" id="star4" name="rating" value="2" />
                                            3<input type="radio" id="star3" name="rating" value="3" />
                                            4<input type="radio" id="star2" name="rating" value="4" />
                                            5<input type="radio" id="star1" name="rating" value="5" />
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
