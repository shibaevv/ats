YAHOO.namespace('ats.table');

/**
 * This method is passed into the DataTable's 'generateRequest' configuration
 * setting overriding the default generateRequest function. This function puts
 * together a query string which is passed to the DataSource each time a new
 * set of data is requested. All of the custom sorting and filtering options
 * added in by this script are gathered up here and inserted into the
 * query string.
 * @param {Object} oState
 * @param {Object} oDataTable
 * These parameters are explained in detail in DataTable's API
 * documentation. It's important to note that oState contains
 * a reference to the paginator and the pagination state and
 * the column sorting state as well.
 */
YAHOO.ats.table.requestBuilder = function (oState, oDataTable) {
    oState = oState || oDataTable.getState() || {pagination: null, sortedBy: null};
    var columns = oDataTable.getColumnSet();
    var column = columns.keys[1];
    var sort = oState.sortedBy ? oState.sortedBy.key : column.getKey();
    var dir = (oState.sortedBy && oState.sortedBy.dir === YAHOO.widget.DataTable.CLASS_DESC) ? 'desc' : 'asc';
    var startIndex = (oState.pagination && oState.pagination.recordOffset != 0) ? oState.pagination.recordOffset : 0;
    var pageSize = (oState.pagination && oState.pagination.rowsPerPage != 0) ? oState.pagination.rowsPerPage : 0; //100
    //TODO: set proper form (hardcode for now)
    var form = oDataTable.form || YAHOO.util.Dom.get(YAHOO.ats.form);
    if (form) {
        if (form.sort) form.sort.value = sort;
        if (form.dir) form.dir.value = dir;
        if (form.startIndex) form.startIndex.value = startIndex;
        if (form.pageSize) {
            if (form.pageSize.value != pageSize) form.startIndex.value = 0;
            form.pageSize.value = pageSize;
        }
        YAHOO.util.Connect.setForm(form);
    }
    // remove focus from page size dropdown box (to prevent user scroll it up/down with mouse wheel)
    var el = YAHOO.util.Dom.get('toolbarDiv');
    if (el) {
        el.focus();
    }
};

/**
 * This method is used to fire off a request for new data for the DataTable from the DataSource. 
 * The new state of the DataTable, after the request for new data, will be determined here.
 * @param {Boolean} resetRecordOffset
 */
YAHOO.ats.table.filter = function(oDataTable, resetRecordOffset) {
    var oState = oDataTable.getState();
    // We don't always want to reset the recordOffset.
    // if we want the Paginator to be set to the first page, pass in a value of true to this method.
    // otherwise, pass in false and the paginator will remain at the page it was set at before.
    if (resetRecordOffset && oState.pagination) {
        oState.pagination.recordOffset = 0;
    }
    //use onDataReturnSetRows because that method will clear out the old data in the DataTable, making way for the new data.
    var oCallback = {
        success: oDataTable.onDataReturnSetRows,
        failure: oDataTable.onDataReturnSetRows,
        argument: oState,
        scope: oDataTable
    };
    // Generate a query string
    var request = YAHOO.ats.table.requestBuilder(oState, oDataTable);
    // Fire off a request for new data.
    oDataTable.getDataSource().sendRequest(request, oCallback);
};

/**
 * id  <string|HTMLElement>
 *     The id of the element, or the element itself that the table will be inserted into.
 *     Existing markup in this element, if valid, will be used to build the table.
 */
YAHOO.ats.table.create = function(id, oData, oForm) {

    // The standard link and email formatters use the same value both for the display text and the underlying href attribute for the link
    YAHOO.widget.DataTable.formatLink = function(elLiner, oRecord, oColumn, oData) {
        //if (!oData) return;
        if (oData && oData.indexOf('javascript:') == 0) {
            var idx = oData.indexOf(';');
            elLiner.innerHTML = '<a href="' + oData.substr(0, idx + 1) + '">' + oData.substr(idx + 1) + '</a>';
        } else {
            elLiner.innerHTML = '<a href="' + oData + '" target="_blank">' + oData + '</a>';
        }
    };
    // custom class formatter
    YAHOO.widget.DataTable.formatClass = function(elLiner, oRecord, oColumn, oData) {
        if (oData == true) {
            elLiner.innerHTML = '<em class="' + oColumn.key + '"/>';
        }
    };
    // Custom formatter for column to preserve line breaks
    YAHOO.widget.DataTable.formatTextarea = function(elCell, oRecord, oColumn, oData) {
        elCell.innerHTML = '<pre>' + oData + '</pre>';
    };

    //
    var hasLink = false;
    var hasEditable = false;
    // Update the Column Definitions (pass as string in find.jsp)
    var columns = oData.columns;
    for (var c = 0; c < columns.length; c++) {
        var column = columns[c];
        if (column.formatter == 'YAHOO.widget.DataTable.formatLink') {
            column.formatter = YAHOO.widget.DataTable.formatLink;
            hasLink = true;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatEmail') {
            column.formatter = YAHOO.widget.DataTable.formatEmail;
            hasLink = true;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatTextarea') {
            column.formatter = YAHOO.widget.DataTable.formatTextarea;
        } else if (column.formatter == 'YAHOO.widget.DataTable.formatCheckbox') {
            column.formatter = YAHOO.widget.DataTable.formatCheckbox;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatRadio') {
            column.formatter = YAHOO.widget.DataTable.formatRadio;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatNumber') {
            column.formatter = YAHOO.widget.DataTable.formatNumber;
        } else  if (column.formatter == 'YAHOO.widget.DataTable.formatClass') {
            column.formatter = YAHOO.widget.DataTable.formatClass;
        }

        if (column.editor) {
            if (column.editor == 'YAHOO.widget.DropdownCellEditor') {
                column.editor = new YAHOO.widget.DropdownCellEditor({dropdownOptions:column.dropdownOptions,disableBtns:false});
            } else if (column.editor == 'YAHOO.widget.TextboxCellEditor') {
                column.editor = new YAHOO.widget.TextboxCellEditor({disableBtns:false});
            } else if (column.editor == 'YAHOO.widget.TextareaCellEditor') {
                column.editor = new YAHOO.widget.TextareaCellEditor({disableBtns:false});
            }
            column.editor.key = column.key;
            hasEditable = true;
        }
    }

    // DataSource configuration
    var oDataSourceConfigs = {
        connXhrMode: 'queueRequests', //If a request is already in progress, wait until response is returned before sending the next request.
        connMethodPost: true, //POST
        maxCacheEntries: 0, //Set to 0 to turn off caching
        responseType: YAHOO.util.XHRDataSource.TYPE_JSON,
        responseSchema: {
            resultsList: 'records',
            fields: oData.fields,
            metaFields: {
                totalRecords: 'paginator.totalRecords',
                paginationRecordOffset: 'paginator.startIndex',
                paginationRowsPerPage: 'paginator.pageSize',
                sortKey: 'sortedBy.key',
                sortDir: 'sortedBy.dir'
            }
        }
    };
    // Create the DataSource
    var dataSource = new YAHOO.util.XHRDataSource(oData.actions.find, oDataSourceConfigs);
    if (!oForm) {
        oForm = YAHOO.util.Dom.get(YAHOO.ats.form);
    }
    if (oForm) {
        YAHOO.util.Connect.setForm(oForm);
    }

    // Create the Paginator (optional)
    var paginator = null;
    if (oData.paginator) {
        paginator = new YAHOO.widget.Paginator({
            containers: 'paginatorDiv',
            //template: '{PageLinks} Show {RowsPerPageDropdown} per page',
            template: '{PreviousPageLink} {CurrentPageReport} {NextPageLink} {RowsPerPageDropdown}',
            pageReportTemplate: 'Showing items {startRecord} - {endRecord} of {totalRecords}',
            rowsPerPageOptions: [10,25,50,100]
        });
    }

    // Define a custom row formatter function
    var rowFormatter = function(elTr, oRecord) {
        var formatRowClass = oRecord.getData('formatRowClass');
        if (formatRowClass) {
            YAHOO.util.Dom.addClass(elTr, formatRowClass);
        }
        return true;
    };

    // DataTable configuration
    var oTableConfigs = {
        //When dynamicData is enabled, sorting or paginating will trigger a DataSource request for new data to reflect the state. By default, the request is formatted with the following syntax:
        //sort={SortColumnKey}&dir={SortColumnDir}&startIndex={PaginationStartIndex}&results={PaginationRowsPerPage}
        dynamicData: true,
        //sortedBy : {key:oData.columns[0].key, dir:YAHOO.widget.DataTable.CLASS_ASC}, // Sets UI initial sort arrow 
        sortedBy: {key:oData.sortedBy.key, dir:oData.sortedBy.dir}, // Sets UI initial sort arrow 
        initialLoad: (oData.initialLoad == true),
        //initialRequest: '1=1',
        scrollable: false,
        //draggableColumns: true,
        //height: dtH + 'px', width: w + 'px',
        paginator: paginator,
        //A value greater than 0 enables DOM rendering of rows to be executed from a non-blocking timeout queue and sets how many rows to be rendered per timeout.
        renderLoopSize: 25,
        // This configuration item is what builds the query string passed to the DataSource.
        generateRequest: YAHOO.ats.table.requestBuilder,
        // Enable the row formatter
        formatRow: rowFormatter
    };
    //recommended for IE
    //YAHOO.widget.DataTable._bDynStylesFallback = true;

    // Create table
    var dataTable = new YAHOO.widget.DataTable(id, columns, dataSource, oTableConfigs);
    dataTable.form = oForm;
    //var dataTable = new YAHOO.widget.ScrollingDataTable(id, columns, dataSource, oTableConfigs);
    // Show loading message while page is being rendered
    dataTable.set('MSG_EMPTY', 'This selection contains no data');
    dataTable.showTableMessage(dataTable.get('MSG_LOADING'), YAHOO.widget.DataTable.CLASS_LOADING);

/*
    // Define an event handler that scoops up the totalRecords which we sent as part of the JSON data.
    // This is then used to tell the paginator the total records.
    // This happens after each time the DataTable is updated with new data.
    dataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
        var meta = oResponse.meta;
        // The payload object usually represents DataTable's state values, including:
        oPayload.totalRecords = meta.totalRecords;
        // oPayload.pagination.rowsPerPage = [number of rows per page]
        // oPayload.pagination.recordOffset = [index of first record of current page]
        // oPayload.sortedBy.key =  [key of currently sorted column]
        // oPayload.sortedBy.dir = [direction of currently sorted column]
        return oPayload;
    }
*/
    // Update payload data on the fly for tight integration with latest values from server 
    dataTable.doBeforeLoadData = function(oRequest, oResponse, oPayload) {
        if (oResponse.error) {
            YAHOO.ats.failure(oResponse);
            return false;
        }
        var meta = oResponse.meta;
        var totalRecords = meta.totalRecords;// || oPayload.totalRecords;
        var pageSize = meta.paginationRowsPerPage || 0;
        var startIndex = meta.paginationRecordOffset || 0;
        var sort = (oPayload.sortedBy && oPayload.sortedBy.key) || meta.sortKey || 'id';
        var dir = (oPayload.sortedBy && oPayload.sortedBy.dir) || 'yui-dt-' + meta.sortDir || 'yui-dt-asc';
        //update payload
        oPayload.totalRecords = totalRecords;
        oPayload.pagination = {
            rowsPerPage: pageSize,
            recordOffset: startIndex
        };
        oPayload.sortedBy = {
            key: sort,
            dir: dir // converts from server value '[dir]' to DataTable format 'yui-dt-[dir]'
        };
        // Flushes cache
        //this.getDataSource().flushCache();
        //
        return true;
    };

    // Subscribe to events for row (global)
    dataTable.subscribe('rowMouseoverEvent', dataTable.onEventHighlightRow);
    dataTable.subscribe('rowMouseoutEvent', dataTable.onEventUnhighlightRow);
    dataTable.subscribe('rowClickEvent', dataTable.onEventSelectRow);
    // Subscribe to events for cell/row selection
    if (hasEditable) {
        dataTable.subscribe('cellMouseoverEvent', function(oArgs) {
            var elCell = oArgs.target;
            if (YAHOO.util.Dom.hasClass(elCell, 'yui-dt-editable')) {
                this.highlightCell(elCell);
            }
        });
        dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
        dataTable.subscribe('cellClickEvent', dataTable.onEventShowCellEditor);
    } else if (hasLink) {
        dataTable.subscribe('cellMouseoverEvent', dataTable.onEventHighlightCell);
        dataTable.subscribe('cellMouseoutEvent', dataTable.onEventUnhighlightCell);
        dataTable.subscribe('cellClickEvent', dataTable.onEventSelectCell);
        //NB: define cell specific navigation in page javascript
        //dataTable.subscribe('cellSelectEvent', function(oArgs) {
        //    var key = oArgs.key;
        //    if (key != 'link' && key != 'email') {
        //        var row = oArgs.record._oData;
        //          YAHOO.ats.navigate(oData.actions.view + row.id, '');
        //    }
        //});
    } else {
        dataTable.subscribe('rowSelectEvent', function() { //rowDblclickEvent
            if (!oData.actions.expand) {
                return;
            }
            // scroll to the top
            if (YAHOO.env.ua.ie == 6) {
                //if (document.body) document.body.scrollTop = 0;
                //if (document.documentElement) document.documentElement.scrollTop = 0;
            }
            var row = this.getRecordSet().getRecord(this.getSelectedRows()[0])._oData;
            YAHOO.ats.navigate(oData.actions.view + row.id, '');
        });
    }

    if (oData.initialLoad == true) {
        //Payload
        if (paginator) {
            paginator.set('recordOffset', 0);//oData.paginator.startIndex);
        }
        if (oForm) {
            if (oForm.startIndex) oForm.startIndex.value = 0;
        }
    } else {
        //initial load (see dataTable.onDataReturnSetRows method L12871)
        setTimeout(function() {
            var rs = dataTable.getRecordSet();
            rs.reset();
            rs.setRecords(oData.records, 0);
            //Payload
            if (paginator) {
                paginator.set('totalRecords', oData.paginator.totalRecords);
                //paginator.set('totalRecords', oData.paginator.recordsReturned);
                paginator.set('rowsPerPage', oData.paginator.pageSize);
                paginator.set('recordOffset', 0);//oData.paginator.startIndex);
            }
            var sort = oData && oData.sortedBy && oData.sortedBy.key ? oData.sortedBy.key : 'id';
            var dir = oData && oData.sortedBy && oData.sortedBy.dir ? 'yui-dt-' + oData.sortedBy.dir : 'yui-dt-asc';
            dataTable.set('sortedBy', {key:sort,dir:dir});
            //
            dataTable.render();
        }, 0);
    }

    return dataTable;
};