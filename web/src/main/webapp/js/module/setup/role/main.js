YAHOO.namespace('ats.setup.role');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {

            YAHOO.ats.setup.role.edit = function(roleId) {
                var data = YAHOO.ats.setup.role.data;
                var callback = YAHOO.ats.viewCallback;
                YAHOO.ats.sendGetRequest(data.actions.edit + roleId, callback);
            };

            YAHOO.ats.setup.role.findCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    var data = null;
                    YAHOO.ats.setup.role.data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                        // save data for edit link
                        YAHOO.ats.setup.role.data = data;
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }

                    //create table (default)
                    dataTable = YAHOO.ats.table.create('dataDiv', data);
                    //saveEvent(s)
                    var saveAction = data.actions.save;
                    dataTable.getColumn('role_name').editor.subscribe('saveEvent', function(oArgs) {
                        var row = oArgs.editor.getRecord()._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&name=' + oArgs.newData
                            + '&priority=' + row.role_priority, callback);
                    });
                    dataTable.getColumn('role_priority').editor.subscribe('saveEvent', function(oArgs) {
                        var row = oArgs.editor.getRecord()._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&name=' + row.role_name
                            + '&priority=' + oArgs.newData, callback);
                    });
                    dataTable.subscribe('checkboxClickEvent', function(oArgs) {
                        var row = this.getRecord(oArgs.target)._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&deleted=' + oArgs.target.checked, callback);
                    });
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            //get data
            YAHOO.ats.sendGetRequest('setup/role/main.do?dispatch=find', YAHOO.ats.setup.role.findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();