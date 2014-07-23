YAHOO.namespace('ats.action');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            YAHOO.ats.cleanup();

            //root
            var root = YAHOO.util.Dom.get('dataHolder');
            YAHOO.ats.toggleInit(root);

            //create move buttons
            var move0Button = new YAHOO.widget.Button('move0');
            move0Button.on('click', function(e) {YAHOO.ats.moveSelected('actionGroupDivisions','availableGroupDivisions','selectionActive');});
            var move1Button = new YAHOO.widget.Button('move1');
            move1Button.on('click', function(e) {YAHOO.ats.moveSelected('actionGroupDivisions','availableGroupDivisions');});
            var move2Button = new YAHOO.widget.Button('move2');
            move2Button.on('click', function(e) {YAHOO.ats.moveSelected('availableGroupDivisions','actionGroupDivisions','selectionActive');});
            var move3Button = new YAHOO.widget.Button('move3');
            move3Button.on('click', function(e) {YAHOO.ats.moveSelected('availableGroupDivisions','actionGroupDivisions');});

            function initDataHeader(filters) {
                //create a Button using an existing <button> element as a data source
                var oFilterButton = new YAHOO.widget.Button('filterButton');
                var oResetButton = new YAHOO.widget.Button('resetButton');
                var oExportButton = new YAHOO.widget.Button('exportButton');

                //create filters (generic)
                YAHOO.ats.filter.init(filters);

                //set calendar
                var el = YAHOO.util.Dom.get('dataHeader');
                YAHOO.ats.calendar.init(el);
            };

            YAHOO.ats.action.initDataTable = function() {
                YAHOO.ats.sendGetRequest('action/main.do?dispatch=find', YAHOO.ats.action.findCallback);
            };

            YAHOO.ats.action.addCommentCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.action.initDataTable();
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            YAHOO.ats.action.addComment = function(actionId) {
                YAHOO.ats.addComment(actionId, 'action/main.do?dispatch=find', YAHOO.ats.action.addCommentCallback, 'POST');
            };

            //reset DueDateFrom button and event
            var resetDueDateFrom = new YAHOO.widget.Button('resetDueDateFrom', {
                label: '<span class="remove">&#160;</span>'
            });
            resetDueDateFrom.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_dueDateFrom').value = ''; 
            });
            
            //reset DueDateTo button and event
            var resetDueDateTo = new YAHOO.widget.Button('resetDueDateTo', {
                label: '<span class="remove">&#160;</span>'
            });
            resetDueDateTo.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_dueDateTo').value = ''; 
            });

            //reset ClosedDateFrom button and event
            var resetClosedDateFrom = new YAHOO.widget.Button('resetClosedDateFrom', {
                label: '<span class="remove">&#160;</span>'
            });
            resetClosedDateFrom.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_closedDateFrom').value = ''; 
            });
            
            //reset ClosedDateTo button and event
            var resetClosedDateTo = new YAHOO.widget.Button('resetClosedDateTo', {
                label: '<span class="remove">&#160;</span>'
            });
            resetClosedDateTo.on('click', function(e) {
                YAHOO.util.Dom.get('f_date_closedDateTo').value = ''; 
            });

            YAHOO.ats.action.findCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }

                    //full reload
                    if (YAHOO.ats.action.data == null) {
                        initDataHeader(data.filters);
                    }

                    YAHOO.ats.action.data = data;

                    //create table (default)
                    if (dataTable) {
                        //dataTable.destroy();
                        dataTable = null;
                    }
                    setTimeout(function() { // For IE
                        var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                        form.startIndex.value = 0;
                        dataTable = YAHOO.ats.table.create('dataDiv', data, form);
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
                    }, 0);
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
            toolbar.addButton({id: 'tb_reset', type: 'push', label: 'Reset', value: 'reset'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_export', type: 'push', label: 'Export', value: 'export'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                //YAHOO.util.Event.preventDefault(e);
                if (e.button.id == 'tb_filter') {
                    YAHOO.ats.table.filter(dataTable, true);
                    //YAHOO.ats.action.initDataTable();
                } else if (e.button.id == 'tb_reset') {
                    YAHOO.ats.sendGetRequest('action/main.do?reset=true', YAHOO.ats.viewCallback);
                } else if (e.button.id == 'tb_export') {
                    var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                    YAHOO.ats.sendDownloadRequest('document/export.do?dispatch=action&exportName=action&', form);
                }
            });

            //var form = YAHOO.util.Dom.get(YAHOO.ats.form);
            //form.actionStatusId[0].checked = true; // first one by default (open)

            //full reload
            YAHOO.ats.action.data = null;
            YAHOO.ats.action.initDataTable();
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();