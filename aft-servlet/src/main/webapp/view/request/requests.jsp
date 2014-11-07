<html>
	<head>
		<jsp:include page="${pageContext.request.contextPath}/common/header.jsp" />	
		<title>Push File</title>		
	</head>
	
	<body>	
	
		<jsp:include page="${pageContext.request.contextPath}/common/navbar.jsp" />
		
		<ul class="nav nav-tabs" role="tablist">	
			<li class="active"><a href="#">All Requests</a></li>
			<li><a href="${pageContext.request.contextPath}/request/create">Create Request</a></li>
		</ul>
	
	

		<div id="requestBody" class="body">
			
			
			<table class="table table-bordered table-striped table-hover">
			
				<thead>
					<tr>
						<td>Name</td>
						<td>Status</td>
						<td>Last Updated</td>
						<td>Device</td>
					</tr>
				</thead>
								
				<tbody data-bind="foreach: Requests">
					<tr data-bind="click: $root.viewRequest">
						<td data-bind="text: requestName"></td>
						<td data-bind="text: requestStatus"></td>
						<td data-bind="text: requestUpdatedText"></td>
						<td data-bind="text: requestDeviceName"></td>						
					</tr>
				</tbody>
			</table>
		</div>

		<script src="${pageContext.request.contextPath}/js/ViewModels/Request/PFRequestListViewModel.js"></script>
		<script type="text/javascript">

			var requestListViewModel = new PFRequestListViewModel();			
			ko.applyBindings(requestListViewModel, document.getElementById("requestBody"));

		</script>


	</body>
</html>