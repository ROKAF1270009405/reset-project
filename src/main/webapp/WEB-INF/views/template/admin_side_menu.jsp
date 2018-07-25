<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-ex1-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="${goRoot}admin/">Reset-Admin</a>
	</div>
         <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu message-dropdown">
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>John Smith</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>John Smith</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>John Smith</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-footer">
                            <a href="#">Read All New Messages</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu alert-dropdown">
                        <li>
                            <a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">View All</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>

	<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
<div class="collapse navbar-collapse navbar-ex1-collapse">
		<ul class="nav navbar-nav side-nav">
			<li class="active"><a href="${goRoot}admin/">Dashboard</a></li>
			<li><a href="javascript:;" data-toggle="collapse" data-target="#menu1">회원관리</a>
				<ul id="menu1" class="collapse">
					<li><a href="${goRoot}admin/member/normal/">일반회원</a></li>
					<li><a href="${goRoot}admin/member/company/">기업회원</a></li>
					<li><a href="${goRoot}admin/member/emp/">직원관리</a></li>
				</ul>
			</li>
			<li><a href="javascript:;" data-toggle="collapse" data-target="#menu2">홈페이지관리</a>
				<ul id="menu2" class="collapse">
					<li><a href="${goRoot}admin/page/login/">로그인페이지</a></li>
					<li><a href="${goRoot}admin/qna/">문의사항</a></li>
					<li><a href="${goRoot}admin/notice/">공지사항</a></li>
				</ul>
			</li>
			<li><a href="${goRoot}admin/member/reviews/">리뷰</a></li>
			<li><a href="${goRoot}admin/member/magazines/">매거진</a></li>
			<li><a href="${goRoot}admin/member/events/">이벤트</a></li>
			<li><a href="${goRoot}admin/member/items/">상품관리</a></li>
			<li><a href="javascript:;" data-toggle="collapse" data-target="#menu3">통계</a>
				<ul id="menu3" class="collapse">
					<li><a href="${goRoot}admin/chart/login/">접속자수</a></li>
					<li><a href="${goRoot}admin/chart/review/">리뷰</a></li>
					<li><a href="${goRoot}admin/chart/magzine/">매거진</a></li>
					<li><a href="${goRoot}admin/chart/event/">이벤트</a></li>
					<li><a href="${goRoot}admin/chart/like/">좋아요</a></li>
				</ul>
			</li>
			<li><a href="javascript:;" data-toggle="collapse" data-target="#menu4">로그</a>
				<ul id="menu4" class="collapse">
					<li><a href="${goRoot}admin/log/">전체로그</a></li>
					<li><a href="${goRoot}admin/log/error/">에러로그</a></li>
				</ul>
			</li>
		</ul>
	</div>
</nav>
<!-- /.navbar-collapse -->