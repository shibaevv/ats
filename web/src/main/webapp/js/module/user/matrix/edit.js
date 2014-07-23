YAHOO.namespace('ats.user.matrix');

(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            var groupIdChangeCallback = {
                cache:false,
                success: function(oResponse) {
                    //reset
                    var dropdown = YAHOO.util.Dom.get('divisionId');
                    dropdown.options.length = 1;

                    //parse data
                    var response = oResponse.responseText;
                    try {
                        var divisions = YAHOO.ats.parseJsonData(response);
                        for (var i = 0; i != divisions.records.length; i++) {
                            var d = divisions.records[i];
                            dropdown.options[dropdown.options.length] = new Option(d.label, d.value);
                        }
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };

            //groupId change event
            YAHOO.util.Event.addListener('groupId', 'change', function(e) {
                //YAHOO.util.Event.stopEvent(e);
                var dropdown = YAHOO.util.Event.getTarget(e);
                var groupId = dropdown.value;
                var groupName = dropdown.options[dropdown.selectedIndex].innerHTML;
                YAHOO.ats.sendGetRequest('filter/division.do?group.groupId=' + groupId + '&group.name=' + groupName, groupIdChangeCallback);
            });
        }
    });

    //Have loader insert only the JS files.
    loader.insert({}, 'js');
    //loader.insert(); 
})();