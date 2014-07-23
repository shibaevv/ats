YAHOO.namespace('ats.user');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
	        YAHOO.ats.cleanup();

            //edit userMatrix link
            YAHOO.ats.user.matrix.edit = function(userMatrixId, userId) {
                var editAction = YAHOO.ats.user.matrix.data.actions.edit;
                YAHOO.ats.openDialog(editAction + userMatrixId + '&user.userId=' + userId, userMatrixId ? 'Edit User Matrix' : 'Add User Matrix', 400, 200);
            };

            //add userMatrix button and event
            var addUserMatrixButton = new YAHOO.widget.Button('addUserMatrix');
            addUserMatrixButton.on('click', function(e) {
                var userIdEl = YAHOO.util.Dom.get('filter0Hidden');
                var userId = userIdEl.value;
                var userMatrixId = '';
                YAHOO.ats.user.matrix.edit(userMatrixId, userId);
            });

            var onUserSelectEvent = function(sType, aArgs) {
                //var myAC = aArgs[0]; // reference back to the AC instance
                //var elLI = aArgs[1]; // reference to the selected LI element
                var oData = aArgs[2]; // object literal of selected item's result data
                // update user view with the selected item's ID
                var userId = oData.value;
                YAHOO.ats.navigate('user/view.do?user.userId=' + userId, 'User');
            };

            function initDataHeader(filters) {
                //create filters (generic)
                YAHOO.ats.filter.init(filters);
                YAHOO.ats.filter.autoCompletes[0].itemSelectEvent.subscribe(onUserSelectEvent);
            };

            var saveCallback = {
                cache:false,
                success: function(oResponse) {
            	    YAHOO.ats.submitCallback.success(oResponse);
            	},
                failure: function(oResponse) {
            	    YAHOO.ats.submitCallback.failure(oResponse);
            	    //error, let user fix it
            	    if (this.argument.editor) {
            	    	this.argument.editor.show();
            	    }
                },
                argument: {editor:null}
                //,timeout: 10000
            };

            var findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.user.matrix.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.user.matrix.data = data;

                    initDataHeader(YAHOO.ats.user.matrix.data.filters);

                    //create table (default)
                    var userIdEl = YAHOO.util.Dom.get('filter0Hidden');
                    var userId = userIdEl.value;
                    data.actions.find = data.actions.find + userId; // we already have it on form (filter0Hidden)
                    dataTable = YAHOO.ats.table.create('dataDiv', data); //do POST to get records
                    //table editor show/save Event(s)
                    var saveAction = YAHOO.ats.user.matrix.data.actions.save;
                    dataTable.getColumn('group').editor.subscribe('showEvent', function(oArgs) {
                        var row = this.getRecord()._oData;
                    	this.dropdown.value = row.groupId;
                    });
                    dataTable.getColumn('group').editor.subscribe('saveEvent', function(oArgs) {
                    	saveCallback.argument.editor = this;
                        var row = this.getRecord()._oData;
                        YAHOO.ats.sendGetRequest(saveAction + row.id + '&user.userId=' + row.userId
                            + '&division.divisionId=' + row.divisionId
                            + '&role.roleId=' + row.roleId
                            + '&reportType.reportTypeId=' + row.reportTypeId
                            + '&group.groupId=' + oArgs.newData, saveCallback);
                    });
                    //
                    dataTable.getColumn('division').editor.subscribe('showEvent', function(oArgs) {
                    	var dropdown = this.dropdown;
                        var row = this.getRecord()._oData;
                        YAHOO.ats.sendGetRequest('filter/division.do?group.groupId=' + row.groupId + '&group.name=' + row.group, {
                            success: function(oResponse) {
                                //reset
                                dropdown.options.length = 0;
                                //parse data
                                try {
                                    var divisions = YAHOO.ats.parseJsonData(oResponse.responseText);
                                    for (var i = 0; i != divisions.records.length; i++) {
                                        var d = divisions.records[i];
                                        var option = new Option(d.label, d.value);
                                        dropdown.options[i] = option;
                                    }
                                	dropdown.value = row.divisionId;
                                } catch (e) {
                                    YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                                }
                            },
                            failure: function(oResponse) {
                                YAHOO.ats.failure(oResponse);
                            }
                            //, timeout: 10000
                        });
                    });
                    dataTable.getColumn('division').editor.subscribe('saveEvent', function(oArgs) {
                    	saveCallback.argument.editor = this;
                        var row = this.getRecord()._oData;
                        YAHOO.ats.sendGetRequest(saveAction + row.id + '&user.userId=' + row.userId
                            + '&group.groupId=' + row.groupId
                            + '&role.roleId=' + row.roleId
                            + '&reportType.reportTypeId=' + row.reportTypeId
                            + '&division.divisionId=' + oArgs.newData, saveCallback);
                    });
                    //
                    dataTable.getColumn('role').editor.subscribe('showEvent', function(oArgs) {
                        var row = this.getRecord()._oData;
                    	this.dropdown.value = row.roleId;
                    });
                    dataTable.getColumn('role').editor.subscribe('saveEvent', function(oArgs) {
                    	saveCallback.argument.editor = this;
                        var row = this.getRecord()._oData;
                        YAHOO.ats.sendGetRequest(saveAction + row.id + '&user.userId=' + row.userId
                            + '&group.groupId=' + row.groupId
                            + '&division.divisionId=' + row.divisionId
                            + '&reportType.reportTypeId=' + row.reportTypeId
                            + '&role.roleId=' + oArgs.newData, saveCallback);
                    });
                    //
                    dataTable.getColumn('reportType').editor.subscribe('showEvent', function(oArgs) {
                        var row = this.getRecord()._oData;
                    	this.dropdown.value = row.reportTypeId;
                    });
                    dataTable.getColumn('reportType').editor.subscribe('saveEvent', function(oArgs) {
                    	saveCallback.argument.editor = this;
                        var row = this.getRecord()._oData;
                        YAHOO.ats.sendGetRequest(saveAction + row.id + '&user.userId=' + row.userId
                            + '&group.groupId=' + row.groupId
                            + '&division.divisionId=' + row.divisionId
                            + '&role.roleId=' + row.roleId
                            + '&reportType.reportTypeId=' + oArgs.newData, saveCallback);
                        //this.getTdEl().firstChild.innerHTML = this.dropdown.options[this.dropdown.selectedIndex].text;
                    });
                    //
                    dataTable.subscribe('checkboxClickEvent', function(oArgs) {
                    	saveCallback.argument.editor = null;
                        var row = this.getRecord(oArgs.target)._oData;
                        YAHOO.ats.sendGetRequest(saveAction + row.id + '&user.userId=' + row.userId
                            + '&deleted=' + oArgs.target.checked, saveCallback);
                    });
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //load user matrix
            var userIdEl = YAHOO.util.Dom.get('filter0Hidden');
            var userId = userIdEl.value;
            YAHOO.ats.sendGetRequest('user/matrix/main.do?dispatch=find&user.userId=' + userId, findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();

/* please refer to related view.jsp before refactoring this */
function disableEnterKey(e)
{
     var key;

     if(window.event)
          key = window.event.keyCode;     //IE
     else
          key = e.which;     //firefox

     if(key == 13)
          return false;
     else
          return true;
}