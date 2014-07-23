YAHOO.namespace('ats.auditAdmin');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            YAHOO.ats.cleanup();

            function initDataHeader(filters) {
                //create a Button using an existing <button> element as a data source
                var oEditButton = new YAHOO.widget.Button('editButton');
                var oNewButton = new YAHOO.widget.Button('newButton');

                //create filters (generic)
                YAHOO.ats.filter.init(filters);

                //set calendar
                var el = YAHOO.util.Dom.get('dataHolder');
                YAHOO.ats.calendar.init(el);
            };

            YAHOO.ats.auditAdmin.initDataTable = function() {
                var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                YAHOO.ats.sendPostRequest(form, 'auditAdmin/main.do?dispatch=find', YAHOO.ats.auditAdmin.findCallback);
            };

            YAHOO.ats.auditAdmin.findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.auditAdmin.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.auditAdmin.data = data;

                    initDataHeader(data.filters);

                    //create table (default)
                    dataTable = YAHOO.ats.table.create('dataDiv', data);
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //create Toolbar
            var toolbar = new YAHOO.widget.Toolbar('toolbarDiv', {buttons: []});
            toolbar.addButton({id: 'tb_edit', type: 'push', label: 'Update Report', value: 'edit'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_new', type: 'push', label: 'Create Report', value: 'new'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                //YAHOO.util.Event.preventDefault(e);
                if (e.button.id == 'tb_edit') {
                    var auditId=YAHOO.util.Dom.get('filter0Hidden').value;
                    if(auditId){
                        YAHOO.ats.navigate('auditAdmin/view.do?auditId=' + auditId, 'View Report');
                    }
                } else if (e.button.id == 'tb_new') {
                    //YAHOO.ats.navigate('auditAdmin/edit.do', 'New Report');
                    YAHOO.ats.sendGetRequest('auditAdmin/edit.do', YAHOO.ats.viewCallback);
                } 
            });

            //
            YAHOO.ats.auditAdmin.initDataTable();
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();