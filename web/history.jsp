<%-- 
    Document   : history
    Created on : Feb 17, 2021, 11:36:00 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <%@include file="header_history.jsp" %>
    <c:if test="${sessionScope.message!=null}">
        <c:choose>
            <c:when test="${sessionScope.message.status==false}">
                <p style="color: red;margin: auto;text-align: center;
                   width: 50%;
                   border: 3px solid green;
                   padding: 10px">${sessionScope.message.content}</p>
            </c:when>
            <c:when test="${sessionScope.message.status==true}">
                <p style="color: green;margin: auto;text-align: center;
                   width: 50%;
                   border: 3px solid green;
                   padding: 10px">${sessionScope.message.content}</p>
            </c:when>
        </c:choose>
        <c:remove var="message" scope="session" /> 
    </c:if>
    <body>
        <div class="container">
            <c:choose>
                <c:when test="${requestScope.listOrders.size()==0}">
                    <div class="alert alert-danger" role="alert">
                        <p style="text-align: center">Không có xe đã xóa</p>
                        <a style="margin-left: 600px" href="Main">Trang chủ</a>
                    </div>
                </c:when>
                <c:when test="${requestScope.listOrders.size()>0}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col"># Mã đơn hàng</th>
                                <th scope="col">Ngày đặt</th>
                                <th scope="col">Sum</th>
                                <th scope="col">Total</th>
                                <th scope="col">Mã giảm giá</th>
                                <th></th>
                                    <c:if test="${requestScope.getAll eq true}">
                                    <th><p style="margin-left: 30px">Xóa</p></th>
                                    </c:if>

                            </tr>
                        </thead>
                        <form action="RemoveOrder" method="POST">
                            <tbody>
                                <c:forEach items="${requestScope.listOrders}" var="order">
                                    <tr>
                                        <td>${order.id}</td>
                                        <td>${order.createDate}</td>
                                        <td>${order.sum} USD</td>
                                        <td>${order.total} USD</td>
                                        <c:if test="${null eq order.discountId}">
                                            <td>không áp dụng discount</td>
                                        </c:if>
                                        <c:if test="${null ne order.discountId}">
                                            <td>${order.discountId}</td>
                                        </c:if>
                                        <td>
                                            <a style="color:white" class="btn btn-info" href="OrderDetail?id=${order.id}">xem chi tiết</a>
                                        </td>
                                        <c:if test="${order.status eq true}">
                                            <td><input style="margin-left: 40px" type="checkbox" id="vehicle1" name="checkRemove" value="${order.id}"></td>
                                            </c:if>

                                    </tr>
                                <p></p>
                            </c:forEach>
                            <tr>
                                <td colspan="5"></td>
                                <td></td>

                                <c:if test="${requestScope.getAll eq true}">
                                    <td><button type="submit" style="width: 100px" class="btn btn-danger">Xóa</button></td>
                                </c:if>

                            </tr>
                            </tbody>
                        </form>
                    </table>
                </c:when>
            </c:choose>

        </div>
    </body>
    <%@include file="footer.jsp" %>
</html>
