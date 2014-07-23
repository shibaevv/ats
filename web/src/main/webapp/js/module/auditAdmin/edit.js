YAHOO.namespace('ats.auditAdmin');

(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {

            //root
            var root = YAHOO.util.Dom.get('dataHolder');
            YAHOO.ats.toggleInit(root);

            //groupId change event
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
            YAHOO.util.Event.addListener('groupId', 'change', function(e) {
                //YAHOO.util.Event.stopEvent(e);
                var dropdown = YAHOO.util.Event.getTarget(e);
                var groupId = dropdown.value;
                var groupName = dropdown.options[dropdown.selectedIndex].innerHTML;
                var callback = groupIdChangeCallback;
                YAHOO.ats.sendGetRequest('filter/division.do?group.groupId=' + groupId + '&group.name=' + groupName, callback);
            });

            //create addAttachment button and event
            var addAttachmentButton = new YAHOO.widget.Button('addAttachment', {
                label: '<span class="add">Add</span>',
                disabled: true
            });
            addAttachmentButton.on('click', function(e) {
                var form = YAHOO.util.Dom.get('auditForm'); //this.form
                var callback = YAHOO.ats.submitCallback;
                YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=addAttachment', callback);
            });
            //attachment change event
            YAHOO.util.Event.addListener('attachment', 'change', function(e) {
                addAttachmentButton.set('disabled', false, true);
            });

            //create removeAttachment button and event
            var removeAttachmentButton = new YAHOO.widget.Button('removeAttachment', {
                label: '<span class="remove">&#160;</span>',
                disabled: !YAHOO.util.Dom.get('document.name').value
            });
            removeAttachmentButton.on('click', function(e) {
                var form = YAHOO.util.Dom.get('auditForm'); //this.form
                var callback = YAHOO.ats.submitCallback;
                YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=removeAttachment', callback);
            });
            
            //create addGroupDivision button and event
            var addGroupDivision = new YAHOO.widget.Button('addGroupDivision', {
                label: '<span class="add">Add</span>'
            });
            addGroupDivision.on('click', function(e) {
                var form = YAHOO.util.Dom.get('auditForm');
                var callback = YAHOO.ats.submitCallback;
                YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=addGroupDivision', callback);
            });
            
            //reset issueDate button and event
            var resetIssuedDate = new YAHOO.widget.Button('resetIssuedDate', {
                label: '<span class="remove">&#160;</span>'
            });
            resetIssuedDate.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_issuedDate').value = ''; 
            });
            
            var removeGroupDivisionButtons = YAHOO.util.Selector.query('input[type=button][name=removeGroupDivision]', root);
            for (var i = 0; i != removeGroupDivisionButtons.length; i++) {
                //create removeGroupDivision button and event
                var el = YAHOO.util.Dom.get(removeGroupDivisionButtons[i]);
                var btn = new YAHOO.widget.Button(el, {
                    label: '<span class="remove">&#160;</span>'
                });
                btn.groupDivisionId = el.value;
                btn.on('click', function(e) {
                    var form = YAHOO.util.Dom.get('auditForm');
                    var callback = YAHOO.ats.submitCallback;
                    YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=removeGroupDivision&groupDivisionId=' + this.groupDivisionId, callback);
                });
            }

            // deleteIssue.issueId-button
            var deleteIssueButtons = YAHOO.util.Selector.query('input[type=button][name=deleteIssue]', root);
            for (var i = 0; i != deleteIssueButtons.length; i++) {
                //create deleteIssue button and event
                var el = YAHOO.util.Dom.get(deleteIssueButtons[i]);
                var btn = new YAHOO.widget.Button(el, {
                    label: '<span class="remove" style="padding-left:2em;">Delete Issue</span>'
                });
                btn.issueId = el.value;
                btn.on('click', function(e) {
                    var args = {id:'deleteIssue',callback:YAHOO.ats.auditAdmin.deleteIssueCallback,issueId:this.issueId};
                    YAHOO.ats.openDialog('issueAdmin/delete.do?issueId=' + this.issueId, 'Delete Issue', 300, 150, args);
                });
            }
            YAHOO.ats.auditAdmin.deleteIssueCallback = {
                cache:false,
                success: function(oResponse) {
                    var response = oResponse.responseText;
                    try {
                        var data = YAHOO.ats.parseJsonData(response);
                        var row = YAHOO.util.Dom.get('issueRow.' + data.actions.deleteId);
                        row.parentNode.removeChild(row);
                    } catch (e) {
                        YAHOO.ats.failure(oResponse);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            // addAction.issueId-button
            var addActionButtons = YAHOO.util.Selector.query('input[type=button][name=addAction]', root);
            for (var i = 0; i != addActionButtons.length; i++) {
                //create addAction button and event
                var el = YAHOO.util.Dom.get(addActionButtons[i]);
                var btn = new YAHOO.widget.Button(el, {
                    label: '<span class="add">Add New Action</span>'
                });
                btn.issueId = el.value;
                btn.on('click', function(e) {
                    var form = YAHOO.util.Dom.get('auditForm');
                    var callback = YAHOO.ats.submitCallback;
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=addAction&issueId=' + this.issueId, callback);
                });
            }

            // editAction.actionId-button
            var editActionButtons = YAHOO.util.Selector.query('input[type=button][name=editAction]', root);
            for (var i = 0; i != editActionButtons.length; i++) {
                //create editAction button and event
                var el = YAHOO.util.Dom.get(editActionButtons[i]);
                var btn = new YAHOO.widget.Button(el, {
                    label: '<span class="edit">Edit</span>'
                });
                btn.actionId = el.value;
                btn.on('click', function(e) {
                    var form = YAHOO.util.Dom.get('auditForm');
                    var callback = YAHOO.ats.submitCallback;
                    YAHOO.ats.sendPostRequest(form, 'actionAdmin/edit.do?readOnly=false&dispatch=addAction&actionId=' + this.actionId, callback);
                });
            }

            // deleteAction.actionId-button
            var deleteActionButtons = YAHOO.util.Selector.query('input[type=button][name=deleteAction]', root);
            for (var i = 0; i != deleteActionButtons.length; i++) {
                //create deleteAction button and event
                var el = YAHOO.util.Dom.get(deleteActionButtons[i]);
                var btn = new YAHOO.widget.Button(el, {
                    label: '<span class="remove">&#160;</span>'
                });
                btn.actionId = el.value;
                btn.on('click', function(e) {
                    var args = {id:'deleteAction',callback:YAHOO.ats.auditAdmin.deleteActionCallback,actionId:this.actionId};
                    YAHOO.ats.openDialog('actionAdmin/delete.do?actionId=' + this.actionId, 'Delete Action', 300, 150, args);
                });
            }
            YAHOO.ats.auditAdmin.deleteActionCallback = {
                cache:false,
                success: function(oResponse) {
                    var response = oResponse.responseText;
                    try {
                        var data = YAHOO.ats.parseJsonData(response);
                        var row = YAHOO.util.Dom.get('actionRow.' + data.actions.deleteId);
                        row.parentNode.removeChild(row);
                    } catch (e) {
                        YAHOO.ats.failure(oResponse);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            var index; // TODO: pass to callback as argument
            var riskChangeCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    var response = oResponse.responseText;
                    //reset
                    var input = YAHOO.util.Dom.get('category.'+ index);
                    input.value = response;
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            //risk.index change event
            var riskNodes = YAHOO.util.Selector.query('select[id^=risk.]', root);
            YAHOO.util.Event.addListener(riskNodes, 'change', function(e) {
                var dropdown = YAHOO.util.Event.getTarget(e);
                var id = dropdown.id; // risk.index
                index = id.substring(5);
                var riskId = dropdown.value;
                var riskName = dropdown.options[dropdown.selectedIndex].innerHTML;
                YAHOO.ats.sendGetRequest('filter/parentRisk.do?dispatch=risk&riskId=' + riskId + '&riskName=' + riskName + '&index=' + index, riskChangeCallback);
            });
            
            //toolbarTop
            var toolbar = new YAHOO.widget.Toolbar('toolbarDiv', {buttons: []});
            toolbar.addButton({id: 'tb_cancel', type: 'push', label: 'Cancel', value: 'cancel'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_save', type: 'push', label: 'Save', value: 'save'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_saveClose', type: 'push', label: 'Save & Close', value: 'saveClose'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                if (e.button.id == 'tb_cancel') {
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    YAHOO.ats.openDialog('auditAdmin/edit.do?dispatch=cancel&auditId=' + auditId, 'Cancel Edit', 300, 150);
                }
                else if (e.button.id == 'tb_save') {
                    var form = YAHOO.util.Dom.get('auditForm');
                    YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=save', YAHOO.ats.submitCallback);
                }
                else if (e.button.id == 'tb_saveClose') {
                    var form = YAHOO.util.Dom.get('auditForm');
                    YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=saveClose', YAHOO.ats.submitCallback);
                }
            });
            //toolbarBottom
            var toolbar2 = new YAHOO.widget.Toolbar('toolbarDiv2', {buttons: []});
            toolbar2.addButton({id: 'tb_cancel2', type: 'push', label: 'Cancel', value: 'cancel'});
            toolbar2.addSeparator();
            toolbar2.addButton({id: 'tb_save2', type: 'push', label: 'Save', value: 'save'});
            toolbar2.addSeparator();
            toolbar2.addButton({id: 'tb_saveClose2', type: 'push', label: 'Save & Close', value: 'saveClose'});
            toolbar2.addSeparator();
            toolbar2.addButton({id: 'tb_addIssue2', type: 'push', label: 'Add Issue', value: 'new'});
            toolbar2.addSeparator();
            toolbar2.on('buttonClick', function(e) {
                if (e.button.id == 'tb_cancel2') {
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    YAHOO.ats.openDialog('auditAdmin/edit.do?dispatch=cancel&auditId=' + auditId, 'Cancel Edit', 300, 150);
                }
                else if (e.button.id == 'tb_save2') {
                    var form = YAHOO.util.Dom.get('auditForm');
                    YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=save', YAHOO.ats.submitCallback);
                }
                else if (e.button.id == 'tb_saveClose2') {
                    var form = YAHOO.util.Dom.get('auditForm');
                    YAHOO.ats.sendPostRequest(form, 'auditAdmin/edit.do?readOnly=false&dispatch=saveClose', YAHOO.ats.submitCallback);
                }
                else if (e.button.id == 'tb_addIssue2') {
                    var form = YAHOO.util.Dom.get('auditForm');
                    YAHOO.ats.sendPostRequest(form, 'issueAdmin/edit.do?readOnly=false&dispatch=addIssue', YAHOO.ats.submitCallback);
                }
            });

            //set calendar
            var el = YAHOO.util.Dom.get('dataHolder');
            YAHOO.ats.calendar.init(el);
        }
    });
    
    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();