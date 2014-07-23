YAHOO.namespace('ats.setup.template');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {

    	    YAHOO.ats.setup.template.findCallback = {
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
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            YAHOO.ats.setup.template.edit = function(templateId) {
                var args = {callback:YAHOO.ats.viewCallback};
                YAHOO.ats.openDialog('setup/template/edit.do?templateId=' + templateId, 'Edit Template', 
                    YAHOO.util.Dom.getViewportWidth() / 4 * 3,
                    YAHOO.util.Dom.getViewportHeight() / 4 * 3,
                    args);
                
            };

            YAHOO.ats.setup.template.download = function(templateId) {
                YAHOO.ats.sendDownloadRequest('setup/template/main.do?dispatch=download&templateId=' + templateId);
            };

            //get data
            YAHOO.ats.sendGetRequest('setup/template/main.do?dispatch=find', YAHOO.ats.setup.template.findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();