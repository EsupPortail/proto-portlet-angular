<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>
<rs:aggregatedResources path="resources.xml" />
<script type="text/javascript">
	angular.element(document).ready(function() {
			proto("<portlet:namespace/>", "${resourcesPath}/app", "${dynamicResourcesPattern}");
			angular.bootstrap(angular.element(document.getElementById("proto-<portlet:namespace/>")), ["<portlet:namespace/>"]);
		});
</script>
<div class="row" id="proto-<portlet:namespace/>" ng-view>
</div>
