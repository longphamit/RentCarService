<%-- 
    Document   : detail
    Created on : Feb 16, 2021, 8:50:53 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <%@include file="header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <div style="width: 300px;height: 300px">
                    <img style="width: 500px;height: 300px"  src="${item.imageAddress}" class="card-img-top" alt="...">
                </div>
            </div>
            <aside class="col-sm-6">
                <article class="card-body p-5">
                    <h3 class="tilte mb-3">${item.name}</h3>
                    <div style="margin-bottom: 20px">
                        <c:if test="${requestScope.avgRating !=0}">
                            <c:if test="${requestScope.avgRating==5}">
                                <span style="color:#ebc934" class="fa fa-star checked"></span>
                                <span style="color:#ebc934" class="fa fa-star checked"></span>
                                <span style="color:#ebc934" class="fa fa-star checked"></span>
                                <span style="color:#ebc934" class="fa fa-star checked"></span>
                                <span style="color:#ebc934" class="fa fa-star checked"></span>
                            </c:if>
                            <c:if test="${requestScope.avgRating!=5}">
                                <c:forEach begin="1" end="${requestScope.avgRating}">
                                    <span style="color:#ebc934" class="fa fa-star checked"></span>
                                </c:forEach>
                                <c:forEach begin="1" end="${5-requestScope.avgRating}">
                                    <span class="fa fa-star"></span>
                                </c:forEach>
                            </c:if>
                        </c:if>
                        <c:if test="${requestScope.avgRating ==0}">

                            <span class="fa fa-star "></span>
                            <span class="fa fa-star "></span>
                            <span class="fa fa-star "></span>
                            <span class="fa fa-star "></span>
                            <span class="fa fa-star "></span>

                        </c:if>   
                    </div>
                    <p class="price-detail-wrap">
                        <span class="price h4 text-danger"> 
                            <span class="currency">Price: $</span><span class="num">${item.price}/Day</span>
                        </span>
                    </p>
                    <div>
                        <span style="font-weight: bold">Year: </span> ${item.yearProduct}
                    </div>
                    <div>
                        <span style="font-weight: bold">Biển số: </span> ${item.carNumber}
                    </div>
                    <div>
                        <span style="font-weight: bold">Loại xe: </span> ${item.categoryDTO.name}
                    </div>
                    <div>
                        <button style="margin-top: 30px" type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#OrderModal">
                            Đặt xe
                        </button>
                    </div>
                </article>
            </aside>
        </div>
        <h4>Bình luận</h4>            
        <div>
            <ul>
                <c:forEach items="${requestScope.listComments}" var="comment">
                    <li class="list-group-item">
                        <span class="badge badge-pill badge-primary">${comment.key}</span>
                        <p>${comment.value}</p>
                    </li>
                </c:forEach>


            </ul>
        </div>
        <div class="modal fade" id="OrderModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">${item.name}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="Order" method="POST" id="form-${item.id}">
                        <div class="modal-body">
                            <input type="hidden" name="${initParam['FORM_PRODUCT_ID']}" value="${item.id}"/>
                            <div class="mb-3">
                                <label for="modalFromDate" class="col-form-label">Từ ngày: </label><br/>
                                <input name="${initParam['FORM_FROM_DATE']}" id="modalFromDate" onkeydown="return false" type="date"/><br/>
                            </div>
                            <div class="mb-3">
                                <label for="modalToDate" class="col-form-label">Đến ngày: </label><br/>
                                <input name="${initParam['FORM_TO_DATE']}" id="modalToDate" onkeydown="return false" type="date"/><br/>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" onclick="checkFormOrder(${item.id});" class="btn btn-success">Đưa vào giỏ hàng</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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

//                                                
    </script>

    <%@include file="footer.jsp" %>
</html>
