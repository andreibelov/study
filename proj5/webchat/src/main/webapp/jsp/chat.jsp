<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Starter Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div id="wrapper">
    <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>

        <!-- Top Menu Items -->
        <div aria-expanded="true" id="navbar" class="navbar-collapse collapse in">
            <ul class="nav navbar-right top-nav">
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Settings</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a></li>
                        <li><a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a></li>
                        <li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a></li>
                        <li class="divider"></li>
                        <li><a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a></li>
                    </ul>
                </li>
                <li><a href="#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </nav>
    <!-- end Fixed navbar -->

    <div class="container-fluid">
        <!-- Side Menu Items -->
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="thumb">
                <img src="https://pp.vk.me/c630222/v630222188/3cb85/YfM5u7D9VEg.jpg" class="img-responsive" alt="Generic placeholder thumbnail" height="200" width="200">
                <h4>John Smith</h4>
                <span class="text-muted">Edit prifile</span>
            </div>
            <ul class="nav nav-sidebar">
                <li><a href="${pageContext.request.contextPath}/main.jsp"><i class="fa fa-fw fa-home"></i> Overview </a></li>
                <li class="active"><a href="#"><i class="fa fa-fw fa-envelope-o"></i> Messages<span class="badge">42</span><span class="sr-only">(current)</span></a></li>
                <li><a href="#"><i class="fa fa-fw fa-music"></i> Music</a></li>
                <li><a href="#">Export</a></li>
            </ul>
        </div>
        <div class="row">
            <!-- Main chat window -->
            <div class="col-sm-6 col-sm-offset-3 col-md-8 col-md-offset-2 main">
                <ul class="media-list">
                    <li class="media">
                        <a data-medium-element="true" data-placeholder="" class="pull-left editable medium-editor-placeholder" href="#" contenteditable="true">
                            <img src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" class="media-object" alt="media">
                        </a>
                        <div class="media-body">
                            <h4 data-medium-element="true" data-placeholder="" class="media-heading editable" contenteditable="true">Media heading</h4>
                            <jsp:useBean id="date" class="java.util.Date" />
                            <span class="text-muted">${date}</span>
                            <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.</p>
                        </div>
                    </li>
                    <hr />
                    <li class="media">
                        <a data-medium-element="true" data-placeholder="" class="pull-left editable medium-editor-placeholder" href="#" contenteditable="true">
                            <img src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" class="media-object" alt="media">
                        </a>
                        <div class="media-body">
                            <h4 data-medium-element="true" data-placeholder="" class="media-heading editable" contenteditable="true">Media heading</h4>
                            <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.</p>
                        </div>
                    </li>
                    <hr />
                    <li class="media">
                        <a data-medium-element="true" data-placeholder="" class="pull-left editable medium-editor-placeholder" href="#" contenteditable="true">
                            <img src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" class="media-object" alt="media">
                        </a>
                        <div class="media-body">
                            <h4 data-medium-element="true" data-placeholder="" class="media-heading editable" contenteditable="true">Media heading</h4>
                            <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.</p>
                        </div>
                    </li>
                    <hr />
                    <li class="media">
                        <a data-medium-element="true" data-placeholder="" class="pull-left editable medium-editor-placeholder" href="#" contenteditable="true">
                            <img src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" class="media-object" alt="media">
                        </a>
                        <div class="media-body">
                            <h4 data-medium-element="true" data-placeholder="" class="media-heading editable" contenteditable="true">Media heading</h4>
                            <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.</p>
                        </div>
                    </li>
                    <hr />
                    <li class="media">
                        <a data-medium-element="true" data-placeholder="" class="pull-left editable medium-editor-placeholder" href="#" contenteditable="true">
                            <img src="https://pp.vk.me/c627323/v627323668/45c16/yg5SuFY-e4s.jpg" class="media-object" alt="media">
                        </a>
                        <div class="media-body">
                            <h4 data-medium-element="true" data-placeholder="" class="media-heading editable" contenteditable="true">Media heading</h4>
                            <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.</p>
                        </div>
                    </li>
                    <hr />
                </ul>
            </div>
            <div class="col-sm-3 col-md-2 rightbar">
                <div class="list-group">
                    <a data-medium-element="true" data-placeholder="" href="#" class="list-group-item active editable" contenteditable="true">Cras justo odio</a>
                    <a data-medium-element="true" data-placeholder="" href="#" class="list-group-item editable" contenteditable="true">Dapibus ac facilisis in</a>
                    <a data-medium-element="true" data-placeholder="" href="#" class="list-group-item editable" contenteditable="true">Morbi leo risus</a>
                    <a data-medium-element="true" data-placeholder="" href="#" class="list-group-item editable" contenteditable="true">Porta ac consectetur ac</a>
                    <a data-medium-element="true" data-placeholder="" href="#" class="list-group-item editable" contenteditable="true">Vestibulum at eros</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
