YAHOO.namespace('ats.tree');

//
var labelStyles = ['icon-audit','icon-issue','icon-action','icon-comment'];

function loadNodeData(node, fnLoadComplete) {
    var callback = {
        cache:false,
        success: function(oResponse) {
            var actions = null;
            var records = [];
            var response = oResponse.responseText;
            try {
                var data = YAHOO.ats.parseJsonData(response);
                actions = data.actions;
                records = data.records;
            } catch (e) {
                YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
            }
            if (records && records.length) {
                if (YAHOO.lang.isArray(records)) {
                    for (var i = 0; i != records.length; i++) {
                        var item = records[i];
                        var oData = {label: item.name, title: item.detail, expanded: false, isLeaf: !actions.expand, nowrap: true};

                        var itemNode = new YAHOO.widget.TextNode(oData, node);
                        //var itemNode = new YAHOO.widget.MenuNode(oData, node);
                        itemNode.labelStyle = labelStyles[itemNode.depth];
                        itemNode.id = item.id;
                        itemNode.actions = actions;
                        //itemNode.propagateHighlightUp = true;
                        itemNode.title = item.detail;
                        itemNode.renderHidden = true;

                        //save the element id for Tooltip
                        //tooltipContext.push(itemNode.labelElId);
                    }
                } else {
                    var item = records;
                    var oData = {label: item.name, title: item.detail, expanded: false, isLeaf: !actions.expand, nowrap: true};

                    var itemNode = new YAHOO.widget.TextNode(oData, node);
                    //var itemNode = new YAHOO.widget.MenuNode(oData, node);
                    itemNode.labelStyle = labelStyles[itemNode.depth];
                    itemNode.id = item.id;
                    itemNode.actions = actions;
                    //itemNode.propagateHighlightUp = true;
                    itemNode.title = item.detail;
                    itemNode.renderHidden = true;

                    //save the element id for Tooltip
                    //tooltipContext.push(itemNode.labelElId);
                }
            }
            //
            YAHOO.ats.createTooltip(false);
            oResponse.argument.fnLoadComplete();
        },
        failure: function(oResponse) {
            YAHOO.ats.failure(oResponse);
            oResponse.argument.fnLoadComplete();
        },
        argument: {
            'node': node,
            'fnLoadComplete': fnLoadComplete
        }
        //, timeout: 10000
    };
    if (node.data && node.actions.expand && node.id) {
        var form = YAHOO.util.Dom.get(YAHOO.ats.form);
        YAHOO.ats.sendPostRequest(form, node.actions.expand + node.id, callback);
    }
};

/**
 * 
 */
YAHOO.ats.tree.filter = function(dataTree, data) {
    var root = dataTree.getRoot();
    for (var i = 0; i != data.records.length; i++) {
        var record = data.records[i];
        //apply specific icon styles to each node
        var oData = {label: record.name, title: record.detail, expanded: false, isLeaf: false, nowrap: true};

        var itemNode = new YAHOO.widget.TextNode(oData, root);
        //var itemNode = new YAHOO.widget.MenuNode(oData, root);
        itemNode.labelStyle = labelStyles[itemNode.depth];
        itemNode.id = record.id;
        itemNode.actions = data.actions;
        // Generate the markup for this node when the tree is first rendered.
        // This is necessary in order to make sure tooltips can be attached to hidden nodes. 
        itemNode.title = record.detail;
        itemNode.renderHidden = true;

        // Save the element id for Tooltip
        //tooltipContext.push(itemNode.labelElId);
    }
}

/**
 * id  <string|HTMLElement>
 *     The id of the element, or the element itself that the tree will be inserted into.
 *     Existing markup in this element, if valid, will be used to build the tree.
 */
YAHOO.ats.tree.create = function(id, data) {
    // Create tree
    var dataTree = new YAHOO.widget.TreeView(id);
    dataTree.setDynamicLoad(loadNodeData, 0);
    //
    YAHOO.ats.tree.filter(dataTree, data);
    //
    dataTree.subscribe('clickEvent', dataTree.onEventToggleHighlight);
    dataTree.singleNodeHighlight = true;
    //dataTree.setNodesProperty('propagateHighlightUp', true);
    //dataTree.setNodesProperty('propagateHighlightDown', true);

    // Trees with TextNodes will fire an event for when the label is clicked: 
    var viewCallback = {
        cache:false,
        success: function(oResponse) {
            var response = oResponse.responseText;
            YAHOO.util.Dom.get('dataFooter').innerHTML = response;
        },
        failure: function(oResponse) {
            YAHOO.ats.failure(oResponse);
        }
        //, timeout: 10000
    };
    dataTree.subscribe('labelClick', function(node) { //labelClick is deprecated use clickEvent
        if (node.data && node.actions.view && node.id) {
            YAHOO.ats.sendGetRequest( node.actions.view + node.id, viewCallback);
        }
    });
    // Expand and collapse happen prior to the actual expand/collapse,
    // and can be used to cancel the operation (return false;)
    //dataTree.subscribe('expand', function(node) {});
    //dataTree.subscribe('collapse', function(node) {});

    // TODO:
    //YAHOO.util.Dom.get('dataDiv').class = 'ygtv-highlight';
    //
    dataTree.draw();
    //
    YAHOO.ats.createTooltip(true);

//    // Instantiate a ContextMenu:
//    // the first argument passed to the constructor is the id for the Menu element to be created,
//    // the second is an object literal of configuration properties.
//    var contextMenu = new YAHOO.widget.ContextMenu(
//        'contextMenu', {
//            trigger: 'dataDiv',
//            lazyload: true,
//            itemdata: [
//                { text: 'View', onclick: { fn: function() {
//                     if (YAHOO.ats.tree.currentNode) {
//                        var data = YAHOO.ats.tree.currentNode.data;
//                        YAHOO.ats.openDialog(data.actions.view + data.id, 'View');
//                    }
//                } } }
//                //, { text: 'Edit', onclick: { fn: function() {
//                //    if (YAHOO.ats.tree.currentNode) {
//                //        var data = YAHOO.ats.tree.currentNode.data;
//                //        YAHOO.ats.openDialog(data.actions.edit + data.id, 'Edit');
//                //    }
//                //} } }
//            ]
//        }
//    );
//    // Subscribe to the 'contextmenu' event for the element(s) specified as the 'trigger' for the ContextMenu instance.
//    contextMenu.subscribe('triggerContextMenu', function(oEvent) {
//        var oTarget = this.contextEventTarget;
//        //get the node instance that that triggered the display of the ContextMenu instance.
//        YAHOO.ats.tree.currentNode = dataTree.getNodeByElement(oTarget);
//        if (!YAHOO.ats.tree.currentNode) {
//            //Cancel the display of the ContextMenu instance.
//            this.cancel();
//        }
//    });
    return dataTree;
};