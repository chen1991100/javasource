var options = {
//                  "sDom" : '<"table-top"<"clear">><"table-scrollable" rt><"table-bottom"<"table-info"ip>>', 
                  "sDom": "t<'row DTTTFooter'<'col-sm-6'li><'col-sm-6'p>>",
                    bLengthChange:true,
                    bAutoWidth: false,  
                    bFilter: false,
           		 	uxCheckable:false,
           		 	uxCloseScroll:false	,
                    bProcessing: true,
                    bServerSide: true,
                    bStateSave: false,
                    sAjaxDataProp: "pageData.result",
                    // set the initial value
                    iDisplayLength: 20,
                    "aLengthMenu": [
                                    [10, 15, 20, 100],
                                    [10, 15, 20, 100] // change per page values here
                                ],
                    sPaginationType: "bootstrap",
//                    "sPaginationType": "full_numbers",
                    oLanguage: {
//                    	"sProcessing": '<img src="'+_GLOBAL.ctx+'/assets/img/loading.gif" >',
                    	"sLengthMenu": "每页&nbsp;_MENU_&nbsp;条 ", 
                    	"sZeroRecords": "没有您要搜索的内容", 
                    	"sInfo": "   共 _TOTAL_ 条",
                    	"sInfoEmpty": "记录数为0",
                    	"sInfoFiltered": "(全部记录数 _MAX_  条)",
                    	"sInfoPostFix": "",
                    	"sSearch": "搜索",
                    	"sUrl": "",
                    	"oPaginate": {
                    	"sFirst":    "第一页",
                    	"sPrevious": "上一页 ",
                    	"sNext":     "下一页",
                    	"sLast":     "最后一页 "
                    	}
                   },
                   
                    "fnDrawCallback": function(oSettings, json) {
                        App.initAjax();
                       
                        // 双击事件
                        oTable.$('tr').on('dblclick',function(e){
                        	$($(this)[0]).find('a.dblclick').click();
                        }); 
                        
                        //详情
                        jQuery('#'+tableId).on('click', ' tbody td .row-details', function () {
                            var nTr = $(this).parents('tr')[0];
                            if (oTable.fnIsOpen(nTr)) {
                                /* This row is already open - close it */
                                $(this).addClass("fa-plus-square-o").removeClass("fa-minus-square-o");
                                oTable.fnClose(nTr);
                            }
                            else {
                                /* Open this row */
                                $(this).addClass("fa-minus-square-o").removeClass("fa-plus-square-o");;
                                oTable.fnOpen(nTr, fnFormatDetails(oTable, nTr), 'details');
                            }
                        });
                        
                        // 初始化操作按钮的tooltips
                        //console.log(jQuery('#'+tableId+' tbody tr'));
                        jQuery('#'+tableId+' tbody i.fa-cog').tooltip({
                        	title:'操作'
                        });
                        jQuery('#'+tableId+' tbody i.fa-sort').tooltip({
                        	title:'排序'
                        });
                        
                        // 拖拽事件
                        if(opt.uxDrapable){
                        	jQuery('#'+tableId+' tbody').dragsort("destroy");
                            jQuery('#'+tableId+' tbody').dragsort({ dragBetween: true, dragEnd: saveOrder});
                        }
                        
                        // scrollable
                        if(opt.uxCloseScroll){
                        	jQuery('#'+tableId).parent().css({
                            	'overflow-x':'visible',
                            	'overflow-y':'visible'
                            		});
                        }
                        // 取消check
                        if(options.uxCheckable){
                        	var checkAll = jQuery('#'+tableId+' .group-checkable');
                        	checkAll.attr("checked", false);
//                        	jQuery.uniform.update(checkAll);
                        	jQuery('#'+tableId+' tbody tr .checkboxes').change(function(){
                        		$(this).parents('tr').toggleClass("active");
                        	});
                        }
                        
                        //初始化上一点击的INLINE类型的BUTTON
                    	/*if(_GLOBAL.initInline){
                    		_GLOBAL.initInline = false;
                    		App.clickLastInlineBtn();
                    	}*/
                        
                     },
                    "fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
//                    	  console.log(aoData);
                    	  var pageNo = getPageNo(aoData);
                    	  var pageLength = getPageLength(aoData);
                    	  var sort = getSort(aoData);
                    	  var data={
                    			  	pageNo:pageNo,
                    			  	pageLength:pageLength,
                    			  	fieldName:sort.fieldName?sort.fieldName:'',
                    				compositorType:sort.compositorType?sort.compositorType:''
                    				};
                    	  data = setFilter(data);
                    	  
	                      oSettings.jqXHR = $.ajax( {
	                          "dataType": 'json',
	                          "type": "POST",
	                          "url": sSource,
	                          "data": data,
	                          "success": fnCallback
	                        } );
                      }
                };