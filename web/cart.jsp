<%-- 
    Document   : cart
    Created on : Jan 31, 2021, 4:21:10 PM
    Author     : ASUS
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="header.jsp" %>

<div class="container">
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
    <c:choose>
        <c:when test="${sessionScope.cartSession.getItems().size() >0}">
            <table class="table table-borderless">
                <thead>
                    <tr>
                        <th scope="col">tên</th>
                        <th scope="col">Biển số</th>
                        <th scope="col">Từ ngày</th>
                        <th scope="col">Đến ngày</th>
                        <th scope="col">Giá</th>
                    </tr>
                </thead>

                <tbody>
                <form action="RemoveFromCart" method="POST">
                    <c:forEach items="${sessionScope.cartSession.getItems()}" var="item">
                        <tr>
                            <td>${item.name}</td>
                            <td>${item.carNumber}</td>
                            <td><fmt:formatDate value="${item.fromDate}" pattern="yyyy/MM/dd"></fmt:formatDate></td>
                            <td><fmt:formatDate value="${item.toDate}" pattern="yyyy/MM/dd"></fmt:formatDate></td>
                            <td>${item.price} USD</td>
                            <td><button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#OrderModal${item.id}">Sửa</button></td>
                            <td><input style="margin-left: 19px" type="checkbox" id="vehicle1" name="checkRemove" value="${item.id}@<fmt:formatDate value="${item.fromDate}" pattern="yyyy/MM/dd"></fmt:formatDate>@<fmt:formatDate value="${item.toDate}" pattern="yyyy/MM/dd"></fmt:formatDate>"></td>
                            </tr>
                    </c:forEach>
                    <c:if test="${sessionScope.cartSession.getItems().size()>0}">
                        <tr>
                            <td colspan="6"></td>
                            <td><button type="submit" name="action" style="width: 60px;height: 40px"  class="btn btn-danger">Xóa</button></td>
                        </tr>
                    </c:if>
                </form>
                <c:forEach items="${sessionScope.cartSession.getItems()}" var="item">
                    <div class="modal fade" id="OrderModal${item.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">${item.name}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <form action="UpdateFromCart" method="POST" id="form-${item.id}">
                                    <div class="modal-body">
                                        <input type="hidden" name="oldToDate" value="<fmt:formatDate value="${item.toDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                                        <input type="hidden" name="oldFromDate" value="<fmt:formatDate value="${item.fromDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
                                        <input type="hidden" name="${initParam['FORM_PRODUCT_ID']}" value="${item.id}"/>
                                        <div class="mb-3">
                                            <label for="modalFromDate" class="col-form-label">Từ ngày: </label><br/>
                                            <input   name="${initParam['FORM_FROM_DATE']}" id="modalFromDate-${item.id}" onkeydown="return false" type="date"/><br/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="modalToDate" class="col-form-label">Đến ngày: </label><br/>
                                            <input name="${initParam['FORM_TO_DATE']}" id="modalToDate-${item.id}" onkeydown="return false" type="date"/><br/>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="button" onclick="checkFormOrder(${item.id})" class="btn btn-success">Update</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <p id='totalHidden' style="visibility: hidden">${sessionScope.cartSession.total()}</p>
                <tr>
                    <th scope="row">Nhập mã giảm giá</th>

                    <td colspan="3">
                        <input id="userDiscountId" placeholder=""/>
                        <div id="loadingCheckCode" class="spinner-border text-success" role="status" style="visibility: hidden">
                        </div>
                        <p id="checkDiscountCodeResult"></p>
                        <button class="btn btn-success" onclick="checkDiscountCode()">Sử dụng</button>
                        <button class="btn btn-danger" onclick="clearDiscount()">Không sử dụng</button>
                        
                    </td>
                    <td>
                        <p style="color: red" id="discountCode"></p>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Tổng tiền USD</th>
                    <td colspan="3"></td>
                    <td><p id="total" style="color: green;font-weight: bold;font-size: 20px">${sessionScope.cartSession.total()}</p></td>
                </tr>
                <tr>
                    <td>
                        <a style="width: 200px;height: 50px;margin: 20px" class="btn btn-secondary" href="${pageContext.servletContext.contextPath}">Tiếp tục đặt xe</a>
                    </td>
                    <td >
                        <form action="CheckOut" method="POST">
                            <input id="discountInForm" name="txtDiscount" type="hidden"/>
                            <input id="discountCodeInForm" name="txtDiscountId" type="hidden"/>
                            <button style="width: 200px;height: 50px;margin: 20px" type="submit" class="btn btn-success">Thanh toán</button>
                        </form>
                    </td>
                </tr>
                <tr>
                </tr>
                </tbody>
                <tfoot>
                </tfoot>
            </table>
        </c:when>
        <c:when test="${sessionScope.cartSession.getItems().size()==0}">
            <div class="alert alert-danger" role="alert">
                <p style="text-align: center">Bạn chưa chọn xe</p>
                <a style="margin-left: 600px" href="Main">Trang chủ</a>
            </div>
        </c:when>
        <c:when test="${sessionScope.cartSession eq null}">
            <div class="alert alert-danger" role="alert">
                <p style="text-align: center">Bạn chưa chọn xe</p>
                <a style="margin-left: 600px" href="Main">Trang chủ</a>
            </div>
        </c:when>
    </c:choose>           

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">

                            const checkFormOrder = async(id) => {
                                var form = document.getElementById("form-" + id);
                                var fromDate = form.elements[3].value;
                                var toDate = form.elements[4].value;
                                if (fromDate == "" || toDate == "") {
                                    alert("Vui lòng chọn ngày mượn và ngày trả")
                                    return false;
                                }
                                if (fromDate != "") {
                                    var d = new Date(fromDate);
                                    var currentDate = new Date()
                                    if (d.getTime() < currentDate.getTime()) {
                                        alert("Vui lòng chọn ngày thuê từ ngày hiện tại")
                                        return false;
                                    }
                                }
                                if (fromDate != "" && toDate != "") {
                                    var dFromDate = new Date(fromDate);
                                    var dToDate = new Date(toDate);
                                    if (dFromDate.getTime() > dToDate.getTime()) {
                                        alert("Ngày thuê xe phải nhỏ hơn ngày trả")
                                        return false;
                                    }
                                }
                                await $.ajax({
                                    url: "http://localhost:8080/RentCarService/Ajax?action=checkOrder",
                                    method: "GET",
                                    cache: false,
                                    data: {
                                        fromDate: fromDate,
                                        toDate: toDate,
                                        id: id
                                    },
                                    success: function (data, textStatus, jqXHR) {
                                        if (data.status == false) {
                                            alert(data.content);
                                            return false;
                                        } else {
                                            alert("oke dat thanh cong");
                                            form.submit();
                                        }
                                    }
                                });
                            }
                            const checkDiscountCode = () => {
                                
                                var code = document.getElementById("userDiscountId").value;
                                if (code != "") {
                                    document.getElementById("loadingCheckCode").style.visibility = "visible"
                                    $.ajax({
                                        url: "http://localhost:8080/RentCarService/CheckDiscount?code=" + code,
                                        method: "GET",
                                        success: function (data, textStatus, jqXHR) {
                                            console.log(data)
                                            if (data.status == 1) {
                                                document.getElementById("checkDiscountCodeResult").style.color = "green"
                                                document.getElementById("checkDiscountCodeResult").innerHTML = "Đã áp dụng mã giảm giá";
                                                document.getElementById("discountCode").innerHTML = document.getElementById("totalHidden").innerHTML + " - " + document.getElementById("totalHidden").innerHTML + "x" + data.discount
                                                document.getElementById("total").innerHTML = document.getElementById("totalHidden").innerHTML - document.getElementById("totalHidden").innerHTML * data.discount
                                                document.getElementById("discountCodeInForm").value = code
                                                document.getElementById("discountInForm").value = data.discount
                                            } else {
                                                document.getElementById("checkDiscountCodeResult").style.color = "red"
                                                if (data.status == -1) {
                                                    document.getElementById("checkDiscountCodeResult").innerHTML = "Mã giảm giá không phù hợp";
                                                }
                                                if (data.status == -2) {
                                                    document.getElementById("checkDiscountCodeResult").innerHTML = "Bạn đã sử dụng mã giảm giá này";
                                                }
                                            }
                                            document.getElementById("loadingCheckCode").style.visibility = "hidden";
                                        }

                                    })
                                } else {
                                    alert("Xin hãy nhập mã giảm giá")
                                }

                            }
                            const clearDiscount = () => {
                                if (document.getElementById("userDiscountId").value == "") {
                                    alert("Xin hãy nhập mã giảm giá")
                                } else {
                                    document.getElementById("userDiscountId").value = ""
                                    document.getElementById("checkDiscountCodeResult").innerHTML = "Đã xóa mã giảm giá";
                                    document.getElementById("discountCode").innerHTML = ""
                                    document.getElementById("discountCodeInForm").value = ""
                                    document.getElementById("discountInForm").value = ""
                                    document.getElementById("total").innerHTML = document.getElementById("totalHidden").innerHTML
                                }
                            }
</script>
<%@include file="footer.jsp" %>
