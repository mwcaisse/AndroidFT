<html>
	<head>
		<jsp:include page="common/header.jsp" />	
		
		<title>Push File</title>		
	</head>
	
	<body>	
	
		<jsp:include page="common/navbar.jsp" />
		
		<ul class="nav nav-tabs" role="tablist">	
			<li><a href="${pageContext.request.contextPath}/request/">All Requests</a></li>
			<li class="active"><a id="liViewRequest" href="#">Create Request</a></li>
			<li><a id="liCreateRequest" href="${pageContext.request.contextPath}/request/create">Create Request</a></li>
		</ul>
	
	
		<script type="text/html" id="request-status-non-modifiable">
			<tr>
				<td>Status</td> 
				<td data-bind="text: requestStatus"></td>			
			</tr>
		</script>
		
		<script type="text/html" id="request-status-modifiable">
			<tr>
				<td>Status</td> 
				<td><select data-bind="value: requestStatus, options: $root.requestStatuses"></select> </td>			
			</tr>
		</script>
		
		<script type="text/html" id="request-device-non-modifiable">
			<tr>
				<td>Status</td> 
				<td data-bind="text: requestDevice().deviceName"></td>			
			</tr>
		</script>
		
		<script type="text/html" id="request-device-modifiable">
			<tr>
				<td>Device</td> 
				<td><select data-bind="value: requestDevice, options: $root.devices, optionsText: 'deviceName'"></select> </td>			
			</tr>	
		</script>
	

		<div id="requestBody" class="body">			

			<table class="table table-bordered" style="width:auto" data-bind="with: request">
			
				<tbody>				
					<tr>
						<td>Request Name</td> 
						<td><input type="text" data-bind="value: requestName, valueUpdate: 'afterkeydown'" /> </td>			
					</tr>
					<tr>
						<td>Base Directory</td> 
						<td><select data-bind="value: requestDirectory, options: $root.requestDirectories"></select> </td>			
					</tr>
					<tr>
						<td>File Path</td> 
						<td><input type="text" data-bind="value: requestFileLocation, valueUpdate: 'afterkeydown'" /> </td>			
					</tr>
					
					<!-- ko template: {name: statusTemplate, data: $data} -->
					<!-- /ko -->
					
					<!-- ko template: {name: deviceTemplate, data: $data} -->
					<!-- /ko -->
					
					<tr>
						<td>Device Key</td>
						<td><input type="text" data-bind="value: requestDeviceKey, valueUpdate: 'afterkeydown'" /> </td>
					</tr>
					
					<!-- ko ifnot: isNew -->					
					<tr>
						<td>Last Updated</td>
						<td data-bind="text: updateDateString"></td>
					</tr>					
					<!-- /ko -->
						
				</tbody>
			</table>
			
			<!-- ko if: request().requestError -->
			<div class="alert alert-danger" role="alert">
				<strong>Error! </strong><span data-bind="text: request().requestErrorText"></span>
			</div>
			<!-- /ko -->
			
			<div id="divFiles">
				<table class="table table-bordered table-stripped" style="width: auto">
					<thead>				
						<tr>
							<th>File Name</th>
							<th>Download</th>
							<th>Remove</th>
						</tr>
					</thead>
					
					<tbody>				
						<!-- ko foreach: request().requestFiles -->
						<tr>
							<td data-bind="text: fileName"></td>
							<td><a data-bind="attr: {href: downloadUrl}">Download</a></td>
							<td data-bind="click: $root.removeRequestFile"><span class="glyphicon glyphicon-minus"></span></td>
							
						</tr>
						<!-- /ko -->
						
						<tr>
							<td colspan="3" data-bind="click: $root.addRequestFile">
								<span class="glyphicon glyphicon-plus"></span> Add File
							</td>
						</tr>				
					</tbody>
				
				</table>
			</div>
			
			<!-- ko if: request().requestFilesError -->
			<div class="alert alert-danger" role="alert">
				<strong>Error! </strong><span data-bind="text: request().requestFilesErrorText"></span>
			</div>
			<!-- /ko -->
			
		

			<div id="controlGroup" class="btn-group">
			
				<button type="button" class="btn" data-bind="click: saveRequest">Save</button>
				<button type="button" class="btn" data-bind="click: discard">Cancel</button>
			
			</div> <!-- ControlGroup -->

		</div>
		
		<!--  The File Upload modal -->
		
		<div id="divFileUploadModal" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-bind="click: hide">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">Request File Upload</h4>
					</div>
					<div class="modal-body">
						
						<input id="fileUploadInput" type="file" multiple data-bind="fileUpload: files" style="display: none"/>
						<h4>
							<a href="#" data-bind="click: selectFiles">Select Files</a>
						</h4>
						
						<table style="padding-top: 20px" class="table table-bordered table-striped table-hover">
						
							<thead>
								<tr>
									<th>File Name</th>
									<th class="compactCell">Remove</th>
								</tr>
							</thead>
							
							<tbody data-bind="foreach: files">
								<tr>
									<td data-bind="text: name"></td>
									<td class="compactCell" data-bind="click: $parent.removeFile"><span class="glyphicon glyphicon-minus"></span></td>
								</tr>
							</tbody>
						</table>
						
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-bind="click: hide">Close</button>
						<button type="button" class="btn btn-default" data-bind="click: uploadFiles">Upload</button> 
					</div>
				</div> <!-- Modal content -->
			</div> <!-- modal dialog -->
		</div> <!-- modal -->
		
		

		<script src="js/ViewModels/Request/PFRequestFileUpload.js"></script>
		<script src="js/ViewModels/Request/PFCreateRequestViewModel.js"></script>
		
		<script type="text/javascript">
			
			//initialize the modal
			//$("#divFileUploadModal").modal();
		
			//get the requestid from the url params
			var requestId = getURLParameter("requestId")
			if (!requestId) {
				requestId = -1;
			}
			
			var fileUploadModalViewModel = new PFRequestFileUploadViewModel();
			var requestViewModel = new PFCreateRequestViewModel(fileUploadModalViewModel, requestId);		
			
			ko.applyBindings(fileUploadModalViewModel, document.getElementById("divFileUploadModal"));
			ko.applyBindings(requestViewModel, document.getElementById("requestBody"));
			
			/** Shows the file upload modal with the given call back */
			function showFileUploadModal(callback) {
				fileUploadModalViewModel.show(callback);
			}

		</script>


	</body>
</html>