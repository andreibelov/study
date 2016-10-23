<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><jsp:useBean id="date" class="java.util.Date" /><div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"></h3>
    </div>
    <div class="panel-body">
        <div class="row row-eq-height">
            <div class="col-xs-5 col-sm-5">
            <img class="img-responsive" alt="100%x200" data-holder-rendered="true" src="${pageContext.request.contextPath}/static/img/MysteriousStranger.png">
            <div class="caption text-center">
                <h4>Misterios Stranger</h4>
            </div>

            <div class="form-group">

                <!-- Button -->
                <button class="btn btn-md btn-primary btn-block">Add to friends</button>
                <button class="btn btn-md btn-default btn-block">Send a message</button>
            </div>
        </div>
            <div class="col-xs-7 col-sm-7">
                <dl>
                    <dt>
                        Description lists
                    </dt>
                    <dd>
                        A description list is perfect for defining terms.
                    </dd>
                    <dt>
                        Euismod
                    </dt>
                    <dd>
                        Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.
                    </dd>
                    <dd>
                        Donec id elit non mi porta gravida at eget metus.
                    </dd>
                    <dt>
                        Malesuada porta
                    </dt>
                    <dd>
                        Etiam porta sem malesuada magna mollis euismod.
                    </dd>
                    <dt>
                        Felis euismod semper eget lacinia
                    </dt>
                    <dd>
                        Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.
                    </dd>
                </dl>
            </div>
        </div>
    </div>

</div>
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
                <p>Lorem ipsum dolor sit amet, vis no reque viderer ponderum, decore appetere pro ad. Ne duo quas iusto, oblique numquam eos ut. Ut suas eleifend vix, viris fierent epicurei eam cu, nam lorem putant et. Graeco persius honestatis sed ea, his officiis deseruisse dissentias ne, in usu regione lucilius forensibus. At cum commune interesset, te molestie pericula qui, ea veri quando audire duo.</p></div>
        </li>
    </c:forEach>
</ul>