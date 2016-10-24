<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="date" class="java.util.Date" />
<div class="row">
    <div class="col-sm-12">
        <ul class="media-list">
            <c:forEach begin="1" end="10" varStatus="loop">
                <li class="media well">
                    <div class="media-heading">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object" src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" alt="Generic placeholder image">
                            </a>
                        </div>
                        <div class="media-body"><h4 class="media-heading"><a href="#">Media heading</a></h4><span class="text-muted">Posted on ${date}</span></div></div>
                    <div class="media-body">
                        <hr style="margin: 7px 0;border-top: 1px solid #A8A8A8;">
                        <p>Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.</p>
                        <p>Lorem ipsum dolor sit amet, vis no reque viderer ponderum, decore appetere pro ad. Ne duo quas iusto, oblique numquam eos ut. Ut suas eleifend vix, viris fierent epicurei eam cu, nam lorem putant et. Graeco persius honestatis sed ea, his officiis deseruisse dissentias ne, in usu regione lucilius forensibus. At cum commune interesset, te molestie pericula qui, ea veri quando audire duo.</p>
                    </div>
                    <hr style="margin: 7px 0;border-top: 1px solid #A8A8A8;">
                    <div class="form-group">
                        <label for="comment">Comment:</label>
                        <textarea class="form-control" rows="3" id="comment" style="resize: none;"></textarea>
                    </div>
                </li>
                <hr />
            </c:forEach>
        </ul>
    </div>
</div>
