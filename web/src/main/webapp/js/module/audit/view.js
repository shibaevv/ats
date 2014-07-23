YAHOO.namespace('ats.audit');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
        
            YAHOO.ats.audit.initDataTable = function() {
                var auditId = YAHOO.util.Dom.get('auditId').value;
                var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                YAHOO.ats.sendPostRequest(form, 'issue/main.do?dispatch=find&auditId=' + auditId, YAHOO.ats.audit.findCallback);
            };

            YAHOO.ats.audit.findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.issue.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.issue.data = data;
                    //create table (default)
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    data.actions.find = data.actions.find + auditId;
                    dataTable = YAHOO.ats.table.create('dataDiv', data);
                    //NB: define cell specific navigation
                    dataTable.subscribe('cellSelectEvent', function(oArgs) {
                        var key = oArgs.key;
                        var row = oArgs.record._oData;
                        if (key == 'link') {
                            //do nothing (will follow link)
                        } else if (key.indexOf('issue') == 0) {
                            YAHOO.ats.navigate(data.actions.view + row.id, '');
                        } else {
                            YAHOO.ats.navigate(data.actions.viewAction + row.actionId, '');
                        }
                    });
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //load issues
            YAHOO.ats.audit.initDataTable();
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();