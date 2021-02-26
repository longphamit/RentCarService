<%-- 
    Document   : home
    Created on : Jan 23, 2021, 12:01:36 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    </head>
    <%@include file="header.jsp" %>

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
    <div class="container">


        <div class="row">
            <div class="row container" >
                <c:forEach items="${requestScope.listProducts}" var="item">
                    <div class="card col-sm-4" style="width: 18rem;margin-right: 30px;margin-top: 30px;padding-bottom: 30px;padding-top: 30px;border-radius: 20px;border-color: #4ca832">
                        <img style="width: 260px;height: 180px" src="${item.value.imageAddress}" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title" style="text-align: left">${item.value.name}</h5>
                            <p class="card-text" style="text-align: left">Biển số: ${item.value.carNumber}</p>
                            <p class="card-text" style="text-align: left">Năm sản xuất: ${item.value.yearProduct}</p>
                            <p class="card-text" style="text-align: left">${item.value.categoryDTO.name}</p>
                            <p class="card-text" style="text-align: left"><span class="badge badge-success">${item.value.price} USD/day</span></p>
                        </div>
                        <div>
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#OrderModal${item.key}">
                                Đặt xe
                            </button>
                            <c:url value="Detail" var="DetailLink" >
                                <c:param name="id" value="${item.value.id}"></c:param>
                            </c:url>
                            <a type="button" href="${DetailLink}" class="btn btn-info" style="color: #ffffff">
                                Xem chi tiết
                            </a>

                        </div>

                    </div>
                    <div class="modal fade" id="OrderModal${item.key}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">${item.value.name}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <form action="Order" method="POST"  id="form-${item.key}">
                                    <div class="modal-body">
                                        <input type="hidden" name="${initParam['FORM_PRODUCT_ID']}" value="${item.key}"/>
                                        <div class="mb-3">
                                            <label for="modalFromDate" class="col-form-label">Từ ngày: </label><br/>
                                            <input name="${initParam['FORM_FROM_DATE']}" id="modalFromDate-${item.key}" onkeydown="return false" type="datetime-local"/><br/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="modalToDate" class="col-form-label">Đến ngày: </label><br/>
                                            <input name="${initParam['FORM_TO_DATE']}" id="modalToDate-${item.key}" onkeydown="return false" type="datetime-local"/><br/>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="button" onclick="checkFormOrder(${item.key});" class="btn btn-success">Đưa vào giỏ hàng</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
            <div style="margin-top: 5px;margin-left:10px">
                <c:choose>
                    <c:when test="${param.page eq null}">
                        <c:out value="Trang 1/${sessionScope.maxPage}"></c:out>
                    </c:when>
                    <c:when test="${param.page ne null}">
                        <c:out value="Trang ${param.page}/${sessionScope.maxPage}"></c:out>
                    </c:when>
                </c:choose>
            </div>
            <nav aria-label="Page navigation example" class="d-flex justify-content-center" style="margin-top: 50px">

                <ul class="pagination">
                    <c:forEach begin="${sessionScope.minPage}" end="${sessionScope.maxPage}" var="i" >
                        <c:url value="/?page=${i}" var="url">
                            <c:param name="searchName" value="${param.searchName}"></c:param>
                            <c:param name="searchFromDate" value="${param.searchFromDate}"></c:param>
                            <c:param name="searchToDate" value="${param.searchToDate}"></c:param>
                            <c:param name="searchCategory" value="${param.searchCategory}"></c:param>
                        </c:url>
                        <li class="page-item"><a class="page-link" href="${pageScope.url}">${i}</a></li>
                        </c:forEach>
                </ul>

            </nav>
        </div>
    </div>
    <%@include file="footer.jsp" %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript">
                                            const checkFormOrder = async(id) => {
                                                var form = document.getElementById("form-" + id);
                                                var fromDate = form.elements[1].value;
                                                var toDate = form.elements[2].value;
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
                                            $(function () {
                                                $.ajax({
                                                    url: "http://localhost:8080/RentCarService/AutoComplete",
                                                    method: "GET",
                                                    success: function (data, textStatus, jqXHR) {
                                                        $("#searchName").autocomplete({
                                                            source: data,
                                                            minLength: 1,
                                                        });
                                                    }
                                                })

                                            })


//                                                
    </script>
</html>
