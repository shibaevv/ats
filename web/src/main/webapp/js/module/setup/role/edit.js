YAHOO.namespace('ats.setup.role');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            var findCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    var data = null;
                    try {
                        var data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }

                    //create table (default)
                    var roleId = YAHOO.util.Dom.get('roleId').value;
                    data.actions.find = data.actions.find + roleId;
                    dataTable = YAHOO.ats.table.create('dataDiv', data);
                    //saveEvent(s)
                    var saveAction = data.actions.save;
                    //add2role
                    dataTable.subscribe('checkboxClickEvent', function(oArgs) {
                        var roleId = YAHOO.util.Dom.get('roleId').value;
                        var row = this.getRecord(oArgs.target)._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + roleId
                            + '&functionId=' + row.id + '&add2role=' + oArgs.target.checked + '&roleHome=' + row.roleHome, callback);
                    });
                    //home
                    dataTable.subscribe('radioClickEvent', function(oArgs) {
                        var roleId = YAHOO.util.Dom.get('roleId').value;
                        var row = this.getRecord(oArgs.target)._oData;
                        if (!row.add2role || !row.home) { // only selected functions can be used as home
                            YAHOO.util.Event.stopEvent(oArgs.event);
                            return;
                        }
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + roleId
                            + '&functionId=' + row.id + '&roleHome=true&add2role=' + row.add2role, callback);
                    });
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //load role functions
            var roleId = YAHOO.util.Dom.get('roleId').value;
            YAHOO.ats.sendGetRequest('setup/role/edit.do?dispatch=findFunction&roleId=' + roleId, findCallback);
        }
    });

    //Have loader insert only the JS files.
    loader.insert({}, 'js');
    //loader.insert(); 
})();