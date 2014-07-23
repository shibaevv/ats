YAHOO.namespace('ats.auditAdmin.action');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            //root
            var root = YAHOO.util.Dom.get('dataCenter');
            YAHOO.ats.toggleInit(root);

            var groupIdChangeCallback = {
                cache:false,
                success: function(oResponse) {
                    //reset
                    var dropdown = YAHOO.util.Dom.get('divisionId');
                    dropdown.options.length = 1;

                    //parse data
                    var response = oResponse.responseText;
                    try {
                        var divisions = YAHOO.ats.parseJsonData(response);
                        for (var i = 0; i != divisions.records.length; i++) {
                            var d = divisions.records[i];
                            dropdown.options[dropdown.options.length] = new Option(d.label, d.value);
                        }
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            //groupId change event
            YAHOO.util.Event.addListener('groupId', 'change', function(e) {
                var dropdown = YAHOO.util.Event.getTarget(e);
                var groupId = dropdown.value;
                var groupName = dropdown.options[dropdown.selectedIndex].innerHTML;
                YAHOO.ats.sendGetRequest('filter/division.do?group.groupId=' + groupId + '&group.name=' + groupName, groupIdChangeCallback);
            });

            var businessStatusIdChangeCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    var response = oResponse.responseText;
                    try {
                        var data = YAHOO.ats.parseJsonData(response);
                        data = data.data;
                        YAHOO.util.Dom.get('f_date_dueDate').value = data.dueDate;
                        YAHOO.util.Dom.get('f_date_closedDate').value = data.closedDate;
                        YAHOO.util.Dom.get('f_date_followupDate').value = data.followupDate;
                        YAHOO.util.Dom.get('actionFollowupStatusId').value = data.actionFollowupStatusId;
                        YAHOO.util.Dom.get('actionStatusId').value = data.actionStatusId;
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            //businessStatusId change event
            YAHOO.util.Event.addListener('businessStatusId', 'change', function(e) {
                var actionId = YAHOO.util.Dom.get('actionId').value;
                var issueId = YAHOO.util.Dom.get('issueId').value;
                var dropdown = YAHOO.util.Event.getTarget(e);
                var businessStatusId = dropdown.value;
                //var businessStatusName = dropdown.options[dropdown.selectedIndex].innerHTML;
                YAHOO.ats.sendGetRequest('actionAdmin/edit.do?dispatch=businessStatusChanged&actionId=' + actionId + '&businessStatusId=' + businessStatusId + '&issueId=' + issueId, businessStatusIdChangeCallback);
            });

            //reset dueDate button and event
            var resetDueDate = new YAHOO.widget.Button('resetDueDate', {
                label: '<span class="remove">&#160;</span>'
            });
            resetDueDate.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_dueDate').value = ''; 
            });

            //reset closedDate button and event
            var resetClosedDate = new YAHOO.widget.Button('resetClosedDate', {
                label: '<span class="remove">&#160;</span>'
            });
            resetClosedDate.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_closedDate').value = ''; 
            });

            //reset followupDate button and event
            var resetFollowupDate = new YAHOO.widget.Button('resetFollowupDate', {
                label: '<span class="remove">&#160;</span>'
            });
            resetFollowupDate.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_followupDate').value = ''; 
            });

            //create addGroupDivision button and event
            var addGroupDivision = new YAHOO.widget.Button('addGroupDivision', {
                label: '<span class="add">Add</span>'
            });
            addGroupDivision.on('click', function(e) {
                var form = YAHOO.util.Dom.get('actionForm');
                YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=addGroupDivision', YAHOO.ats.submitCallback);
            });
            var removeGroupDivisionButtons = YAHOO.util.Selector.query('input[type=button][name=removeGroupDivisionId]', root);
            for (var i = 0; i != removeGroupDivisionButtons.length; i++) {
                var el = YAHOO.util.Dom.get(removeGroupDivisionButtons[i]);
                var btn = new YAHOO.widget.Button(el, {
                    label: '<span class="remove">&#160;</span>'
                });
                btn.groupDivisionId = el.value;
                btn.on('click', function(e) {
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=removeGroupDivision&groupDivisionId=' + this.groupDivisionId, YAHOO.ats.submitCallback);
                });
            }

            //addResponsibleUser button(s)
            var addResponsibleUserButton = new YAHOO.widget.Button('addResponsibleUser', {
                label: '<span class="add">Add</span>',
                disabled: true
            });
            var removeResponsibleUserButtons = YAHOO.util.Selector.query('input[type=button][name=removeResponsibleUser]', root);
            for (var i = 0; i != removeResponsibleUserButtons.length; i++) {
                var el = YAHOO.util.Dom.get(removeResponsibleUserButtons[i]);
                var btn = new YAHOO.widget.Button(el, {
                    label: '<span class="remove">&#160;</span>'
                });
                btn.userId = el.value;
                btn.on('click', function(e) {
                    var el = YAHOO.util.Event.getTarget(e);
                    var userId = el.value;
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=removePerson&userId=' + this.userId, YAHOO.ats.submitCallback);
                });
            }
            var onUserSelectEvent = function(sType, aArgs) {
                addResponsibleUserButton.set('disabled', false, true);
                //var myAC = aArgs[0]; // reference back to the AC instance
                //var elLI = aArgs[1]; // reference to the selected LI element
                var oData = aArgs[2]; // object literal of selected item's result data
                // update user view with the selected item's ID
                var userId = oData.value;
                //create addResponsibleUser button and event
                addResponsibleUserButton.on('click', function(e) {
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=addPerson&userId=' + userId, YAHOO.ats.submitCallback);
                    //reset Person Responsible field
                    YAHOO.util.Dom.get('filter0Input').value = '';
                });
            };

            //create addComment button and event
            var addCommentButton = new YAHOO.widget.Button('addComment');
            addCommentButton.on('click', function(e) {
                var actionId = YAHOO.util.Dom.get('actionId').value;
                if (actionId) {
                    YAHOO.ats.addComment(actionId, 'actionAdmin/edit.do?actionId=' + actionId, YAHOO.ats.viewCallback, 'POST');
                } else {
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=saveAction', YAHOO.ats.submitCallback);
                }
            });

            YAHOO.ats.auditAdmin.action.findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.auditAdmin.action.comment.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.auditAdmin.action.comment.data = data;

                    //create table (default)
                    if (data.records.length > 0) {
                        dataTable = YAHOO.ats.table.create('dataDiv', data);
                    }

                    //addResponsibleUser
                    var filters = [
                        {valuesUrl:'filter/userName.do?activeUser=true',forceSelection:true} //?1=1
                    ];
                    //create filters (generic)
                    YAHOO.ats.filter.init(filters);
                    YAHOO.ats.filter.autoCompletes[0].itemSelectEvent.subscribe(onUserSelectEvent);

                    //load audit log
                    var actionId = YAHOO.util.Dom.get('actionId').value;
                    YAHOO.ats.sendGetRequest('comment/main.do?dispatch=findAuditLog&actionId=' + actionId, YAHOO.ats.auditAdmin.action.findAuditLogCallback);
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            YAHOO.ats.auditAdmin.action.findAuditLogCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.auditAdmin.action.comment.dataAuditLog = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.auditAdmin.action.comment.dataAuditLog = data;

                    //create table (default)
                    if (data.records.length > 0) {
                        dataTable = YAHOO.ats.table.create('dataAuditLogDiv', data);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //create Toolbar
            var toolbar = new YAHOO.widget.Toolbar('toolbarDiv', {buttons: []});
            toolbar.addButton({id: 'tb_cancel', type: 'push', label: 'Cancel', value: 'cancel'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_save', type: 'push', label: 'Save', value: 'save'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_save_close', type: 'push', label: 'Save & Close', value: 'saveClose'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                if (e.button.id == 'tb_cancel') {
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    YAHOO.ats.openDialog('actionAdmin/edit.do?dispatch=cancel&auditId=' + auditId, 'Cancel Edit', 300, 150);
                }
                else if (e.button.id == 'tb_save') {
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=saveAction', YAHOO.ats.submitCallback);
                }
                else if (e.button.id == 'tb_save_close') {
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=saveClose', YAHOO.ats.submitCallback);
                }
            });

            //toolbarBottom
            var toolbar2 = new YAHOO.widget.Toolbar('toolbarDiv2', {buttons: []});
            toolbar2.addButton({id: 'tb_cancel2', type: 'push', label: 'Cancel', value: 'cancel'});
            toolbar2.addSeparator();
            toolbar2.addButton({id: 'tb_save2', type: 'push', label: 'Save', value: 'save'});
            toolbar2.addSeparator();
            toolbar2.addButton({id: 'tb_save_close2', type: 'push', label: 'Save & Close', value: 'saveClose'});
            toolbar2.addSeparator();
            toolbar2.on('buttonClick', function(e) {
                if (e.button.id == 'tb_cancel2') {
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    YAHOO.ats.openDialog('actionAdmin/edit.do?dispatch=cancel&auditId=' + auditId, 'Cancel Edit', 300, 150);
                }
                else if (e.button.id == 'tb_save2') {
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=saveAction', YAHOO.ats.submitCallback);
                }
                else if (e.button.id == 'tb_save_close2') {
                    var form = YAHOO.util.Dom.get('actionForm');
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=saveClose', YAHOO.ats.submitCallback);
                }
            });

            //set calendar
            var el = YAHOO.util.Dom.get('dataHolder');
            YAHOO.ats.calendar.init(el);

            //load comments
            var actionId = YAHOO.util.Dom.get('actionId').value;
            YAHOO.ats.sendGetRequest('comment/main.do?dispatch=find&actionId=' + actionId, YAHOO.ats.auditAdmin.action.findCallback);
        }
    });

    //Have loader insert only the JS files.
    loader.insert({}, 'js');
    //loader.insert();
})();