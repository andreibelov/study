<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="date" class="java.util.Date" />
<div class="row">
    <div class="col-sm-12">
        <ul class="media-list">
            <c:forEach begin="1" end="10" varStatus="loop">
                <li class="media">
                    <div class="media-heading">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object" src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" alt="Generic placeholder image">
                            </a>
                        </div>
                        <div class="media-body"><h5 class="media-heading">Media heading</h5>
                            Some text here for example data</div></div>
                    <div class="media-body">

                        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                        <div class="media m-t-2">
                            <a class="media-left" href="#">
                                <img class="media-object" src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" alt="Generic placeholder image">
                            </a>
                            <div class="media-body">
                                <h4 class="media-heading">Nested media heading</h4>
                                Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                            </div>
                        </div>
                    </div>
                </li>
                <hr />
            </c:forEach>
        </ul>
    </div>
</div>
