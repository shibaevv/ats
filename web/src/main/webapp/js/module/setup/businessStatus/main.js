YAHOO.namespace('ats.setup.businessStatus');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {

    	    YAHOO.ats.setup.businessStatus.findCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    var response = oResponse.responseText;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(response);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }

                    //create table (default)
                    dataTable = YAHOO.ats.table.create('dataDiv', data);
                    //saveEvent(s)
                    var saveAction = data.actions.save;
                    dataTable.getColumn('name').editor.subscribe('saveEvent', function(oArgs) {
                        var row = oArgs.editor.getRecord()._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&name=' + oArgs.newData, callback);
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
            YAHOO.ats.sendGetRequest('setup/businessStatus/main.do?dispatch=find', YAHOO.ats.setup.businessStatus.findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();