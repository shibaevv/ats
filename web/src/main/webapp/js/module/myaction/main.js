YAHOO.namespace('ats.myaction');

(function() {
    var dataTable = null;
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            YAHOO.ats.cleanup();

            YAHOO.ats.myaction.initDataTable = function() {
                var actionStatusId = YAHOO.util.Dom.get('actionStatusId').value;
                YAHOO.ats.sendGetRequest('myaction/main.do?dispatch=find' + (actionStatusId ? ('&actionStatusId=' + actionStatusId) : ''), YAHOO.ats.myaction.findCallback);
            };

            YAHOO.ats.myaction.addCommentCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.myaction.initDataTable();
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            YAHOO.ats.myaction.addComment = function(actionId) {
                YAHOO.ats.addComment(actionId, 'myaction/main.do?dispatch=find', YAHOO.ats.myaction.addCommentCallback, 'POST');
            };

            YAHOO.ats.myaction.findCallback = {
                cache:false,
                success: function(oResponse) {
                    YAHOO.ats.myaction.data = null;
                    var data = null;
                    try {
                        data = YAHOO.ats.parseJsonData(oResponse.responseText);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                    YAHOO.ats.myaction.data = data;

                    //update hidden field(s) - set in controller using some logic based on actionStatusId
                    var form = YAHOO.util.Dom.get('myactionForm');
                    var sortedBy = data.sortedBy;
                    form.sort.value = sortedBy.key;
                    form.dir.value = sortedBy.dir;

                    //create table (default)
                    dataTable = YAHOO.ats.table.create('dataDiv', data, form);
                    dataTable.set('MSG_EMPTY', 'No actions are currently assigned to you');
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
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            //ActionStatus event (radio button)
            YAHOO.util.Event.addListener(['openActions','closedActions','allActions'], 'click', function(e) {
                YAHOO.util.Dom.get('actionStatusId').value = this.value;
                YAHOO.ats.myaction.initDataTable();
            });

            YAHOO.ats.myaction.initDataTable();
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();