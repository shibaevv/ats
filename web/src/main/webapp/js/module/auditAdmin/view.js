YAHOO.namespace('ats.auditAdmin');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {

            //root
            var root = YAHOO.util.Dom.get('dataHolder');
            YAHOO.ats.toggleInit(root);

            //create Toolbar
            var toolbar = new YAHOO.widget.Toolbar('toolbarDiv', {buttons: []});
            toolbar.addButton({id: 'tb_edit', type: 'push', label: 'Edit', value: 'edit'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_publish', type: 'push', label: 'Publish', value: 'publishReport'});
            toolbar.addSeparator();
            toolbar.addButton({id: 'tb_delete', type: 'push', label: 'Delete', value: 'deleteReport'});
            toolbar.addSeparator();
            toolbar.on('buttonClick', function(e) {
                //YAHOO.util.Event.preventDefault(e);
                if(e.button.id == 'tb_edit'){
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    //YAHOO.ats.navigate('auditAdmin/edit.do?auditId=' + auditId, 'Report');
                    YAHOO.ats.sendGetRequest('auditAdmin/edit.do?auditId=' + auditId , YAHOO.ats.viewCallback);
                }
                if(e.button.id == 'tb_publish'){
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    YAHOO.ats.openDialog('auditAdmin/edit.do?dispatch=publish&auditId=' + auditId, 'Publish Report', 300, 170); 
                }
                if(e.button.id == 'tb_delete'){
                    var auditId = YAHOO.util.Dom.get('auditId').value;
                    YAHOO.ats.openDialog('auditAdmin/delete.do?auditId=' + auditId, 'Delete Report', 300, 170);
                }
            });
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();

