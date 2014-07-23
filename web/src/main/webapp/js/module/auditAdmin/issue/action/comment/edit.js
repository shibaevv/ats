YAHOO.namespace('ats.action.comment');

 (function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
            removeAttachment = function(index) {
                var row = YAHOO.util.Dom.get('attachmentRow' + index);
                if (row != null) {
                    row.parentNode.removeChild(row);
                }
            };
            YAHOO.util.Event.addListener('addAttachment', 'click', function(e) {
                YAHOO.util.Event.stopEvent(e);
                var table = YAHOO.util.Dom.get('attachmentTable');
                var rows = table.rows;
                var index = rows.length;
                index = index - 1; //less header row
                //invisible row (to clone)
                var cloneRow = YAHOO.util.Dom.get('attachmentRow2clone');//rows[rows.length - 1];
                var newRow = cloneRow.cloneNode(true);
                newRow.setAttribute('id', 'attachmentRow' + index);
                newRow.removeAttribute('style'); // make it visible
                //newRow.setAttribute('class', index % 2 == 0 ? 'odd' : 'even');
                var input = YAHOO.util.Selector.query('input[type=file]', newRow);
                input[0].removeAttribute('disabled');
                input[0].setAttribute('id', 'attachment' + index);
                input[0].setAttribute('name', 'attachments[' + index + ']');
                var a = YAHOO.util.Selector.query('a[href^=javascript:removeAttachment]', newRow);
                a[0].setAttribute('href', 'javascript:removeAttachment(' + index + ');');
                //insert before last row
                YAHOO.util.Dom.insertBefore(newRow, cloneRow);
            });
        }
    });

    //Have loader insert only the JS files.
    loader.insert({}, 'js');
    //loader.insert(); 
})();