YAHOO.namespace('ats.setup.function2');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {

    	    YAHOO.ats.setup.function2.findCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }

                    //create table (default)
                    dataTable = YAHOO.ats.table.create('dataDiv', data);
                    //saveEvent(s)
                    var saveAction = data.actions.save;
                    dataTable.getColumn('function_name').editor.subscribe('saveEvent', function(oArgs) {
                        var row = oArgs.editor.getRecord()._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&name=' + oArgs.newData, callback);
                    });
                    dataTable.getColumn('function_module').editor.subscribe('saveEvent', function(oArgs) {
                        var row = oArgs.editor.getRecord()._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&module=' + oArgs.newData, callback);
                    });
                    dataTable.getColumn('function_query').editor.subscribe('saveEvent', function(oArgs) {
                        var row = oArgs.editor.getRecord()._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&query=' + oArgs.newData, callback);
                    });
                    dataTable.subscribe('checkboxClickEvent', function(oArgs) {
                        var row = this.getRecord(oArgs.target)._oData;
                        //set generic menu callback (on edit dialog submit)
                        var callback = YAHOO.ats.dialogSubmitCallback;
                        callback.argument = {callback:YAHOO.ats.viewCallback};
                        YAHOO.ats.sendPostRequest(null, saveAction + row.id
                            + '&home=' + oArgs.target.checked, callback);
                    });
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //create addFunction button(s)
            var addFunctionButton = new YAHOO.widget.Button('addFunction');
            addFunctionButton.on('click', function(e) {
                //YAHOO.util.Event.stopEvent(e);
                //set generic menu callback (on edit dialog submit)
                var args = {callback:YAHOO.ats.viewCallback};
                YAHOO.ats.openDialog('setup/function/edit.do?functionId=', 'Add Function', 300, 200, args);
            });

            //get data
            YAHOO.ats.sendGetRequest('setup/function/main.do?dispatch=find', YAHOO.ats.setup.function2.findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();