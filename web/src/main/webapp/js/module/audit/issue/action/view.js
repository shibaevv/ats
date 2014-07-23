YAHOO.namespace('ats.action');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({    
        onSuccess: function() {
            //root
            var root = YAHOO.util.Dom.get('dataCenter');
            YAHOO.ats.toggleInit(root);

            //homeAudit event
            YAHOO.util.Event.addListener('homeAudit', 'click', function(e) {
                YAHOO.util.Event.stopEvent(e);
                var auditId = YAHOO.util.Dom.get('auditId').value;
                YAHOO.ats.navigate('audit/view.do?auditId=' + auditId, 'Report');
            });
            //homeIssue event
            YAHOO.util.Event.addListener('homeIssue', 'click', function(e) {
                YAHOO.util.Event.stopEvent(e);
                var issueId = YAHOO.util.Dom.get('issueId').value;
                YAHOO.ats.navigate('issue/view.do?issueId=' + issueId, 'Issue');
            });
            //create addComment, updateStatus button and event (if enabled in user security profile)
            var updateStatus = YAHOO.util.Dom.get('updateStatus');
            if (updateStatus) {
                var btn = new YAHOO.widget.Button('updateStatus');
                btn.on('click', function(e) {
                    var actionId = YAHOO.util.Dom.get('actionId').value;
                    YAHOO.ats.addComment(actionId);
                });
            }
            var addComment = YAHOO.util.Dom.get('addComment');
            if (addComment) {
                var btn = new YAHOO.widget.Button('addComment');
                btn.on('click', function(e) {
                    var actionId = YAHOO.util.Dom.get('actionId').value;
                    YAHOO.ats.addComment(actionId);
                });
            }

            YAHOO.ats.action.findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.action.comment.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.action.comment.data = data;
                    //create table (default)
                    if (data.records.length > 0) {
                        dataTable = YAHOO.ats.table.create('dataDiv', data);
                    }
                    //load audit log
                    var actionId = YAHOO.util.Dom.get('actionId').value;
                    YAHOO.ats.sendGetRequest('comment/main.do?dispatch=findAuditLog&actionId=' + actionId, YAHOO.ats.action.findAuditLogCallback);
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            
            YAHOO.ats.action.findAuditLogCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.action.comment.dataAuditLog = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.action.comment.dataAuditLog = data;
                    //create table (default)
                    if (data.records.length > 0) {
                        var id = YAHOO.util.Dom.get('actionId').value;
                        data.actions.find = data.actions.find + '&actionId=' + id;
                        dataTable = YAHOO.ats.table.create('dataAuditLogDiv', data);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //load comments
            var actionId = YAHOO.util.Dom.get('actionId').value;
            YAHOO.ats.sendGetRequest('comment/main.do?dispatch=find&actionId=' + actionId, YAHOO.ats.action.findCallback);
        }
    });

    //Have loader insert only the JS files.
    loader.insert({}, 'js');
    //loader.insert();
})();