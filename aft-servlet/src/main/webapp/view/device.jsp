<html>
	<head>
		<jsp:include page="common/header.jsp" />		
		<title>Push File</title>		
	</head>
	
	<body>		
		<jsp:include page="common/navbar.jsp" />	
	
		<div id="deviceBody" class="body">			
			
			<table class="table table-bordered table-striped table-hover">
			
				<thead>
					<tr>
						<th>Name</th>
						<th>Uid</th>
						<th class="compactCell">Active</th>						
					</tr>
				</thead>
				<tbody data-bind="foreach: devices">
					<tr>
						<td data-bind="text: deviceName"></td>
						<td data-bind="text: deviceUid"></td>
						<td class="compactCell"><input style="width: auto" type="checkbox" data-bind="checked: deviceActive"></input></td>						
					</tr>
				</tbody>
			</table>
			
		</div>
		
		
		<script src="${pageContext.request.contextPath}/js/ViewModels/PFDeviceViewModel.js"></script>
		<script type="text/javascript">

			$(document).ready(function() {
			
				$("#navDevices").toggleClass("active");
			
				var deviceViewModel = new PFDeviceViewModel();			
				ko.applyBindings(deviceViewModel, document.getElementById("deviceBody"));
			});

		</script>

	</body>
</html>