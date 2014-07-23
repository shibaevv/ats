YAHOO.namespace('ats.issue');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {

            //homeAudit event
            YAHOO.util.Event.addListener('homeAudit', 'click', function(e) {
                YAHOO.util.Event.stopEvent(e);
                var auditId = YAHOO.util.Dom.get('auditId').value;
                YAHOO.ats.navigate('audit/view.do?auditId=' + auditId, 'Report');
            });

            YAHOO.ats.issue.initDataTable = function() {
                var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                var issueId = YAHOO.util.Dom.get('issueId').value;
                YAHOO.ats.sendPostRequest(form, 'action/main.do?dispatch=findByIssue&issueId=' + issueId, YAHOO.ats.issue.findCallback);
            };

            YAHOO.ats.issue.findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.action.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data', oResponse);
                    }
                    YAHOO.ats.action.data = data;
                    //create table (default)
                    var id = YAHOO.util.Dom.get('issueId').value;
                    data.actions.find = data.actions.find + id;
                    dataTable = YAHOO.ats.table.create('dataDiv', data);
                    //NB: define cell specific navigation
                    dataTable.subscribe('cellSelectEvent', function(oArgs) {
                        var key = oArgs.key;
                        var row = oArgs.record._oData;
                        if (key == 'link') {
                            //do nothing (will follow link)
                        } else {
                            YAHOO.ats.navigate(data.actions.view + row.id, '');
                        }
                    });
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //load issue/actions
            YAHOO.ats.issue.initDataTable();
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();