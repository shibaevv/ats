YAHOO.namespace('ats');

(function() {
//=======================================================================
//vars.js
//=======================================================================
    YAHOO.ats = {
        debug: false,
        title: null,
        module: 'atsModule', //history
        form: 'dataForm', //default id/name for all forms
        waitPanel: null,
        alertPanel: null,
        dialogPanel: null
    };
    YAHOO.namespace('ats.calendar');
    YAHOO.namespace('ats.filter');
    YAHOO.ats.filter = {
        filters: [],
        autoCompletes: [],
        dataSources: []
    };
    YAHOO.namespace('ats.table');
    YAHOO.namespace('ats.auditAdmin');
    YAHOO.ats.auditAdmin = {
        data: null
    };
    YAHOO.namespace('ats.auditAdmin.action');
    YAHOO.ats.auditAdmin.action = {
        data: null
    };
    YAHOO.namespace('ats.auditAdmin.action.comment');
    YAHOO.ats.auditAdmin.action.comment = {
        data: null
    };
    YAHOO.namespace('ats.audit');
    YAHOO.ats.audit = {
        data: null
    };
    YAHOO.namespace('ats.issue');
    YAHOO.ats.issue = {
        data: null
    };
    YAHOO.namespace('ats.action');
    YAHOO.ats.action = {
        data: null
    };
    YAHOO.namespace('ats.action.comment');
    YAHOO.ats.action.comment = {
        data: null,
        dataAuditLogs: null
    };
    YAHOO.namespace('ats.user');
    YAHOO.ats.user = {
        data: null
    };
    YAHOO.namespace('ats.user.matrix');
    YAHOO.ats.user.matrix = {
        data: null
    };
    YAHOO.namespace('ats.setup');
    YAHOO.ats.setup = {
        menuBar: null
    };
    YAHOO.namespace('ats.setup.role');
    YAHOO.ats.setup.role = {
        data: null
    };

    YAHOO.ats.login = function() {
    	window.location.href = 'login.do';
    };
    YAHOO.ats.logout = function() {
    	window.location.href = 'logout.do';
    };

    /**
     * 
     */
    YAHOO.ats.toggleClass = function(el, selectedClass) {
        if (YAHOO.util.Dom.hasClass(el, selectedClass)) {
            YAHOO.util.Dom.removeClass(el, selectedClass);
        } else {
            YAHOO.util.Dom.addClass(el, selectedClass);
        }
    };

    /**
     * 
     */
    YAHOO.ats.toggleInit = function(root) {
        var toggleNodes = YAHOO.util.Selector.query('span[id^=toggle.]', root);
        YAHOO.util.Event.addListener(toggleNodes, 'click', function(e) {
            var toggleEl = YAHOO.util.Event.getTarget(e);
            var containerEl = YAHOO.util.Dom.get(toggleEl.id + '.container');
            if (YAHOO.util.Dom.hasClass(toggleEl, 'collapsed')) {
                YAHOO.util.Dom.removeClass(toggleEl, 'collapsed');
                YAHOO.util.Dom.setStyle(containerEl, 'display', 'block');
            } else {
                YAHOO.util.Dom.addClass(toggleEl, 'collapsed');
                YAHOO.util.Dom.setStyle(containerEl, 'display', 'none');
            }
        });
    };

    /**
     * 
     */
    YAHOO.ats.moveSelected = function(targetId, sourceId, selectedClass) {
        var targetEl = YAHOO.util.Dom.get(targetId);
        var sourceEl = YAHOO.util.Dom.get(sourceId);
        var nodes = YAHOO.util.Selector.query('div[id^=' + sourceId + ']', sourceEl);
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];
            try {if (!node.textContent) node.textContent = node.innerText;} catch(e) {/* IE6 hack */}
            if (!selectedClass || YAHOO.util.Dom.hasClass(node, selectedClass)) {
                node.parentNode.removeChild(node);
                if (selectedClass) {
                    YAHOO.util.Dom.removeClass(node, selectedClass);
                }
                var id = node.id;
                id = id.replace(sourceId, targetId);
                YAHOO.util.Dom.setAttribute(node, 'id', id);
                var input = node.children[0];
                input.setAttribute('name', targetId);
                var referenceNode = targetEl.firstChild;
                if (referenceNode == null) {
                    targetEl.appendChild(node);
                } else {
                    //insert in alphabetical order (compare text nodes)
                    var n = referenceNode;
                    while (n != null) {
                        try {if (!n.textContent) n.textContent = n.innerText;} catch(e) {/* IE6 hack */}
                        referenceNode = n;
                        if (node.textContent < n.textContent) { // IE does not like .trim()
                            YAHOO.util.Dom.insertBefore(node, referenceNode);
                            break;
                        }
                        n = n.nextSibling;
                        if (n == null) {
                            YAHOO.util.Dom.insertAfter(node, referenceNode);
                        }
                    }
                }
            }
        }
    };

    /**
     * parse JSON string - replace unsafe characters
     */
    YAHOO.ats.parseJsonData = function(jsonData) {
        if (!jsonData) {
            return null;
        }
        //encode Back Slashes
        jsonData = jsonData.replace(/\\/g, '\\\\');
        //parse
        return YAHOO.lang.JSON.parse(jsonData);
    };

    /**
     * @param root - root element (optional)
     */
    YAHOO.ats.hideErrors = function(root) {
        var errorRows = YAHOO.util.Selector.query('tr[class=error]', root);
        for (var i = 0; i != errorRows.length; i++) {
            var trEl = errorRows[i];
            YAHOO.util.Dom.replaceClass(trEl, 'error', 'error-hidden');
            for (var j = 0; j < trEl.childNodes.length; j++) {
                var tdEl = trEl.childNodes[j];
                tdEl.innerHTML = '';
            }
        }
    };

    /**
     * @param errors - json errors
     * @param root - root element (optional)
     */
    YAHOO.ats.showErrors = function(errors, root) {
        for (var i = 0; i != errors.length; i++) {
            var error = errors[i];
            var errorId = !error.name ? 'errors' : ('errors.' + error.name);
            var errorMessage = error.message;
            var tdEl = YAHOO.util.Dom.get(errorId);
            if (tdEl) {
                tdEl.innerHTML = errorMessage;
                var trEl = tdEl.parentNode;
                YAHOO.util.Dom.replaceClass(trEl, 'error-hidden', 'error');
            } else {
                // create new error row
                var errorRows = YAHOO.util.Selector.query('tr[class=error-hidden]', root);
                if (errorRows.length != 0) {
                    var cloneRow = errorRows[0]; // first row
                    var trEl = cloneRow.cloneNode(true);
                    var tdEl = YAHOO.util.Selector.query('td', trEl)[0];
                    tdEl.setAttribute('id', errorId);
                    tdEl.innerHTML = errorMessage;
                    YAHOO.util.Dom.replaceClass(trEl, 'error-hidden', 'error');
                    YAHOO.util.Dom.insertAfter(trEl, cloneRow);
                }
            }
        }
    };

    /**
     * Show/hide progress
     */
    YAHOO.ats.progress = function(show, modal) {
        var loadingEl = YAHOO.util.Dom.get('menu');
        if (show) {
            if (YAHOO.ats.waitPanel && modal) {
                YAHOO.ats.waitPanel.show();
            }
            if (loadingEl) {
                YAHOO.util.Dom.addClass(loadingEl, 'loading');
            }
        } else {
            if (YAHOO.ats.waitPanel) {
                YAHOO.ats.waitPanel.hide();
            }
            if (loadingEl) {
                YAHOO.util.Dom.removeClass(loadingEl, 'loading');
            }
        }
    };

    /**
     * Validate integer input
     */
    YAHOO.ats.inputInteger = function(event, obj) {
        return YAHOO.ats.inputNumber(event, obj, false);    
    };
    /**
     * Validate float input
     */
    YAHOO.ats.inputFloat = function(event, obj) {
        return YAHOO.ats.inputNumber(event, obj, true);
    };
    /**
     * Validate number input
     */
    YAHOO.ats.inputNumber = function(event, obj, decimals, length) {
        if (!length) {
            length = 64;
        }
        var which = event.which;
        var key = event.keyCode;
        if (typeof(which) == 'undefined') {
            which = key;
            key = 0;
        }
        // 46='.' dot
        if (which == 46 && obj.value.indexOf('.') == -1 && decimals && obj.value.length < length) {
            return true;
        }
        // 8=backspace, 9=tab, 13=CR, 27=
        if (!((which >= 48 && which <= 57 && obj.value.length < length) || (key >= 37 && key <= 40) || (key >= 112 && key <= 123) || event.ctrlKey || (key == 8 || key == 9 || key == 13))) {
            event.returnValue = false;
            return false;
        }
        return true;
    };

    /**
     * Replace Microsoft Smart Quotes with regular quotes.
     */
    YAHOO.ats.inputString = function(str) {
        var stringReplacements = new Array();
        stringReplacements[8216] = 39; // '
        stringReplacements[8217] = 39; // '
        stringReplacements[8220] = 34; // "
        stringReplacements[8221] = 34; // "
        stringReplacements[8212] = 45; // -
        for (var c = 0; c < str.length; c++) {
            var code = str.charCodeAt(c);
            var value = stringReplacements[code];
            if (value != undefined) {
                str = str.substr(0, c) + String.fromCharCode(value) + str.substr(c + 1);
            }
        }
        return str;
    };

    /**
     * clean up (if any)
     */
    YAHOO.ats.cleanup = function() {
        YAHOO.ats.filter.cleanup();
        YAHOO.ats.setup.cleanup();
    };
    //destroy filter autoCompletes/dataSources
    YAHOO.ats.filter.cleanup = function() {
    	if (YAHOO.ats.filter && YAHOO.ats.filter.filters) {
            for (var i = 0; i != YAHOO.ats.filter.filters.length; i++) {
                if (YAHOO.ats.filter.autoCompletes[i]) {
                    YAHOO.ats.filter.autoCompletes[i].destroy();
                    YAHOO.ats.filter.autoCompletes[i] = null;
                }
                if (YAHOO.ats.filter.dataSources[i]) {
                    //YAHOO.ats.filter.dataSources[i].destroy();
                    YAHOO.ats.filter.dataSources[i] = null;
                }
                YAHOO.ats.filter.filters[i] = null;
            }
            YAHOO.ats.filter.filters = [];
            YAHOO.ats.filter.autoCompletes = [];
            YAHOO.ats.filter.dataSources = [];
    	}
    };
    //destroy/remove menu
    YAHOO.ats.setup.cleanup = function() {
        if (YAHOO.ats.setup.menuBar) {
            try {
                YAHOO.ats.setup.menuBar.destroy();
            } catch (e) {
                alert('menuBar.destroy: ' + e.message);
            }
            YAHOO.ats.setup.menuBar = null;
        }
        //
        var el = YAHOO.util.Dom.get('menuCenter');
        if (!YAHOO.util.Dom.hasClass(el, 'hidden')) {
            YAHOO.util.Dom.addClass(el, 'hidden');
        }
    };

    /**
     * 
     */
    YAHOO.ats.emptyCallback = {
        cache:false,
        success: function(oResponse) {
        },
        failure: function(oResponse) {
            YAHOO.ats.failure(oResponse);
        }
        //, timeout: 10000
    };

    /**
     * 
     */
    YAHOO.ats.viewCallback = {
        cache:false,
        success: function(oResponse) {
            // Set the body to the string passed
            var el = YAHOO.util.Dom.get('center');
            el.innerHTML = oResponse.responseText;
            // execute javascript (if any)
            YAHOO.ats.loadScripts(el);
        },
        failure: function(oResponse) {
            YAHOO.ats.failure(oResponse);
        }
        //, timeout: 10000
    };

    /**
     * 
     */
    YAHOO.ats.failure = function(oResponse) {
        YAHOO.ats.alert('Failure', oResponse);
    };

    /**
     * execute script(s) - if any
     */
    YAHOO.ats.loadScripts = function(root) {
        if (root) {
            var scriptNodes = YAHOO.util.Selector.query('script[type=text/javascript]', root);
            for (var i = 0; scriptNodes && i < scriptNodes.length; i++) {
                var scriptNode = scriptNodes[i];
                YAHOO.util.Get.script(scriptNode.src, {
                    onSuccess: function(scriptData) {
                        if (!YAHOO.ats.debug) {
                            scriptData.purge(); //removes the script node immediately after executing
                        }
                        scriptNode.parentNode.removeChild(scriptNode);
                    },
                    onFailure: function(oResponse) {
                        var response = oResponse.responseText;
                        YAHOO.ats.alert('Failure', oResponse);
                    }
                });
            }
            //load css(s) - if any
            var cssNodes = YAHOO.util.Selector.query('link[type=text/css]', root);
            for (var i = 0; cssNodes && i < cssNodes.length; i++) {
                var cssNode = cssNodes[i];
                YAHOO.util.Get.css(cssNode.href);
                cssNode.parentNode.removeChild(cssNode);
            }
        }
    };

//=======================================================================
//filter.js
//=======================================================================
    function linkToggleButton(oToggle, oToggleButton, oAutoComplete) {
        oToggleButton.on('click', function(e) {
            //YAHOO.util.Event.stopEvent(e);
            if (!YAHOO.util.Dom.hasClass(oToggle, 'open')) {
                YAHOO.util.Dom.addClass(oToggle, 'open');
            }
            if (oAutoComplete.isContainerOpen()) { // Is open
                oAutoComplete.collapseContainer();
            } else { // Is closed
                oAutoComplete.getInputEl().focus(); // Needed to keep widget active
                setTimeout(function() { // For IE
                    oAutoComplete.sendQuery('');
                }, 0);
            }
        });
        oAutoComplete.containerCollapseEvent.subscribe(function(){YAHOO.util.Dom.removeClass(oToggle, 'open');});
    };
    function itemSelectEventSubscribe(oHidden, oAutoComplete) {
        oAutoComplete.itemSelectEvent.subscribe(function(sType, aArgs) {
            //var myAC = aArgs[0]; // reference back to the AC instance
            //var elLI = aArgs[1]; // reference to the selected LI element
            var oData = aArgs[2]; // object literal of selected item's result data
            
            // update hidden form field with the selected item's ID
            oHidden.value = oData.value;
        });
    };
    /**
     * Generic create filters.
     * Elements with id filterNInput, filterNContainer should exist.
     */
    YAHOO.ats.filter.init = function(filters) {
        //clean up (if any)
        YAHOO.ats.filter.cleanup();
        //create DataSource and AutoComplete
        var oConfigs = {
            prehighlightClassName: 'yui-ac-prehighlight',
            //alwaysShowContainer: true,
            //forceSelection: true,
            maxResultsDisplayed: 20,
            useShadow: true,
            useIFrame: true,
            queryDelay: .5,
            minQueryLength: 0
            //,animVert: .01
        };
        YAHOO.ats.filter.filters = [];
        YAHOO.ats.filter.dataSources = new Array(filters.length);
        YAHOO.ats.filter.autoCompletes = new Array(filters.length);
        for (var i = 0; i != filters.length; i++) {
            var input = YAHOO.util.Dom.get('filter' + i + 'Input');
            var toggle = YAHOO.util.Dom.get('filter' + i + 'Toggle');
            var toggleButton = toggle ? new YAHOO.widget.Button({container:toggle}) : null; 
            var container = YAHOO.util.Dom.get('filter' + i + 'Container');
            var hidden = YAHOO.util.Dom.get('filter'+ i + 'Hidden');
            if (input && container) {
                var filter = filters[i];
                var ac = null;
                var ds = null;
                if (filter.values) {
                    var values = filter.values;
                    ds = new YAHOO.util.LocalDataSource(values);
                    ds.responseSchema = {fields : ['label','value']}; 
                    ac = new YAHOO.widget.AutoComplete(input, container, ds, oConfigs);
                    ac.resultTypeList = false;
                    ac.useShadow = true;
                    // Fired when the input field value has changed when it loses focus.
                    ac.textboxChangeEvent.subscribe(function(oSelf) {
                        var input = YAHOO.util.Dom.get('filter' + this.filterIndex + 'Input');
                        // reset hidden value (if input blank)
                        var hidden = YAHOO.util.Dom.get('filter' + this.filterIndex + 'Hidden');
                        if (input && !input.value && hidden) {
                            hidden.value = '';
                        }
                    });
                } else if (filter.valuesUrl) {
                    ds = new YAHOO.util.XHRDataSource(filter.valuesUrl);
                    ds.connMethodPost = true;
                    ds.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;
                    ds.responseSchema = {resultsList:'records',fields:['label','value']};
                    ac = new YAHOO.widget.AutoComplete(input, container, ds, oConfigs);
                    ac.resultTypeList = false;
                    ac.useShadow = true;
                    // Fired when the AutoComplete instance makes a request to the DataSource
                    ac.dataRequestEvent.subscribe(function(oSelf, sQuery, oRequest) {
                        YAHOO.ats.fixIEFormHeader();
                        // reset hidden value
                        var hidden = YAHOO.util.Dom.get('filter' + this.filterIndex + 'Hidden');
                        if (hidden) {
                            hidden.value = '';
                        }
                        var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                        YAHOO.util.Connect.setForm(form);
                    });
                }
                if (hidden) {
                    itemSelectEventSubscribe(hidden, ac);
                }
                if (filter.forceSelection == true) {
                    ac.forceSelection = true;
                }
                if (filter.maxResultsDisplayed) {
                    ac.maxResultsDisplayed = filter.maxResultsDisplayed;
                }
                if (toggleButton) {
                    linkToggleButton(toggle, toggleButton, ac);
                }
                ac.formatResult = function(oResultData, sQuery, sResultMatch) {
                    var sMarkup = sResultMatch ? sResultMatch : '&#160;';
                    return sMarkup;
                };
                ac.filterIndex = i;
                YAHOO.ats.filter.autoCompletes[i] = ac;
                YAHOO.ats.filter.dataSources[i] = ds;
            }
        }
        YAHOO.ats.filter.filters = filters;
    };

//=======================================================================
//menu.js
//=======================================================================
    /**
     * 
     */
    YAHOO.ats.initMenu = function(containerId, menuBarId, menuItems, callback) {
          var innerHTML = '<div id="' + menuBarId + '" class="yuimenubar yuimenubarnav"><div class="bd"><ul class="first-of-type">';
          for (var i = 0; i != menuItems.length; i++) {
              var menuItem = menuItems[i];
              var classname = menuItem.classname ? ' ' + menuItem.classname : '';
              var title = menuItem.title ? ' title="' + menuItem.title + '"' : '';
              var onclick = 'return false;';
              if (menuItem.url && menuItem.url.indexOf('javascript:') == 0) {
            	  onclick = '';
              }
              innerHTML += '<li id="' + menuItem.id + '" class="yuimenubaritem' + (i == 0 ? ' first-of-type' : '') + classname + '"' + title + '>';
              innerHTML += '<a class="yuimenubaritemlabel" href="' + (menuItem.url ? menuItem.url : 'javascript:;') + '" onclick="' + onclick + '"><span></span>' + menuItem.text + '</a>';
              innerHTML += '</li>';
          }
          innerHTML = innerHTML + '</ul></div></div>';
          var d = YAHOO.util.Dom.get(containerId);
          d.innerHTML = innerHTML;
          // create menuBar
          var menuBar = new YAHOO.widget.MenuBar(menuBarId, {
              autosubmenudisplay: true,
              keepopen: false
          });
          menuBar.subscribe('beforeRender', function () {
              if (this.getRoot() == this) {
                  for (i = 0; i != menuItems.length; i++) {
                      var menuItem = menuItems[i];
                      if (menuItem.itemdata) {
                          this.getItem(i).cfg.setProperty('submenu', menuItem);
                      }
                  }
              }
          });
          menuBar.render();
          // Setup the click listeners on the menuBar
          YAHOO.util.Event.on(menuBarId, 'click', function(e) {
              var tar = YAHOO.util.Event.getTarget(e);
              if (tar.tagName.toLowerCase() != 'a') {
                  tar = null;
              }
              if (tar && tar.href.indexOf('javascript:') == 0) {
                  tar = null;
              }
              //Make sure we are a link in the list's 
              if (tar && YAHOO.util.Selector.test(tar, '#' + menuBarId + ' ul li a')) {
                  YAHOO.util.Event.preventDefault(e);
                  var action = tar.href;
                  YAHOO.util.Dom.removeClass(YAHOO.util.Selector.query('#' + menuBarId + ' li'), 'selected');
                  YAHOO.util.Dom.addClass(tar.parentNode, 'selected');
                  //get data
                  if (!callback) {
                	  callback = YAHOO.ats.viewCallback;
                  }
                  YAHOO.ats.sendGetRequest(action, callback);
              }
          });
          //
          return menuBar;
    };

//=======================================================================
//calendar.js
//=======================================================================
    /**
     * set calendar(s) - if any
     */
    YAHOO.ats.calendar.init = function(root) {
        var ifFormat = '%d-%b-%Y';
        //all input elements whose names begin with 'f_date_'. This is a job for the 'starts with' ('^=') operator:
        var nodes = YAHOO.util.Selector.query('input[id^=f_date_]', root);
        for (var i = 0; i != nodes.length; i++) {
            var node = nodes[i];
            var inputFieldId = node.id;
            Calendar.setup({
                inputField  : inputFieldId,
                //eventName   : 'dblclick',
                ifFormat    : ifFormat,
                firstDay    : '0', //Sunday
                //showsTime   : true,
                //timeFormat  : '24',
                //align       : 'Br',
                //singleClick : false,
                cache       : true,
                onUpdate    : function(cal) {
                    var inputFieldId = cal.inputField;
                    var time = cal.date.getTime();
                    var date = new Date(time);
                    var dateField = YAHOO.util.Dom.get(inputFieldId);
                    if (dateField) {
                        dateField.value = date.print(ifFormat);
                    }
                    //var timeField = YAHOO.util.Dom.get('f_time');
                    //if (timeField) {
                    //    timeField.value = date.print('%H:%M');
                    //}
                }
            });
            YAHOO.util.Event.addListener(inputFieldId, 'click', function(e) {
                YAHOO.util.Dom.setStyle(window.calendar.element, 'z-index', 199);
            });
        }
    };
//    Calendar.setup({
//        flat           : 'calDiv',  // ID of the parent element
//        flatCallback   : function(cal) {
//            if (cal.dateClicked) {
//                //a date was clicked
//                //var date = cal.date;
//            }
//        },
//        firstDay       : '0', //Sunday
//        dateStatusFunc : function(date, year, month, day) {
//            return false; // other dates are enabled
//            //return true; // we want to disable other dates
//        }
//    });

//=======================================================================
//loader
//=======================================================================
    var loader = new YAHOO.util.YUILoader({ 
        base: 'js/yui/', 
        require: ['animation','autocomplete','button','connection','container','datasource','datatable','editor','element','get','history','json','menu','paginator','reset-fonts-grids','resize','selector'], 
        ignore: [],
        loadOptional: false,
        //Combine YUI files into a single request (per file type) by using the Yahoo! CDN combo service.
        //combine: true,
        //filter: 'DEBUG',
        filter: YAHOO.ats.debug ? 'RAW' : 'MIN',
        allowRollup: true,
        skin: {
            base: 'assets/skins/',
            defaultSkin: 'sam'
        },
        onSuccess: function() {
            var Dom = YAHOO.util.Dom,
                Event = YAHOO.util.Event;

            //Use the DD shim on all DD objects
            YAHOO.util.DDM.useShim = true;

            // save title
            YAHOO.ats.title = document.title;

            YAHOO.util.Get.script('js/module/common/table.js', {
                onSuccess: function(scriptData) {
                    if (!YAHOO.ats.debug) {
                        scriptData.purge(); //removes the script node immediately after executing
                    }
                },
                onFailure: function(oResponse) {
                    YAHOO.ats.alert('Failure to load table.js', oResponse);
                }
            });

            //Error message: "A script on this page is causing Internet Explorer to run slowly" (http://support.microsoft.com/kb/175500)
            //http://ajaxian.com/archives/no-more-ie6-background-flicker
            if (YAHOO.env.ua.ie == 6) {
                try {
                    document.execCommand('BackgroundImageCache', false, true);
                } catch (ignore) {
                    // just in case this fails .. ?
                }
            }

            /*
             * Subscribe to global listeners
             * var oResponse = args[0];
             */
            var cleanupGlobalEvents = function(type, args) {
                var oResponse = args[0];
                if (oResponse && oResponse.argument) {
                    oResponse.argument = null;
                }
                YAHOO.ats.progress(false);
            };
            var globalEvents = {
                start: function(type, args){
                    YAHOO.ats.progress(true, true);
                },
                complete: function(type, args){
                    cleanupGlobalEvents(type, args);
                },
                success: function(type, args){
                    cleanupGlobalEvents(type, args);
                },
                failure: function(type, args){
                    cleanupGlobalEvents(type, args);
                },
                abort: function(type, args){
                    cleanupGlobalEvents(type, args);
                }
            };
            YAHOO.util.Connect.startEvent.subscribe(globalEvents.start);
            YAHOO.util.Connect.completeEvent.subscribe(globalEvents.complete);
            YAHOO.util.Connect.successEvent.subscribe(globalEvents.success);
            YAHOO.util.Connect.failureEvent.subscribe(globalEvents.failure);
            YAHOO.util.Connect.abortEvent.subscribe(globalEvents.abort);

//=======================================================================
//history.js
//=======================================================================
            // Update the UI of ats module according to the 'state' parameter
            function loadState(oState) {
                // update title (last parameter)
                var idx = oState.indexOf('title=');
                var pageTitle = idx < 0 ? '' : oState.substring(idx + 6);
                document.title = YAHOO.ats.title + (pageTitle ? ' :: ' + pageTitle : '');
                //check for callback (not default)
                var callback = oState.callback ? oState.callback : YAHOO.ats.viewCallback;
                // first request to load page is always GET
                YAHOO.ats.sendGetRequest(oState, callback);
            };
            function reload() {
                var currentState = YAHOO.util.History.getCurrentState(YAHOO.ats.module);
                loadState(currentState); // the latest history entry
            };

            // Use the Browser History Manager onReady method to initialize the application.
            YAHOO.util.History.onReady(function () {
                reload();
            });

            /**
             * 
             */
            YAHOO.ats.navigate = function(action, title) {
                var state = YAHOO.util.History.getQueryStringParameter('state', action) || action;
                state += (state.indexOf('?') < 0 ? '?' : '&') + 'title=' + title;
                YAHOO.util.History.navigate(YAHOO.ats.module, state);
            };

            YAHOO.ats.initState = function() {
                // Register our only module. Module registration MUST take place
                // BEFORE calling initializing the browser history management library!
                // If there is no bookmarked state, assign the default state:
                var menus = YAHOO.util.Selector.query('#mainMenu ul li a');
                var firstMenu = menus[0];
                if (firstMenu) {
                    var initialState = YAHOO.util.History.getBookmarkedState(YAHOO.ats.module)
    	                || YAHOO.util.History.getQueryStringParameter('state')
    	                || firstMenu.getAttribute('href') + '?title=' + firstMenu.title;
    	            YAHOO.util.History.register(YAHOO.ats.module, initialState, function (oState) {
    	                // This is called after calling YAHOO.util.History.navigate, or after the user has triggered the back/forward button.
    	                // We cannot distinguish between these two situations.
    	                loadState(oState); // the earliest history entry
    	            });
                }

                // Initialize the browser history management library.
                YAHOO.util.History.initialize('yui-history-field', 'yui-history-iframe');
            };

//=======================================================================
//menu.js
//=======================================================================
            //Setup the click listeners on the menu list
            Event.on('menu', 'click', function(ev) {
                var tar = Event.getTarget(ev);
                // <span> tag inside <a> tag for image
                if (tar.tagName.toLowerCase() == 'span') {
                    tar = tar.parentNode;
                }
                if (tar.tagName.toLowerCase() != 'a') {
                    tar = null;
                }
                //Make sure we have a link
                if (tar && YAHOO.util.Selector.test(tar, '#menu span a')) {
                    Event.stopEvent(ev);
                    var title = tar.title;
                    var action = tar.getAttribute('href');
                    //
                    Dom.removeClass(YAHOO.util.Selector.query('#menu span'), 'selected');
                    Dom.addClass(tar.parentNode, 'selected');
                    //
                    YAHOO.ats.navigate(action, title);
                }
            });

//=======================================================================
//wait.js
//=======================================================================
            //Initialize the temporary Panel to display while waiting for external content to load
            YAHOO.ats.waitPanel = new YAHOO.widget.Panel('waitDiv', {
//                width: '180px', 
                fixedcenter: true, 
                close: false, 
                draggable: false, 
                underlay: 'none',
                zindex: 9998,
                modal: true,
                visible: false
            });
//            YAHOO.ats.waitPanel.setHeader('Loading, please wait...');
//            YAHOO.ats.waitPanel.setBody('<img src="img/progress2.gif" />');
            YAHOO.ats.waitPanel.render(document.body);

//=======================================================================
//alert.js
//=======================================================================
            //Create a SimpleDialog used to mimic an OS dialog
            var alertPanel = new YAHOO.widget.SimpleDialog('alertDiv', {
                fixedcenter: true,
                visible: false,
                modal: true,
                zindex: 299,
                width: '300px',
                height: '200px',
                autofillheight: 'body',
                constraintoviewport: true, 
                icon: YAHOO.widget.SimpleDialog.ICON_WARN,
                buttons: [
                    {
                        text: 'OK',
                        handler: function() {
                            YAHOO.ats.cancel(this);
                            //SC_PROXY_AUTHENTICATION_REQUIRED = 407
                            if (this.status == 407) {
                            	YAHOO.ats.login();
                            }
                        },
                        isDefault: true
                    }
                ]
            });

            //will hide a Panel when the escape key is pressed
            var alertPanelEscapeKey = new YAHOO.util.KeyListener(document, { keys: 27 },                              
                { fn: function() {YAHOO.ats.cancel(this);}, scope: alertPanel, correctScope: true } );
            alertPanel.cfg.queueProperty('keylisteners', alertPanelEscapeKey);

            //resizable by the right side (t, r, b, l, tr, tl, br, bl) or (all)
            var alertPanelResize = new YAHOO.util.Resize('alertDiv', {
                handles: ['br'], //Defaults to: ['r', 'b', 'br']
                //knobHandles: true,
                autoRatio: false,
                width: (YAHOO.util.Dom.getViewportWidth() / 4 * 3 + 'px'),
                height: (YAHOO.util.Dom.getViewportHeight() / 4 * 3 + 'px'),
                minWidth: 300,
                minHeight: 100,
                status: false
            });
            /*
            Dragging the handle will now resize the outer containing DIV of the panel.
            Since we want to keep the contents of the panel in sync with the new dimensions of the containing DIV,
            we listen for the resize event fired by the Resize instance.
            In the listener, we set the Panel's height configuration property to match the new pixel height of the containing DIV.
            This will result in the body element, which we specified in the autofillheight property for the Panel,
            being resized to fill out the height of the containing DIV. The width is handled automatically by the browser,
            with the header, body and footer DIVs filling out their containing element.
            Setting the height configuration property, will also result in the iframe shim and shadow being resized
            to match the new dimensions of the containing DIV if required for the browser (IE6 and IE7 quirks mode).
             */
            alertPanelResize.on('resize', function(args) {
                var panelHeight = args.height;
                this.cfg.setProperty('height', panelHeight + 'px');
            }, alertPanel, true);
            /*
             We also setup a listener for the startResize event, which we use to setup the constraints
             for the height and width of the resized element, if the panel's constraintoviewport value is set to true.
             */
            // Setup startResize handler, to constrain the resize width/height
            // if the constraintoviewport configuration property is enabled.
            alertPanelResize.on('startResize', function(args) {
                if (this.cfg.getProperty('constraintoviewport')) {
                    var clientRegion = Dom.getClientRegion();
                    var elRegion = Dom.getRegion(this.element);
                    alertPanelResize.set('maxWidth', clientRegion.right - elRegion.left - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                    alertPanelResize.set('maxHeight', clientRegion.bottom - elRegion.top - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                } else {
                    alertPanelResize.set('maxWidth', null);
                    alertPanelResize.set('maxHeight', null);
                }
            }, alertPanel, true);

            //Set the header
            alertPanel.setHeader('Alert');
            //Give the body something to render with
            alertPanel.setBody('Notta');
            //Render the Dialog to the body
            alertPanel.render(document.body);
            //save
            YAHOO.ats.alertPanel = alertPanel;

            //Create a alert dialog
            YAHOO.ats.alert = function(str, oResponse) {
                var panel = YAHOO.ats.alertPanel;
                panel.status = oResponse.status;
                //Set the body to the string passed
                //SC_PROXY_AUTHENTICATION_REQUIRED = 407
                if (panel.status == 407) {
                    panel.setBody('<b>' + oResponse.responseText + '</b>'); // login page
                } else {
                    panel.setBody(str + '<br/>Status: ' + oResponse.status + '<br/>' + oResponse.responseText);
                }
                //Set an icon
                panel.cfg.setProperty('icon', YAHOO.widget.SimpleDialog.ICON_WARN);
                //Bring the dialog to the top
                panel.bringToTop();
                //Show it
                panel.show();
            };

//=======================================================================
//edit.js
//=======================================================================
            //wrapped as xml for multipart, see
            //http://old.nabble.com/jquery.form-fails-to-handle-JSON-responses-with-iframe-submit-td22245770s27240.html
            function processResponse(response) {
                var index1 = Math.max(response.indexOf('<pre>'), response.indexOf('<PRE>'));
                var index2 = Math.max(response.indexOf('</pre>'), response.indexOf('</PRE>'));
                if (index1 >= 0 && index2 > 0) {
                    response = response.substring(index1 + 5, index2);
                }
                return response;
            };
            
            YAHOO.ats.submitCallback = {
                cache:false,
                upload: function(oResponse) {
                    var root = null;
                    YAHOO.ats.hideErrors(root);
                    //http://developer.yahoo.com/yui/connection/#upload
                    var response = oResponse.responseText;
                    response = processResponse(response);
                    try {
                        //check response markup for errors (json)
                        var data = YAHOO.ats.parseJsonData(response);
                        //only errors data form validation are expected here (mandatory)
                        if (data.errors) {
                            //failure:
                            YAHOO.ats.showErrors(data.errors, root);
                        } else if (data.actions) {
                            //success
                            this._onSuccess(oResponse, data);
                            YAHOO.ats.cancel(YAHOO.ats.dialogPanel);
                        }
                    } catch (e) {
                        //invalid json format (all exception has to be wrapped as errors json)
                        //YAHOO.ats.failure(oResponse);
                        YAHOO.ats.alert('submitCallback:upload', oResponse);
                        //YAHOO.ats.cancel(YAHOO.ats.dialogPanel);
                    }
                },
                success: function(oResponse) {
                    var response = oResponse.responseText;
                    response = processResponse(response);
                    try {
                        var data = YAHOO.ats.parseJsonData(response);
                        this._onSuccess(oResponse, data);
                    } catch (e) {
                        //invalid json format (all exception has to be wrapped as errors json)
                        YAHOO.ats.alert('submitCallback:success', oResponse);
                    }
                    YAHOO.ats.cancel(YAHOO.ats.dialogPanel);
                },
                failure: function(oResponse) {
                    var root = null;
                    YAHOO.ats.hideErrors(root);
                    var response = oResponse.responseText;
                    response = processResponse(response);
                    try {
                        var data = YAHOO.ats.parseJsonData(response);
                        //only errors data form validation are expected here (mandatory)
                        YAHOO.ats.showErrors(data.errors, root);
                    } catch (e) {
                        //invalid json format (all exception has to be wrapped as errors json)
                        YAHOO.ats.alert('submitCallback:failure', oResponse);
                    }
                },
                _onSuccess: function(oResponse, data) {
                    //check for callback (not default)
                    var args = oResponse.argument;
                    //oResponse.argument = null; // remove from response

                    var callback = /*args && args.callback ? args.callback : */YAHOO.ats.viewCallback;
                    var method = args && args.method ? args.method : 'GET';
                    //only actions data (eg redirect) are expected here (mandatory)
                    var redirect = data.actions.redirect;
                    if (!redirect && args) {
                        redirect = args.redirect;
                    }
                    if (!redirect) {
                        reload();
                    } else {
                        if (redirect == 'none') {
                            callback.success(oResponse);
                        } else {
                            if (method == 'POST') {
                                var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                                YAHOO.ats.sendPostRequest(form, redirect, callback);
                            } else {
                                YAHOO.ats.sendGetRequest(redirect, callback);
                            }
                        }
                    }
                }
                //,argument: {action:'', title:'edit'}
                //,timeout: 10000
            };

            YAHOO.ats.dialogSubmitCallback = {
                cache:false,
                upload: function(oResponse) {
                    var root = YAHOO.util.Dom.get('editDiv');
                    YAHOO.ats.hideErrors(root);
                    //http://developer.yahoo.com/yui/connection/#upload
                    var response = oResponse.responseText;
                    response = processResponse(response);
                    try {
                        //check response markup for errors (json)
                        var data = YAHOO.ats.parseJsonData(response);
                        //only errors data form validation are expected here (mandatory)
                        if (data.errors) {
                            //failure:
                            var errors = data.errors;
                            YAHOO.ats.showErrors(data.errors, root);
                        } else if (data.actions) {
                            //success
                            this._onSuccess(oResponse, data);
                            YAHOO.ats.cancel(YAHOO.ats.dialogPanel);
                        }
                    } catch (e) {
                        //invalid json format (all exception has to be wrapped as errors json)
                        //YAHOO.ats.failure(oResponse);
                        YAHOO.ats.alert('dialogSubmitCallback:upload', oResponse);
                        //YAHOO.ats.cancel(YAHOO.ats.dialogPanel);
                    }
                },
                success: function(oResponse) {
                    var response = oResponse.responseText;
                    response = processResponse(response);
                    try {
                        var data = YAHOO.ats.parseJsonData(response);
                        this._onSuccess(oResponse, data);
                    } catch (e) {
                        //invalid json format (all exception has to be wrapped as errors json)
                        YAHOO.ats.alert('dialogSubmitCallback:success', oResponse);
                    }
                    YAHOO.ats.cancel(YAHOO.ats.dialogPanel);
                },
                failure: function(oResponse) {
                    var root = YAHOO.util.Dom.get('editDiv');
                    YAHOO.ats.hideErrors(root);
                    var response = oResponse.responseText;
                    response = processResponse(response);
                    try {
                        var data = YAHOO.ats.parseJsonData(response);
                        //only errors data form validation are expected here (mandatory)
                        var errors = data.errors;
                        YAHOO.ats.showErrors(data.errors, root);
                    } catch (e) {
                        //invalid json format (all exception has to be wrapped as errors json)
                        YAHOO.ats.alert('dialogSubmitCallback:failure', oResponse);
                    }
                },
                _onSuccess: function(oResponse, data) {
                    //check for callback (not default)
                    var args = oResponse.argument;
                    //oResponse.argument = null; // remove from response

                    var callback = args && args.callback ? args.callback : YAHOO.ats.viewCallback;
                    var method = args && args.method ? args.method : 'GET';
                    //only actions data (eg redirect) are expected here (mandatory)
                    var redirect = data.actions.redirect;
                    if (!redirect && args) {
                        redirect = args.redirect;
                    }
                    if (!redirect) {
                        reload();
                    } else {
                        if (redirect == 'none') {
                            callback.success(oResponse);
                        } else {
                            if (method == 'POST') {
                                var form = YAHOO.util.Dom.get(YAHOO.ats.form);
                                YAHOO.ats.sendPostRequest(form, redirect, callback);
                            } else {
                                YAHOO.ats.sendGetRequest(redirect, callback);
                            }
                        }
                    }
                }
                //,argument: {action:'', title:'edit'}
                //,timeout: 10000
            };
            //Create a SimpleDialog used to mimic an OS dialog
            var editPanel = new YAHOO.widget.SimpleDialog('editDiv', {
                close: true,
                draggable: true,
                fixedcenter: true,
                visible: false,
                modal: true,
                zindex: 297,
                width: '600px',
                height: '450px',
                autofillheight: 'body',
                constraintoviewport: true,
                context: ['showbtn', 'tl', 'bl'],
                buttons: [
                   { text: 'Submit', isDefault: true, handler: function() {
                       var action = this.form.action;
                       var callback = YAHOO.ats.dialogSubmitCallback;
                       callback.argument = this.argument;
                       YAHOO.ats.sendPostRequest(this.form, action, callback);
                   } },
                   { text: 'Cancel', handler: function() {
                       YAHOO.ats.cancel(this);
                   } }
                ]
            });

            //will hide a Panel when the escape key is pressed
            var editPanelEscapeKey = new YAHOO.util.KeyListener(document, { keys: 27 },                              
                { fn: function() {YAHOO.ats.cancel(this);}, scope: editPanel, correctScope: true } );
            editPanel.cfg.queueProperty('keylisteners', editPanelEscapeKey);

            //resizable by the right side (t, r, b, l, tr, tl, br, bl) or (all)
            var editPanelResize = new YAHOO.util.Resize('editDiv', {
                handles: ['br'], //Defaults to: ['r', 'b', 'br']
                //knobHandles: true,
                autoRatio: false,
                minWidth: 300,
                minHeight: 100,
                status: false
            });
            /*
            Dragging the handle will now resize the outer containing DIV of the panel.
            Since we want to keep the contents of the panel in sync with the new dimensions of the containing DIV,
            we listen for the resize event fired by the Resize instance.
            In the listener, we set the Panel's height configuration property to match the new pixel height of the containing DIV.
            This will result in the body element, which we specified in the autofillheight property for the Panel,
            being resized to fill out the height of the containing DIV. The width is handled automatically by the browser,
            with the header, body and footer DIVs filling out their containing element.
            Setting the height configuration property, will also result in the iframe shim and shadow being resized
            to match the new dimensions of the containing DIV if required for the browser (IE6 and IE7 quirks mode).
             */
            editPanelResize.on('resize', function(args) {
                var panelHeight = args.height;
                this.cfg.setProperty('height', panelHeight + 'px');
            }, editPanel, true);
            /*
             We also setup a listener for the startResize event, which we use to setup the constraints
             for the height and width of the resized element, if the panel's constraintoviewport value is set to true.
             */
            // Setup startResize handler, to constrain the resize width/height
            // if the constraintoviewport configuration property is enabled.
            editPanelResize.on('startResize', function(args) {
                if (this.cfg.getProperty('constraintoviewport')) {
                    var clientRegion = Dom.getClientRegion();
                    var elRegion = Dom.getRegion(this.element);
                    editPanelResize.set('maxWidth', clientRegion.right - elRegion.left - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                    editPanelResize.set('maxHeight', clientRegion.bottom - elRegion.top - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
                } else {
                    editPanelResize.set('maxWidth', null);
                    editPanelResize.set('maxHeight', null);
                }
            }, editPanel, true);

            //Set the header
            editPanel.setHeader('Dialog');
            //Give the body something to render with
            editPanel.setBody('Notta');
            //Render the Dialog to the body
            editPanel.render(document.body);
            //save
            YAHOO.ats.dialogPanel = editPanel;

//=======================================================================
// RUN ON PAGE LOAD
//=======================================================================
            Event.onDOMReady(function() {
                // finally - load default css (will override any css set before)
                YAHOO.util.Get.css('css/default.css', {
                    onSuccess: function(cssData) {
                        //
                        Dom.setStyle(document.body, 'visibility', 'visible');
                        // 1). menu initialisation
                        YAHOO.ats.menuCallback = {
                            cache: false,
                            success: function(oResponse) {
                                try {
                                    var data = YAHOO.ats.parseJsonData(oResponse.responseText);
                                    var mainMenuBar = YAHOO.ats.initMenu('mainMenuDiv', 'mainMenu', data.menuItems);
                                    // 2). history
                                    YAHOO.ats.initState();
                                } catch (e) {
                                    YAHOO.ats.alert('Invalid menu JSON data: ' + e, oResponse);
                                }
                            },
                            failure: function(oResponse) {
                                YAHOO.ats.failure(oResponse);
                            }
                        };
                        YAHOO.ats.sendGetRequest('main.do?dispatch=find', YAHOO.ats.menuCallback);
                    }
                });
            });
//=======================================================================
// RUN ON PAGE LOAD
//=======================================================================
        }
    });

    //Open dialog
    YAHOO.ats.dialogCallback = {
        cache:false,
        success: function(oResponse) {
            var args = oResponse.argument;
            oResponse.argument = null; // remove from response

            var panel = YAHOO.ats.dialogPanel;

            // argument (if any) - store it in panel (hack)
            panel.argument = args; //eg callback, or {redirect:someurl,..}

            //Set the header to the string passed
            if (args && args.title) {
                panel.setHeader(args.title);
            }

            //Set the body to the string passed
            panel.setBody(oResponse.responseText);

            //Render the Dialog to the body
            panel.render(document.body);

            //execute javascript (if any)
            YAHOO.ats.loadScripts(panel.body);

            //hide/show submit button (edit - show, view - hide)
            var noSubmit = !panel.form.action;
            panel.cfg.config.buttons.value[0].disabled = noSubmit;
            //submitButton.setStyle('visibility', noSubmit ? 'hidden' : 'visible');

            //update save button
            var submitBtn = panel.cfg.config.buttons.value[0].htmlButton.firstChild.firstChild;
            var submitCfg = YAHOO.util.Dom.get('submit');
            if (submitCfg) {
                submitBtn.innerHTML = submitCfg.title;
            } else {
                submitBtn.innerHTML = 'Submit';
            }
            //update cancel button
            var cancelBtn = panel.cfg.config.buttons.value[1].htmlButton.firstChild.firstChild;
            var cancelCfg = YAHOO.util.Dom.get('cancel');
            if (cancelCfg) {
                cancelBtn.innerHTML = cancelCfg.title;
            } else {
                cancelBtn.innerHTML = 'Cancel';
            }

            //Bring the dialog to the top
            panel.bringToTop();
            //Show it
            panel.show();
        },
        failure: function(oResponse) {
            YAHOO.ats.failure(oResponse);
        }
        //,argument: {action:'', title:''}
        //, timeout: 10000
    };

    /**
     * 
     */
    YAHOO.ats.openDialog = function(action, title, w, h, args) {
        var panel = YAHOO.ats.dialogPanel;
        //Set the header to the string passed
        if (title) {
            panel.setHeader(title);
        }
        if (w) {
            panel.cfg.setProperty('width', w + 'px');
        }
        if (h) {
            panel.cfg.setProperty('height', h + 'px');
        }
        //set argument (if any)
        YAHOO.ats.dialogCallback.argument = args;
        YAHOO.ats.sendGetRequest(action, YAHOO.ats.dialogCallback);
    };

    /**
     * Cleanup panel
     */
    YAHOO.ats.cancel = function(panel) {
        //var panel = YAHOO.ats.dialogPanel;
        panel.argument = null;
        panel.setBody('Notta');
        panel.cancel();
    };

    /**
     * 
     */
    YAHOO.ats.downloadCallback = {
        cache:false,
        success: function(oResponse) {
            //http://msdn.microsoft.com/en-us/library/ms536651%28VS.85%29.aspx
            var cfg = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,scrollbars=yes,width=400,height=300';
            var openWindow = window.open('', 'DownloadWindow', cfg);
            openWindow.document.write(oResponse.responseText);
            openWindow.document.close();
        },
        failure: function(oResponse) {
            YAHOO.ats.failure(oResponse);
        }
        //, timeout: 10000
    };

    /**
     * send download request (eg download document).
     */
    YAHOO.ats.sendDownloadRequest = function(oAction, oForm) {
        //check for default form
        if (!oForm) {
            oForm = YAHOO.util.Dom.get(YAHOO.ats.form);
        }
        //no form, just plain download
        if (!oForm) {
            var cfg = 'toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,scrollbars=yes,width=400,height=300';
            var openWindow = window.open(oAction, 'DownloadWindow', cfg);
            //YAHOO.ats.sendGetRequest(oAction, YAHOO.ats.downloadCallback);
            return;
        }

        //save original values
        var target = oForm.getAttribute('target');
        var method = oForm.getAttribute('method');
        var action = oForm.getAttribute('action');
        //set new values
        oForm.setAttribute('target', 'DownloadWindow');
        oForm.setAttribute('method', 'POST');
        oForm.setAttribute('action', oAction);
        //submit
        oForm.submit();
        //restore original values
        if (target) oForm.setAttribute('target', target); else oForm.removeAttribute('target');
        if (method) oForm.setAttribute('method', method); else oForm.removeAttribute('method');
        if (action) oForm.setAttribute('action', action); else oForm.removeAttribute('action');

/*
        //extract form parameters
        var params = '';
        for (var i = 0; oForm && i != oForm.elements.length; i++) {
            var p = oForm.elements[i];
            if (params.length != 0) params += '&';
            if (p.name && p.value) {
                if (p.type == 'radio' && !p.checked) {
                    continue;
                }
                if (p.type == 'checkbox' && !p.checked) {
                    continue;
                }
                params += p.name + '=' + p.value;
            }
        }
        var cfg = 'toolbar=no,location=no,directories=no,status=no,menubar=no,width=200,height=150';
        //var pop = 
        window.open(oAction + (oAction.indexOf('?') > 0 ? '' : '?') + params, 'Download', cfg);
*/
/*
        pop.document.write('<script language="javascript">');
        pop.document.write('setTimeout("self.close();",5000)');
        pop.document.write('</script>');
        pop.document.write('<body bgcolor=ltgreen>');
        pop.document.write('<center><b><h2>Welcome</h2></b></center>');
        pop.document.write('<center><b><h3>Please Wait... While This Page Loads</h3></b></center>');
        pop.document.write('<center><b><h6>This Window Will Close Itself...</h6></b></center>');
        pop.document.write('</body>');
*/
/*
        var downloadForm = document.createElement('downloadForm');
        downloadForm.setAttribute('method', 'POST');
        //downloadForm.setAttribute('action', oAction);
        // setting downloadForm target to a window named 'formresult'
        downloadForm.setAttribute('target', 'downloadResult');
        for (var i = 0; oForm && i != oForm.elements.length; i++) {
            var p = oForm.elements[i];
            if (p.name && p.value) {
                if (p.type == 'checkbox' && !p.checked) {
                    continue;
                }
                if (p.type == 'radio' && !p.checked) {
                    continue;
                }
                var hiddenField = document.createElement('input');              
                hiddenField.setAttribute('name', p.name);
                hiddenField.setAttribute('value', p.value);
                downloadForm.appendChild(hiddenField);
            }
        }
        document.body.appendChild(downloadForm);
        var cfg = 'toolbar=no,location=no,directories=no,status=no,menubar=no,width=200,height=150';
        // creating the 'downloadResult' window with custom features prior to submitting the downloadForm
        window.open(oAction, 'downloadResult', cfg);
        downloadForm.submit();
*/        
        //YAHOO.ats.sendPostRequest(oForm, oAction, YAHOO.ats.downloadCallback);
    };

    /**
     * send download request (eg download document).
     */
    YAHOO.ats.downloadReport = function(auditId, documentId, oForm) {
        YAHOO.ats.sendDownloadRequest('document/download.do?dispatch=report&auditId=' + auditId + '&documentId=' + documentId, oForm);
    };

    /**
     * send download request (eg download document).
     */
    YAHOO.ats.downloadComment = function(commentId, documentId, oForm) {
        YAHOO.ats.sendDownloadRequest('document/download.do?dispatch=comment&commentId=' + commentId + '&documentId=' + documentId, oForm);
    };

    /**
     * @param actionId
     * @param oRedirect - optional ('none' or 'some_valid_url')
     * @param oCallback - optional
     * @param oMethod - optional ('GET' or 'POST')
     */
    YAHOO.ats.addComment = function(actionId, oRedirect, oCallback, oMethod) {
        var args = {redirect:oRedirect,callback:oCallback,method:oMethod};
        //var viewport = [YAHOO.util.Dom.getViewportWidth(),YAHOO.util.Dom.getViewportHeight()];
        YAHOO.ats.openDialog('commentAdmin/edit.do?actionId=' + actionId, 'Add Comment',
            YAHOO.util.Dom.getViewportWidth() / 4 * 3,
            YAHOO.util.Dom.getViewportHeight() / 4 * 3,
            args);
    };

    /**
     * send GET request for update
     * used to represent view/edit form.
     */
    YAHOO.ats.sendGetRequest = function(action, callback) {
        if (!callback) {
            callback = YAHOO.ats.dialogCallback;
        }
        YAHOO.ats.asyncRequest('GET', action, callback); 
    };

    /**
     * send POST request for update
     * used to represent edit form.
     */
    YAHOO.ats.sendPostRequest = function(form, action, callback) {
        //if (!form) {
        //    form = YAHOO.util.Dom.get(YAHOO.ats.form);
        //}
        if (form) {
            //handle multi-part
            if (YAHOO.ats.isMultipart(form)) {
                //YAHOO.log('File Upload: ' + file.value, 'info', 'edit.js');
                // http://developer.yahoo.com/yui/connection/#file
                // the second argument is true to indicate file upload
                // the third argument is set true to have Connection Manager set the iframe source to 'javascript:false'
                YAHOO.util.Connect.setForm(form, true, true);
            } else {
                YAHOO.util.Connect.setForm(form);
            }
            YAHOO.ats.fixIEFormHeader();
        }
        //
        if (!callback) {
            callback = YAHOO.ats.dialogCallback;
        }
        //POST to keep user entered data
        YAHOO.ats.asyncRequest('POST', action, callback); 
    };

    /**
     * 
     */
    YAHOO.ats.asyncRequest = function(method, action, callback, postData) {
        if (!callback) {
            callback = YAHOO.ats.emptyCallback;
        }
        return YAHOO.util.Connect.asyncRequest(method, action, callback, postData);
    };

    /**
     * 
     */
    YAHOO.ats.isMultipart = function(form) {
        var multipart = YAHOO.util.Selector.test(form, 'form[enctype=multipart/form-data]');
        var files = multipart ? YAHOO.util.Selector.query('input[type=file][id^=attachment]', form) : null;
        var file = files && files.length != 0 ? files[0] : null;
        return multipart && file && file.value;
    };

    YAHOO.ats.fixIEFormHeader = function() {
        if (YAHOO.env.ua.ie == 6) {
            //YAHOO.util.Connect.initHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
            YAHOO.util.Connect._default_form_header = 'application/x-www-form-urlencoded; charset=UTF-8';
            //YAHOO.util.Connect.setDefaultXhrHeader('application/x-www-form-urlencoded; charset=UTF-8');
        }
    };

    //Load the files using the insert() method.
    loader.insert(); 
})(); 