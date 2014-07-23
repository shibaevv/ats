YAHOO.namespace('ats.audit');

(function() {
    var dataTree = null;
    var loader = new YAHOO.util.YUILoader({
        base: 'js/yui/',
        require: ['treeview'],
        ignore: [],
        loadOptional: false,
        //Combine YUI files into a single request (per file type) by using the Yahoo! CDN combo service.
        //combine: true,
        filter: YAHOO.ats.debug ? 'RAW' : 'MIN',
        allowRollup: true,
        onSuccess: function() {
            YAHOO.ats.cleanup();

            function initDataHeader(filters) {
                //create a Button using an existing <button> element as a data source
                var oFilterButton = new YAHOO.widget.Button('filterButton');
                var oExportButton = new YAHOO.widget.Button('exportButton');

                //create filters (generic)
                YAHOO.ats.filter.init(filters);

                //set calendar
                var el = YAHOO.util.Dom.get('dataHolder');
                YAHOO.ats.calendar.init(el);
            };

            var filterCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.audit.data = null;
                    //parse data
                    var response = oResponse.responseText;
                    try {
                        YAHOO.ats.audit.data = YAHOO.ats.parseJsonData(response);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }

                    YAHOO.ats.tree.filter(dataTree, YAHOO.ats.audit.data);
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            var findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.audit.data = null;
                    //parse data
                    var response = oResponse.responseText;
                    try {
                        YAHOO.ats.audit.data = YAHOO.ats.parseJsonData(response);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }

                    initDataHeader(YAHOO.ats.audit.data.filters);

                    //create tree
                    YAHOO.util.Get.script('js/module/common/tree.js', {
                        onSuccess: function(scriptData) {
                            dataTree = YAHOO.ats.tree.create('dataDiv', YAHOO.ats.audit.data);
                            if (!YAHOO.ats.debug) {
                                scriptData.purge(); //removes the script node immediately after executing
                            }
                        },
                        onFailure: function(oResponse) {
                            var response = oResponse.responseText;
                            YAHOO.ats.alert('Failure to create tree', oResponse);
                        }
                    });
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
            toolbar.addButton({id: 'tb_export', type: 'push', label: 'Export', value: 'export'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                //YAHOO.util.Event.preventDefault(e);
                if (e.button.id == 'tb_filter') {
                    var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                    YAHOO.ats.sendPostRequest(form, 'audit/main.do?dispatch=find', filterCallback);
                } else if (e.button.id == 'tb_export') {
                    var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                    YAHOO.ats.sendDownloadRequest('audit/export.do', form);
                }
            });

            //
            var form = YAHOO.util.Dom.get(YAHOO.ats.form);
            YAHOO.ats.sendPostRequest(form, 'audit/main.do?dispatch=find', findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();