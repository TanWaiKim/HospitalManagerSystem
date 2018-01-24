function judgeDelete(id)
{
  if(confirm("确定要删除吗？"))
  {
      window.location.href="DeleteOneServlet?id="+id;
  }
}

function deleteBatch(basePath){
	if(confirm("确定要删除吗？"))
	  {
		if($('#id').val()==null){

			alert("请选择您想要删除的信息");
		}

		else{

			$('#mainForm').attr("action",basePath+"DeleteBatchServlet");

			$('#mainForm').submit();

			alert("删除成功");
			}
	
	 }
}

function insertBatch(basePath){
	if(confirm("确定要新增数据？")){
		$('#mainForm').attr("action",basePath+"InsertBatch");

		$('#mainForm').submit();
		alert("新增成功");
	}
}

function changeCurrentPage(currentPage){
	$('#currentPage').val(currentPage);
	$('#listform').submit();
}