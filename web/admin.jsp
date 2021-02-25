<%-- 
    Document   : admin
    Created on : Jan 22, 2021, 11:13:31 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-q2kxQ16AaE6UbzuKqyBE9/u/KzioAlnx2maXQHiDX9d4/zp8Ok3f+M7DPm+Ib6IU" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-pQQkAEnwaBkjpqZ8RU1fF1AKtTcHJwFl3pblpTlHXybJjHpMYo79HY3hIi4NKxyj" crossorigin="anonymous"></script> 
    </head>
    
    <body>
        
        <div class="container">
            <div >
                <nav class="navbar navbar-light bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand">Navbar</a>
                        <form class="d-flex">
                            <input id="searchName" class="form-control me-2" required type="search" placeholder="Search" aria-label="Search">
                            <input class="form-control me-2" required type="search" placeholder="Search" aria-label="Search">
                            <input class="form-control me-2" required type="search" placeholder="Search" aria-label="Search">
                            <button class="btn btn-outline-success" type="submit">Search</button>
                        </form>
                    </div>
                </nav>

                <ul class="nav">
                    <li class="nav-item" style="margin: 20px">
                        <button class="btn btn-primary">Thêm xe</button>
                    </li>
                    <li class="nav-item" style="margin: 20px">
                        <button class="btn btn-success">Thêm loại xe</button>
                    </li>
                    <li class="nav-item" style="margin: 20px">
                        <button class="btn btn-warning">Xem đơn hàng</button>
                    </li>

                </ul>
            </div>
            <div>
                <table class="table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên xe</th>
                            <th>Giá xe</th>
                            <th>Loại xe</th>
                            <th>Trạng thái</th>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>Toyota vios</td>
                            <td>2 USD</td>
                            <td>Toyota</td>
                            <td><span class="badge rounded-pill bg-danger">Đang thuê</span></td>
                            <td><button class="btn btn-primary">Sửa</button></td>
                            <td></td>
                            
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Toyota vios</td>
                            <td>2 USD</td>
                            <td>Toyota</td>
                            <td><span class="badge rounded-pill bg-success">Đang rảnh</span></td>
                            <td><button class="btn btn-primary">Sửa</button></td>
                            <td></td>
                            
                        </tr>
                    </thead>
                </table>
            </div>
            <div>
                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">Next</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </body>
</html>
