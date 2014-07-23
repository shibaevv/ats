YAHOO.namespace('ats.user');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            YAHOO.ats.cleanup();

            function initDataHeader(filters) {
                //create a Button using an existing <button> element as a data source
                var oFilterButton = new YAHOO.widget.Button('filterButton');
                //var oExportButton = new YAHOO.widget.Button('exportButton');

                //create filters (generic)
                YAHOO.ats.filter.init(filters);

                //set calendar
                var el = YAHOO.util.Dom.get('dataHolder');
                YAHOO.ats.calendar.init(el);
            };

            YAHOO.ats.user.findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.user.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.user.data = data;

                    initDataHeader(data.filters);

                    //create table (default)
                    dataTable = YAHOO.ats.table.create('dataDiv', YAHOO.ats.user.data);
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //create Toolbar
            var toolbar = new YAHOO.widget.Toolbar('toolbarDiv', {buttons: []});
            toolbar.addButton({id: 'tb_filter', type: 'push', label: 'Search', value: 'filter'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_new', type: 'push', label: 'New', value: 'new'});
            toolbar.addSeparator();
            //toolbar.addButton({id: 'tb_export', type: 'push', label: 'Export', value: 'export'});
            //toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                //YAHOO.util.Event.preventDefault(e);
                if (e.button.id == 'tb_filter') {
                    YAHOO.ats.sendGetRequest('user/main.do?dispatch=find', YAHOO.ats.user.findCallback);
                } else if (e.button.id == 'tb_filter') {

                } else if (e.button.id == 'tb_export') {
                    var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                    YAHOO.ats.sendDownloadRequest('user/export.do', form);
                }
            });

            //
            YAHOO.ats.sendGetRequest('user/main.do?dispatch=find', YAHOO.ats.user.findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();